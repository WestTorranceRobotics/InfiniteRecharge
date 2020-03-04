/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.auto;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ChangeCamera extends InstantCommand {

  private static final NetworkTableEntry cameraSelection = 
      NetworkTableInstance.getDefault().getTable("rpi").getEntry("camera");
  
  // including the limelight, so this is one more than the number of usb cameras
  private static final int NUMBER_CAMERAS = 4;

  public static final ChangeDirection NEXT = ChangeDirection.NEXT;
  public static final ChangeDirection LAST = ChangeDirection.LAST;

  public enum ChangeDirection {
    NEXT(+1),
    LAST(-1);
    private final int change;
    private ChangeDirection(int change) {
      this.change = change;
    }
    public int getChange() {
      return change;
    }
  }

  /**
   * Creates a new ChangeCamera.
   */
  public ChangeCamera(ChangeDirection change) {
    super(() -> {
      int choice = (int) cameraSelection.getDouble(0) + change.getChange();
      cameraSelection.setDouble(choice % NUMBER_CAMERAS + NUMBER_CAMERAS % NUMBER_CAMERAS);
    });
  }
}
