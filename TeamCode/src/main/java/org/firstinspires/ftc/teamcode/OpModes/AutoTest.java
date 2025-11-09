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
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@Autonomous(name = "nico has no hoes")
public class AutoTest extends LinearOpMode {

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

    public class Carousel {
        private DcMotor carouselMotor;

        public double motorCPR = 28 * 5 * 4;

        public Carousel() {
            carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        }

        public class shoots implements Action {
            boolean initialized = false;
            PIDController pidController = new PIDController(0,0,0);

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    carouselMotor.setPower(0.8);
                    initialized = true;
                }
                //double pos = carouselMotor.getCurrentPosition();



                carouselMotor.setPower(
                    pidController.calculate(
                        carouselMotor.getCurrentPosition(), carouselMotor.getCurrentPosition()) + motorCPR / 3
                    )
                );

                if (pidController.getPositionError() < 1)
                {
                    return true;
                }
                else
                {
                    carouselMotor.setPower(0);
                    initialized = false;
                    return false;
                }
                //if (pos > ) {
                //    return true;
            }
        }
        public Action Shoots() {
            return new shoots();
        }
    }

    @Override
    public void runOpMode()
    {
        Pose2d startPose = new Pose2d(new Vector2d(-48, 48), Math.toRadians(315));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder backup = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-12,12), Math.toRadians(135));

        TrajectoryActionBuilder turnMove = drive.actionBuilder(new Pose2d(-12, 12,315))
                .turnTo(270)
                .lineToX(-60);

        waitForStart();

        Outtake outtake = new Outtake();
        Carousel carousel = new Carousel();

        Actions.runBlocking(
                new SequentialAction(
                        outtake.StartOuttake(),
                        backup.build(),
                        carousel.Shoots(),
                        new SleepAction(1),
                        carousel.Shoots(),
                        new SleepAction(1),
                        carousel.Shoots(),
                        new SleepAction(1),
                        turnMove.build()
                )
        );
                        if then ( equalsTo==else{)pidCONTROL:(
                                -x---){
            final x Class (fi5 i) towardsE
        {
                                    {
                {    X  |  O  |  O
                -----------------------
                     X  |      | O
                  -----------------------
                     O  | O O O| OO OO OO OOO OOO O


                    {
                        true()
                    } False false( );
                        }} { } { }
            }}
        }
    }
}
