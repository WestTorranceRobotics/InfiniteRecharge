package frc5124.robot2020;

import java.util.function.IntToDoubleFunction;
import com.revrobotics.ColorSensorV3.RawColor;

public class RobotMap {
    public static int pcmCanId = 0;
    public static class DriveTrainMap {
        public static int rightLeaderCanID = 1;
        public static int rightFollowerCanID = 2;
        public static int leftLeaderCanID = 4;
        public static int leftFollowerCanID = 3;
      
        public static double P = 1;
        public static double I = 0.1;
        public static double D = 0.01;
        public static double F = 0.5;

        public static double maxV = 12;
        public static double maxA = 2;

        public static double motorS = 1;
        public static double motorV = 1;
        public static double motorA = 0;
        
        public static double trackWidth = 35;
    }
  
    public static class ShooterMap {
        public static int shootLeaderCanID = 8; 
        public static int shootFollowerCanID = 9; 
        public static double Kp = 0;
        public static double Ki = 0;
        public static double Kd = 0;
        public static double Kf = .195;
        public static double conversionConstant = 2 * Math.PI * .33333 * .75 * (1.0/60.0); 
        public static double maxVelocity = 99; //ft/s
        public static double shootVelocity = 30;
    }

    public static class Intake {
        public static double motorPower = 0.5;
        public static int rollerCanId = 5;
    }

    public static class HangerMap {
        public static double hangerMotor = 0.5;   
        public static double hangerHalt = 0.0;
        public static int hangerCanID = 7;
        public static int topLimitChannelID = 1;             //DIO port 
        public static int bottomLimitChannelID = 2;             //DIO port    
    }

    public static class PanelControlMap {
        public static final int spinnerCanId = 10;
        public static final int deployerSolenoidChannel = 0;

        public static final RawColor yellowReading = new RawColor(35000, 60000, 11000, 400); // yellow
        public static final RawColor redReading = new RawColor(22000, 12000, 4400, 250); // red
        public static final RawColor greenReading = new RawColor(6000, 22500, 9000, 250); // green
        public static final RawColor blueReading = new RawColor(6500, 26000, 28000, 440); // blue

        public static final double positionControlPower = 0.1;
        public static final IntToDoubleFunction rotationControlDistanceToPowerFunction =
            (i) -> i > 8 ? 1 : i / 8;
    }

    public static class Loader {
        public static int topBeltCanId = 3;
        public static int bottomBeltCanId = 4; 
    }

    public static class TurretMap{
        public static int turretCanID = 6;
        public static double turretSpeed = 1;
    }
    
    public static class CameraMap {}

    public static class LoaderMap{}
}