/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class LED extends SubsystemBase {
  private Spark LED = new Spark(0);
  public double defaultColor = 0;
 
  public LED () {
  }

  public void setDefaultColor(double color){
    this.defaultColor = color;
    setLED(this.defaultColor);
  }

  public void setLED(double power) {
    LED.set(power);
  }

  @Override
  public void periodic() {
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
    }
}


