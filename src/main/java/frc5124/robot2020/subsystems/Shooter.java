/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Shooter extends SubsystemBase {
  private CANSparkMax shootMotorFollower = new CANSparkMax(RobotMap.ShooterMap.shootFollowerCanID, MotorType.kBrushless);
  private CANSparkMax shootMotorLeader = new CANSparkMax(RobotMap.ShooterMap.shootLeaderCanID, MotorType.kBrushless);
  private CANPIDController shootPID; 
  private boolean atSpeed;
  private Solenoid shootSolenoid = new Solenoid(RobotMap.modNumSolenoid, RobotMap.ShooterMap.shootSolenoid);
  private int ballsShot = 0;
  private boolean passedBallCurrent = false;
  private ShuffleboardTab debuggingTab;
 
  public Shooter() {
    shootMotorFollower.restoreFactoryDefaults();
    shootMotorLeader.restoreFactoryDefaults();
    shootMotorFollower.follow(shootMotorLeader, true);
    shootMotorLeader.setIdleMode(IdleMode.kCoast);
    shootMotorFollower.setIdleMode(IdleMode.kCoast);
    shootMotorLeader.setInverted(true);
    shootMotorFollower.setInverted(true);
    shootPID = shootMotorLeader.getPIDController();
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
    shootPID.setReference(0, ControlType.kVelocity);
    SmartDashboard.putNumber("PSHOOT", RobotMap.ShooterMap.Kp);
    SmartDashboard.putNumber("DSHOOT", RobotMap.ShooterMap.Kd);
    SmartDashboard.putNumber("FSHOOT", RobotMap.ShooterMap.Kf);
    // shootMotorFollower.setClosedLoopRampRate(.00001);
    // shootMotorLeader.setClosedLoopRampRate(.00001);
    if (RobotMap.debugEnabled) {
      debuggingTab = Shuffleboard.getTab("Shooter Debug");
      debuggingTab.addNumber("Lead Shooter Current", shootMotorLeader::getOutputCurrent)
      .withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
      debuggingTab.addNumber("Shooter RPM", this::getVelocity)
      .withPosition(2, 0).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
      debuggingTab.addNumber("Balls Shot", this::getBallsShot)
      .withPosition(0, 3).withSize(1, 1);
    }
  }

  public boolean active() {
    return shootMotorLeader.getAppliedOutput() != 0;
  }

  public int getBallsShot() {
    return ballsShot;
  }

  public double getCurrent() {
    return shootMotorLeader.getOutputCurrent();
  }

  public void resetBallsShot() {
    ballsShot = 0;
  }

  public void disablePID() {
    shootPID.setD(0);
    shootPID.setP(0);
    shootPID.setFF(0);
  }

  public void enablePID() {
    shootPID.setD(RobotMap.ShooterMap.Kd);
    shootPID.setP(RobotMap.ShooterMap.Kp);
    shootPID.setFF(RobotMap.ShooterMap.Kf);
  }

  public void updatePID() {
    shootPID.setD(SmartDashboard.getNumber("DSHOOT", RobotMap.ShooterMap.Kd));
    shootPID.setP(SmartDashboard.getNumber("PSHOOT", RobotMap.ShooterMap.Kp));
    shootPID.setFF(SmartDashboard.getNumber("FSHOOT", RobotMap.ShooterMap.Kf));
  }

  /**
   * @param targetRPM desired RPM of shooter
   */
  public void startShooter() {
    enablePID();
    shootPID.setReference(RobotMap.ShooterMap.lineShootRPM, ControlType.kVelocity);
  }

  public void startShooter(double rpm) {
    //enablePID();
    updatePID();
    shootPID.setReference(rpm, ControlType.kVelocity);
  }

    public void stopShooter () {
      disablePID();
     shootMotorLeader.set(0);
    }
  
/**
 * Units of ft/s
 */
  public double getVelocity() {
    return (shootMotorLeader.getEncoder().getVelocity() / RobotMap.ShooterMap.gearRatio); 
   }

   public double getVoltage() {
     return shootMotorLeader.getBusVoltage();
   }
  
   public void atSpeed(boolean atSpeed) {
    this.atSpeed= atSpeed;
  }
  public boolean atSpeed() {
    return this.atSpeed;
  }
  
  public boolean holeOpenedOrClose(){
    return shootSolenoid.get();
  }

  public void openHole(){
    shootSolenoid.set(true);
  }

  public void closeHole(){
    shootSolenoid.set(false);
  }

  public void directPower (double power) {
    shootMotorLeader.set(power);
  }

  /**
   * Checks output current to shooter to count balls that have passed
   * 
   * Call in command execute or periodic
   * 
   * @param targetRPM PID RPM reference; will not count a ball shot if a current spike is detected below speed
   */
  public void currentWatch(double targetRPM) {
    if (shootMotorLeader.getOutputCurrent() >= RobotMap.ShooterMap.ballCurrent && passedBallCurrent == false) {
      passedBallCurrent = true;
      ballsShot = ballsShot + 1;
    } else if (passedBallCurrent == true && shootMotorLeader.getOutputCurrent() < RobotMap.ShooterMap.ballCurrent-7) {
      passedBallCurrent = false;
    } 
  }

  public void directVolts(double volts) {
    shootMotorLeader.setVoltage(volts);
  }
  

  @Override
  public void periodic() {
    SmartDashboard.putNumber("shoot V", getVelocity());
    SmartDashboard.updateValues();
  }
}