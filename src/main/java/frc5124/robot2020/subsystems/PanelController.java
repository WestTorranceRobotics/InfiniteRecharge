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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.ColorShim;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc5124.robot2020.RobotMap;

public class PanelController implements Subsystem {

  private final DoubleSolenoid deployer;
  private final CANSparkMax spinnerMotor;
  private final ColorSensorV3 colorSensor;

  private final ColorMatch matcher;

  private PanelColor color;
  private int colorEncoderCount;
  private boolean deployed;
  
  public PanelController() {
    deployer = new DoubleSolenoid(RobotMap.pcmCanId,
      RobotMap.PanelController.deployerForwardSolenoidChannel, RobotMap.PanelController.deployerReverseSolenoidChannel);
    spinnerMotor = new CANSparkMax(RobotMap.PanelController.spinnerCanId, MotorType.kBrushless);
    colorSensor = new ColorSensorV3(Port.kOnboard);

    matcher = new ColorMatch();
  }

  @Override
  public void periodic() {
    if (!deployed) {
      color = null;
    }
    if (deployed) {
      PanelColor nextColor = getColor();
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
    deployer.set(deployed ? Value.kForward : Value.kReverse);
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
    color = getColor();
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

  public PanelColor getColor() {
    Color match = matcher.matchClosestColor(convertRaw(readColor())).color;
    for (PanelColor possibility : PanelColor.values()) {
      if (match == possibility.color()) {
        return possibility;
      }
    }
    return null;
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
    YELLOW(RobotMap.PanelController.yellowReading),
    BLUE(RobotMap.PanelController.blueReading),
    GREEN(RobotMap.PanelController.greenReading),
    RED(RobotMap.PanelController.redReading);
    private final Color color;
    private PanelColor(RawColor color) {
      this.color = convertRaw(color);
    }
    public Color color() {
      return color;
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
