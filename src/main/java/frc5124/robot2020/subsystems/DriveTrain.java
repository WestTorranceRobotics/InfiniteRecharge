package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;

public class DriveTrain implements Subsystem {

    private WPI_TalonSRX leftLeader;
    private WPI_TalonSRX rightLeader;
    private WPI_TalonSRX leftFollower;
    private WPI_TalonSRX rightFollower;
    private Encoder leftGroup;
    private Encoder rightGroup;
    SimpleWidget odometryWidget;
    private AHRS gyro;

    private double kP = 1;
    //Proptional of PID

    private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;

    public DriveTrain() {

        leftLeader = new WPI_TalonSRX(RobotMap.DriveTrain.leftLeaderCanId);
        rightLeader = new WPI_TalonSRX(RobotMap.DriveTrain.rightLeaderCanId);

        leftFollower = new WPI_TalonSRX(RobotMap.DriveTrain.leftFollowerCanId);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonSRX(RobotMap.DriveTrain.rightFollowerCanId);
        rightFollower.follow(rightLeader);
        
    

        gyro = new AHRS();
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);
       

        kinematics = new DifferentialDriveKinematics(30);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, 100);
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();
        
        leftLeader.setNeutralMode(NeutralMode.Brake);
        rightLeader.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);

        //sets motors to break mode not coast


    }

    @Override
    public void periodic() {
        double leftGROUP = (double) leftGroup.get();

        double rightGROUP = (double) rightGroup.get();

        odometry.update(getGyro(), leftGROUP, rightGROUP);
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
    public TalonSRX getleftLeader(){
        return leftLeader;
    }
    public Encoder getEncoderLeft(){
        return leftGroup;
    }
    public Encoder getEncoderRight(){
        return rightGroup;
    }

    public DifferentialDriveKinematicsConstraint getKinematicsConstraint() {
        return trajectoryConstraint;
    }
    public DifferentialDriveOdometry getOdometry(){
        return odometry;
    }

    public void setMode(ControlMode mode){
        leftLeader.set(mode,0);
        rightLeader.set(mode,0);
    }

    private Rotation2d getGyro() {
        double radians = Math.toRadians(90 - gyro.getAngle());
        return new Rotation2d(radians);
    }
} 