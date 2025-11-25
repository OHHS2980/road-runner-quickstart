package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;


@TeleOp
public class pidDebugger extends OpMode {

    public enum state {
    }
    GamepadEx driveOp;

    PIDController pidController;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    public double motorCPR = 28 * 5.23 * 3.61;

    Intake intake;
    Outtake outtake;
    Carousel carousel;

    @Override
    public void init() {

        driveOp = new GamepadEx(gamepad1);

        intake = new Intake(hardwareMap, "intakeMotor"); //motor
        outtake =  new Outtake(hardwareMap, "outtakeMotor"); //motor
        carousel = new Carousel(hardwareMap, "carouselMotor"); //motor

        pidController = new PIDController(kP, kI, kD);
        pidController.reset();
        pidController.setSetPoint(motorCPR/3);
    }

    @Override
    public void loop() {
        boolean initialized = false;

        if (driveOp.wasJustPressed(GamepadKeys.Button.X)) {
            kP = kP + 0.001;
        } else if (driveOp.wasJustPressed(GamepadKeys.Button.Y)) {
            kI = kI + 0.001;
        } else if (driveOp.wasJustPressed(GamepadKeys.Button.B)) {
            kD = kD + 0.001;
        } else if (driveOp.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            kP = kP - 0.001;
        } else if (driveOp.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            kI = kI - 0.001;
        } else if (driveOp.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
            kD = kD - 0.001;
        }


        if (driveOp.wasJustPressed(GamepadKeys.Button.A)) {
            initialized = true;
        }

        if (initialized = true) {
            double target = pidController.calculate(carousel.carouselMotor.getCurrentPosition());
            carousel.carouselMotor.setPower(target);
            if (pidController.getPositionError() < 1) {
                carousel.carouselMotor.setPower(0);
            }
        }

        telemetry.addData("P: ", pidController.getP());
        telemetry.addData("I: ", pidController.getI());
        telemetry.addData("D: ", pidController.getP());
        telemetry.addData("error: ", pidController.getPositionError());
        telemetry.update();

    }
}
