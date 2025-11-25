package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

// this sets up the drive subsystem to be used in teleop.

// first, define the motors, gamepad, and type of drivebase in this subsystem:
public class Drive extends SubsystemBase {
    private Motor front_left  = null;
    private Motor front_right = null;
    private Motor back_left   = null;
    private Motor back_right  = null;
    private final GamepadEx gamepad;
    public MecanumDrive mecanum;
    public Drive(final HardwareMap hMap, GamepadEx givenGamepad) {
        register();
        front_left   = new Motor(hMap, "leftFront");
        front_right   = new Motor(hMap, "par"); // for parallel odometry wheel
        back_left    = new Motor(hMap, "leftBack");
        back_right  = new Motor(hMap, "perp"); // for perpendicular odometry wheel

        front_right.setInverted(true);
        front_left.setInverted(true);
        back_right.setInverted(true);
        back_left.setInverted(true);

        mecanum = new MecanumDrive(
                front_left,
                front_right, //frontRight backleft
                back_left, //backLeft backright
                back_right //backRight frontright
        );
        this.gamepad = givenGamepad;
    }


    @Override
    public void periodic()
    {

        mecanum.driveRobotCentric(
                gamepad.getLeftX(),
                gamepad.getLeftY(),
                gamepad.getRightX()
        );
    }



}
