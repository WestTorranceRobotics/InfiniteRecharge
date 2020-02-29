/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto.runpos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.DriveTrain;

import com.ctre.phoenix.CANifier.LEDChannel;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


public class RunDistance extends CommandBase {
private Pose2d currentPos;
private double targetTheta;
private DriveTrain driveTrain;
private double transX;
private double transY;
private double targetDistance;
private double currentDistance;
private WPI_TalonFX leftLeader;
private WPI_TalonFX rightLeader;
/* We allow either a 0 or 1 when selecting an ordinal for remote devices [You can have up to 2 devices assigned remotely to a talon/victor] */
public final int REMOTE_0 = 0;
public final int REMOTE_1 = 1;
/* We allow either a 0 or 1 when selecting a PID Index, where 0 is primary and 1 is auxiliary */
public final int PID_PRIMARY = 0;
public final int PID_TURN = 1;
/* Firmware currently supports slots [0, 3] and can be used for either PID Set */
public final int SLOT_0 = 0;
public final int SLOT_1 = 1;
public final int SLOT_2 = 2;
public final int SLOT_3 = 3;
/* ---- Named slots, used to clarify code ---- */
public final int kSlot_Distanc = SLOT_0;
public final int kSlot_Turning = SLOT_1;
public final int kSlot_Velocit = SLOT_2;
public final int kSlot_MotProf = SLOT_3;
/*distance PIDF/ kGains_Distanc */
private double distancekP = 0.1;
private double distancekI = 0.0;
private double distancekD = 0.0;
private double distancekF = 0.0;
private int distancekIz = 100;
private double distancekPeakOut = 0.5;

/*Turning PIDF/ kGains_Turning */

private double turningkP = 2;
private double turningkI = 0.0;
private double turningkD = 4.0;
private double turningkF = 0.0;
private int turningkIz = 200;
private double turningkPeakOut = 1.00;


/**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
  public final static int kTimeoutMs = 30;
  
private double targetAngle;

private boolean isDone = false;




  /**
   * Creates a new RunToPosition.
   */
  /**
   * 
   * Creates a new RunToPosition.
   * 
   * @param transX translation in X axis
   * @param transY translation in Y axis
   * 
   */
  public RunDistance(DriveTrain subsystem, double transX, double transY) {
    driveTrain = subsystem;
    addRequirements(driveTrain);
    this.transX = transX;
    this.transY = transY;
      // Use addRequirements() here to declare subsystem dependencies.
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftLeader = driveTrain.getLeftLeader();
    rightLeader = driveTrain.getRightLeader();

    //Intialization
    // leftLeader.set(ControlMode.PercentOutput, 0);
    // rightLeader.set(ControlMode.PercentOutput, 0);


    rightLeader.configFactoryDefault();
		leftLeader.configFactoryDefault();


   /* Configure the left Talon's selected sensor as local QuadEncoder */
		leftLeader.configSelectedFeedbackSensor(	FeedbackDevice.IntegratedSensor,				// Local Feedback Source
    PID_PRIMARY,					// PID Slot for Source [0, 1]
    kTimeoutMs);					// Configuration Timeout

/* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
 rightLeader.configRemoteFeedbackFilter(leftLeader.getDeviceID(),					// Device ID of Source
  RemoteSensorSource.TalonSRX_SelectedSensor,	// Remote Feedback Source
  REMOTE_0,							// Source number [0, 1]
  kTimeoutMs);						// Configuration Timeout

/* Setup Sum signal to be used for Distance */
rightLeader.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, kTimeoutMs);				// Feedback Device of Remote Talon
rightLeader.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.IntegratedSensor, kTimeoutMs);	// Integreated Sensor of current Talon

/* Setup Difference signal to be used for Turn */
rightLeader.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, kTimeoutMs);
rightLeader.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.IntegratedSensor, kTimeoutMs);

/* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
rightLeader.configSelectedFeedbackSensor(	FeedbackDevice.SensorSum, 
    PID_PRIMARY,
    kTimeoutMs);

/* Scale Feedback by 0.5 to half the sum of Distance */
rightLeader.configSelectedFeedbackCoefficient(	0.5, 						// Coefficient
      PID_PRIMARY,		// PID Slot of Source 
      kTimeoutMs);		// Configuration Timeout

