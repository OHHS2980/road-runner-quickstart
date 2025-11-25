package org.firstinspires.ftc.teamcode.accursed;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(new Vector2d(-48,48), Math.toRadians(135));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        waitForStart();

        Action path = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-12,13))
                .build();
    }
}
