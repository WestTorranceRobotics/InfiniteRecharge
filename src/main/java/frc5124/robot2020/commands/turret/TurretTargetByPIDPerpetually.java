/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class TurretTargetByPIDPerpetually extends CommandBase {
  private Turret subsystem;
  /**
   * Creates a new TurretTargetByPIDPerpetually.
   */
  public TurretTargetByPIDPerpetually(Turret subsystem) {
  
    this.subsystem = subsystem;
    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(0.0);
    subsystem.enableTurretPID();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double target = subsystem.getDegrees() - 
      NetworkTableInstance.getDefault().getTable("limelight")
      .getEntry("tx").getDouble(0);
  subsystem.setTurretDegrees(target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //subsystem.setTurretDegrees(0);
    new Thread(() -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException ex) {
        return;
      }
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("trackNow").setBoolean(false);
    }).start();
   // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}