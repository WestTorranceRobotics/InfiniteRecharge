package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
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
    private AHRS gyro;
    private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;
    private PIDController angleController = new PIDController(0, 0, 0);

    public DriveTrain() {

        leftLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.leftLeaderCanID);
        rightLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.rightLeaderCanID);

        leftFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.leftFollowerCanID);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.rightFollowerCanID);
        rightFollower.follow(rightLeader);

        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);

        gyro = new AHRS(SPI.Port.kMXP);
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        kinematics = new DifferentialDriveKinematics(30);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, 100);
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();

        gyro.reset();
        gyro.zeroYaw();
    }

    @Override
    public void periodic() {
        //the following is test code**********************************the following is test code

    double l = rightLeader.getSensorCollection().getIntegratedSensorAbsolutePosition();
    double r = leftLeader.getSensorCollection().getIntegratedSensorAbsolutePosition();
    
        odometry.update(getGyro(), -l * (18.0f/28.0f) * (10.0f/64.0f) * 0.1524f * Math.PI * (1.0f/2048.0f), r * (18.0f/28.0f) * (10.0f/64.0f) * 0.1524f * Math.PI * (1.0f/2048.0f)) ;
        SmartDashboard.putNumber("X", odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("Y", odometry.getPoseMeters().getTranslation().getY());
        SmartDashboard.putNumber("encodeyBoy", Math.abs(l * (18.0f/28.0f) * (10.0f/64.0f) * 0.1524f * Math.PI * (1.0f/2048.0f)));
        SmartDashboard.putNumber("encodeyGuy", Math.abs(r * (18.0f/28.0f) * (10.0f/64.0f) * 0.1524f * Math.PI * (1.0f/2048.0f)));
        //(18.0f/28.0f) = gearRatio; (10.0f/64.0f) = gearRatio2; 0.1524f * Math.PI = WheelDiameter; (1.0f/2048.0f) = 1 revoltion/ 2048 counts;
        SmartDashboard.putNumber("angle", getGryoDegree());
        SmartDashboard.putNumber("Target Value", Math.toDegrees(Math.atan2(10,10)));
        SmartDashboard.putNumber("Power",  angleController.calculate(getGryoDegree(), 45));
        SmartDashboard.updateValues();
    }

    // Control methods

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left,right);   
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

    public AHRS getGyroScope(){
        return gyro;
    }
    public double getGryoDegree() {
        return gyro.getAngle();
    }
}
