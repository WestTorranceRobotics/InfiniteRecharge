package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Intake;

public class OuttakeBall extends CommandBase {

    private final Intake m_intake;

    public OuttakeBall(Intake subsystem) {
        m_intake = subsystem;
        addRequirements(m_intake);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_intake.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_intake.out();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_intake.stop();
    }
}