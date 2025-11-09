package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Carousel extends SubsystemBase {
    public DcMotor carouselMotor;

    Carousel.PID pidController;
    double encoder = 28 * 4 * 5; //4 and 5 from the gearboxes
    public Carousel(final HardwareMap hMap, final String CMotor) {
        carouselMotor = hMap.get(DcMotor.class, "carouselMotor"); //motor

        pidController = new Carousel.PID();
    }

    public class PID {
        float kP = 0;
        float kI = 0;
        float kD = 0;
        float kF = 0;

        float target = 0;
        float error = 0;
        float accumulatedError = 0;
        float lastError = 0;

        ElapsedTime timeElapsed = new ElapsedTime();

        public float doPID() {
            error = target - carouselMotor.getCurrentPosition();

            accumulatedError = accumulatedError + error;
            float out = (float) ((kP*error) + // P
                                (kI*accumulatedError) + // I
                                kD*( (error - lastError)/timeElapsed.seconds() )); //D
            timeElapsed.reset();
            return out;

        }


    }

    public class autoShoot implements Action {
        int initialEncoderPosition = carouselMotor.getCurrentPosition();
        @Override
        public boolean run(@NonNull TelemetryPacket telemetry)
        {
            carouselMotor.setPower(-1);

            if (carouselMotor.getCurrentPosition() - initialEncoderPosition > 100)
            {
                carouselMotor.setPower(0);
                return true;
            }
            return false;
        }
    }

    public Action AutoShoot() {
        return new autoShoot();
    }
    public DcMotor getCarouselMotor()
    {
        return carouselMotor;
    }

    public void startCarousel(double power) {
        carouselMotor.setPower(power);
    }

    public void reverseCarousel() {
        carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carouselMotor.setTargetPosition((int)encoder/10);
        carouselMotor.setPower(-0.2);
        carouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rotateCarouselThird()
    {
        float target = (float) (carouselMotor.getCurrentPosition() + (encoder / 3));
        carouselMotor.setPower(pidController.doPID());
    }
    public void rotateCarouselSection(double power) {
        carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carouselMotor.setTargetPosition(carouselMotor.getCurrentPosition() + (int)encoder/3);
        //carouselMotor.setPower(power);
        carouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*public void reverseCarouselSection(double power) {
        carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carouselMotor.setTargetPosition((int) encoder / 3);
        carouselMotor.setPower(power);
        carouselMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }*/

    public void stopCarousel() {
        carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        carouselMotor.setPower(0);
    }
}
