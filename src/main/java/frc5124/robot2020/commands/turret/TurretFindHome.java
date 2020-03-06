/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;


import edu.wpi.first.wpilibj.AnalogInput;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.Turret;

public class TurretFindHome extends CommandBase {
  private Turret turret;
  private AnalogInput mag = Turret.mag;
  private boolean isDone;


  //in class


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
    turret.disableTurretLimit();
    turret.isHome(false);
    turret.isInitialHome(false);
    if (!(turret.isHome()) && !turret.isInitialHome()) {
     turret.directPower(RobotMap.TurretMap.zeroSpeed);
     turret.setCoast();
  }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // SmartDashboard.putNumber("voltage", mag.getVoltage());
    if (!turret.isHome() && !turret.isInitialHome()) {
    if (mag.getVoltage() < .1) {
      turret.directPower(0);
      turret.resetTurretDegrees();
      turret.isHome(true);
    } 
  }


  if (turret.isHome() && !turret.isInitialHome()) {
    turret.setTurretDegrees(-17);
    turret.isInitialHome(true);
    turret.isHome(false);
  }
    if (turret.getDegrees() <= -17 && turret.isInitialHome() && !turret.isHome()) {
      turret.resetTurretDegrees();
      turret.turretLimitSet();
      turret.setTurretDegrees(0);
      turret.isHome(true);
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