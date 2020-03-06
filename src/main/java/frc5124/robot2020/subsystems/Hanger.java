/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc5124.robot2020.RobotMap;  
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Hanger extends SubsystemBase {
  private CANSparkMax hangerMotor;
  private Solenoid brakeOff;
  private DigitalInput topLimit;
  private DigitalInput bottomLimit;
  private ShuffleboardTab debuggingTab;

  public Hanger() {
    hangerMotor = new CANSparkMax(RobotMap.HangerMap.hangerCanID, MotorType.kBrushless);
    brakeOff = new Solenoid(RobotMap.modNumSolenoid, RobotMap.HangerMap.hangerSolenoid);
    topLimit = new DigitalInput(RobotMap.HangerMap.topLimitChannelID);
    bottomLimit = new DigitalInput(RobotMap.HangerMap.bottomLimitChannelID);
    brakeOff.set(false);
    hangerMotor.setInverted(true);
    hangerMotor.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    if (RobotMap.debugEnabled) {}
    SmartDashboard.putNumber("encoder Hanger", hangerMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition());
    SmartDashboard.updateValues();
  }

  public double getPosition() {
    return hangerMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }
   
  public void liftUp() {  
      hangerMotor.set(RobotMap.HangerMap.hangerMotorUp);
      brakeOff.set(true);
  }

  public void liftDown(){
      hangerMotor.set(RobotMap.HangerMap.hangerMotorDown);
      brakeOff.set(true);
  }

  public boolean reachedTopLimit(){
    return topLimit.get();
  }

  public boolean reachedBottomLimit(){
    return bottomLimit.get();
  }

  public void setNoPower(){
    hangerMotor.set(RobotMap.HangerMap.hangerHalt);
    brakeOff.set(false);
  }
}