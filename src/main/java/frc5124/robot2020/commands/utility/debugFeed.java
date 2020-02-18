/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.utility;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.*;
import frc5124.robot2020.subsystems.Shooter;

public class debugFeed extends CommandBase {
  private int[] debugGet = new int[9];
  private Shooter shooter;
  private Turret turret;
  private PanelController panelController;
  private DriveTrain driveTrain;
  private Intake intake;
  private Hanger hanger;
  private Camera camera;

  public debugFeed(int[] debugGet, Shooter shooter, Turret turret, PanelController panelController, DriveTrain driveTrain, Intake intake, Hanger hanger, Camera camera) {
    this.debugGet = debugGet;
   // this.
  }

  @Override
  public void initialize() {   
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (debugGet[0] == 1) {
    SmartDashboard.putNumber("TurretDegrees", 0);
    SmartDashboard.putNumber("TurretCurrent", 0);
    SmartDashboard.putNumber("TurretVoltage", 0);
    }
    if (debugGet[1] == 1) {   
    SmartDashboard.putNumber("ShooterCurrent", 0);
    SmartDashboard.putNumber("ShooterVelocity", 0);
    SmartDashboard.putNumber("ShooterVoltage", 0);
      SmartDashboard.putNumber("ShooterRPM", 0);
      ShuffleboardTab shooterDebug = Shuffleboard.getTab("Shooter Debug");
      //shooterDebug.addNumber("Motor Rpm", () -> );
    }
    if (debugGet[2] == 1) {
      //panel
    }
    if (debugGet[3] == 1) {
      //loader
    }
    if (debugGet[4] == 1) {
      //intake
    }
    if (debugGet[5] == 1) {
      //hanger
    }
    if (debugGet[6] == 1) {
      //dt
    }
    if (debugGet[7] == 1) {
      //camera
    }
    if (debugGet[8] == 1) {
      //LM
    }
    if (debugGet[9] == 1) {
      //DT
    }
  }
}
