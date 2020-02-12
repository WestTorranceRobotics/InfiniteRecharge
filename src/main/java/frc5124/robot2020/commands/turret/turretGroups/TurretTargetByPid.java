/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.turret.turretGroups;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc5124.robot2020.subsystems.Turret;

/**
 * Add your docs here.
 */
public class TurretTargetByPid extends setTurretDegrees {

    public TurretTargetByPid(Turret subsystem) {
        super(subsystem, subsystem.getDegrees() + 
            NetworkTableInstance.getDefault().getTable("limelight")
            .getEntry("tx").getDouble(0)
        );
    }

}
