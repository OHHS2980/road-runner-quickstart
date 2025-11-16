package org.firstinspires.ftc.teamcode.OpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.AutoDrive;

@Autonomous
public class RightLeftAuto extends LinearOpMode {

    public DcMotor carouselMotor;



    boolean fullyDone = false;

    int done = 0;


    public IMU imu;

    public DcMotor outtakeMotorA;

    public DcMotor outtakeMotorB;

    private Motor front_left  = null;
    private Motor front_right = null;
    private Motor back_left   = null;
    private Motor back_right  = null;
    public class powerOuttake implements Action {
        public boolean run(@NonNull TelemetryPacket telemetry)
        {
            outtakeMotorA.setPower(1);
            outtakeMotorB.setPower(-1);
            return false;
        }

    }
    public class autoShoot implements Action {
        int initialEncoderPosition = carouselMotor.getCurrentPosition();
        @Override
        public boolean run(@NonNull TelemetryPacket telemetry)
        {

            carouselMotor.setPower(0.45);

            if (Math.abs(carouselMotor.getCurrentPosition() - initialEncoderPosition) > 80)
            {
                carouselMotor.setPower(0);
                sleep(2000);

                done += 1;
                initialEncoderPosition = carouselMotor.getCurrentPosition();

                if (done == 3)
                {
                    fullyDone = true;
                    return false;
                }
            }


            return true;
        }
    }


    public class emergencyTurn implements Action {

        boolean first = true;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetry) {
            if (first == true)
            {
                imu.resetYaw();
                first = false;
            }

            front_left.set(-0.15);
            front_right.set(0.15);
            back_left.set(0.15);
            back_right.set(-0.15);
            if (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > 90) {
                front_left.set(0);


                front_right.set(0);
                back_left.set(0);
                back_right.set(0);
                return false;
            }
            return true;
        }
    }

    public class emergencyMove implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetry)
        {
            front_left.set(-0.5);
            front_right.set(-0.5);
            back_left.set(-0.5);
            back_right.set(-0.5);
            sleep(2300);
            return false;
        }
    }

    public class emergencyMoveFoward implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetry)
        {
            front_left.set(0.5);
            front_right.set(0.5);
            back_left.set(0.5);
            back_right.set(0.5);
            sleep(2300);
            return false;
        }
    }

    public class emergencyStop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetry)
        {
            front_left.set(0);
            front_right.set(0);
            back_left.set(0);
            back_right.set(0);
            return false;
        }
    }
    public Action AutoShoot() {
        return new autoShoot();
    }

    public Action PowerOuttake() {
        return new powerOuttake();
    }

    public Action EmergencyTurn() {
        return new emergencyTurn();
    }
    public Action EmergencyMove() {
        return new emergencyMove();
    }
    public Action EmergencyStop() {
        return new emergencyStop();
    }
    public Action EmergencyMoveFoward() {
        return new emergencyMoveFoward();
    }


    @Override
    public void runOpMode() {

        waitForStart();

        front_left   = new Motor(hardwareMap, "leftFront");
        front_right   = new Motor(hardwareMap, "rightFront");
        back_left    = new Motor(hardwareMap, "leftBack");
        back_right  = new Motor(hardwareMap, "rightBack");
        imu = hardwareMap.get(IMU.class, "imu");

        back_right.setInverted(true);
        front_right.setInverted(true);

        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                                RevHubOrientationOnRobot.UsbFacingDirection.UP
                        )
                )
        );

        carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor"); //motor

        outtakeMotorA = hardwareMap.get(DcMotor.class, "outtakeMotorA"); //motor
        outtakeMotorB = hardwareMap.get(DcMotor.class, "outtakeMotorB"); //motor

        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));

        AutoDrive drive = new AutoDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = drive.mecanum.actionBuilder(initialPose)
                .turn(90);



        while (opModeIsActive()){

            if (fullyDone == false)
            {
                Actions.runBlocking(
                        new SequentialAction(
                                //tab1.build(),
                                PowerOuttake(),
                                EmergencyMove(),
                                EmergencyStop(),
                                AutoShoot()
                        )
                );
            }
        }


        //front_left   = new Motor(hardwareMap, "leftFront");
        //front_right   = new Motor(hardwareMap, "rightFront");
        ///back_left    = new Motor(hardwareMap, "leftBack");
        ///back_right  = new Motor(hardwareMap, "rightBack");

        //front_right.setInverted(true);
        ///back_right.setInverted(true);

        //moveForward();


    }



}

