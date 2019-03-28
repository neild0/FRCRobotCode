package frc.robot.commands;

import frc.robot.Robot;

//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftDown extends LiftSetpoint {
	
	private double rate = 0.2;

	// set negative and let limit switch set 0 AND reset the lift position
	private static final double downpos = -100;		// negative

    public LiftDown() {    	
    	super(downpos);
    	
    	requires(Robot.m_Lift);
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
	}
	
	  // Called repeatedly when this Command is scheduled to run
	  protected void execute() {
    	Robot.m_Lift.lower(rate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	boolean isAtMax = false;
//    	if (direction == 1 && Robot.m_Lift.isAtTop()) {
//    		isAtMax = true;
//    	} else if (direction == -1 && Robot.m_Lift.isAtBottom()) {
//    		isAtMax = true;
//    	}
//    	
//        return isAtMax;
        
        return Robot.m_Lift.isAtBottom();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.m_Lift.stop();	
		Robot.m_Lift.Reset();	// set position	
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

