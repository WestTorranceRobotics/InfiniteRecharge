package frc5124.robot2020.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.InvertType;

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
    private PIDController angleController = new PIDController(0.00125,0.00005,0.000005);
    
    private double INCHES_PER_TICK = (18.0f/28.0f) * (10.0f/64.0f) * 6.0f * Math.PI * (1.0f/2048.0f);
    private double TICK_PER_INCHES = 40 * (1.0/(Math.PI * 6.0) * 2048.0 * (64.0/10.0) * (28.0/18.0));

    public DriveTrain() {

        leftLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.leftLeaderCanID);
        rightLeader = new WPI_TalonFX(RobotMap.DriveTrainMap.rightLeaderCanID);

        leftFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.leftFollowerCanID);
        leftFollower.follow(leftLeader);
        rightFollower = new WPI_TalonFX(RobotMap.DriveTrainMap.rightFollowerCanID);
        rightFollower.follow(rightLeader);

        leftLeader.setInverted(true);
        
        rightLeader.setInverted(false);

        leftFollower.setInverted(InvertType.FollowMaster);
        rightFollower.setInverted(InvertType.FollowMaster); 

        //  /*
        //  * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
        //  */
        // rightLeader.setSensorPhase(true);
        // leftLeader.setSensorPhase(true);


        gyro = new AHRS(SPI.Port.kMXP);
        
        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
        differentialDrive.setSafetyEnabled(true);

        kinematics = new DifferentialDriveKinematics(30);
        trajectoryConstraint = new DifferentialDriveKinematicsConstraint(kinematics, RobotMap.DriveTrainMap.maxV);
        odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(90 - gyro.getAngle())));
        resetOdometry();

        gyro.reset();
        gyro.zeroYaw();
    }

    @Override
    public void periodic() {
        //the following is test code**********************************the following is test code

        double r = rightLeader.getSelectedSensorPosition();
        double l = leftLeader.getSelectedSensorPosition();
        
        odometry.update(getGyro(), l * INCHES_PER_TICK, r * INCHES_PER_TICK);
        SmartDashboard.putNumber("X", odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("Y", odometry.getPoseMeters().getTranslation().getY());
        SmartDashboard.putNumber("encodeyBoy", l * INCHES_PER_TICK);
        SmartDashboard.putNumber("encodeyGuy", r * INCHES_PER_TICK);
        SmartDashboard.putNumber("Target Distance", 40 *TICK_PER_INCHES);

        //(18.0f/28.0f) = gearRatio; (10.0f/64.0f) = gearRatio2; 0.1524f * Math.PI = WheelDiamet[=p-er; (1.0f/2048.0f) = 1 revoltion/ 2048 counts;

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

    public double getTICKS_PER_INCHES(){
        return TICK_PER_INCHES;
    }

    public void directPower(double power){
        rightLeader.set(power);
        leftLeader.set(power);
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

    private Rotation2d getGyro() {
        gyro.getRoll();
        double radians = Math.toRadians(90 - gyro.getAngle());
        return new Rotation2d(radians);
    }

    public AHRS getGyroScope(){
        return gyro;
    }

    public double getGyroDegree() {
        return gyro.getAngle();
    }

    public DifferentialDriveWheelSpeeds wheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            leftLeader.getSelectedSensorVelocity() * 10 * INCHES_PER_TICK,
            rightLeader.getSelectedSensorVelocity() * 10 * INCHES_PER_TICK
        );
    }

    public void setSeperatePower(double leftPower, double rightPower){
        leftLeader.set(leftPower);
        rightLeader.set(rightPower);
    }

    //IM NOT SURE IF THIS IS CORRECT:
    public double leftEncoder(){ //In Position mode, output value is in encoder ticks or an analog value,
        return leftLeader.getSelectedSensorPosition();
    }

}