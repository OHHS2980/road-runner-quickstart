package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class EmergencyShoot extends CommandBase {

    private final Outtake OuttakeSubsystem;

    public EmergencyShoot(Outtake subsystem) {
        OuttakeSubsystem = subsystem;
        addRequirements(OuttakeSubsystem);
    }

    public void initialize() {
        OuttakeSubsystem.startOuttake();
    }

}

