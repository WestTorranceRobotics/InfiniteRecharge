/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.utility;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.LED;
import frc5124.robot2020.RobotMap;

public class LEDControl extends CommandBase {
  private LED led;
  private double frequency;
  private int ledCount = 0;
  private double[] colorIndex;
  /**
   * Creates a new LEDControl.
   */
  public LEDControl(LED led, double frequency, double... colorIndex) {
    this.led = led;
    this.colorIndex = colorIndex;    
    this.frequency = frequency;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() { 
    led.isTiming(true);
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ((led.getTime() - led.lastSwitch()) >= frequency && ledCount <= colorIndex.length) {
      led.setLED(colorIndex[ledCount]);
      led.lastSwitch(led.getTime());
      ledCount += 1;
    } else {
      ledCount = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    led.isTiming(false);
    led.setLED(led.defaultColor);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
