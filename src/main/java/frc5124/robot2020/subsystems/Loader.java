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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.smartdashboard.*;

public class Loader implements Subsystem {

 // DoubleSolenoid CylinderDoubleSol = new DoubleSolenoid(1, 2);
  AnalogInput sensor = new AnalogInput(1);
  CANSparkMax topBeltMotor = new CANSparkMax(7, MotorType.kBrushless);
  //CANSparkMax bottomBeltMotor = new CANSparkMax(4, MotorType.kBrushless);
  private static final double fieldEmptyVoltage = 1.0;
  double beltSpeed = 0.5;
  //private CANSparkMax beltSpeedController;
  
  public Loader() {
  }
  
  public void runBelt() {
    topBeltMotor.set(beltSpeed);
    // bottomBeltMotor.set(1);
  }
  public void stopBelt() {    
    topBeltMotor.set(0.0);
    // bottomBeltMotor.set(0); 
  }
  //This is the flipCylinder method. Based on its input it goes into one of three modes. 
  //Mode 0 causes the cylinder to go forward, mode 1 causes the cylinder to go backward, and mode 2 turns the cylinder off.
  public void flipCylinder(int x) {
    // if (x == 0){
    //   CylinderDoubleSol.set(DoubleSolenoid.Value.kForward); 
    // }
    // else if (x == 1){
    //   CylinderDoubleSol.set(DoubleSolenoid.Value.kReverse);
    // }
    // else {
    //   CylinderDoubleSol.set(DoubleSolenoid.Value.kOff);
    // }
  }

  //This is the hasBall function. It assumes that the ultrasonicsensor is placed level with the top belt and is facing down
  public double getVoltage() {
    return sensor.getVoltage();
  }

  public boolean seeBall() {
    return (getVoltage() < fieldEmptyVoltage);
  }

  //This was here when I started so I left it that way.
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Ultrasonic Sensor Voltage", getVoltage());
    SmartDashboard.putBoolean("Ultrasonic Sensor sees ball", seeBall());
  }

    // TODO:
    // Connect everything to the correct ports
    // Test if this actually works and edit accordingly if it does not 
    //Fix the wait time when bottomBeltMotor is running
}