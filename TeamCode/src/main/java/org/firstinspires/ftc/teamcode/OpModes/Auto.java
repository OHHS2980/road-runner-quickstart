package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@Autonomous
public class Auto extends OpMode {

    @Override
    public void init() {

        Drive drive = new Drive(hardwareMap);
        Intake intake = new Intake(hardwareMap, "intakeMotor");
        Outtake outtake =  new Outtake(hardwareMap, "outtakeMotor");
        Carousel carousel = new Carousel(hardwareMap, "carouselMotor");
    }

    @Override
    public void loop() {

    }
}
