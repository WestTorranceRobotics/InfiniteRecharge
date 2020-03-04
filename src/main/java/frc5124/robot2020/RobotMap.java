package frc5124.robot2020;

import java.util.function.IntToDoubleFunction;
import com.revrobotics.ColorSensorV3.RawColor;

public class RobotMap {

    public static final int pcmCanId = 0;
    public static final int modNumSolenoid = 0;

    public static class DriveTrainMap {

        public static final int rightLeaderCanID = 1;
        public static final int rightFollowerCanID = 2;
        public static final int leftLeaderCanID = 4;
        public static final int leftFollowerCanID = 3;
      
        public static final double P = 0.000102;
        public static final double I = 0.1;
        public static final double D = 4.14e-5;

        public static final double maxV = 12;
        public static final double maxA = 2;

        public static final double motorS = 1;
        public static final double motorV = 1;
        public static final double motorA = 0;
        
        public static final double trackWidth = 35;
    }
  
    public static class ShooterMap {
        public static final int shootLeaderCanID = 8; //8
        public static final int shootFollowerCanID = 6; //6
        public static final int shootSolenoid = 0;
        public static final double Kp = 0.00021;
        public static final double Ki = 0;
        public static final double Kd = 0.00002;
        public static final double Kf = 0.00008;
        public static final double lineShootRPM = 4350; 
        public static final double trenchShootRPM = 5200;
        public static final double maxShootRPM = 7600;
        public static final int smartCurrentLimit = 20;
        public static final double ballCurrent = 20;
        public static final double gearRatio = .75;

        public static final double[] regressionCoefficients = new double[]{1, 10, 100, 1000, 10000, 100000};
    }

    public static class IntakeMap {
        public static final double motorPower = 0.5;
        public static final int rollerCanId = 14; //14
        public static final int intakeSolenoid = 1;
    }

    public static class HangerMap {
        public static final double hangerMotor = 0.8;   
        public static final double hangerHalt = 0.0;
        public static final int hangerCanID = 5;
        public static final int hangerSolenoid = 2;
        public static final int topLimitChannelID = 1;                
        public static final int bottomLimitChannelID = 2;             
        public static final int shootFollowerCanID = 6;
        public static final double Kp = 0.000016;
        public static final double Ki = 0;
        public static final double Kd = 0.000037;
        public static final double Kf = 0.000227;
        public static final double lineRefRPM = 573;
        public static final double reduction = .75;
    }

    public static class PanelControlMap {
        public static final int spinnerCanId = 100; //10
        public static final int deployerSolenoidChannel = 4;

        public static final RawColor yellowReading = new RawColor(35000, 60000, 11000, 400); // yellow
        public static final RawColor redReading = new RawColor(22000, 12000, 4400, 250); // red
        public static final RawColor greenReading = new RawColor(6000, 22500, 9000, 250); // green
        public static final RawColor blueReading = new RawColor(6500, 26000, 28000, 440); // blue

        public static final double positionControlPower = 0.1;
        public static final IntToDoubleFunction rotationControlDistanceToPowerFunction =
            (i) -> i > 8 ? 1 : i / 8;
    }

    public static class LoaderMap {
        public static final int topBeltCanId = 9; //9
        public static final int bottomBeltCanId = 12; 
        public static final double beltSpeed = .5;
        public static final double fieldEmptyVoltage = 1.0;
        public static final int motionSensorID = 1;
        public static final double motorRotationsPerBall = 10;
    }

    public static class TurretMap{
        public static final int turretCanID = 13; 
        public static final double turretSpeed = .2;
        public static final double Kp = 0.050000; 
        public static final double Ki = 0.000350;
        public static final double Kd = 0;
        public static final double KiZone = 1.000000;
        public static final double turretGearing = ((18.0/230.0) * (1.0/10.0));
        public static final double turretDegreeToRotations = ((66.0 + (2.0/3.0)) / 360); //multiply by desired degrees
        public static final double sweepConstant = 2.5;
        public static final double turnLimit = 170;
        public static final double limelightAngle = 20.5;
        public static final double limelightHeight = 24;
        public static final double targetHeight = 100;
    }

    public static class CameraMap {
        
    }
}