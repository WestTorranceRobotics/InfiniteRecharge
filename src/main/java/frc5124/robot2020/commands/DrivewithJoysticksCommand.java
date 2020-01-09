
package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc5124.robot2020.subsystems.DriveTrain;

/**
 *
 */
public class DrivewithJoysticksCommand extends CommandBase {

    private final DriveTrain m_drivetrain_subsystem;
    private final XboxController m_gPad;

    public DrivewithJoysticksCommand(XboxController gPad, DriveTrain subsystem) {
        m_drivetrain_subsystem= subsystem;
        m_gPad = gPad;
        addRequirements(m_drivetrain_subsystem);
    }

    // Called just before this Command runs the first time
	@Override
	public void initialize() {
	}

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        double leftValue = m_gPad.getY(Hand.kLeft);
        double rightValue = m_gPad.getY(Hand.kRight);

        m_drivetrain_subsystem.tankDrive(leftValue, rightValue);
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
