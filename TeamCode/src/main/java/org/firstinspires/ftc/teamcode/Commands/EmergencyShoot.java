package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Carousel;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class EmergencyShoot extends CommandBase {

    private final Outtake OuttakeSubsystem;

    private final int on;

    public EmergencyShoot(Outtake subsystem, int on) {
        OuttakeSubsystem = subsystem;
        addRequirements(OuttakeSubsystem);
        this.on = on;
    }

    public void initialize() {
        OuttakeSubsystem.startOuttake(on);
    }

    //public void end(boolean interrupted) {OuttakeSubsystem.stopOuttake();}

}

