/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */


package frc5124.robot2020.subsystems;
import frc5124.robot2020.commands.*;
import frc5124.robot2020.RobotMap;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.*;


public class Shooter extends SubsystemBase {

    private double shooterSpeed=0.8;
    private boolean timeOut= false;

    private CANSparkMax wheelSpeedController;
    private CANEncoder quadratureEncoder2;
    private CANPIDController wheelPIDController;
    private PWMVictorSPX turretSpeedController;
    private Encoder quadratureEncoder;
    private PIDController turretPIDController;
    private DigitalInput photoelectricBallDetectorDigitalInput;
    private Solenoid gateKeeperSolenoid;

    public Shooter() {

        // Wheel
        wheelSpeedController = new PWMTalonSRX(RobotMap.CanId.shooterWheel);
        wheelSpeedController.setInverted(false);
                
        quadratureEncoder2 = new Encoder(0, 1, false, EncodingType.k4X);
        quadratureEncoder2.setDistancePerPulse(1.0);

        wheelPIDController = new PIDController(1.0, 0.0, 0.0, 0.0);
    
        
        // Turret
        turretSpeedController = new PWMVictorSPX(RobotMap.CanId.shooterTurret);
        turretSpeedController.setInverted(false);
                
        quadratureEncoder = new Encoder(3, 4, false, EncodingType.k4X);
        quadratureEncoder.setDistancePerPulse(1.0);
        quadratureEncoder.setPIDSourceType(PIDSourceType.kRate);
                
        turretPIDController = new PIDController(1.0, 0.0, 0.0, 0.0);
        turretPIDController.setContinuous(false);
        turretPIDController.setAbsoluteTolerance(0.2);
                
        photoelectricBallDetectorDigitalInput = new DigitalInput(6);
                
        gateKeeperSolenoid = new Solenoid(0, 0);

    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public boolean fire() {
        if (wheelPIDController.atSetpoint() & hasBall()) {
         gateKeeperSolenoid.set(true);
         return true;
        }
        return false;
     }
 
     public void spinUp() {
         wheelPIDController.setSetpoint(shooterSpeed);
     }
 
     public void spinDown() {
         wheelPIDController.setSetpoint(0);
     }
     
     public boolean atSpeed() {
         return wheelPIDController.atSetpoint();
     }
     public boolean hasBall(){
         return !photoelectricBallDetectorDigitalInput.get();
       }
 
}
