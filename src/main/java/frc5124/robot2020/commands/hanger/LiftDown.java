package frc5124.robot2020.commands.hanger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Hanger;

public class LiftDown extends CommandBase {

    private final Hanger m_hanger;

    public LiftDown(Hanger subsystem) {
        m_hanger = subsystem;
        addRequirements(m_hanger);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() { 
        m_hanger.liftDown();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() { 
        if ((m_hanger.getPosition() <= RobotMap.HangerMap.lowerLimit)) {
            m_hanger.setNoPower();
          } 
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