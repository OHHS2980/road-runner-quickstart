package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class AutoDrive extends SubsystemBase {
    public MecanumDrive mecanum;

    public AutoDrive(final HardwareMap hMap, Pose2d initialPose) {


        mecanum = new MecanumDrive(hMap, initialPose);
    }
    @Override
    public void periodic()
    {

    }



}
