/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.panelcontrol;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc5124.robot2020.subsystems.PanelController;

public class PanelControllerToggleDeployed extends InstantCommand {

    public PanelControllerToggleDeployed(PanelController panelController) {
       // super(() -> panelController.setDeployed(!panelController.isDeployed()), panelController);
    }
}
