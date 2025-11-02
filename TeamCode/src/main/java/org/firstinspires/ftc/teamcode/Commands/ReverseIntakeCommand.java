package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class ReverseIntakeCommand extends CommandBase {
    private final Intake IntakeSubsystem;
    public ReverseIntakeCommand(Intake subsystem) {
        IntakeSubsystem = subsystem;
        addRequirements(IntakeSubsystem);
    }

    public void execute()
    {
        IntakeSubsystem.startIntake(-1);
    }

    public void end(boolean isFinished)
    {
        IntakeSubsystem.stopIntake();
    }
}
