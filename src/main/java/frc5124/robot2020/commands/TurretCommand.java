
package frc5124.robot2020.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

/**
 *
 */
public class TurretCommand extends CommandBase {
    private final Turret m_turret_subsystem;

    public TurretCommand(Turret subsystem) {
        m_turret_subsystem= subsystem;
        addRequirements(m_turret_subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_turret_subsystem.resetEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_turret_subsystem.enable();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        m_turret_subsystem.disable();
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

}
