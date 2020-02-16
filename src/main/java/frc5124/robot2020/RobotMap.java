package frc5124.robot2020;

import java.util.function.IntToDoubleFunction;
import com.revrobotics.ColorSensorV3.RawColor;

public class RobotMap {

    public static int pcmCanId = 0;
    public static final int modNumSolenoid = 0;

    public static class DriveTrainMap {

        public static int rightLeaderCanID = 1;
        public static int rightFollowerCanID = 2;
        public static int leftLeaderCanID = 4;
        public static int leftFollowerCanID = 3;
      
        public static double P = 0.000102;
        public static double I = 0.1;
        public static double D = 4.14e-5;

        public static double maxV = 12;
        public static double maxA = 2;

        public static double motorS = 1;
        public static double motorV = 1;
        public static double motorA = 0;
        
        public static double trackWidth = 35;
    }
  
    public static class ShooterMap {
        public static int shootLeaderCanID = 8; //8
        public static int shootFollowerCanID = 6; //6
        public static final int shootSolenoid = 0;
        public static double Kp = .000005;
        public static double Ki = 0;
        public static double Kd = .000002;
        public static double Kf = .000134;
        public static double lineShootRPM = 5200; //4700 for closer target
        public static double maxShootRPM = 7600;
        public static int smartCurrentLimit = 20;
        public static double ballCurrent = 20;
        public static double gearRatio = .75;

        public static double[] regressionCoefficients = new double[]{1, 10, 100, 1000, 10000, 100000};
    }

    public static class IntakeMap {
        public static double motorPower = 0.5;
        public static int rollerCanId = 14; //14
        public static int intakeSolenoid = 1;
    }

    public static class HangerMap {
        public static double hangerMotor = 0.8;   
        public static double hangerHalt = 0.0;
        public static int hangerCanID = 5;
        public static int hangerSolenoid = 2;
        public static int topLimitChannelID = 1;                
        public static int bottomLimitChannelID = 2;             
        public static int shootFollowerCanID = 6;
        public static double Kp = 0.000016;
        public static double Ki = 0;
        public static double Kd = 0.000037;
        public static double Kf = 0.000227;
        public static double lineRefRPM = 573;
        public static double reduction = .75;
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
        public static int topBeltCanId = 9; //9
        public static int bottomBeltCanId = 12; 
        public static double beltSpeed = .45;
        public static double fieldEmptyVoltage = 1.0;
        public static int motionSensorID = 1;
        public static double motorRotationsPerBall = 10;
    }

    public static class TurretMap{
        public static int turretCanID = 10; 
        public static double turretSpeed = .2;
        public static double Kp = 0.050000; 
        public static double Ki = 0.000350;
        public static double Kd = 0;
        public static double KiZone = 1.000000;
        public static double turretGearing = ((18.0/230.0) * (1.0/10.0));
        public static double turretDegreeToRotations = ((66.0 + (2.0/3.0)) / 360); //multiply by desired degrees
        public static double sweepConstant = 2.5;
        public static double turnLimit = 170;
        public static double limelightAngle = 20.5;
        public static double limelightHeight = 24;
        public static double targetHeight = 100;
    }

    public static class CameraMap {
        
    }
}