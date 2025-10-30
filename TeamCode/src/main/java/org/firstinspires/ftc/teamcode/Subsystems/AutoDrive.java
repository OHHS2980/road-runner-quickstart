package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class AutoDrive extends SubsystemBase {
    private DcMotor front_left  = null;
    private DcMotor front_right = null;
    private DcMotor back_left   = null;
    private DcMotor back_right  = null;
    public MecanumDrive mecanum;

    public AutoDrive(final HardwareMap hMap, Pose2d initialPose) {
        front_left   = hMap.get(DcMotor.class, "front_left");
        front_right  = hMap.get(DcMotor.class, "front_right");
        back_left    = hMap.get(DcMotor.class, "back_left");
        back_right   = hMap.get(DcMotor.class, "back_right");

        mecanum = new MecanumDrive(hMap, initialPose);
    }
    @Override
    public void periodic()
    {

    }



}
