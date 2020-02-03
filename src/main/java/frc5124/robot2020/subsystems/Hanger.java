/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANDigitalInput.LimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;  

public class Hanger implements Subsystem {
  private TalonFX hangerMotor;
  private Solenoid brake;
  private DigitalInput heightLimit;

  public Hanger() {
    hangerMotor = new TalonFX(5);
    brake = new Solenoid(2);
    heightLimit = new DigitalInput(RobotMap.Hanger.limitChannelID);
  }

  @Override
  public void periodic() {
  }
  
  public void liftUp(){
    hangerMotor.set(ControlMode.PercentOutput, 1);
    brake.set(false);
  }

  public void reachedLimit(){
    if (heightLimit.get() == false){
      hangerMotor.set(ControlMode.PercentOutput, 0);
      brake.set(true);
    }
  }

  public void liftDown(){
    hangerMotor.set(ControlMode.PercentOutput, -RobotMap.Hanger.hangerMotor);
  }

  public void setNoPower(){
    hangerMotor.set(ControlMode.PercentOutput, RobotMap.Hanger.hangerHalt);
  }

  public void brake(){
    brake.set(true);
  }

  public boolean limitSwitchPressed(){
    return heightLimit.get();
  }
}