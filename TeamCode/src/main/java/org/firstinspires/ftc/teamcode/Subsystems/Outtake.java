// note: thorin says that the flywheel will not use encoders for this comp

package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake extends SubsystemBase {

    DcMotor outtakeMotorA;
    DcMotor outtakeMotorB;

    public Outtake(final HardwareMap hMap, final String OMotor) {
        outtakeMotorA = hMap.get(DcMotor.class, "outtakeMotorA"); //motor
        outtakeMotorB = hMap.get(DcMotor.class, "outtakeMotorB"); //motor

        outtakeMotorA.setPower(1);
        outtakeMotorB.setPower(1);
    }

    public void startOuttake(int power) {
        outtakeMotorA.setPower(power);
        outtakeMotorB.setPower(-power);
    }

    public void stopOuttake() {
        outtakeMotorA.setPower(0);
        outtakeMotorB.setPower(0);
    }
}
