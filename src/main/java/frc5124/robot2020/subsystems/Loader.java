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
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;


public class Loader implements Subsystem {
  private WPI_TalonSRX topBelt;
  private WPI_TalonSRX bottomBelt;
  private ColorSensorV3 ballDetection;
  private final ColorMatch matcher;

  
  public Loader() {
    topBelt = new WPI_TalonSRX(1);
    bottomBelt = new WPI_TalonSRX(2);
    ballDetection = new ColorSensorV3(Port.kOnboard);

    matcher = new ColorMatch();
  }
  public void runBelt (double x) {
    topBelt.set(x);
    bottomBelt.set(x);
  }

  @Override
  public void periodic() {
  }

  // Control Methods

  // TODO
}
