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
    }
  
    public static class ShooterMap {
        public static int shootLeaderCanID = 8; 
        public static int shootFollowerCanID = 9; 
        public static double Kp = 0;
        public static double Ki = 0;
        public static double Kd = 0;
        public static double Kf = .195;
        public static double conversionConstant = 2 * 3.141592654 * .33333 * .75 * (1.0/60.0); 
        public static double maxVelocity = 99; //ft/s
        public static double shootVelocity = 30;

    }

    public static class IntakeMap {
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

    public static class PanelControlMap{
        public static int deployerSolenoidChannel = 0;
        public static int spinnerCanID = 10;
       

        
    }

    public static class TurretMap{
        public static int turretCanID = 6;
        public static double turretSpeed = 1;
    
    }
    
    public static class CameraMap {}

    public static class LoaderMap{}
}
