/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret.turretGroups;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Turret;

/**
 * Add your docs here.
 */
public class TurretTargetByPid extends CommandBase {

    private Turret subsystem;

    public TurretTargetByPid(Turret subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        double target = subsystem.getDegrees() - 
            NetworkTableInstance.getDefault().getTable("limelight")
            .getEntry("tx").getDouble(0);
        subsystem.setTurretDegrees(target);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
