/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2021.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2021.commands.turret.TurretZeroPosition;
import frc5124.robot2021.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

public class TurnUntilTargeted extends CommandBase {
  private Turret turret;
  private boolean isDone = false;
  private double degrees = 0;
  /**
   * Creates a new DriveForTime.
   */
  public TurnUntilTargeted(Turret subsystem, double degrees) {
    turret = subsystem;
    this.degrees = degrees;
    addRequirements(subsystem);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(0.0);
  }

  @Override
  public void initialize() {
    super.initialize();
    turret.setCoast();
    turret.directPower(-.5);
  }

  @Override
  public void execute() {
    super.execute();
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) != 0) {
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