package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveTrain implements Subsystem {


    public DriveTrain() {
        
    }

    @Overridepackage frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;

public class DriveTrainEndover implements Subsystem {

    private WPI_TalonSRX leftLeader;
    private WPI_TalonSRX rightLeader;
    private WPI_TalonSRX leftFollower;
    private WPI_TalonSRX rightFollower;
    SimpleWidget odometryWidget;

    private AHRS gyro;

    private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;

    public DriveTrainEndover() {
        leftLeader = new WPI_TalonSRX(RobotMap.CanId.driveLeftLeader);
        rightLeader = new WPI_TalonSRX(RobotMap.CanId.driveRightLeader);

        leftFollower = new WPI_TalonSRX(RobotMap.CanId.driveLeftFollower);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonSRX(RobotMap.CanId.driveRightFollower);
        rightFollower.follow(rightLeader);

        gyro = new AHRS();
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        kinematics = new DifferentialDriveKinematics(RobotMap.DriveTrain.trackWidth);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, RobotMap.DriveTrain.maxMotorSpeed);
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();

        
        String shuffleTabName = "Driving Display";
        ShuffleboardTab drivingDisplay = Shuffleboard.getTab(shuffleTabName);
        Shuffleboard.selectTab(shuffleTabName);
        odometryWidget = drivingDisplay.add("Location",getLocation()).withSize(2, 1).withPosition(0, 0);;

    }

    @Override
    public void periodic() {
        odometry.update(getGyro(), leftLeader.getSelectedSensorPosition(), rightLeader.getSelectedSensorPosition());
        odometryWidget.getEntry();
    }

    // Control methods

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left,right)
;    }

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
}


    public void periodic() {

    }
    public void tankDrive(double left, double right) {
        
    }
  
}

