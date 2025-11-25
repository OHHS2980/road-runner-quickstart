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

@Autonomous(name = "Red Back Auto")
public class RedBackAuto extends LinearOpMode {


    // basically all the subsystem code
// also puts code into roadrunner's "actions" which are like commands in teleop
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
            private double kP = 0.012;
            private double kI = 0.15;
            private double kD = 0;
            public double motorCPR = 28 * 5.23 * 3.61;
            double target;
            public rotate(int Direction)
            {
                direction = Direction;
                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (initialized == false)
                {
                    carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    pidController = new PIDController(kP, kI, kD);
                    target = //carouselMotor.getCurrentPosition() +
                            ((motorCPR / 6) * direction);
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


                if (Math.abs(target - carouselMotor.getCurrentPosition()) < 5)
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
    // this is where the bulk of auto is coded
    @Override
    public void runOpMode()
    {
        // creates "trajectories" which are like actions but specific to the drivebase
        // roadrunner has the ability to take in coordinates which relate to the field and calculate a way for the robot to go there.
        Pose2d startPose = new Pose2d(new Vector2d(24, -64), Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder forward = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(12,12), Math.toRadians(90));

        TrajectoryActionBuilder turn = drive.actionBuilder(new Pose2d(12, 12,Math.toRadians(90)))
                .turnTo(Math.toRadians(80));

        waitForStart();

        Outtake outtake = new Outtake();
        Intake intake = new Intake();
        Carousel carousel = new Carousel();

        Actions.runBlocking(
                new SequentialAction(
                        outtake.StartOuttake(),
                        forward.build(),
                        new SleepAction(0.5),
                        turn.build(),
                        new SleepAction(1),
                        carousel.Rotate(-1),
                        new SleepAction(1),
                        carousel.Rotate(-1),
                        new SleepAction(1),
                        carousel.Rotate(-1),
                        new SleepAction(1)
                )
        );

    }
}
