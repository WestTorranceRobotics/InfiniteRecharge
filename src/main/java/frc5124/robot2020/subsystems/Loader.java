/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader extends SubsystemBase {
  private CANSparkMax topBeltMotor;
  private CANSparkMax bottomBeltMotor;
  AnalogInput motionSensor = new AnalogInput(1);
  private ShuffleboardTab display;
  private int ballIntaked;
  private int newBall = 1;
  
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

  public double getAppliedOutput() {
    return topBeltMotor.getAppliedOutput();
  }
  
  public void runBelt(double power) {
    topBeltMotor.set(power);
  }
    
  public void runBelt() {
    topBeltMotor.set(RobotMap.LoaderMap.beltSpeed);
  }
  public void stopBelt() {    
    topBeltMotor.set(0);
  }

  public void reverseBelt(){
    topBeltMotor.set(RobotMap.LoaderMap.reverseBeltSpeed);
  }

  public double getVoltage() {
    return motionSensor.getVoltage();
  }

  public boolean seeBall() {
    return (getVoltage() < RobotMap.LoaderMap.seeBallVoltage);
  }

  public double returnRotations() {
    return topBeltMotor.getEncoder(EncoderType.kHallSensor, RobotMap.neoCounts).getCountsPerRevolution();
  }

  public void runLoader() {
    if(!seeBall()){
      runBelt();
    }
    else{
      stopBelt();
    }
  }

  public void ballIntaked(){
    ballIntaked += newBall;
  }

  public void ballIntaked(int balls) {
    ballIntaked = balls;
  }

  public int getBallsIntaked() {
    return ballIntaked;
  }

  //This was here when I started so I left it that way.
  @Override
  public void periodic() {    
  }
}
