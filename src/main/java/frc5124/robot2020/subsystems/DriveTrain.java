package frc5124.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveTrain implements Subsystem {
    private TalonFX leftFront;
    private TalonFX leftBack;
    private TalonFX rightFront;
    private TalonFX rightBack;
    private AnalogGyro gyro;
    private PIDController myPID;
    private double countsPerInch;

    public DriveTrain() {
        leftFront = new TalonFX(0); 
        leftBack = new TalonFX(1);  
        rightFront = new TalonFX(2);  
        rightBack = new TalonFX(3);

        gyro = new AnalogGyro(0);
        myPID = new PIDController(.75, 0, 0);
        myPID.disableContinuousInput();
        myPID.setTolerance(5, 1);

        double countsPerInch = 1082;
    }

    @Override
    public void periodic() {
    }

    public void driveToPos(double distance){
        myPID.setSetpoint(distance);
        double setPower = myPID.calculate(countsPerInch, distance);
        leftFront.set(ControlMode.PercentOutput, setPower);
        leftBack.set(ControlMode.PercentOutput, setPower);
        rightFront.set(ControlMode.PercentOutput, setPower);
        rightBack.set(ControlMode.PercentOutput, setPower);
    }

    public boolean atSetpoint(){
        return myPID.atSetpoint();
    }

    public void noDrive(){
        leftFront.set(ControlMode.PercentOutput, 0);
        leftBack.set(ControlMode.PercentOutput, 0);
        leftFront.set(ControlMode.PercentOutput, 0);
        rightBack.set(ControlMode.PercentOutput, 0);
    }
}
// to get encoder ticks/counts value, it's motor.getSelectedSensorPosition()