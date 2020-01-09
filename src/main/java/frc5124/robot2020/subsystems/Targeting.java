
package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class Targeting extends PIDSubsystem {

    private int kTargetingMotorPort = 3;
    public static final int[] kEncoderPorts = new int[]{0, 1};

    private double toleranceRPS = 0.2;
    private double kEncoderDistancePerPulse = 1.0;
    private double kTargetRPS = 1.0;

    private final PWMVictorSPX m_targetingMotor = new PWMVictorSPX(kTargetingMotorPort);
    private final Encoder m_targetingEncoder = new Encoder(0, 1, false);

    /**
     * The shooter subsystem for the robot.
     */
    public Targeting() {
        super(new PIDController(1.0, 0.0, 0.0));
        getController().setTolerance(toleranceRPS);
        m_targetingEncoder.setDistancePerPulse(kEncoderDistancePerPulse);
        setSetpoint(kTargetRPS);
    }

    @Override
    public void useOutput(double output, double setpoint) {
        m_targetingMotor.setVoltage(output);
    }

    @Override
    public double getMeasurement() {
        return m_targetingEncoder.getRate();
    }

    public boolean atSetpoint() {
        return m_controller.atSetpoint();
    }


}


/***
package edu.wpi.first.wpilibj.examples.frisbeebot;


public final class Constants {
    public static final class DriveConstants {
      public static final int kLeftMotor1Port = 0;
      public static final int kLeftMotor2Port = 1;
      public static final int kRightMotor1Port = 2;
      public static final int kRightMotor2Port = 3;
  
      public static final int[] kLeftEncoderPorts = new int[]{0, 1};
      public static final int[] kRightEncoderPorts = new int[]{2, 3};
      public static final boolean kLeftEncoderReversed = false;
      public static final boolean kRightEncoderReversed = true;
  
      public static final int kEncoderCPR = 1024;
      public static final double kWheelDiameterInches = 6;
      public static final double kEncoderDistancePerPulse =
          // Assumes the encoders are directly mounted on the wheel shafts
          (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
    }
  
    public static final class ShooterConstants {
      public static final int[] kEncoderPorts = new int[]{4, 5};
      public static final boolean kEncoderReversed = false;
      public static final int kEncoderCPR = 1024;
      public static final double kEncoderDistancePerPulse =
          // Distance units will be rotations
          1.0 / (double) kEncoderCPR;
  
      public static final int kShooterMotorPort = 4;
      public static final int kFeederMotorPort = 5;
  
      public static final double kShooterFreeRPS = 5300;
      public static final double kShooterTargetRPS = 4000;
      public static final double kShooterToleranceRPS = 50;
  
      // These are not real PID gains, and will have to be tuned for your specific robot.
      public static final double kP = 1;
      public static final double kI = 0;
      public static final double kD = 0;
  
      // On a real robot the feedforward constants should be empirically determined; these are
      // reasonable guesses.
      public static final double kSVolts = 0.05;
      public static final double kVVoltSecondsPerRotation =
          // Should have value 12V at free speed...
          12.0 / kShooterFreeRPS;
  
      public static final double kFeederSpeed = 0.5;
    }
  
    public static final class AutoConstants {
      public static final double kAutoTimeoutSeconds = 12;
      public static final double kAutoShootTimeSeconds = 7;
    }
  
    public static final class OIConstants {
      public static final int kDriverControllerPort = 1;
    }
  }
  */



/*
import frc5124.robot2020.commands.*;
import frc5124.robot2020.Constants.RobotMap;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.*;


public class Shooter extends SubsystemBase {

    private double shooterSpeed=0.8;
    private boolean timeOut= false;

    private PWMTalonSRX wheelSpeedController;
    private Encoder quadratureEncoder2;
    private PIDController wheelPIDController;
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

*/