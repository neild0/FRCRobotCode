package frc.robot.commands;

import frc.robot.Robot;

//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftScale extends LiftSetpoint {

	// TODO determine exact value for scale
	private static final double downpos = 600;		// mm

    public LiftScale() {    	
    	super(downpos);
    	
    	requires(Robot.m_Lift);
    }
}
