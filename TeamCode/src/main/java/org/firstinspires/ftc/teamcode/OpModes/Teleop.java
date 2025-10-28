package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.CarouselCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseCarouselCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;


@TeleOp
public class Teleop extends OpMode {

    public enum state {
    }
    GamepadEx driveOp;

    @Override
    public void init() {

        driveOp = new GamepadEx(gamepad1);

        Drive drive = new Drive(hardwareMap, driveOp);
        Intake intake = new Intake(hardwareMap, "intakeMotor");
        Outtake outtake =  new Outtake(hardwareMap, "outtakeMotor");
        Carousel carousel = new Carousel(hardwareMap, "carouselMotor");

        Button A = new GamepadButton(driveOp, GamepadKeys.Button.A);
        Button B = new GamepadButton(driveOp, GamepadKeys.Button.B);
        Button Y = new GamepadButton(driveOp, GamepadKeys.Button.Y);
        Button X = new GamepadButton(driveOp, GamepadKeys.Button.X);

        A.whenHeld(new IntakeCommand(intake));
        B.whenHeld(new OuttakeCommand(outtake));
        Y.whenHeld(new CarouselCommand(carousel));
        X.whenPressed(new ReverseCarouselCommand(carousel));

    }

    @Override
    public void loop() {
    }
}
