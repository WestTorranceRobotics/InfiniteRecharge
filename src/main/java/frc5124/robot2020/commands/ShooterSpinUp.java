/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.*;
/**
 *
 */
public class ShooterSpinUp extends CommandBase {
    private final Shooter m_shooter;


    public ShooterSpinUp(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_shooter.spinUp();
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
        m_shooter.spinDown();
    }

}
