/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Shooter;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Loader;

public class RPMbyFF extends CommandBase {

  private Shooter shooter;
  private double rpm;
  private Loader loader;

  /**
   * Creates a new RPMbyFF.
   */
  public RPMbyFF(Shooter shooter, Loader loader, double rpm) {
    this.shooter = shooter;
    this.rpm = rpm;
    this.loader = loader;


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    loader.ballIntaked(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = shooter.getVelocity() - rpm;
    double comp = -1.2e-5 * Math.pow(error,3);
    if (Math.abs(comp) > 0.2) {
      comp = Math.signum(comp) * 2;
    }
    shooter.directVolts(0.147 + 0.0015538 * rpm );
    
    if (shooter.atSpeed()) {
      shooter.currentWatch(RobotMap.ShooterMap.lineShootRPM);
    }
    if (shooter.getVelocity() >= rpm-50 && loader.getAppliedOutput() == 0) {
      loader.runBelt(1);
      shooter.atSpeed(true);
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.directPower(0);
    shooter.atSpeed(false);
    loader.stopBelt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
