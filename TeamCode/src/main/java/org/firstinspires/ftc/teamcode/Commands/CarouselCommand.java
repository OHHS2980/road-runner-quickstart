package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;

public class CarouselCommand extends CommandBase {

    private final Carousel CarouselSubsystem;

    public CarouselCommand(Carousel subsystem) {
        CarouselSubsystem = subsystem;
        addRequirements(CarouselSubsystem);
    }

    public void execute() {
        CarouselSubsystem.startCarousel();
    }

    public void end() {
        CarouselSubsystem.stopCarousel();
    }
}
