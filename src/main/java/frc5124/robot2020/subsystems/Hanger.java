/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
    brake = new Solenoid(RobotMap.modNumSolenoid, RobotMap.HangerMap.hangerSolenoid);
    topLimit = new DigitalInput(RobotMap.HangerMap.topLimitChannelID);
    bottomLimit = new DigitalInput(RobotMap.HangerMap.bottomLimitChannelID);
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
      hangerMotor.set(ControlMode.PercentOutput, RobotMap.HangerMap.hangerMotor);
    }
  }

  public void liftDown(){
    if (reachedBottomLimit()) {
      setNoPower();
    }
    else {
      brake.set(false);
      hangerMotor.set(ControlMode.PercentOutput, -RobotMap.HangerMap.hangerMotor);
    }
  }

  public boolean reachedTopLimit(){
    return topLimit.get();
  }

  public boolean reachedBottomLimit(){
    return bottomLimit.get();
  }

  public void setNoPower(){
    hangerMotor.set(ControlMode.PercentOutput, RobotMap.HangerMap.hangerHalt);
    brake.set(true);
  }
}