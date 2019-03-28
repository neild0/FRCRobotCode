package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Command for manually moving the lift - primarily for testing purposes. May be kept for the purposes of resetting to zero.
 */
public class LiftManualMove extends Command {

	public int direction;
	private double rate = 0.2;
	
	/**
	 * Constructor for only the direction (1 raise, -1 lower). Rate is 0.2 by default.
	 * @param direction
	 */
	public LiftManualMove(int direction) {
        requires(Robot.m_Lift);
        this.direction = direction;
	}

	/**
	 * Constructor for the direction (1 raise, -1 lower) and the rate.
	 * @param direction
	 * @param rate
	 */
    public LiftManualMove(int direction, double rate) {
        requires(Robot.m_Lift);
        this.direction = direction;
        this.rate = rate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_Lift.raise(direction * rate);
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
        
        return (direction == 1 && Robot.m_Lift.isAtTop()) || (direction == -1 && Robot.m_Lift.isAtBottom());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
