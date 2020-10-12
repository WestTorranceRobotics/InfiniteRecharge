package frc5124.robot2020.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Intake;

public class ToggleIntakePivot extends CommandBase {

    private final Intake intake;

    public ToggleIntakePivot(Intake subsystem) {
        intake = subsystem;
        addRequirements(intake);
    }
    
    @Override
    public void initialize(){
        intake.setDeployed(!intake.isDeployed());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }
}
