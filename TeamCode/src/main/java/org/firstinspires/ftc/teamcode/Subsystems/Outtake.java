package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// this sets up the outtake subsystem to be used in teleop.

// first, define the motors in this subsystem:
public class Outtake extends SubsystemBase {

    DcMotor outtakeMotorA;
    DcMotor outtakeMotorB;

    public Outtake(final HardwareMap hMap, final String OMotor) {
        outtakeMotorA = hMap.get(DcMotor.class, "outtakeMotorA"); //motor
        outtakeMotorB = hMap.get(DcMotor.class, "outtakeMotorB"); //motor

        outtakeMotorA.setPower(1);
        outtakeMotorB.setPower(1);
    }

    // below are what java calls methods
    // they are fed into commands, which are later called by opmodes

    // setting a power to negative makes the motor run backward!
    public void startOuttake(int power) {
        outtakeMotorA.setPower(power);
        outtakeMotorB.setPower(-power);
    }

    public void stopOuttake() {
        outtakeMotorA.setPower(0);
        outtakeMotorB.setPower(0);
    }
}
