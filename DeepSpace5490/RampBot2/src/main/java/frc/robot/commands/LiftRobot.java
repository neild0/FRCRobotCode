package frc.robot.commands;

import frc.robot.Robot;

//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftRobot extends LiftSetpoint {

    	//TODO determine exact value for switch
    private static final double ballpos = 5; // mm
    
    public LiftRobot() {

        super(ballpos);

    	requires(Robot.m_Lift);
    }
}
