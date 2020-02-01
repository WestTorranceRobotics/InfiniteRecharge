package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;

public class DriveTrain implements Subsystem {

    private WPI_TalonFX leftLeader;
    private WPI_TalonFX rightLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightFollower;

    private AHRS gyro;

    private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;

    	/** Config Objects for motor controllers */
	TalonFXConfiguration _leftConfig = new TalonFXConfiguration();
    TalonFXConfiguration _rightConfig = new TalonFXConfiguration();

    	/** Invert Directions for Left and Right */
	TalonFXInvertType _leftInvert = TalonFXInvertType.CounterClockwise; //Same as invert = "false"
	TalonFXInvertType _rightInvert = TalonFXInvertType.Clockwise; //Same as invert = "true"
    

    public DriveTrain() {
        		/* Set Neutral Mode */
        leftLeader.setNeutralMode(NeutralMode.Brake);
		rightLeader.setNeutralMode(NeutralMode.Brake);

		/* Configure output and sensor direction */
		leftLeader.setInverted(_leftInvert);
        rightLeader.setInverted(_rightInvert);
        
        leftLeader = new WPI_TalonFX(Constants.RobotMap.CanId.driveLeftLeader);
        rightLeader = new WPI_TalonFX(Constants.RobotMap.CanId.driveRightLeader);

        leftFollower = new WPI_TalonFX(Constants.RobotMap.CanId.driveLeftFollower);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonFX(Constants.RobotMap.CanId.driveRightFollower);
        rightFollower.follow(rightLeader);

        gyro = new AHRS();
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        kinematics = new DifferentialDriveKinematics(Constants.RobotStructure.DriveTrain.trackWidth);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, Constants.RobotStructure.DriveTrain.maxMotorSpeed);
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();


        /* Configure the left Talon's selected sensor as integrated sensor */
        _leftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor; //Local Feedback Source

        /* Configure the Remote (Left) Talon's selected sensor as a remote sensor for the right Talon */
        _rightConfig.remoteFilter0.remoteSensorDeviceID = leftLeader.getDeviceID(); //Device ID of Remote Source
        _rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor; //Remote Source Type

                /* Now that the Left sensor can be used by the master Talon,
        * set up the Left (Aux) and Right (Master) distance into a single
        * Robot distance as the Master's Selected Sensor 0. */
        setRobotDistanceConfigs(_rightInvert, _rightConfig);

        /* FPID for Distance */
        _rightConfig.slot0.kF = Constants.kGains_Distanc.kF;
        _rightConfig.slot0.kP = Constants.kGains_Distanc.kP;
        _rightConfig.slot0.kI = Constants.kGains_Distanc.kI;
        _rightConfig.slot0.kD = Constants.kGains_Distanc.kD;
        _rightConfig.slot0.integralZone = Constants.kGains_Distanc.kIzone;
        _rightConfig.slot0.closedLoopPeakOutput = Constants.kGains_Distanc.kPeakOutput;



        /** Heading Configs */
        // _rightConfig.remoteFilter1.remoteSensorDeviceID = _pidgey.getDeviceID();    //Pigeon Device ID
        // _rightConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw; //This is for a Pigeon over CAN
        // _rightConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor1; //Set as the Aux Sensor
        // _rightConfig.auxiliaryPID.selectedFeedbackCoefficient = 3600.0 / Constants.kPigeonUnitsPerRotation; //Convert Yaw to tenths of a degree

        /* false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
        *   This is typical when the master is the right Talon FX and using Pigeon
        * 
        * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
        *   This is typical when the master is the left Talon FX and using Pigeon
        */
        _rightConfig.auxPIDPolarity = false;

        /* FPID for Heading */
        _rightConfig.slot1.kF = Constants.kGains_Turning.kF;
        _rightConfig.slot1.kP = Constants.kGains_Turning.kP;
        _rightConfig.slot1.kI = Constants.kGains_Turning.kI;
        _rightConfig.slot1.kD = Constants.kGains_Turning.kD;
        _rightConfig.slot1.integralZone = Constants.kGains_Turning.kIzone;
        _rightConfig.slot1.closedLoopPeakOutput = Constants.kGains_Turning.kPeakOutput;


        /* Config the neutral deadband. */
        _leftConfig.neutralDeadband = Constants.kNeutralDeadband;
        _rightConfig.neutralDeadband = Constants.kNeutralDeadband;

