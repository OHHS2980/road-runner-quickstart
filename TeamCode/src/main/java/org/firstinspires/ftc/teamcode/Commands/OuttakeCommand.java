package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeCommand extends CommandBase {
    private final Outtake OuttakeSubsystem;

    public OuttakeCommand(Outtake subsystem) {

        OuttakeSubsystem = subsystem;
    }

    public void execute() {
        OuttakeSubsystem.startOuttake();
    }

    public void end(boolean interrupted) {
        OuttakeSubsystem.stopOuttake();
    }
}