package frc5124.robot2021;
import edu.wpi.first.wpilibj.RobotBase;
import frc5124.robot2021.Robot;

/**
 * Robot application JAR entry point class.
 */
public final class Main {
  private Main() {
  }

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
