/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc5124.robot2020.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateTurret extends CommandBase {
  private Turret turret;
  private Boolean clockwise = false;
  private double lastDegrees = 0;
  /**
   * Creates a new RotateTurret.
   * @param power Useable if limit not reached. Suggest moving by units (not coded yet)
   */
  public RotateTurret(Turret subsystem, Boolean clockwise) {
    turret = subsystem;
    addRequirements(turret);
    this.clockwise = clockwise;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Need encoder position limits to finish coding
  @Override
  public void execute() {
    if (clockwise && !turret.isAutomatic()) {
      turret.directPower(-.2);
      // lastDegrees = lastDegrees + .02 * 10;
      // lastDegrees = (lastDegrees + 33) % 360 - 33;
     // turret.setTurretDegrees(lastDegrees);
    } else if (!clockwise && !turret.isAutomatic()) {
      turret.directPower(.2);
      // lastDegrees = lastDegrees + .2 * 10;
      // lastDegrees = (lastDegrees + 33) % 360 - 33;
      //turret.setTurretDegrees(lastDegrees);
    } 

    

    SmartDashboard.putNumber("TurretDegrees", turret.getDegrees());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.directPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}