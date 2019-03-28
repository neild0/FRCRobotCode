package frc.robot.commands;

import frc.robot.Robot;

//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftManSetpoint extends LiftSetpoint {

	//TODO determine exact value for hook
	private static double adjust = 300;

    public LiftManSetpoint(double initial) {     
		super(initial);

		adjust = initial;
		
    	
    	requires(Robot.m_Lift);
	}
	
	
}
