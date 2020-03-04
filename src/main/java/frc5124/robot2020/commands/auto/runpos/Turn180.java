/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runpos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.commands.turret.TurretZeroPosition;
import frc5124.robot2020.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Turn180 extends CommandBase {
  private Turret turret;
  private boolean isDone = false;
  /**
   * Creates a new DriveForTime.
   */
  public Turn180(Turret subsystem) {
    turret = subsystem;
    addRequirements(subsystem);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(0.0);
  }

  @Override
  public void initialize() {
    super.initialize();
    turret.setCoast();
    turret.resetTurretDegrees();
    turret.turretLimitSet();
    turret.setTurretDegrees(-180);
  }

  @Override
  public void execute() {
    super.execute();
    if ( 180 - Math.abs(turret.getDegrees()) < 1) {
      isDone = true;
    }
   
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    turret.setBrake();
  }

  @Override
  public boolean isFinished() {
    return isDone;
  }
}