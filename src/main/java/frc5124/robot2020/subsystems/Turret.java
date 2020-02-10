/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import frc5124.robot2020.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Turret implements Subsystem {
  private CANSparkMax turretMotor;
  public CANPIDController turretPID;
  private CANEncoder turretEncoder;
  public double startPosition;
  public boolean limitReached = false;

  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretMotor.setIdleMode(IdleMode.kBrake);
    turretPID = turretMotor.getPIDController();
    turretEncoder = turretMotor.getEncoder();
    turretMotor.restoreFactoryDefaults();
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setReference(0, ControlType.kVelocity);
    startPosition = getEncoder();
  }



  public double getEncoder() {
    return turretEncoder.getPosition()*RobotMap.TurretMap.turretGearing;
  }

  public void rotateTurret(double power) {
    //limits how much the turret can turn when set to a power
    if (Math.abs(getEncoder()-startPosition) < RobotMap.TurretMap.turnLimit) {
      turretMotor.set(power);
    }  
    //When the power is set to reverse, it should allow the motor to go in the opposite direction, going below the limit
    else if (-Math.signum(getEncoder()-startPosition) == Math.signum(power) && Math.abs(getEncoder()-startPosition) > RobotMap.TurretMap.turnLimit) {
      turretMotor.set(power);
    }
    //Sets up the condition to be used when the turret wants to return home
    else if (Math.abs(getEncoder()-startPosition) > RobotMap.TurretMap.turnLimit) {
      limitReached = true;
    }
    else {
      turretMotor.set(0);
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Current Position", getEncoder());
    SmartDashboard.putNumber("Start position", startPosition);
    SmartDashboard.updateValues();
  }

} 