package frc5124.robot2020.commands.hanger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Hanger;

public class GoToMiddleLevel extends CommandBase {

    private final Hanger m_hanger;

    public GoToMiddleLevel(Hanger hanger) {
        m_hanger = hanger;
        addRequirements(m_hanger);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() { 
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_hanger.runToMiddleHeight();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_hanger.setNoPower();
    }
}