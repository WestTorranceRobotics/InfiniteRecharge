/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.panelcontrol;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.RobotMap;
import frc5124.robot2020.subsystems.PanelController;
//import frc5124.robot2020.subsystems.PanelController.PanelColor;

public class PositionControl implements Command {

    private final PanelController panelController;
   // private PanelColor target;

    public PositionControl(PanelController panelController) {
        this.panelController = panelController;
    }

    @Override
    public void initialize() {
        // target = panelController.getPositionControlTarget();
        // panelController.resetColorEncoder();
    }

    @Override
    public void execute() {
       // panelController.setSpinner(Math.signum(panelController.getCountsToTarget(target)) * RobotMap.PanelController.positionControlPower);
    }

    //@Override
    //public boolean isFinished() {
       // return panelController.getCountsToTarget(target) == 0;
   // }

    @Override
    public void end(boolean interrupted) {
       // panelController.setSpinner(0);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of(panelController);
    }
}
