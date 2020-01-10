
package frc5124.robot2020.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.*;
/**
 *
 */
public class ShootPowerCellCommand extends CommandBase {
    private final Shooter m_shooter_subsystem;

    public ShootPowerCellCommand(Shooter subsystem) {
        m_shooter_subsystem = subsystem;
        addRequirements(m_shooter_subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_shooter_subsystem.runFeeder();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_shooter_subsystem.stopFeeder();
    }

}
