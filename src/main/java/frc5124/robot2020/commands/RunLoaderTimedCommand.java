
package frc5124.robot2020.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc5124.robot2020.Robot;

/**
 *
 */
public class RunLoaderTimedCommand extends TimedCommand {

    public RunLoaderTimedCommand() {
        this(0);
    }

    public RunLoaderTimedCommand(double timeout) {
        super(timeout);
        requires(Robot.loader);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
