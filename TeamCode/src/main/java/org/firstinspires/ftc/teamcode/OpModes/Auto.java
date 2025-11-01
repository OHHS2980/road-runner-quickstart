package org.firstinspires.ftc.teamcode.OpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.AutoDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@Autonomous
public class Auto extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d initialPose = new Pose2d(new Vector2d(0.1f, 0.1f), new Rotation2d(0d, 0d));

        AutoDrive drive = new AutoDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap, "intakeMotor");
        Outtake outtake =  new Outtake(hardwareMap, "outtakeMotor");
        Carousel carousel = new Carousel(hardwareMap, "carouselMotor");



        TrajectoryActionBuilder tab1 = drive.mecanum.actionBuilder(initialPose)
                .lineToX(20)
                .turn(90);

        Actions.runBlocking(
                new SequentialAction(
                        tab1.build()
                        //Carousel.AutoShoot()

                )
        );
    }



}
