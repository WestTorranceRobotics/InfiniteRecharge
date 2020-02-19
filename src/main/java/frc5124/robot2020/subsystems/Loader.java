/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;
//import frc5124.robot2020.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader implements Subsystem {
  private CANSparkMax topBeltMotor;
  private CANSparkMax bottomBeltMotor;
  AnalogInput motionSensor = new AnalogInput(RobotMap.LoaderMap.motionSensorID);
  
  public Loader() { 
    topBeltMotor = new CANSparkMax(RobotMap.LoaderMap.topBeltCanId, MotorType.kBrushless);
    bottomBeltMotor = new CANSparkMax(RobotMap.LoaderMap.bottomBeltCanId, MotorType.kBrushless);
    topBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.follow(topBeltMotor);
    bottomBeltMotor.setInverted(true);
    
  }

  public void setPower(double power){
    topBeltMotor.set(power);
  }
  
  public void runBelt() {
    topBeltMotor.set(RobotMap.LoaderMap.beltSpeed);
  }
  public void stopBelt() {    
    topBeltMotor.set(0);
  }

  public double getEncoder(){
    return topBeltMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }

  public void reverseBelt(){
    topBeltMotor.set(-1);
  }

  public double getVoltage() {
    return motionSensor.getVoltage();
  }

  public double topBeltCurrent() {
    return topBeltMotor.getOutputCurrent();
  }

  public double bottomBeltCurrent() {
    return bottomBeltMotor.getOutputCurrent();
  }

  public boolean seeBall() {
    return (getVoltage() < RobotMap.LoaderMap.fieldEmptyVoltage);
  }

  public double returnRotations() {
    return topBeltMotor.getEncoder().getPosition();
  }
  public void runLoader() {
    if(!seeBall()){
      runBelt();
    }
    else{
      stopBelt();
    }
  }

  public void flushOut() {
    topBeltMotor.set(-1);
  }

  //This was here when I started so I left it that way.
  @Override
  public void periodic() {
    SmartDashboard.updateValues();
  }
}
