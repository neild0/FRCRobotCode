package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Autonomous extends Command {

	private enum AutoState {
		Start,
		Winch,
		Done,
		Error,
	}
	
	private AutoState state;
	
	
    public Autonomous() {
    	requires(Robot.m_Chassis);
    	requires(Robot.m_Lift);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = AutoState.Start;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	switch(state) {
    		case Start:
    			state =  AutoState.Winch;
    			break;
    		case Winch:
    			state =  AutoState.Done;
    			break;
    		case Done:
    			break;
    		case Error:
    			break;
    		default:
    			state =  AutoState.Error;
    			break;
    		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == AutoState.Done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
