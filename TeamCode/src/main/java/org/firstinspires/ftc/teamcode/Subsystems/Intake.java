package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// this sets up the intake subsystem to be used in teleop.

// first, define the motors in this subsystem:
public class Intake extends SubsystemBase {

    DcMotor intakeMotor;

    public Intake(final HardwareMap hMap, final String IMotor) {
        intakeMotor = hMap.get(DcMotor.class, "intakeMotor"); //motor

    }
    // below are what java calls methods
    // they are fed into commands, which are later called by opmodes
    public void startIntake(double power) {
        intakeMotor.setPower(power);
    }

    public void stopIntake()
    {
        intakeMotor.setPower(0);
    }
}