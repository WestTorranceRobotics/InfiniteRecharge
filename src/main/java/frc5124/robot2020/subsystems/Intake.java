/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

public class Intake implements Subsystem {

  // private final SparkMax intakeMotor = RobotMap.ballIntake;  
  // private final DoubleSolenoid intakePivot = RobotMap.intakeDeploy(2);

  private final SparkMax intakeMotor;
  private final DoubleSolenoid intakePivot;

  public Intake() {
  }

  @Override
  public void periodic() {
  }
  public void intake(){
    intakeMotor.set(RobotMap.motorPower);
  }

  public void outtake(){
    intakeMotor.set(-RobotMap.motorPower);
  }

  public void motorNoPower(){
    intakeMotor.set(0);
  }

  public void liftUp(){
    intakePivot.set(Value.kForward);          // kForward Value makes it stay up. 
  }

  public void liftDown(){
    intakePivot.set(Value.kReverse);          // kReverse Value will make the arm come out.  
  }

  // Control Methods

  // TODO
}
