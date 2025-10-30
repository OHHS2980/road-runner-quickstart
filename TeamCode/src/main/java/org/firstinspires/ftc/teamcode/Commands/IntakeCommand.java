package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private final Intake IntakeSubsystem;

    public IntakeCommand(Intake subsystem) {
        IntakeSubsystem = subsystem;
        addRequirements(IntakeSubsystem);
    }

    public void initialize()
    {
        IntakeSubsystem.startIntake();
    }

    public void end(boolean isFinished)
    {
        IntakeSubsystem.stopIntake();
    }
}
