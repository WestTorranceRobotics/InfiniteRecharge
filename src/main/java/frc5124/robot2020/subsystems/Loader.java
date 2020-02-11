/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
//import frc5124.robot2020.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader implements Subsystem {
  private CANSparkMax topBeltMotor;
  private CANSparkMax bottomBeltMotor;
  AnalogInput sensor = new AnalogInput(1);
  private static final double fieldEmptyVoltage = 1.0;
  double beltSpeed = 0.8;
  
  public Loader() {
    topBeltMotor = new CANSparkMax(RobotMap.Loader.topBeltCanId, MotorType.kBrushless);
    bottomBeltMotor = new CANSparkMax(RobotMap.Loader.bottomBeltCanId, MotorType.kBrushless);
    bottomBeltMotor.follow(topBeltMotor);
    bottomBeltMotor.setInverted(true);
    topBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.restoreFactoryDefaults();
    // SmartDashboard.putNumber("Ultrasonic Sensor Voltage", getVoltage());
    // SmartDashboard.putBoolean("Ultrasonic Sensor sees ball", seeBall());
  }

  public void setPower(double power){
    topBeltMotor.set(power);
  }
  
  public void runBelt() {
    topBeltMotor.set(beltSpeed);
  }
  public void stopBelt() {    
    topBeltMotor.set(0);
  }

  //This is the hasBall function. It assumes that the ultrasonicsensor is placed level with the top belt and is facing down
  public double getVoltage() {
    return sensor.getVoltage();
  }

  public boolean seeBall() {
    return (getVoltage() < fieldEmptyVoltage);
  }

  public void runLoader() {
    if(!seeBall()){
      runBelt();
    }
    else{
      stopBelt();
    }
  }

  //This was here when I started so I left it that way.
  @Override
  public void periodic() {
    //SmartDashboard.updateValues();
  }
  
}