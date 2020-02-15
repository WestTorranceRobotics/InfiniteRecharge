/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;  

public class Hanger implements Subsystem {
  private WPI_TalonFX hangerMotor;
  private Solenoid brake;
  private DigitalInput topLimit;
  private DigitalInput bottomLimit;
  private Encoder liftEncoder;
  private int distanceInchesToDrive;
  private double ticksToRunTo;
  private double ticksPerInch;

  public Hanger() {
    hangerMotor = new WPI_TalonFX(RobotMap.HangerMap.hangerCanID);
    brake = new Solenoid(RobotMap.modNumSolenoid, RobotMap.HangerMap.hangerSolenoid);
    topLimit = new DigitalInput(RobotMap.HangerMap.topLimitChannelID);
    bottomLimit = new DigitalInput(RobotMap.HangerMap.bottomLimitChannelID);
    liftEncoder = new Encoder(4,5);
    distanceInchesToDrive = 63; 
    ticksPerInch = 47334; //thats a random number, idk yet lol
    ticksToRunTo = distanceInchesToDrive * ticksPerInch; 
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("limitSwitchPressed?", reachedTopLimit());
    SmartDashboard.updateValues();
  }
   
  public void liftUp() {
    if (reachedTopLimit()) {
      setNoPower();
    }
    else {
      brake.set(false);
      hangerMotor.set(RobotMap.HangerMap.hangerMotor);
    }
  }

  public void liftDown(){
    if (reachedBottomLimit()) {
      setNoPower();
    }
    else {
      brake.set(false);
      hangerMotor.set(-RobotMap.HangerMap.hangerMotor);
    }
  }

  public boolean reachedTopLimit(){
    return topLimit.get();
  }

  public boolean reachedBottomLimit(){
    return bottomLimit.get();
  }

  public void setNoPower(){
    hangerMotor.set(RobotMap.HangerMap.hangerHalt);
    brake.set(true);
  }

  public void runToMiddleHeight(){
    if (liftEncoder.get() <= ticksToRunTo) {
      hangerMotor.set(.8);
    }
    hangerMotor.set(0);
  }
}