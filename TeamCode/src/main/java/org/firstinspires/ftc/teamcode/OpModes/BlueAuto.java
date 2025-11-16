package org.firstinspires.ftc.teamcode.OpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "Blue Auto")
public class BlueAuto extends LinearOpMode {



    public class Outtake {
        private DcMotor outtakeMotorA;
        private DcMotor outtakeMotorB;

        public Outtake() {
            outtakeMotorA = hardwareMap.get(DcMotor.class, "outtakeMotorA");
            outtakeMotorB = hardwareMap.get(DcMotor.class, "outtakeMotorB");
        }

        public class startOuttake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                outtakeMotorA.setPower(1);
                outtakeMotorB.setPower(-1);
                return false;
            }
        }
        public Action StartOuttake() {
            return new startOuttake();
        }
    }

    public class Intake {
        private DcMotor intakeMotor;


        public Intake() {
            intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        }

        public class startIntake implements Action {

            double intakePower;
            public startIntake(double IntakePower) {
                intakePower = IntakePower;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                intakeMotor.setPower(intakePower);
                return false;
            }
        }
        public Action StartIntake(double IntakePower) {
            return new startIntake(IntakePower);
        }
    }
    public class Carousel {
        private DcMotor carouselMotor;

        public Carousel() {
            carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        }
        public class rotate implements Action {

            private PIDController pidController;
            public double direction;
            private double kP = 0.0025;
            private double kI = 0;
            private double kD = 0;
            public double motorCPR = 28 * 5.23 * 3.61;
            double target;
            public rotate(int Direction)
            {
                direction = Direction;
                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (initialized == false)
                {
                    carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    pidController = new PIDController(kP, kI, kD);
                    target = carouselMotor.getCurrentPosition() + ((motorCPR / 3) * direction);
                    pidController.reset();
                    pidController.setSetPoint(target);
                    initialized = true;
                }

                double output = pidController.calculate(carouselMotor.getCurrentPosition());
                carouselMotor.setPower(output);

                telemetry.addData("power", carouselMotor.getPower());
                telemetry.addData("target", target);
                telemetry.addData("current", carouselMotor.getCurrentPosition());
                telemetry.update();


                if (Math.abs(target - carouselMotor.getCurrentPosition()) < 4)
                {
                    carouselMotor.setPower(0);
                    return false;
                }
                else
                {
                    return true;
                }

                //if (pos > ) {
                //    return true;
            }
        }
        public Action Rotate(int Direction) {
            return new rotate(Direction);
        }
    }

    @Override
    public void runOpMode()
    {
        Pose2d startPose = new Pose2d(new Vector2d(-48, 48), Math.toRadians(135));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder backup = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-12,12), Math.toRadians(135));

        TrajectoryActionBuilder turnMove = drive.actionBuilder(new Pose2d(-12, 12,Math.toRadians(135)))
                .turnTo(Math.toRadians(180))
                .lineToX(-32);

        TrajectoryActionBuilder move1 = drive.actionBuilder(new Pose2d(-32, 12, Math.toRadians(180)))
                .lineToX(-37);

        TrajectoryActionBuilder move2 = drive.actionBuilder(new Pose2d(-37, 12, Math.toRadians(180)))
                .lineToX(-42);

        TrajectoryActionBuilder move3 = drive.actionBuilder(new Pose2d(-42, 12, Math.toRadians(180)))
                .lineToX(-47);

        TrajectoryActionBuilder move4 = drive.actionBuilder(new Pose2d(-47, 12, Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(-12,12), Math.toRadians(135));


        waitForStart();

        Outtake outtake = new Outtake();
        Intake intake = new Intake();
        Carousel carousel = new Carousel();

        Actions.runBlocking(
                new SequentialAction(
                        outtake.StartOuttake(),
                        backup.build(),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        turnMove.build(),
                        intake.StartIntake(1),
                        new SleepAction(0.5),
                        move1.build(),
                        new SleepAction(0.5),
                        carousel.Rotate(1),
                        new SleepAction(0.5),
                        move2.build(),
                        new SleepAction(0.5),
                        carousel.Rotate(1),
                        new SleepAction(0.5),
                        move3.build(),
                        new SleepAction(0.5),
                        carousel.Rotate(1),
                        new SleepAction(0.5),
                        intake.StartIntake(0),
                        move4.build(),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        new SleepAction(0.5)
                )
        );

    }
}
