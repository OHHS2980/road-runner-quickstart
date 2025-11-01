package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Carousel extends SubsystemBase {
    public DcMotor carouselMotor;
    double encoder = 28;
    public Carousel(final HardwareMap hMap, final String CMotor) {
        carouselMotor = hMap.get(DcMotor.class, "carouselMotor"); //motor
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


    public void stopCarousel() {
        carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        carouselMotor.setPower(0);
    }
}
