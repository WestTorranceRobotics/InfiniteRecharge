package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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

    public DriveTrain() {
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
    }


    @Override
    public void periodic() {
        odometry.update(getGyro(), leftLeader.getSelectedSensorPosition(), rightLeader.getSelectedSensorPosition());
    }



    // Control methods

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left, right);
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
}

