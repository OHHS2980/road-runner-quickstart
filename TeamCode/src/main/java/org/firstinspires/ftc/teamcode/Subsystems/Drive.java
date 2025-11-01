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
        front_left   = new Motor(hMap, "frontLeft");
        front_right   = new Motor(hMap, "frontRight");
        back_left    = new Motor(hMap, "backLeft");
        back_right  = new Motor(hMap, "backRight");

        front_left.set(0.25);
        back_right.set(0.25);

        //front_right.setInverted(true);
        ////back_left.setInverted(true);


        mecanum = new MecanumDrive(
                front_left,
                back_left, //frontRight
                back_right, //backLeft
                front_right //backRight
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
