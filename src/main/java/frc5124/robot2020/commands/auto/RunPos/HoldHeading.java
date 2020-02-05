/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.RunPos;

import edu.wpi.first.wpilibj2.command.CommandBase;
<<<<<<< HEAD:src/main/java/frc5124/robot2020/commands/RunPos/HoldHeading.java
import frc5124.robot2020.subsystems.DriveTrain;

public class HoldHeading extends CommandBase {
  private DriveTrain driveTrain;
  /**
   * Creates a new holdAnglee.
   */
  public HoldHeading() {
    driveTrain = new DriveTrain();
    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
=======
import frc5124.robot2020.subsystems.Loader;

public class SeeBallRunBelt extends CommandBase {

  private Loader m_Loader;

  public SeeBallRunBelt(Loader subsystem) {
    m_Loader = subsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
>>>>>>> origin/master:src/main/java/frc5124/robot2020/commands/SeeBallRunBelt.java
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
<<<<<<< HEAD:src/main/java/frc5124/robot2020/commands/RunPos/HoldHeading.java

=======
    if (m_Loader.seeBall()) {
      m_Loader.runBelt();
    } else {
      m_Loader.stopBelt();
     // isDone = true;
    }
    // 1000 is just a placeholder, after we test for optimal time we'll replace it
>>>>>>> origin/master:src/main/java/frc5124/robot2020/commands/SeeBallRunBelt.java
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
<<<<<<< HEAD:src/main/java/frc5124/robot2020/commands/RunPos/HoldHeading.java
=======
    m_Loader.stopBelt();
>>>>>>> origin/master:src/main/java/frc5124/robot2020/commands/SeeBallRunBelt.java
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}