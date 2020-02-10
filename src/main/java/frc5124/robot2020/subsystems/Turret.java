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
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  private CANSparkMax turretMotor;
  private CANPIDController turretPID;
  private DigitalInput magneticSensor;
  private DigitalOutput mDigitalOutput;
  
  public Turret() {
    turretMotor = new CANSparkMax(7, MotorType.kBrushless);
    turretPID = turretMotor.getPIDController();
    turretMotor.restoreFactoryDefaults();
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setReference(0, ControlType.kVelocity);
    magneticSensor = new DigitalInput(0);
    turretMotor.setIdleMode(IdleMode.kBrake);

    
  }



  public double getRotations() {
    return turretMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }
  public double getDegrees(){
    return getRotations() * (1.0/(66.0 + (2/3))) * 360.0;
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

  public void setPower(double power){
    turretMotor.set(power);
  }

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
  public DigitalInput getMagnetSensor(){
    return magneticSensor;
  }

  @Override
  public void periodic() {
    boolean x = magneticSensor.get();
    SmartDashboard.putBoolean("Is Magnet there?",x);
    SmartDashboard.putNumber("Turret Degree", getDegrees());
    SmartDashboard.updateValues();
    
  }

} 