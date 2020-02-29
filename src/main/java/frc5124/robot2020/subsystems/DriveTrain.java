package frc5124.robot2020.subsystems;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc5124.robot2020.RobotContainer;
import frc5124.robot2020.RobotMap;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveTrain extends SubsystemBase {
    public WPI_TalonFX leftLeader;
    public WPI_TalonFX rightLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightFollower;
    private AHRS gyro;
    private DifferentialDrive differentialDrive;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveKinematicsConstraint trajectoryConstraint;
    private DifferentialDriveOdometry odometry;
    private PIDController angleController = new PIDController(0.00125,0.00005,0.000005);
    private ShuffleboardTab debuggingTab;
    
    private double INCHES_PER_TICK = (18.0f/28.0f) * (10.0f/64.0f) * 6.0f * Math.PI * (1.0f/2048.0f);
    private double TICK_PER_INCHES = 40 * (1.0/(Math.PI * 6.0) * 2048.0 * (64.0/10.0) * (28.0/18.0));

    public DriveTrain() {

        leftLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.leftLeaderCanID);
        rightLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.rightLeaderCanID);

        leftFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.leftFollowerCanID);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.rightFollowerCanID);
        rightFollower.follow(rightLeader);

        rightLeader.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);
        leftLeader.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);

        leftLeader.setInverted(true);
        
        rightLeader.setInverted(false);

        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster); 

        gyro = new AHRS(SPI.Port.kMXP);
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);

        kinematics = new DifferentialDriveKinematics(RobotMap.DriveTrainMap.kTrackwidth);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, RobotMap.DriveTrainMap.kMaxVelocity);
        gyro.reset();
        gyro.zeroYaw();
        
        if (RobotMap.debugEnabled) {
            debuggingTab = Shuffleboard.getTab("Drive Train Debug");
            debuggingTab.addNumber("Left Leader Current", () -> leftLeader.getStatorCurrent())
            .withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
            debuggingTab.addNumber("Right Leader Current", () -> rightLeader.getStatorCurrent())
            .withPosition(0, 2).withSize(3, 2).withWidget(BuiltInWidgets.kGraph);
            debuggingTab.addNumber("X position", () -> odometry.getPoseMeters().getTranslation().getX())
            .withPosition(3, 0).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
            debuggingTab.addNumber("Y position", () -> odometry.getPoseMeters().getTranslation().getY())
            .withPosition(3, 1).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
            debuggingTab.addNumber("Left Encoder Position", () -> leftLeader.getSelectedSensorPosition() * INCHES_PER_TICK)
            .withPosition(3, 2).withSize(1, 1);
            debuggingTab.addNumber("Left Encoder Position", () -> leftLeader.getSelectedSensorPosition() * INCHES_PER_TICK)
            .withPosition(4, 2).withSize(1, 1);
            debuggingTab.add("Rotation", RobotContainer.shuffleboardGyro(
                () -> 90 - getLocation().getRotation().getDegrees())
            ).withWidget(BuiltInWidgets.kGyro).withSize(3, 3).withPosition(3, 0);
        }
        last = gyro.getPitch();
        odometry = new DifferentialDriveOdometry(getGyro());
        resetOdometry();
    }

    @Override
    public void periodic() {
        double r = rightLeader.getSelectedSensorPosition();
        double l = leftLeader.getSelectedSensorPosition();
        
        odometry.update(getGyro(), l * INCHES_PER_TICK, r * INCHES_PER_TICK);

        preventWrapping(gyro.getPitch());
        //(18.0f/28.0f) = gearRatio; (10.0f/64.0f) = gearRatio2; 0.1524f * Math.PI = WheelDiamet[=p-er; (1.0f/2048.0f) = 1 revoltion/ 2048 counts;
    }


    // Control methods

    public void tankDrive(double left, double right) {
        differentialDrive.tankDrive(left,-right);   
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
    public DifferentialDriveKinematics getKinematics(){
        return kinematics;
    }
     
    public WPI_TalonFX getLeftLeader(){
        return leftLeader;
    }
    public WPI_TalonFX getRightLeader(){
        return rightLeader;
    }

    public void resetOdometry(Pose2d start) {
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);
        odometry.resetPosition(start, getGyro());
    }

    public Pose2d getLocation() {
        return odometry.getPoseMeters();
    }
    public PIDController getPID(){
        return angleController;
    }

    public DifferentialDriveKinematicsConstraint getKinematicsConstraint() {
        return trajectoryConstraint;
    }

    public DifferentialDriveOdometry getOdometry(){
        return odometry;
    }

    private static double last;
    private static double preventWrapping(double x) {
        while (last - 180 > x) {
            x += 360;
        }
        while (last + 360 < x) {
            x -= 360;
        }
        last = x;
        return x;
    }

    private Rotation2d getGyro() {
        double radians = Math.toRadians(preventWrapping(90 - last));
        return new Rotation2d(radians);
    }

    public double getGryoDegree() {
        return gyro.getAngle();
    }

    public DifferentialDriveWheelSpeeds wheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            leftLeader.getSelectedSensorVelocity() * 10 * INCHES_PER_TICK,
            rightLeader.getSelectedSensorVelocity() * 10 * INCHES_PER_TICK
        );
    }

    public int getLeftEncoderVal(){
        return leftLeader.getSelectedSensorPosition();
    }
}