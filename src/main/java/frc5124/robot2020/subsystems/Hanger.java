/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;  

public class Hanger implements Subsystem {
  private TalonFX hangerMotor;
  private Solenoid brake;
  private DigitalInput topLimit;
  private DigitalInput bottomLimit;

  public Hanger() {
    hangerMotor = new TalonFX(RobotMap.HangerMap.hangerCanID);
    brake = new Solenoid(0, 2);
    topLimit = new DigitalInput(RobotMap.HangerMap.topLimitChannelID);
    bottomLimit = new DigitalInput(RobotMap.HangerMap.bottomLimitChannelID);
  }

  @Override
  public void periodic() {
  }
   
  public void liftUp() {
    if (notReachedTopLimit()) {
      brake.set(true);
      hangerMotor.set(ControlMode.PercentOutput, RobotMap.HangerMap.hangerMotor);
    }
    else {
      hangerMotor.set(ControlMode.PercentOutput, 0);
      brake.set(false);
    }
  }

  public void liftDown(){
    if (notReachedBottomLimit()) {
      brake.set(true);
      hangerMotor.set(ControlMode.PercentOutput, -RobotMap.HangerMap.hangerMotor);
    }
    else {
      hangerMotor.set(ControlMode.PercentOutput, 0);
      brake.set(false);
    }
  }

  public boolean notReachedTopLimit(){
    return topLimit.get();
  }

  public boolean notReachedBottomLimit(){
    return bottomLimit.get();
  }

  public void setNoPower(){
    hangerMotor.set(ControlMode.PercentOutput, RobotMap.HangerMap.hangerHalt);
    brake.set(false);
  }
}