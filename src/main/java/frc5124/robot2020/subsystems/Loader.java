/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader implements Subsystem {
  private CANSparkMax topBeltMotor;
  private CANSparkMax bottomBeltMotor;
  AnalogInput motionSensor = new AnalogInput(1);
  private ShuffleboardTab debuggingTab;
  
  public Loader() { 
    topBeltMotor = new CANSparkMax(RobotMap.LoaderMap.topBeltCanId, MotorType.kBrushless);
    bottomBeltMotor = new CANSparkMax(RobotMap.LoaderMap.bottomBeltCanId, MotorType.kBrushless);
    topBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.restoreFactoryDefaults();
    bottomBeltMotor.follow(topBeltMotor);
    bottomBeltMotor.setInverted(true);
    if (RobotMap.debugEnabled) {
      debuggingTab = Shuffleboard.getTab("Loader Debug");
      debuggingTab.addNumber("Top Motor Current", topBeltMotor::getOutputCurrent)
      .withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
      debuggingTab.addNumber("Bottom Motor Current", bottomBeltMotor::getOutputCurrent)
      .withPosition(0, 2).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
      debuggingTab.addBoolean("Sees Ball", this::seeBall)
      .withPosition(3, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
      debuggingTab.addNumber("Number Balls In (Stub)", () -> 0)
      .withPosition(3, 1).withSize(1, 1);
      debuggingTab.addNumber("Number Balls Out (Stub)", () -> 0)
      .withPosition(3, 2).withSize(1, 1);
    }
  }

  public void setPower(double power){
    topBeltMotor.set(power);
   // bottomBeltMotor.set(power);
  }

  public double getAppliedOutput() {
    return topBeltMotor.getAppliedOutput();
  }
  
  public void runBelt() {
    topBeltMotor.set(RobotMap.LoaderMap.beltSpeed);
  }
  public void stopBelt() {    
    topBeltMotor.set(0);
   // bottomBeltMotor.set(0);
  }

  public void reverseBelt(){
    topBeltMotor.set(-.35);
  //  bottomBeltMotor.set(-.5); 
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
  }
}
