/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc5124.robot2020.commands.loader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc5124.robot2020.subsystems.Loader;

public class SeeBallRunBelt extends CommandBase {
  double loaderdelay = 0;
  private boolean sawball = false;
  private boolean runbelt = false;
  private boolean startedWaiting = false;
  private Loader m_Loader;
  double intialPosition;
  double currentPosition;

  private long startWaiting;
  private long waitTime = 500;
  private long middleWaiting;

  public SeeBallRunBelt(Loader subsystem) {
    m_Loader = subsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_Loader.seeBall()){
      sawball = true;
    }
    if(sawball == true && runbelt == false){
      intialPosition = m_Loader.returnRotations();
      m_Loader.runBelt();
      sawball = false;
      runbelt = true;
    }
    if(runbelt){
      currentPosition = m_Loader.returnRotations(); 
      if(currentPosition - intialPosition > 26){
        m_Loader.stopBelt();
        runbelt = false;
      }
    }

    SmartDashboard.putNumber("Current position", currentPosition);
    SmartDashboard.putNumber("Intial Position", intialPosition);
    SmartDashboard.putBoolean("Sawball", sawball);
    SmartDashboard.putBoolean("runbelt", runbelt);
    SmartDashboard.updateValues();

    // if (startedWaiting == false){
    //   startWaiting = System.currentTimeMillis();
    //   startedWaiting = true;
    // }
    // middleWaiting = System.currentTimeMillis();
    // if (middleWaiting-startWaiting >= waitTime){
    //   m_Loader.runBelt();
    //   runbelt = true;
    //   sawball = false;
      
    // }



    // if (m_Loader.seeBall()) {
    //   if (sawball == false) {
    //     sawball = true;
    //   }
    // }
    // if (loaderdelay == 0 && sawball && runbelt == false) {
    //   m_Loader.runBelt();
    //   runbelt = true;
    // } else {
    //   loaderdelay--;
    // }
    // if (runbelt) {
    //   if (no < m_Loader.returnRotations()) {
    //     thicc++;
    //     no++;
    //   }
    //   if (thicc >= 26) {
    //     m_Loader.stopBelt();
    //     thicc = 0;
    //     sawball = false;
    //     runbelt = false;
    //   }
    // }
    // 26 is just a placeholder, after we test for optimal rotations we'll replace it
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Loader.stopBelt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
