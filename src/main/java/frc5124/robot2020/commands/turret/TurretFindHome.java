/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class TurretFindHome extends CommandBase {
  private Turret turret;
  private AnalogInput mag = new AnalogInput(3);
  private boolean isDone;


  /**
   * Creates a new TurretFindHome.
   */
  public TurretFindHome(Turret subsystem) {
    turret = subsystem;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret.setHome(false);
    turret.initialHome(false);
    if (!(turret.setHome()) && !turret.initialHome()) {
     turret.directPower(.25);
     SmartDashboard.updateValues();
     turret.setCoast();
  }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("voltage", mag.getVoltage());
    if (!turret.setHome() && !turret.initialHome()) {
    if (mag.getVoltage() < .1) {
      turret.directPower(0);
      turret.resetTurretDegrees();
      turret.setHome(true);
    } 
  }


  if (turret.setHome() && !turret.initialHome()) {
    turret.setTurretDegrees(-17);
    turret.initialHome(true);
    turret.setHome(false);
  }
    if (turret.getDegrees() <= -17 && turret.initialHome() && !turret.setHome()) {
      turret.resetTurretDegrees();
      turret.turretLimitSet();
      turret.setTurretDegrees(0);
      turret.setHome(true);
      isDone = true;
    }
  } 

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      turret.setBrake();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      if (isDone) {
        return true;
      } else {
        return false;
      }
    }
  }