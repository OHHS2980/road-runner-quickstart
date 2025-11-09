package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.OneSpecificFolderForPoses.Poses;
import org.firstinspires.ftc.teamcode.OpModes.AutoTest;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;

public class DriveToPark extends CommandBase {

    public Drive drive;

    public MecanumDrive mecanum;
    public DriveToPark(Drive drive, HardwareMap hardwareMap)
    {

        this.drive = drive;
        Pose2d startPose = Poses.pose;

        mecanum = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder moveToPark = mecanum.actionBuilder(new Pose2d(-12, 12,315))
                .turnTo(270)
                .lineToX(-60);
    }

    public void execute()
    {

        Actions.runBlocking(
                new SequentialAction(
                        moveToPark()
                )
        );
    }


    public void end(boolean interrupted)
    {

    }
}
