package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;


@TeleOp
public class reversePidDebugger extends OpMode {

    public enum state {
    }
    GamepadEx driveOp;

    PIDController pidController;
    private double kP = 0.004;
    private double kI = 0.285;
    private double kD = 0.0015;
    DcMotor carousel;
    DcMotor intake;
    DcMotor outtakeA;
    DcMotor outtakeB;
    public double setPoint = 3;
    public double motorCPR = 28 * 5.23 * 3.61;
    boolean initialized = false;

    @Override
    public void init() {

        driveOp = new GamepadEx(gamepad1);

        carousel = hardwareMap.get(DcMotor.class,"carouselMotor");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        outtakeA = hardwareMap.get(DcMotor.class, "outtakeMotorA"); //motor
        outtakeB = hardwareMap.get(DcMotor.class, "outtakeMotorB");

        pidController = new PIDController(kP, kI, kD);
        pidController.reset();
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidController.setSetPoint(-(motorCPR/setPoint));

        outtakeA.setPower(1);
        outtakeB.setPower(-1);
    }

    @Override
    public void loop() {

        if (gamepad1.xWasPressed()) {
            kP = kP + 0.001;
        } else if (gamepad1.yWasPressed()) { //y
            kI = kI + 0.001;
        } else if (gamepad1.bWasPressed()) { //b
            kD = kD + 0.0001;
        } else if (gamepad1.dpadLeftWasPressed()) { //left
            kP = kP - 0.001;
        } else if (gamepad1.dpadUpWasPressed()) { //up
            kI = kI - 0.001;
        } else if (gamepad1.dpadRightWasPressed()) { //right
            kD = kD - 0.0001;
        }

        if (gamepad1.aWasPressed()) {
            pidController.setSetPoint(-(motorCPR/setPoint));
            initialized = true;
        }

        if (gamepad1.leftBumperWasPressed()) {
            setPoint = setPoint - 0.01;
        } else if (gamepad1.rightBumperWasPressed()) {
            setPoint = setPoint + 0.01;
        }

        if (initialized == true) {
            double target = pidController.calculate(carousel.getCurrentPosition());
            carousel.setPower(target);
            if (Math.abs(pidController.getPositionError()) < 1) {
                carousel.setPower(0);
                pidController.reset();
                carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                pidController.reset();
                carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                initialized = false;
            }
        }

        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);

        telemetry.addData("P: ", pidController.getP());
        telemetry.addData("I: ", pidController.getI());
        telemetry.addData("D: ", pidController.getD());
        telemetry.addData("error: ", pidController.getPositionError());
        telemetry.addData("initialized: ", initialized);
        telemetry.addData("sp: ", pidController.getSetPoint());
        telemetry.addData("sp division: ", setPoint);

        telemetry.update();

    }
}
