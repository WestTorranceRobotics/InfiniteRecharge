/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.panelcontrol;

import java.util.Set;
import java.util.function.Consumer;

import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc5124.robot2020.subsystems.PanelController;

public class ColorDisplayer implements Command {

    private final PanelController panelController;
    private final Consumer<RawColor> displayer;

    public ColorDisplayer(PanelController panelController, Consumer<RawColor> displayer) {
        this.panelController = panelController;
        this.displayer = displayer;
    }

    public void execute() {
        displayer.accept(panelController.readColor());
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Set.of();
    }

}