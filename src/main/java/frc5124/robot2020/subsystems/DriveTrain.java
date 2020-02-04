package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;

public class DriveTrain implements Subsystem {
    public WPI_TalonFX leftLeader;
    public WPI_TalonFX rightLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightFollower;
    private SimpleWidget odometryWidget;
    private AHRS gyro;
    // private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;
    

    public DriveTrain() {

        leftLeader = new WPI_TalonFX(RobotMap.DriveTrain.leftLeaderCanId);
        rightLeader = new WPI_TalonFX(RobotMap.DriveTrain.rightLeaderCanId);

        leftFollower = new WPI_TalonFX(RobotMap.DriveTrain.leftFollowerCanId);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonFX(RobotMap.DriveTrain.rightFollowerCanId);
        rightFollower.follow(rightLeader);

        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);

        gyro = new AHRS(SPI.Port.kMXP);
        
        // differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        // differentialDrive.setSafetyEnabled(true);
        // differentialDrive.setExpiration(0.1);
        // differentialDrive.setMaxOutput(1.0);

        kinematics = new DifferentialDriveKinematics(30);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, 100);
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();

        gyro.reset();
        gyro.zeroYaw();
    }

    @Override
    public void periodic() {
    double l = rightLeader.getSensorCollection().getIntegratedSensorAbsolutePosition();
    double r = leftLeader.getSensorCollection().getIntegratedSensorAbsolutePosition();
    
        odometry.update(getGyro(), l * (18/28) * (10/64) * (1/2048) * (.1524*Math.PI), r * (18/28) * (10/64) * (1/2048) * (.1524*Math.PI)) ;
        SmartDashboard.putNumber("X", odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("Y", odometry.getPoseMeters().getTranslation().getY());
        SmartDashboard.putNumber("encodeyBoy", l * (18/28) * (10/64) * (1/2048) * (6*Math.PI));
        SmartDashboard.putNumber("angle", getGryoDegree());
        SmartDashboard.updateValues();
    }

    // Control methods

    // public void tankDrive(double left, double right) {
    //     differentialDrive.tankDrive(left,right);   
    //  }

    // public void arcadeDrive(double speed, double turn) {
    //     differentialDrive.arcadeDrive(speed, turn);
    // }

    // public void curvatureDrive(double speed, double curve, boolean isQuickTurn) {
    //     differentialDrive.curvatureDrive(speed, curve, isQuickTurn);
    // }

    public void resetOdometry() {
        resetOdometry(new Pose2d(0, 0, new Rotation2d(0, 1)));
    }

    public void directPower(double power){
        rightLeader.set(power);
        leftLeader.set(power);
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
    public DifferentialDriveOdometry getOdometry(){
        return odometry;
    }

    private Rotation2d getGyro() {
        double radians = Math.toRadians(90 - gyro.getAngle());
        return new Rotation2d(radians);
    }
    public double getGryoDegree() {
        return gyro.getAngle();
    }
}