/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3.RawColor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.ColorShim;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import frc5124.robot2020.RobotMap;

public class PanelController implements Subsystem {

  private final Solenoid deployer;
  private final CANSparkMax spinnerMotor;
  private final ColorSensorV3 colorSensor;

  private final ColorMatch matcher;

  private PanelColor color;
  private int colorEncoderCount;
  private boolean deployed;
  private ShuffleboardTab debuggingTab;
  
  public PanelController() {
    deployer = new Solenoid(RobotMap.pcmCanId, RobotMap.PanelControlMap.deployerSolenoidChannel);
    spinnerMotor = new CANSparkMax(RobotMap.PanelControlMap.spinnerCanId, MotorType.kBrushless);
    colorSensor = new ColorSensorV3(Port.kOnboard);

    matcher = new ColorMatch();
    matcher.addColorMatch(PanelColor.BLUE.color());
    matcher.addColorMatch(PanelColor.YELLOW.color());
    matcher.addColorMatch(PanelColor.GREEN.color());
    matcher.addColorMatch(PanelColor.RED.color());
    // if (RobotMap.debugEnabled) {
    //   debuggingTab = Shuffleboard.getTab("Panel Debugger");
    //   debuggingTab.addString("Status", () -> "Imagine Having a Panel Controller");
    //   // Color encoder
    //   // Motor encoder
    //   // Motor current
    //   // Color view with numbers
    // }
  }

  @Override
  public void periodic() {
    if (!deployed) {
      color = null;
    }
    if (deployed) {
      PanelColor nextColor = getColor().choice;
      if (color == null) {
        color = nextColor;
        return;
      }
      if (nextColor == null) {
        return;
      }
      if ((color.ordinal() - nextColor.ordinal() + 4) % 4 == 1) { 
        colorEncoderCount--;
      }
      if ((nextColor.ordinal() - color.ordinal() - 4) % 4 == -1) { 
        colorEncoderCount++;
      }
      color = nextColor;
    }
  }

  // Control Methods

  public synchronized void setDeployed(boolean deployed) {
    this.deployed = deployed;
    deployer.set(deployed);
  }

  public boolean isDeployed() {
    return deployed;
  }

  public RawColor readColor() {
    RawColor colorReading = colorSensor.getRawColor();
    return colorReading;
  }

  public synchronized void resetColorEncoder() {
    colorEncoderCount = 0;
    color = getColor().choice;
  }

  public int getColorEncoder() {
    return colorEncoderCount;
  }

  public int getCountsToTarget(PanelColor target) {
    if (target == null || color == null) {
      return 0;
    }
    int diff = color.ordinal() - target.ordinal();
    while (diff > 2) {
      diff -= 4;
    }
    while (diff < -2) {
      diff += 4;
    }
    return diff;
  }

  public OutputColor getColor() {
    RawColor color = readColor();
    Color match = matcher.matchClosestColor(convertRaw(color)).color;
    for (PanelColor possibility : PanelColor.values()) {
      if (match == possibility.color()) {
        return new OutputColor(color, possibility);
      }
    }
    return new OutputColor(color, null);
  }

  public void setSpinner(double power) {
    spinnerMotor.set(power);
  }

  public PanelColor getPositionControlTarget() {
    String message = DriverStation.getInstance().getGameSpecificMessage();
    if (message.length() == 0) {
      return null;
    }
    switch (message.charAt(0)) {
      case 'B': return PanelColor.RED;
      case 'G': return PanelColor.YELLOW;
      case 'R': return PanelColor.BLUE;
      case 'Y': return PanelColor.GREEN;
    }
    return null;
  }

  public enum PanelColor {
    YELLOW(RobotMap.PanelControlMap.yellowReading),
    BLUE(RobotMap.PanelControlMap.blueReading),
    GREEN(RobotMap.PanelControlMap.greenReading),
    RED(RobotMap.PanelControlMap.redReading);
    private final Color color;
    private PanelColor(RawColor color) {
      this.color = convertRaw(color);
    }
    public Color color() {
      return color;
    }
  }

  public static class OutputColor {
    public final RawColor value;
    public final PanelColor choice;
    private OutputColor(RawColor value, PanelColor choice) {
      this.value = value;
      this.choice = choice;
    }
  }

  private static Color convertRaw(RawColor raw) {
    double r = raw.red;
    double g = raw.green;
    double b = raw.blue;
    double mag = r + g + b;
    return new ColorShim(r / mag, g / mag, b / mag);
  }
}