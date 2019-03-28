package frc.robot.commands;

import frc.robot.Robot;

//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftHook extends LiftSetpoint {

	//TODO determine exact value for hook
	private static final double hookpos = 800;

    public LiftHook() {    	
    	super(hookpos);
    	
    	requires(Robot.m_Lift);
    }
}
