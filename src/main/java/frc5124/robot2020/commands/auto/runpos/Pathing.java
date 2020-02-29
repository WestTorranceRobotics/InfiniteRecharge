/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runpos;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc5124.robot2020.RobotMap.DriveTrainMap;
import frc5124.robot2020.subsystems.DriveTrain;

public class Pathing extends RamseteCommand {

  /**
   * Creates a new ZoomZoom.
   */
  public Pathing(Trajectory path, DriveTrain drivebase) {
    super(
      path,
      drivebase::getLocation,
      new RamseteController(1,0.5),
      new SimpleMotorFeedforward(DriveTrainMap.kS, DriveTrainMap.kV, DriveTrainMap.kA),
      new DifferentialDriveKinematics(DriveTrainMap.kTrackwidth),
      drivebase::wheelSpeeds,
      new PIDController(DriveTrainMap.kP, 0, 0),
      new PIDController(DriveTrainMap.kP, 0, 0),
      drivebase::tankDrive,
      drivebase
    );
  }
}
