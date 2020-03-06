/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.utility;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.LED;
import frc5124.robot2020.subsystems.LED.Color;
import edu.wpi.first.wpilibj.Timer;
import frc5124.robot2020.RobotMap;

public class LEDTimer extends CommandBase {
  private LED led;
  private double lastSwitch = 0;
  private Timer colorTimer = new Timer();
  private double delaySeconds = RobotMap.LEDMap.delaySeconds;
  private boolean isColor1 = false;
  private boolean isColor2 = false;
  private double color1;
  private double color2;
  /**
   * Creates a new LEDTimer.
   */
  public LEDTimer(LED led, double color1, double color2 ) {
    this.led = led;
    this.color1 = color1;
    this.color2 = color2;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorTimer.reset();
    colorTimer.start();
    led.isTiming(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ((colorTimer.get() - lastSwitch) >= delaySeconds && isColor2) {
      led.setLED(color1);
      isColor2 = false;
      isColor1 = true;
      lastSwitch = colorTimer.get();
    } else if ((colorTimer.get() - lastSwitch) >= delaySeconds && isColor1) {
      led.setLED(color2);
      isColor2 = true;
      isColor1 = true;
      lastSwitch = colorTimer.get();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    led.setLED(led.defaultColor);
    led.isTiming(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
