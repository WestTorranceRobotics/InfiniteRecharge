package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.Turret;

public class TurretTurn extends CommandBase {

    private final Turret m_turret;

    public TurretTurn(Turret subsystem) {
        m_turret = subsystem;
        addRequirements(m_turret);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if (!m_turret.isAtTurnLimit()) {
            m_turret.turnTurretPos();
        }
        else {
            m_turret.stop();
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
        m_turret.stop();
    }
}