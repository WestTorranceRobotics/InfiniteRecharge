package frc5124.robot2020;

import java.util.function.IntToDoubleFunction;

import com.revrobotics.ColorSensorV3.RawColor;

public class RobotMap {
    public static final int pcmCanId = 0;
    public static class DriveTrain {
        public static final int leftLeaderCanId = 1;
        public static final int leftFollowerCanId = 2;
        public static final int rightLeaderCanId = 3;
        public static final int rightFollowerCanId = 4;

        public static final double P = 1;
        public static final double I = 0.1;
        public static final double D = 0.01;
        public static final double F = 0.5;
    }
    public static class PanelController {
        public static final int spinnerCanId = 11;
        public static final int deployerForwardSolenoidChannel = 0;
        public static final int deployerReverseSolenoidChannel = 7;

        public static final RawColor blueReading = new RawColor(100, 150, 800, 500);
        public static final RawColor greenReading = new RawColor(150, 800, 100, 500);
        public static final RawColor redReading = new RawColor(800, 100, 200, 500);
        public static final RawColor yellowReading = new RawColor(700, 700, 50, 500);

        public static final double positionControlPower = 10;
        public static final IntToDoubleFunction rotationControlDistanceToPowerFunction =
            (i) -> i > 8 ? 1 : i / 8;
    }
}
