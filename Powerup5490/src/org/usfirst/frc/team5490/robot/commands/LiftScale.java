package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftScale extends LiftSetpoint {

	// TODO determine exact value for scale
	private static final double downpos = 800;		// mm

    public LiftScale() {    	
    	super(downpos);
    	
    	requires(Robot.m_Lift);
    }
}
