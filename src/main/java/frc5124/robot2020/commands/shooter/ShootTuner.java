/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

<<<<<<< HEAD
// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Shooter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc5124.robot2020.subsystems.Loader;

=======
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Shooter;
>>>>>>> 07b78e911f1a03d3ff04daa0d0e90fa06b6875b9


public class ShootTuner extends CommandBase {
  private Shooter m_shooter;

  /**
   * Creates a new setShootVelocity.
   */
  public ShootTuner (Shooter shooter) {
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.startShooter(RobotMap.ShooterMap.lineShootRPM);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  m_shooter.updatePID();
<<<<<<< HEAD
  // SmartDashboard.putNumber("SHOOTVelocity", m_shooter.getVelocity());
  // SmartDashboard.updateValues();
=======
>>>>>>> 07b78e911f1a03d3ff04daa0d0e90fa06b6875b9
  }
  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
<<<<<<< HEAD
    // SmartDashboard.putBoolean("ShooterRunning", false);
=======
>>>>>>> 07b78e911f1a03d3ff04daa0d0e90fa06b6875b9
    m_shooter.stopShooter();
  }
  
}