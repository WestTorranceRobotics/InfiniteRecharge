package frc5124.robot2020.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc5124.robot2020.subsystems.DriveTrain;;
public class DriveDistance implements Command {
    private DriveTrain driveTrain;
    private double distance;
    private boolean isDone = false;
    private final double convertToRevolution = 2048;
    private final double Wheel_Circumference = 1/(6*Math.PI);
    private double distanceTraveled;
    private double gearRatio1 = 1/(10/64);
    private double gearRatio2 = 1/(18/28);
    private final double countsPerIn = convertToRevolution * gearRatio1 * gearRatio2 * Wheel_Circumference;
    private double targetAngle;
    private double gyroAngle;
    private double error;
    
    // Called just before this Command runs the first time
    
    public DriveDistance(DriveTrain driveTrain, double x, double y, double dist) {
        this.driveTrain = driveTrain;
        this.distance = dist;
    }
    // Called repeatedly when this Command is scheduled to run
    @Override 
    public void initialize() {
        driveTrain.leftLeader.getSensorCollection().setIntegratedSensorPosition(0,50);
        driveTrain.rightLeader.getSensorCollection().setIntegratedSensorPosition(0,50);
        targetAngle = 0;
        gyroAngle = driveTrain.getGryoDegree();
        distanceTraveled = distance * countsPerIn;

    }
    @Override
    public void execute() {
        
        //  if (driveTrain.leftLeader.getSensorCollection().getIntegratedSensorAbsolutePosition() < distanceTraveled) {
        //     error = targetAngle - gyroAngle;   
        //     if (error > 3 && Math.signum(error) == 1.0) {
        //         driveTrain.arcadeDrive(0.75, -0.5);
        //     } else if (error < -3 && Math.signum(error) == -1.0) {
        //         driveTrain.arcadeDrive(0.75, 0.5);
        //     }
        //     else{
        //         driveTrain.tankDrive(1, 1);
        //     }
        // }
        // else{
        //   isDone = true;
        // }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
      driveTrain.leftLeader.getSensorCollection().setIntegratedSensorPosition(0,50);
      driveTrain.rightLeader.getSensorCollection().setIntegratedSensorPosition(0,50);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(driveTrain);
    }

    
}