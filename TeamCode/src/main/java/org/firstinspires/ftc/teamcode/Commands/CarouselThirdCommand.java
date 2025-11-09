package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;

public class CarouselThirdCommand extends CommandBase {
    private final Carousel CarouselSubsystem;
    public CarouselThirdCommand(Carousel subsystem) {
        CarouselSubsystem = subsystem;
        addRequirements(CarouselSubsystem);
    }

    public void execute() {

        CarouselSubsystem.rotateCarouselThird();
    }

    public void end(boolean isFinished) {

        CarouselSubsystem.stopCarousel();
    }

}
