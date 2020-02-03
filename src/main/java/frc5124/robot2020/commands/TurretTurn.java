package frc5124.robot2020.commands;

import java.lang.module.ModuleDescriptor.Requires;
import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Robot;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.subsystems.Intake;
import frc5124.robot2020.subsystems.Turret;

public class TurretTurn implements Command {

    private final Turret turret;

    public TurretTurn(Turret subsystem) {
        turret = subsystem;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        turret.turnTurretPos();
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