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
import frc5124.robot2020.subsystems.LED;
import frc5124.robot2020.RobotMap;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurretTargetByPIDPerpetually extends CommandBase {
  private Turret subsystem;
  private LED led;
  /**
   * Creates a new TurretTargetByPIDPerpetually.
   */
  public TurretTargetByPIDPerpetually(Turret subsystem, LED led) {
    this.led = led;
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

      double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      double angle = ty + RobotMap.limelightAngle;
      double tan = Math.tan(Math.toRadians(angle));
      double dx = (RobotMap.targetHeight - RobotMap.limelightHeight) / tan;
      if (Math.abs(120 - dx) <= 10) {
        led.setLED(LED.Color.lime);
      } else if (Math.abs(206.5 - dx) <= 10) {
        led.setLED(LED.Color.lawnGreen);
      } else {
        led.setLED(led.defaultColor);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.isAutomatic(false);
    NetworkTableInstance.getDefault().getTable("rpi").getEntry("aimbot").setDouble(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1.0);
    subsystem.setBrake();
    led.setLED(led.defaultColor);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}