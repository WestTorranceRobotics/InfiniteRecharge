/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import frc5124.robot2020.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  private CANSparkMax turretMotor;
  private CANEncoder turretEncoder;

  private double initialValue;
  private double clockwiseLimit;
  private double counterClockwiseLimit;
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretEncoder = turretMotor.getEncoder();
    clockwiseLimit = 33.0;
    counterClockwiseLimit = -clockwiseLimit;

  }

  @Override
  public void periodic() {
    //Starting value is 25
    // +- 33 encoder values

    SmartDashboard.putNumber("Turret Encoder Value", turretEncoder.getPosition());
    SmartDashboard.updateValues();
  }

  public void rotateTurret(double power) {
    //if clockwise limit reached, and an attempt is made to keep going clockwise, stop the motor
    if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= clockwiseLimit && power > 0) {
      turretMotor.set(0);
    } 
    //if clockwise limit reached, but an attempt is made to go counter-clockwise, move the motor
    else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= clockwiseLimit && power < 0) {
      turretMotor.set(power);
    } 

    //if counter-clockwise limit reached, and an attempt is made to keep going counter-clockwise, stop the motor
    else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() <= counterClockwiseLimit && power < 0) {
      turretMotor.set(0);
    } 
    //if counter-clockwise limit reached, and an attempt is made to go clockwise, move the motor
    else if (turretMotor.getAppliedOutput() == 0 && turretEncoder.getPosition() >= counterClockwiseLimit && power > 0) {
      turretMotor.set(power);
    }

    else {
      turretMotor.set(0);
    }
  }

  public int getEncoderCountsPerRevolution(){
    return turretMotor.getEncoder().getCountsPerRevolution();
  }

  public CANSparkMax getMotor() {
    return turretMotor;
  }

  public CANEncoder getEncoder(){
    return turretEncoder;
  }

  private boolean limitReached() {
    return true;
  }

} 