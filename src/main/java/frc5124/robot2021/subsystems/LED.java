/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2021.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc5124.robot2021.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * Add your docs here.
 */
public class LED extends SubsystemBase {
  private Spark LED = new Spark(0);
  public double defaultColor = 0;
  private boolean isTiming = false;
  private double lastSwitch = 0;
  private Timer colorTimer = new Timer();
  private double delaySeconds = RobotMap.LEDMap.delaySeconds;
  private boolean isColor1 = false;
  private boolean isColor2 = false;
  private double color1;
  private double color2;
  private int lastBalls = 0;
 
  public LED () {
    colorTimer.reset();
    colorTimer.start();
  }

  public boolean isTiming() {
    return isTiming;
  }

  public void isTiming(boolean isTiming) {
    this.isTiming = isTiming;
  }

  public void setDefaultColor(double color){
    this.defaultColor = color;
    setLED(this.defaultColor);
  }

  public void setLED(double power) {
    LED.set(power);
  }

  public double getTime() {
    return colorTimer.get();
  }

  public double lastSwitch() {
    return lastSwitch;
  }

  public void lastSwitch(double lastSwitch) {
    this.lastSwitch = lastSwitch;
  }

  NetworkTableEntry ballsEntry = NetworkTableInstance.getDefault()
    .getTable("Shuffleboard").getSubTable("Driving Display").getEntry("Balls Intaked");
  private long changeMillis = -1;
  NetworkTableEntry intakeEntry = NetworkTableInstance.getDefault()
    .getTable("Shuffleboard").getSubTable("Driving Display").getEntry("Intake Running?");

  @Override
  public void periodic() {
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double angle = ty + RobotMap.limelightAngle;
    double tan = Math.tan(Math.toRadians(angle));
    double dx = (RobotMap.targetHeight - RobotMap.limelightHeight) / tan;
    if (!isTiming()) {
      if (Math.abs(120 - dx) <= 15) {
        setLED(Color.lime);
      } else if (Math.abs(206.5 - dx) <= 15) {
        setLED(Color.lawnGreen);
      } else {
        if (intakeEntry.getBoolean(false)) {    
          setLED(Color.yellow);
        } else {
          setLED(defaultColor);
        }
      }
      int balls = (int) ballsEntry.getDouble(0);
      if (balls > lastBalls) {
        setLED(Color.violet);
        changeMillis = System.currentTimeMillis();
      }
      if (changeMillis != -1) {
        if (System.currentTimeMillis() - changeMillis > 250) {
          setLED(Color.yellow);
          changeMillis = -1;
        } else {
          setLED(Color.violet);
        }
      }
      lastBalls = balls;
    }
  }

  public class Color extends LED {
    public static final double hotPink = .57;
    public static final double darkRed = .59;
    public static final double red = .61;
    public static final double redOrange = .63;
    public static final double orange = .65;
    public static final double gold = .67;
    public static final double yellow = .69;
    public static final double lawnGreen = .71;
    public static final double lime = .73;
    public static final double darkGreen = .75;
    public static final double green = .77;
    public static final double blueGreen = .79;
    public static final double aqua = .81;
    public static final double skyBlue = .83;
    public static final double darkBlue = .85;
    public static final double blue = .87;
    public static final double blueViolet = .89;
    public static final double violet = .91;
    public static final double white = .93;
    public static final double gray = .95;
    public static final double darkGray = .97;
    public static final double black = .99;
    public static final double noColor = 0;
    }
}


