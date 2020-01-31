package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.DriveTrain;
import frc5124.robot2020.subsystems.Loader;
import frc5124.robot2020.subsystems.Shooter;

public class AutonomousCommand implements Command {

    private DriveTrain driveTrain;
    private Loader loader;
    private Shooter shooter;


    public AutonomousCommand(DriveTrain driveTrain, Loader loader, Shooter shooter) {
        this.driveTrain =  driveTrain;
        this.loader = loader;
        this.shooter = shooter;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        
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

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of();
    }
}