/* Configure Difference [Difference between both Integrated] to be used for Auxiliary PID Index */
rightLeader.configSelectedFeedbackSensor(	FeedbackDevice.SensorDifference, 
    PID_TURN, 
    kTimeoutMs);

/* Scale the Feedback Sensor using a coefficient */
rightLeader.configSelectedFeedbackCoefficient(	1,
      PID_TURN, 
      kTimeoutMs);

/* Configure output and sensor direction */
// leftLeader.setInverted(false);
// leftLeader.setSensorPhase(true);
// rightLeader.setInverted(true); 
// rightLeader.setSensorPhase(true);

/* Set status frame periods to ensure we don't have stale data */
rightLeader.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, kTimeoutMs);
rightLeader.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20,kTimeoutMs);
rightLeader.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20,kTimeoutMs);
rightLeader.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, kTimeoutMs);
rightLeader.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, kTimeoutMs);

// /* Configure neutral deadband */
// _rightMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
// _leftMaster.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

/* Motion Magic Configurations */
rightLeader.configMotionAcceleration(2000, kTimeoutMs);
rightLeader.configMotionCruiseVelocity(2000, kTimeoutMs);

/**
* Max out the peak output (for all modes).  
* However you can limit the output of a given PID object with configClosedLoopPeakOutput().
*/
leftLeader.configPeakOutputForward(+1.0, kTimeoutMs);
leftLeader.configPeakOutputReverse(-1.0, kTimeoutMs);
rightLeader.configPeakOutputForward(+1.0, kTimeoutMs);
rightLeader.configPeakOutputReverse(-1.0, kTimeoutMs);

/* FPID Gains for distance servo */
rightLeader.config_kP(kSlot_Distanc, distancekP, kTimeoutMs);
rightLeader.config_kI(kSlot_Distanc, distancekI, kTimeoutMs);
rightLeader.config_kD(kSlot_Distanc, distancekD, kTimeoutMs);
rightLeader.config_kF(kSlot_Distanc, distancekF, kTimeoutMs);
rightLeader.config_IntegralZone(kSlot_Distanc, distancekIz, kTimeoutMs);
rightLeader.configClosedLoopPeakOutput(kSlot_Distanc, distancekPeakOut, kTimeoutMs);
rightLeader.configAllowableClosedloopError(kSlot_Distanc, 0, kTimeoutMs);

/* FPID Gains for turn servo */
rightLeader.config_kP(kSlot_Turning, turningkP, kTimeoutMs);
rightLeader.config_kI(kSlot_Turning, turningkI, kTimeoutMs);
rightLeader.config_kD(kSlot_Turning, turningkD, kTimeoutMs);
rightLeader.config_kF(kSlot_Turning, turningkF, kTimeoutMs);
rightLeader.config_IntegralZone(kSlot_Turning, turningkIz, kTimeoutMs);
rightLeader.configClosedLoopPeakOutput(kSlot_Turning, turningkPeakOut, kTimeoutMs);
rightLeader.configAllowableClosedloopError(kSlot_Turning, 0, kTimeoutMs);

/**
* 1ms per loop.  PID loop can be slowed down if need be.
* For example,
* - if sensor updates are too slow
* - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
* - sensor movement is very slow causing the derivative error to be near zero.
*/
int closedLoopTimeMs = 1;
rightLeader.configClosedLoopPeriod(0, closedLoopTimeMs, kTimeoutMs);
rightLeader.configClosedLoopPeriod(1, closedLoopTimeMs, kTimeoutMs);

/**
* configAuxPIDPolarity(boolean invert, int timeoutMs)
* false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
* true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
*/
rightLeader.configAuxPIDPolarity(false, kTimeoutMs);


  targetAngle = rightLeader.getSelectedSensorPosition(1);

 targetDistance = Math.sqrt((transX*transX)+(transY*transY)) * driveTrain.TICKS_PER_INCH;

 leftLeader.setSelectedSensorPosition(0);
  rightLeader.setSelectedSensorPosition(0);

 rightLeader.selectProfileSlot(kSlot_Distanc, PID_PRIMARY);
 rightLeader.selectProfileSlot(kSlot_Turning, PID_TURN);
  
}
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      rightLeader.set(ControlMode.Position, targetDistance, DemandType.AuxPID, targetAngle);
     
      leftLeader.follow(rightLeader, FollowerType.AuxOutput1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leftLeader.set(ControlMode.PercentOutput, 0);
    rightLeader.set(ControlMode.PercentOutput, 0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}

