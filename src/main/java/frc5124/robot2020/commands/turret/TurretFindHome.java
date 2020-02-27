/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

public class TurretFindHome extends CommandBase {
  private Turret turret;
  private CANPIDController turretPID;
  private boolean isDone = false;
  private AnalogInput mag = new AnalogInput(3);
  private boolean switchAround = false;
  private double currentDegrees = 0;

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
    if (!(turret.setHome())) {
     turret.directPower(.1);
     SmartDashboard.putNumber("yeet", 0);
     SmartDashboard.updateValues();
  }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!(turret.setHome())) {
    if (mag.getVoltage() < .1) {
      turret.directPower(0);
      turret.resetTurretDegrees();
      turret.turretLimitSet();
      turret.setTurretDegrees(-11);
      if (turret.getAppliedOutput() == 0)
      {
        isDone=true;
        turret.resetTurretDegrees();
      }
      
   // }
    
      // if (turret.setHome()) {
      // currentDegrees = turret.getDegrees();
      // if (currentDegrees > 270) {
      //   switchAround = true;
      //   turret.rightLimitReached(true);
      //   turret.setTurretDegrees(-19);
      // }
      // else if (currentDegrees < -16) {
      //   switchAround = true;
      //   turret.leftLimitReached(true);
      //   turret.setTurretDegrees(265);
      // }
      // if (switchAround && turret.rightLimitReached()) {
      //   if (currentDegrees < -15) {
      //     switchAround = false;
      //     turret.rightLimitReached(false);
      //   }
      // } else if (switchAround && turret.leftLimitReached()) {
      //   if (currentDegrees > 265) {
      //     switchAround = false;
      //     turret.leftLimitReached(false);
      //   }
     // } 
      }
  }
    


    
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putNumber("yeet", 1);
    SmartDashboard.updateValues();
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (isDone) {
      turret.setHome(true);
      return true;
    } else {
      return false;
    }
  }
}