/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc5124.robot2020.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;


public class Loader implements Subsystem {
  private WPI_TalonSRX topBelt;
  private WPI_TalonSRX bottomBelt;
  private DriveTrain driveTrain = new DriveTrain();
  // distance in inches the robot wants to stay from an object
  private static final double kHoldDistance = 12.0;

  // factor to convert sensor values to a distance in inches
  private static final double kValueToInches = 0.125;

  // proportional speed constant
  private static final double kP = 0.05;

  private static final int kUltrasonicPort = 1;

  private final AnalogInput m_ultrasonic = new AnalogInput(kUltrasonicPort);

  public double currentDistance;

  /**
   * Tells the robot to drive to a set distance (in inches) from an object
   * using proportional control.
   */
  

  
  public Loader() {

  }

  @Override
  public void periodic() {
    currentDistance = m_ultrasonic.getValue() * kValueToInches;
  }

  // Control Methods

  // TODO
}
