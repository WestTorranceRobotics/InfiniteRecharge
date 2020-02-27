/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;  
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Hanger implements Subsystem {
  private CANSparkMax hangerMotor;
  private Solenoid noBrake;
  private DigitalInput topLimit;
  private DigitalInput bottomLimit;
  private NetworkTableEntry shuffleboardButtonBooleanEntry;
  private ShuffleboardTab display;

  public Hanger() {
    hangerMotor = new CANSparkMax(RobotMap.HangerMap.hangerCanID, MotorType.kBrushless);
    noBrake = new Solenoid(RobotMap.modNumSolenoid, RobotMap.HangerMap.hangerSolenoid);
    topLimit = new DigitalInput(RobotMap.HangerMap.topLimitChannelID);
    bottomLimit = new DigitalInput(RobotMap.HangerMap.bottomLimitChannelID);
    noBrake.set(false);
    hangerMotor.setInverted(true);
    display = Shuffleboard.getTab("Hanger Display");
    Shuffleboard.update();
  }

  @Override
  public void periodic() {
    if (RobotMap.debugEnabled) {}
    SmartDashboard.putBoolean("limitSwitchPressed?", reachedTopLimit());
    SmartDashboard.updateValues();
  }
   
  public void liftUp() {
    // if (reachedTopLimit()) {
    //   setNoPower();
    // }
    // else {
    //   brake.set(false);
    //   hangerMotor.set(RobotMap.HangerMap.hangerMotor);
    // }
    hangerMotor.set(.2);
    noBrake.set(true);
  }



  public void liftDown(){
    // if (reachedBottomLimit()) {
    //   setNoPower();
    // }
    // else {
    //   brake.set(false);
    //   hangerMotor.set(-RobotMap.HangerMap.hangerMotor);
    // }
    hangerMotor.set(-.1);
    noBrake.set(true);
  }

  public boolean reachedTopLimit(){
    return topLimit.get();
  }

  public boolean reachedBottomLimit(){
    return bottomLimit.get();
  }


  public void setNoPower(){
    hangerMotor.set(RobotMap.HangerMap.hangerHalt);
    noBrake.set(false);
  }
}