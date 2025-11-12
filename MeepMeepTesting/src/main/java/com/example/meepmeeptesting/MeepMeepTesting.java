package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-48, 48, Math.toRadians(135)))
                        .lineTo(new Vector2d(-12,12))
                        .turn(Math.toRadians(45))
                        .lineTo(new Vector2d(-32, 12))
                        .waitSeconds(0.5)
                        .lineTo(new Vector2d(-37,12))
                        .waitSeconds(0.5)
                        .lineTo(new Vector2d(-42,12))
                        .waitSeconds(0.5)
                        .lineTo(new Vector2d(-47,12))
                        .lineTo(new Vector2d(-12, 12))
                        .turn(Math.toRadians(-45))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.GRID_BLUE)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}