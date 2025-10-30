package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;

public class ReverseCarouselCommand extends CommandBase {

    private final Carousel CarouselSubsystem;

    public ReverseCarouselCommand(Carousel subsystem) {
        CarouselSubsystem = subsystem;
        addRequirements(CarouselSubsystem);
    }

    public void initialize() {
        CarouselSubsystem.startCarousel(-0.5);
    }

    public void end(boolean interrupted)
    {
        CarouselSubsystem.stopCarousel();
    }
}
