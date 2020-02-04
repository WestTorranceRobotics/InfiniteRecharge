/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */


package frc5124.robot2020.subsystems;
import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Robot;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.*;

/**
 * 
 * WARNING 
 * Untuned
 * 
 * 
 * 
 */
public cc clas * 
 * 
 * 
 */
public clas
  private boolean run = true;

  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.Shooter.shootFollowerCanID, MotorType.kBrushless);
  private CorLeader = new rCanID, MotorType.kBrushless);
  private Prontrol = new PIDContrr);
  }

  @Override
  public void periodic() {
    holdVelocity(targetVelocity);
  }

  /**
     * @param targetVelocity in units of ft/s; truncated if exceeding maxVelocity
   * 
   */
  public void setVelocity (double targetVelocity) {
    if (targetVelocityMap.Shooter.maxVelocity) {
      targetVelocity = RobotMap.Shooter.maxVelocity;
    }
    this.targetVelVelocity;
  }

  /**
   * Must be called dic  /**
   * Must be called in periodicvate void holdVelocity (double targetVelocity) {
  kPI(targetVelocity);
  setPower(kOut); //kOut is the kPI output
  }

  /**
   * @deprecated
   */
  public void directPower (double power) {
  setPower(power);
  }


  public void runWheel(boolean run) {
