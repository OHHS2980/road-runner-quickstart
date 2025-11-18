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

@Autonomous(name = "Self Destruct")
public class YellowAuto extends LinearOpMode {



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

        private double intakePower;

        public Intake() {
            intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        }

        public class startIntake implements Action {
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
        private PIDController pid;
        private double kP = 5;
        private double kI = 0;
        private double kD = 0.5;
        public double motorCPR = 28 * 5 * 4;
        public double direction;
        public Carousel() {
            carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        }
        public class rotate implements Action {
            boolean initialized = false;
            PIDController pidController = new PIDController(kP, kI, kD);
            public rotate(double Direction)
            {
                direction = Direction;
            }
            double target = carouselMotor.getCurrentPosition() + motorCPR / 3 * direction;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    carouselMotor.setPower(direction);
                    initialized = true;
                }
                //double pos = carouselMotor.getCurrentPosition();

                carouselMotor.setPower(
                        pidController.calculate(carouselMotor.getCurrentPosition(), target)
                );
                telemetry.addData("speed:", carouselMotor.getPower());

                if (pidController.getPositionError() > 5)
                {
                    return true;
                }
                else
                {
                    carouselMotor.setPower(0);
                    return false;
                }
                //if (pos > ) {
                //    return true;
            }
        }
        public Action Rotate(double Direction) {
            return new rotate(Direction);
        }
    }

    @Override
    public void runOpMode()
    {
        Pose2d startPose = new Pose2d(new Vector2d(48, 48), Math.toRadians(45));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder backup = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(12,12), Math.toRadians(45));

        TrajectoryActionBuilder turnMove = drive.actionBuilder(new Pose2d(12, 12,Math.toRadians(45)))
                .turnTo(Math.toRadians(0))
                .lineToX(32);

        TrajectoryActionBuilder move1 = drive.actionBuilder(new Pose2d(32, 12, Math.toRadians(0)))
                .lineToX(37);

        TrajectoryActionBuilder move2 = drive.actionBuilder(new Pose2d(37, 12, Math.toRadians(0)))
                .lineToX(42);

        TrajectoryActionBuilder move3 = drive.actionBuilder(new Pose2d(42, 12, Math.toRadians(0)))
                .lineToX(47);

        TrajectoryActionBuilder move4 = drive.actionBuilder(new Pose2d(47, 12, Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(12,12), Math.toRadians(45));


        waitForStart();

        Outtake outtake = new Outtake();
        Intake intake = new Intake();
        Carousel carousel = new Carousel();

        Actions.runBlocking(
                new SequentialAction(
                        outtake.StartOuttake(),
                        backup.build(),
                        carousel.Rotate(-1),
                        new SleepAction(0.5),
                        carousel.Rotate(-1),
                        backup.build(),
                        carousel.Rotate(1),
                        backup.build(),
                        carousel.Rotate(0.2),
                        backup.build(),
                        carousel.Rotate(-1),
                        new SleepAction(0.5)
                )
        );
    }
}
