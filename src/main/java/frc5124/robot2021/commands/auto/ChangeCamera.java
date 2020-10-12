/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import java.util.function.IntSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ChangeCamera extends InstantCommand {

  public static int lastSelection = 0;

  private static final NetworkTableEntry cameraSelection = 
      NetworkTableInstance.getDefault().getTable("rpi").getEntry("camera");

  public static final int CLIMB_CAM = 0;
  public static final int INTAKE_CAM = 1;
  public static final int LIMELIGHT = 3;

  /**
   * Creates a new ChangeCamera.
   */
  public ChangeCamera(IntSupplier value) {
    super(() -> {
      int choice = value.getAsInt();
      cameraSelection.setDouble(choice);
      lastSelection = choice;
    });
  }

  public ChangeCamera(int value) {
    this(() -> value);
  }
}
