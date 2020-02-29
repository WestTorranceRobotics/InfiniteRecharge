/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class TurretTargetByPID extends CommandBase {
  private Turret subsystem;
  /**
   * Creates a new TurretTargetByPID.
   */
  public TurretTargetByPID(Turret subsystem) {
    this.subsystem = subsystem;
  }

  public void initialize() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("trackNow").setBoolean(true);
    double target = subsystem.getDegrees() - 
        NetworkTableInstance.getDefault().getTable("limelight")
        .getEntry("tx").getDouble(0);
    subsystem.setTurretDegrees(target);
  }

  @Override
  public void end(boolean interrupted) {
    new Thread(() -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException ex) {
        return;
      }
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("trackNow").setBoolean(false);
    }).start();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}