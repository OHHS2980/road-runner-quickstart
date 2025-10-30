package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Carousel extends SubsystemBase {
    DcMotor carouselMotor;
    double encoder = 28;
    public Carousel(final HardwareMap hMap, final String CMotor) {
        carouselMotor = hMap.get(DcMotor.class, "carouselMotor"); //motor
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
