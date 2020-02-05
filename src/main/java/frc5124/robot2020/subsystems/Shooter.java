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
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
 

  
  public Shooter() {
    shootMotorFollower.follow(shootMotorLeader);
  }

  /**
   * PI loop
   * @param targetVelocity desired velocity
   */
  public void kPIHold(double targetVelocity) {
    
  }

/**
 * Units of ft/s
 */

  public double getVelocity() {
    return ((shootMotorLeader.getEncoder().getVelocity() / 60)  * RobotMap.ShooterMap.conversionConstant); // 1 rpm * .75 (gear reduction) * conversionConstant
    
   }
  
  public void setPower (double power) {
    shootMotorLeader.set(power);
  }

  
  @Override
  public void periodic() {
  }

}

