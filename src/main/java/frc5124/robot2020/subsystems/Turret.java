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
    turretPID.setReference(0, ControlType.kPosition);
  }

  public void setTurretDegrees(double degrees) {
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setReference(((degrees) * (RobotMap.TurretMap.turretDegreeToRotations)), ControlType.kPosition);
  }

  public void disableTurretPID () {
    turretPID.setP(0);
  }


  public double getRotations() {
    return turretMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }
  public double getDegrees(){
    return getRotations() * (1.0/(66.0 + (2/3))) * 360.0;
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
