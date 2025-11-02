package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {

    DcMotor intakeMotor;

    public Intake(final HardwareMap hMap, final String IMotor) {
        intakeMotor = hMap.get(DcMotor.class, "intakeMotor"); //motor

        //.setPower(1.0);
    }

    public void startIntake(double power) {
        intakeMotor.setPower(power);
    }

    public void stopIntake()
    {
        intakeMotor.setPower(0);
    }
}