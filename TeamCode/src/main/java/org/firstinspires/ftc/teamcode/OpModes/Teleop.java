package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Commands.CarouselCommand;
import org.firstinspires.ftc.teamcode.Commands.EmergencyShoot;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseCarouselCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
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
        Intake intake = new Intake(hardwareMap, "intakeMotor"); //motor
        Outtake outtake =  new Outtake(hardwareMap, "outtakeMotor"); //motor
        Carousel carousel = new Carousel(hardwareMap, "carouselMotor"); //motor

        //DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        //intakeMotor.setPower(1);

        Button A = new GamepadButton(driveOp, GamepadKeys.Button.A);
        Button B = new GamepadButton(driveOp, GamepadKeys.Button.B);
        Button rT = new GamepadButton(driveOp, GamepadKeys.Button.LEFT_BUMPER);
        Button X = new GamepadButton(driveOp, GamepadKeys.Button.X);
        Button Y = new GamepadButton(driveOp, GamepadKeys.Button.Y);
        Button lT = new GamepadButton(driveOp, GamepadKeys.Button.RIGHT_BUMPER);

        B.whenHeld(new IntakeCommand(intake));
        A.whenHeld(new ReverseIntakeCommand(intake));
        lT.whenHeld(new CarouselCommand(carousel));
        rT.whenHeld(new ReverseCarouselCommand(carousel));
        X.whenPressed(new EmergencyShoot(outtake, 1));
        Y.whenPressed(new EmergencyShoot(outtake, 0));

    }

    @Override
    public void loop() {
       CommandScheduler.getInstance().run();
   }
}
