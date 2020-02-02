/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Robot;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.controller.*;


/**
 * 
 * WARNING 
 * Untuned
 * 
 * 
 * 
 */
public class Shooter implements Subsystem {
  private double kOut = 0;
  private double currentVelocity = 0;
  private boolean run = true;
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.Shooter.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.Shooter.shootLeaderCanID, MotorType.kBrushless);
  private PIDController shootControl = new PIDController(RobotMap.Shooter.Kp, RobotMap.Shooter.Ki, RobotMap.Shooter.Kd, RobotMap.Shooter.period);

  
  public Shooter() {
    shootMotorFollower.follow(shootMotorLeader);
    
    
  }

  @Override
  public void periodic() {
  }

  public void runWheel(boolean run) {
    this.run = run;
  }
 

  /**
   * Must be called in execute
   */
  public void holdVelocity (double targetVelocity) {
  kPI(targetVelocity);
  setPower(kOut); //kOut is the kPI output
  }

  /**
   * @deprecated
   */
  public void directPower (double power) {
    if (run) {
  setPower(power);
  } else {
    setPower(0);
  }
  }

  /**
   * lightweight PI loop
   */
  private void kPI(double targetVelocity) {
    getVelocity();
    kOut = shootControl.calculate(currentVelocity, targetVelocity);
     if (targetVelocity== 0) {
       return;
     }
  
    kOut = kOut + RobotMap.Shooter.Kf ; 
  }

/**
 * Units of ft/s
 */
  public void getVelocity() {
    this.currentVelocity = ((shootMotorLeader.getEncoder().getVelocity() / 60)  * RobotMap.Shooter.conversionConstant); // 1 rpm * .75 (gear reduction) * conversionConstant
    
   }
  
  private void setPower (double power) {
    shootMotorLeader.set(power);
  }
}
