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
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretTargetByPIDPerpetually extends CommandBase {
  private Turret subsystem;
  /**
   * Creates a new TurretTargetByPIDPerpetually.
   */
  public TurretTargetByPIDPerpetually(Turret subsystem) {
  
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    NetworkTableInstance.getDefault().getTable("rpi").getEntry("aimbot").setDouble(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0.0);
    subsystem.enableTurretPID();
    subsystem.setCoast();
    subsystem.isAutomatic(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double target = subsystem.getDegrees() - 
      NetworkTableInstance.getDefault().getTable("limelight")
      .getEntry("tx").getDouble(0);
      subsystem.setTurretDegrees(target);
  // SmartDashboard.putNumber("Deg", subsystem.getDegrees());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.isAutomatic(false);
    NetworkTableInstance.getDefault().getTable("rpi").getEntry("aimbot").setDouble(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1.0);
    subsystem.setBrake();
    //subsystem.setTurretDegrees(0);
   // NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}