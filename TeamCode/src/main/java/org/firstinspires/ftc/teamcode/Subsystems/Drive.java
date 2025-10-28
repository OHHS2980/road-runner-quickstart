package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive extends SubsystemBase {
    private DcMotor front_left  = null;
    private DcMotor front_right = null;
    private DcMotor back_left   = null;
    private DcMotor back_right  = null;

    private GamepadEx gamepad;

    public MecanumDrive mecanum;

    public Drive(final HardwareMap hMap, GamepadEx gamepad) {
        front_left   = hMap.get(DcMotor.class, "front_left");
        front_right  = hMap.get(DcMotor.class, "front_right");
        back_left    = hMap.get(DcMotor.class, "back_left");
        back_right   = hMap.get(DcMotor.class, "back_right");

        this.gamepad = gamepad;
    }
    @Override
    public void periodic()
    {
        mecanum.driveRobotCentric(
                gamepad.getLeftX(),
                gamepad.getLeftY(),
                gamepad.getRightY()
        );
    }



}
