/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Camera implements Subsystem {
  private NetworkTableEntry shuffleboardButtonBooleanEntry;
  private ShuffleboardTab display;
  
  public Camera() {
  }

  @Override
  public void periodic() {
    if (RobotMap.debugEnabled) {}
  }

  // Control Methods

  // TODO
}
