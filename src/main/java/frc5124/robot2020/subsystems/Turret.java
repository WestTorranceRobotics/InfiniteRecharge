/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

public class Turret implements Subsystem {

  private final NetworkTable limelight;
  private final CANSparkMax motor;
  
  public Turret() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    motor = new CANSparkMax(RobotMap.Turret.spinnerCanId, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.setInverted(true);
    getController().setIMaxAccum(RobotMap.Turret.percentSpeedLimit, 0);
  }

  public void rotateTurret(double power) {
    motor.set(power);
  }

  public void setPower(double power) {
    motor.set(power);
  }

  public double getEncoder() {
    return motor.getEncoder().getPosition()*RobotMap.Turret.turretGearing;
  }

  public CANPIDController getController() {
    return motor.getPIDController();
  }
  
  public void setTurretDegrees(double degrees) {
    getController().setP(RobotMap.TurretMap.Kp);
    getController().setReference(((degrees) * (RobotMap.TurretMap.turretDegreeToRotations)), ControlType.kPosition);
  }

  public double getRotations() {
    return motor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }

  public int getEncoderCountsPerRevolution(){
    return motor.getEncoder().getCountsPerRevolution();
  }

  public CANSparkMax getMotor() {
    return motor;
  }

  // public CANEncoder getEncoder(){
  //   return turretEncoder;
  // }

  private boolean limitReached() {
    return true;
  }

  @Override
  public void periodic() {
  }

}
