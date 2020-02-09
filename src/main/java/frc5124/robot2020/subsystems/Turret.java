/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  private CANSparkMax turretMotor;
  private CANPIDController turretPID;
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretPID = turretMotor.getPIDController();
    turretMotor.restoreFactoryDefaults();
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setReference(0, ControlType.kVelocity);
    

    
  }



  public double getRotations() {
    return turretMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }

  // public void rotateTurret(double power) {
  //   //if clockwise limit reached, and an attempt is made to keep going clockwise, stop the motor
  //   if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= clockwiseLimit && power > 0) {
  //     turretMotor.set(0);
  //   } 
  //   //if clockwise limit reached, but an attempt is made to go counter-clockwise, move the motor
  //   else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= clockwiseLimit && power < 0) {
  //     turretMotor.set(power);
  //   } 

  //   //if counter-clockwise limit reached, and an attempt is made to keep going counter-clockwise, stop the motor
  //   else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() <= counterClockwiseLimit && power < 0) {
  //     turretMotor.set(0);
  //   } 
  //   //if counter-clockwise limit reached, and an attempt is made to go clockwise, move the motor
  //   else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= counterClockwiseLimit && power > 0) {
  //     turretMotor.set(power);
  //   }

  //   else {
  //     turretMotor.set(0);
  //   }
  // }

  public int getEncoderCountsPerRevolution(){
    return turretMotor.getEncoder().getCountsPerRevolution();
  }

  public CANSparkMax getMotor() {
    return turretMotor;
  }

  // public CANEncoder getEncoder(){
  //   return turretEncoder;
  // }

  private boolean limitReached() {
    return true;
  }

  @Override
  public void periodic() {
  }

} 