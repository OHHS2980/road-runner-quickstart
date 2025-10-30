package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive extends SubsystemBase {
    private Motor front_left  = null;
    private Motor front_right = null;
    private Motor back_left   = null;
    private Motor back_right  = null;
    private final GamepadEx gamepad;
    public MecanumDrive mecanum;
    public Drive(final HardwareMap hMap, GamepadEx givenGamepad) {
        register();
        front_left   = new Motor(hMap, "front_left");
        front_right  = new Motor(hMap, "front_right");
        back_left    = new Motor(hMap, "back_left");
        back_right   = new Motor(hMap, "back_right");
        mecanum = new MecanumDrive(
                front_left,
                back_right, // backright
                back_left,
                front_right
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
