package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftHook extends LiftSetpoint {

	//TODO determine exact value for hook
	private static final double hookpos = 900;

    public LiftHook() {    	
    	super(hookpos);
    	
    	requires(Robot.m_Lift);
    }
}
