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
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay.Direction;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Turret extends SubsystemBase {
  private CANSparkMax turretMotor;
  private CANPIDController turretPID;
  private boolean leftLimitReached = false;
  private boolean rightLimitReached = false;
  private boolean automatic = false;
  private NetworkTableEntry shuffleboardButtonBooleanEntry;
  private ShuffleboardTab debuggingTab;
  private boolean isHome = false;
  private double startDegrees = 0;
  private boolean isInitialHome = false;

  public static AnalogInput mag = new AnalogInput(3);
  
  public Turret() {
    turretMotor = new CANSparkMax(RobotMap.TurretMap.turretCanID, MotorType.kBrushless);
    turretPID = turretMotor.getPIDController();
    turretMotor.restoreFactoryDefaults();
    setBrake();
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setI(RobotMap.TurretMap.Ki);
    turretPID.setIZone(RobotMap.TurretMap.KiZone);
    startDegrees = getDegrees();
    resetTurretDegrees();
    turretLimitSet();
  }


  public void updateCoeffs() {
    turretPID.setP(SmartDashboard.getNumber("P", RobotMap.TurretMap.Kp));
    turretPID.setI(SmartDashboard.getNumber("I", RobotMap.TurretMap.Ki));
    turretPID.setD(SmartDashboard.getNumber("D", 0));
    turretPID.setIZone(SmartDashboard.getNumber("IZONE", RobotMap.TurretMap.KiZone));
  }

  public void isAutomatic(boolean automatic) {
    this.automatic = automatic;
  }
  
  public boolean isAutomatic() {
    return automatic;
  }

  public void setTurretDegrees(double degrees) {
    turretPID.setReference(((degrees) * (RobotMap.TurretMap.turretDegreeToRotations)), ControlType.kPosition);
  }

  public void turretLimitSet() {
    turretMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, ((int) (RobotMap.TurretMap.reverseRotationLimit * RobotMap.TurretMap.turretDegreeToRotations)));
    turretMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, ((int) (RobotMap.TurretMap.forwardRotationLimit * RobotMap.TurretMap.turretDegreeToRotations)));
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
  }

  public void disableTurretLimit() {
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
    turretMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
  }

  public void resetTurretDegrees() {
    turretMotor.getEncoder().setPosition(0);
  }

  /**
   * Disables turret PID for manual control
   */
  public void disableTurretPID () {
    turretPID.setP(0);
    turretPID.setI(0);
    turretPID.setIZone(0);
    turretPID.setD(0);
  }

  public void isHome(boolean isHome) {
    this.isHome = isHome;
  }

  public boolean isHome () {
    return isHome;
  }

  public void isInitialHome(boolean isInitialHome) {
    this.isInitialHome = isInitialHome;
  }

  public boolean isInitialHome () {
    return isInitialHome;
  }

  public void setCoast() {
    turretMotor.setIdleMode(IdleMode.kCoast);
  }

  public void setBrake() {
    turretMotor.setIdleMode(IdleMode.kBrake);
  }

  public boolean leftLimitReached() {
    return leftLimitReached;
  }

  public void leftLimitReached(boolean leftLimitReached) { 
    this.leftLimitReached = leftLimitReached;
  }

  public boolean rightLimitReached() {
    return rightLimitReached;
  }

  public void rightLimitReached(boolean rightLimitReached) { 
    this.rightLimitReached = rightLimitReached;
  }

  public double getVoltage(){
    return turretMotor.getBusVoltage();
  }

  public double getCurrent() {
    return turretMotor.getOutputCurrent();
  }

  /**
   * Enables turret PID for automatic control
   */
  public void enableTurretPID () {
    turretPID.setP(RobotMap.TurretMap.Kp);
    turretPID.setI(RobotMap.TurretMap.Ki);
    turretPID.setIZone(RobotMap.TurretMap.KiZone);
  }
 
  /**
   * Used for testing
   * @param power
   */
  public void directPower(double power) {
    turretMotor.set(power);
  }
  /**
   * Returns rotations of NEO 550 (NOT TURRET)
   * @return
   */
  public double getRotations() {
    return turretMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
  }
  /**
   * Returns current heading of turret based on zeroing
   * @return
   */
  public double getDegrees() {
    return ((turretMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition()) / RobotMap.TurretMap.turretDegreeToRotations);
  }

  public int getEncoderCountsPerRevolution(){
    return turretMotor.getEncoder().getCountsPerRevolution();
  }

  public CANSparkMax getMotor() {
    return turretMotor;
  }

  public double getAppliedOutput() {
    return turretMotor.getAppliedOutput();
  }

  @Override
  public void periodic() {
  }
} 