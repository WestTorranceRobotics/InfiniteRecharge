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
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader implements Subsystem {
  private CANSparkMax topBeltMotor;
  private CANSparkMax bottomBeltMotor;
  AnalogInput motionSensor = new AnalogInput(1);
  
  public Loader() { 
    topBeltMotor = new CANSparkMax(RobotMap.Loader.topBeltCanId, MotorType.kBrushless);
    bottomBeltMotor = new CANSparkMax(RobotMap.Loader.bottomBeltCanId, MotorType.kBrushless);
    topBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.follow(topBeltMotor);
    bottomBeltMotor.setInverted(true);
    
  }

  public void setPower(double power){
    topBeltMotor.set(power);
  }
  
  public void runBelt() {
    topBeltMotor.set(1);
  }
  public void stopBelt() {    
    topBeltMotor.set(0);
  }

  public void reverseBelt(){
    topBeltMotor.set(-1);
  }

  public double getVoltage() {
    return motionSensor.getVoltage();
  }

  public boolean seeBall() {
    return (getVoltage() < 1.0);
  }

  public double returnRotations() {
    return topBeltMotor.getEncoder(EncoderType.kHallSensor, 42).getCountsPerRevolution();
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
    SmartDashboard.updateValues();
  }
}
