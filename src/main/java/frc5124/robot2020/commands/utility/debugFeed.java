/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.utility;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.*;
import frc5124.robot2020.subsystems.Shooter;

public class debugFeed extends CommandBase {
  private int[] debugGet;
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
    this.shooter = shooter;
    this.turret = turret;
    this.panelController = panelController;
    this.driveTrain = driveTrain;
    this.intake = intake;
    this.hanger = hanger;
    this.loader = loader;
    this.camera = camera;
    this.debugGet = debugGet;
  }

  @Override
  public void initialize() {   
    if (debugGet[0] == 1) {
      this.turretDebug = Shuffleboard.getTab("Turret Debug");
      turretDebug.addNumber("P", () -> 0);
      turretDebug.addNumber("I", () -> 0);
      turretDebug.addNumber("D", () -> 0);
      turretDebug.addNumber("F", () -> 0);
      turretDebug.addNumber("Izone", () -> 0);
      turretDebug.addNumber("Reference", () -> 0);
      
    }
    if (debugGet[1] == 1) {   
      this.shooterDebug = Shuffleboard.getTab("Shooter Debug");
      shooterDebug.addNumber("P", () -> 0);
      shooterDebug.addNumber("I", () -> 0);
      shooterDebug.addNumber("D", () -> 0);
      shooterDebug.addNumber("F", () -> 0);
      shooterDebug.addNumber("Izone", () -> 0);
      shooterDebug.addNumber("Reference", () -> 0);
    }
    if (debugGet[2] == 1) {
      this.panelControllerDebug = Shuffleboard.getTab("Panel Debug");
    }
    if (debugGet[3] == 1) {
      this.loaderDebug = Shuffleboard.getTab("Loader Debug");
    }
    if (debugGet[4] == 1) {
      intakeDebug = Shuffleboard.getTab("Intake Debug");
    }
    if (debugGet[5] == 1) {
      this.hangerDebug = Shuffleboard.getTab("Hanger Debug");
    }
    if (debugGet[6] == 1) {
      this.driveTrainDebug = Shuffleboard.getTab("DriveTrain Debug");
      driveTrainDebug.addNumber("P", () -> 0);
      driveTrainDebug.addNumber("I", () -> 0);
      driveTrainDebug.addNumber("D", () -> 0);
      driveTrainDebug.addNumber("F", () -> 0);
      driveTrainDebug.addNumber("Izone", () -> 0);
      driveTrainDebug.addNumber("Reference", () -> 0);
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
      turretDebug.addNumber("Motor Rpm", () -> turret.getDegrees());
      turretDebug.addNumber("Motor Current", () -> 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).withPosition(1, 0);
     // .add("test", 1).withSize(2, 2).withWidget(BuiltInWidgets.kTextView).withPosition(1, 0);
    }
    if (debugGet[1] == 1) {   
      shooterDebug.addNumber("Motor Rpm", () -> shooter.getVelocity() );
      shooterDebug.addNumber("Motor Current", () -> shooter.getCurrent() );
      shooterDebug.addNumber("Ball Count", () -> 0);
  
    }
    if (debugGet[2] == 1) {
      panelControllerDebug.addBoolean("Deployed", () -> panelController.isDeployed());
    }
    if (debugGet[3] == 1) {
      loaderDebug.addNumber("Sensor Voltage", () -> loader.getVoltage() );
      loaderDebug.addNumber("Top Motor Current", () -> loader.topBeltCurrent() );
      loaderDebug.addNumber("Bottom Motor Current", () -> loader.bottomBeltCurrent() );
    }
    if (debugGet[4] == 1) {
      intakeDebug.add("Intake Motor Current", intake.getIntakeMotorCurrent()).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
      intakeDebug.addBoolean("Intake Deployed", () -> intake.isDeployed() ).withPosition(3, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
    }
    if (debugGet[5] == 1) {
      hangerDebug.addNumber("Hanger Motor Current", () -> hanger.getHangerMotorCurrent());
      hangerDebug.addBoolean("Top Limit", () -> hanger.reachedTopLimit());
      hangerDebug.addBoolean("Bottom Limit", () -> hanger.reachedBottomLimit());
    }
    if (debugGet[6] == 1) {
      driveTrainDebug.addNumber("X - Feet", () -> driveTrain.getLocation().getTranslation().getX());
      driveTrainDebug.addNumber("Y - Feet", () -> driveTrain.getLocation().getTranslation().getY());
      driveTrainDebug.addNumber("Left Motor Current", () -> driveTrain.getLeftPower());
      driveTrainDebug.addNumber("Right Motor Current", () -> driveTrain.getRightPower());
    }
    if (debugGet[7] == 1) {
      cameraDebug.addNumber("Null", () -> 0);
      //cameraDebug.add(video)
    }
    if (debugGet[8] == 1) {
      limeLightDebug.addNumber("null", () -> 0);
    }
    turretDebug.addNumber("Motor Current", () -> 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).withPosition(1, 0);
    
  
  }
}
