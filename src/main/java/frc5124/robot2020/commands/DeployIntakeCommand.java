
package frc5124.robot2020.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;;
import frc5124.robot2020.Robot;
import frc5124.robot2020.subsystems.Intake;


/**
 *
 */
public class DeployIntakeCommand extends CommandBase {

    private final Intake m_intake_subsystem;

    public DeployIntakeCommand(Intake subsystem) {
        m_intake_subsystem= subsystem;
        addRequirements(m_intake_subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_intake_subsystem.deploy();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

}