        /**
         * 1ms per loop.  PID loop can be slowed down if need be.
         * For example,
         * - if sensor updates are too slow
         * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
         * - sensor movement is very slow causing the derivative error to be near zero.
         */
        int closedLoopTimeMs = 1;
        rightLeader.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.kTimeoutMs);
        rightLeader.configClosedLoopPeriod(1, closedLoopTimeMs, Constants.kTimeoutMs);

        /* Motion Magic Configs */
        _rightConfig.motionAcceleration = 2000; //(distance units per 100 ms) per second
        _rightConfig.motionCruiseVelocity = 2000; //distance units per 100 ms

        /* APPLY the config settings */
        rightLeader.configAllSettings(_leftConfig);
        rightLeader.configAllSettings(_rightConfig);

        /* Set status frame periods to ensure we don't have stale data */
        /* These aren't configs (they're not persistant) so we can set these after the configs.  */
        rightLeader.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
        rightLeader.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
        rightLeader.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, Constants.kTimeoutMs);
        rightLeader.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, Constants.kTimeoutMs);
        leftLeader.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);

        leftLeader.set(mode, demand0, demand1Type, demand1);

    }


    @Override
    public void periodic() {
        odometry.update(getGyro(), leftLeader.getSelectedSensorPosition(), rightLeader.getSelectedSensorPosition());
    }



    // Control methods

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left, right);
    }
  
    // set tank drive by volts when you want to compensate for any drop in battery voltage
    // autonomous may be better using volts
    public void tankDriveVolts(double leftVolts, double rightVolts) { 
        leftLeader.setVoltage(leftVolts); rightLeader.setVoltage(-rightVolts);
    }
    
    public void arcadeDrive(double speed, double turn) {
        differentialDrive.arcadeDrive(speed, turn);
    }

    public void curvatureDrive(double speed, double curve, boolean isQuickTurn) {
        differentialDrive.curvatureDrive(speed, curve, isQuickTurn);
    }

    public void resetOdometry() {
        resetOdometry(new Pose2d(0, 0, new Rotation2d(0, 1)));
    }

    public void resetOdometry(Pose2d start) {
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);
        odometry.resetPosition(start, getGyro());
    }
  
    public Pose2d getLocation() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveKinematicsConstraint getKinematicsConstraint() {
        return trajectoryConstraint;
    }

    private Rotation2d getGyro() {
        double radians = Math.toRadians(90 - gyro.getAngle());
        return new Rotation2d(radians);
    }

    /** 
	 * Determines if SensorSum or SensorDiff should be used 
	 * for combining left/right sensors into Robot Distance.  
	 * 
	 * Assumes Aux Position is set as Remote Sensor 0.  
	 * 
	 * configAllSettings must still be called on the master config
	 * after this function modifies the config values. 
	 * 
	 * @param masterInvertType Invert of the Master Talon
	 * @param masterConfig Configuration object to fill
	 */
	 void setRobotDistanceConfigs(TalonFXInvertType masterInvertType, TalonFXConfiguration masterConfig){
		/**
		 * Determine if we need a Sum or Difference.
		 * 
		 * The auxiliary Talon FX will always be positive
		 * in the forward direction because it's a selected sensor
		 * over the CAN bus.
		 * 
		 * The master's native integrated sensor may not always be positive when forward because
		 * sensor phase is only applied to *Selected Sensors*, not native
		 * sensor sources.  And we need the native to be combined with the 
		 * aux (other side's) distance into a single robot distance.
		 */

		/* THIS FUNCTION should not need to be modified. 
		   This setup will work regardless of whether the master
		   is on the Right or Left side since it only deals with
		   distance magnitude.  */

		/* Check if we're inverted */
		if (masterInvertType == TalonFXInvertType.Clockwise){
			/* 
				If master is inverted, that means the integrated sensor
				will be negative in the forward direction.
				If master is inverted, the final sum/diff result will also be inverted.
				This is how Talon FX corrects the sensor phase when inverting 
				the motor direction.  This inversion applies to the *Selected Sensor*,
				not the native value.
				Will a sensor sum or difference give us a positive total magnitude?
				Remember the Master is one side of your drivetrain distance and 
				Auxiliary is the other side's distance.
					Phase | Term 0   |   Term 1  | Result
				Sum:  -1 *((-)Master + (+)Aux   )| NOT OK, will cancel each other out
				Diff: -1 *((-)Master - (+)Aux   )| OK - This is what we want, magnitude will be correct and positive.
				Diff: -1 *((+)Aux    - (-)Master)| NOT OK, magnitude will be correct but negative
			*/

			masterConfig.diff0Term = FeedbackDevice.IntegratedSensor; //Local Integrated Sensor
			masterConfig.diff1Term = FeedbackDevice.RemoteSensor0;   //Aux Selected Sensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference; //Diff0 - Diff1
		} else {
			/* Master is not inverted, both sides are positive so we can sum them. */
			masterConfig.sum0Term = FeedbackDevice.RemoteSensor0;    //Aux Selected Sensor
			masterConfig.sum1Term = FeedbackDevice.IntegratedSensor; //Local IntegratedSensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorSum; //Sum0 + Sum1
		}

		/* Since the Distance is the sum of the two sides, divide by 2 so the total isn't double
		   the real-world value */
		masterConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
	 }
}

