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
  private Loader loader;
 
  public ShuffleboardTab turretDebug; 
  public ShuffleboardTab panelControllerDebug; 
  public ShuffleboardTab shooterDebug;
  public ShuffleboardTab loaderDebug;
  public ShuffleboardTab intakeDebug;
  public ShuffleboardTab hangerDebug;
  public ShuffleboardTab driveTrainDebug;
  public ShuffleboardTab cameraDebug;
  public ShuffleboardTab limeLightDebug;

  public debugFeed(int[] debugGet, Shooter shooter, Turret turret, PanelController panelController, DriveTrain driveTrain, Intake intake, Hanger hanger, Camera camera, Loader loader) {
    this.debugGet = debugGet;
    this.shooter = shooter;
    this.turret = turret;
    this.panelController = panelController;
    this.driveTrain = driveTrain;
    this.intake = intake;
    this.hanger = hanger;
    this.loader = loader;
    this.camera = camera;
  }

  @Override
  public void initialize() {   
    if (debugGet[0] == 1) {
      this.turretDebug = Shuffleboard.getTab("Turret Debug");
    }
    if (debugGet[1] == 1) {   
      this.shooterDebug = Shuffleboard.getTab("Shooter Debug");
    }
    if (debugGet[2] == 1) {
      this.panelControllerDebug = Shuffleboard.getTab("Panel Debug");
    }
    if (debugGet[3] == 1) {
      this.loaderDebug = Shuffleboard.getTab("Loader Debug");
    }
    if (debugGet[4] == 1) {
      this.intakeDebug = Shuffleboard.getTab("Intake Debug");
    }
    if (debugGet[5] == 1) {
      this.hangerDebug = Shuffleboard.getTab("Hanger Debug");
    }
    if (debugGet[6] == 1) {
      this.driveTrainDebug = Shuffleboard.getTab("DriveTrain Debug");
    }
    if (debugGet[7] == 1) {
      this.cameraDebug = Shuffleboard.getTab("Camera Debug");
    }
    if (debugGet[8] == 1) {
      this.limeLightDebug = Shuffleboard.getTab("LimeLight Debug"); 
    }
        
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (debugGet[0] == 1) {
      turretDebug.addNumber("Motor Rpm", () -> turret.getDegrees() );
      turretDebug.addNumber("Motor Current", () -> turret.getCurrent());
      turretDebug.addNumber("Motor Voltage", () -> turret.getVoltage() );
    }
    if (debugGet[1] == 1) {   
      shooterDebug.addNumber("Motor Rpm", () -> shooter.getVelocity() );
      shooterDebug.addNumber("Motor Current", () -> shooter.getCurrent() );
      shooterDebug.addNumber("Motor Voltage", () -> shooter.getVoltage() );
    }
    if (debugGet[2] == 1) {
      panelControllerDebug.addNumber("Null", () -> 0);
    }
    if (debugGet[3] == 1) {
      loaderDebug.addNumber("Top Motor Voltage", () -> loader.getVoltage() );
    }
    if (debugGet[4] == 1) {
      intakeDebug.addNumber("Null", () -> 0);
    }
    if (debugGet[5] == 1) {
      hangerDebug.addNumber("Null", () -> 0);
    }
    if (debugGet[6] == 1) {
      driveTrainDebug.addNumber("Null", () -> 0);
    }
    if (debugGet[7] == 1) {
      cameraDebug.addNumber("Null", () -> 0);
    }
    if (debugGet[8] == 1) {
      limeLightDebug.addNumber("null", () -> 0);
    }
    Shuffleboard.update();
  }
}
