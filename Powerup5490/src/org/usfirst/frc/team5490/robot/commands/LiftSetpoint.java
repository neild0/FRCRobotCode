package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.EnhancedPIDSetpoint;
import org.usfirst.frc.team5490.robot.Robot;
import org.usfirst.frc.team5490.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Move the lift to a given location. This command finishes when it is
 * within the tolerance, but leaves the PID loop running to maintain the
 * position. Other commands using the lift should make sure they disable
 * PID!
 * 
 * <p>NOTES - any or all will cause mayhem with the control..
 * 
 * <p> When things go wrong with the control, check:
 * <pre>
 * encoder wires, connected
 * encoder wires swapped (A/B)
 * motor wires swapped (A/B)
 * </pre>
 */

public class LiftSetpoint extends Command {
	
	private static Lift pid_subsystem = Robot.m_Lift;
	private static double pid_active_range = 400;
	private static int ramp_length = 100;
	
	private double m_setpoint;
	
	public LiftSetpoint(double setpoint) {
		m_setpoint = setpoint;
		requires(pid_subsystem);
	}
	
	private EnhancedPIDSetpoint enhancedPID = 
			new EnhancedPIDSetpoint(pid_subsystem, pid_active_range, ramp_length);
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		enhancedPID.initialize(m_setpoint);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		enhancedPID.execute();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		
		return (pid_subsystem.onTarget() && (! pid_subsystem.isClamped())); 
	}
	
	@Override
	protected void end() {
		if (Robot.m_Lift.isAtBottom()) {
			Robot.m_Lift.Reset();
		}
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
}


// BELOW CODE IS OLD CODE. This can be deleted when it is confirmed that EnhancedPIdSetpoint works successfully for all subsystems.
//public class LiftSetpoint extends Command {
//	private double m_setpoint;
//	
//	private double pid_active_range = 400; // this is +/- mm, where we want to do PID control 
//	private int ramp_length = 100;
//	
//	private int velocity_step;
//	private double[] S_Curve;
//
//	private enum ControlStates {
//		Accelerate_Up,
//		Fast_Up,
//		PID,
//		Fast_Down,		
//		Accelerate_Down,
//	}
//
//	private ControlStates m_state;
//
//	public LiftSetpoint(double setpoint) {
//		m_setpoint = setpoint;
//				
//		requires(Robot.m_Lift);	
//	}
//	
//	private void create_ramp() {
//		// divides [0,pi/2] into "ramp_length" pieces
//		double step = (Math.PI / 2) / S_Curve.length;
//		
//		// values of sin(step) are stored into S_Curve
//		for(int i = 0; i < S_Curve.length; i++) {
//			S_Curve[i] = Math.sin(step);
//			step = (Math.PI / 2) / S_Curve.length * (i+1);
//		}	
//	}
//
//	/**
//	 * Determines the initial state for the move request.
//	 */
//	private ControlStates CheckState()
//	{
//		// where are we to start
//		double required_delta = Robot.m_Lift.getSetpoint() - Robot.m_Lift.getPosition();
//		
//		if (required_delta > 0)  {
//			if (required_delta > pid_active_range)  {
//				return ControlStates.Accelerate_Up;
//			} else {
//				return ControlStates.PID;
//			}			
//		} else {
//			if (required_delta < (-1 * pid_active_range))  {
//				return  ControlStates.Accelerate_Down;
//			} else {
//				return  ControlStates.PID;
//			}
//		}		
//	}
//
//	// Called just before this Command runs the first time
//	@Override
//	protected void initialize() {
//		
//		S_Curve = new double[ramp_length];
//		create_ramp();
//		
//		Robot.m_Lift.disable();
//		Robot.m_Lift.setSetpoint(m_setpoint);
//		
//		velocity_step = 0;		
//
//		m_state = CheckState();
//	}
//	
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	
//    	// use absolute value to make comparisons less confusing..
//    	double required_delta = Math.abs(Robot.m_Lift.getSetpoint() - Robot.m_Lift.getPosition());
//    	
//    	switch(m_state) {
//	    	case Accelerate_Up:
//	    		// follow velocity curve to get to full output
//	    		Robot.m_Lift.raise(S_Curve[velocity_step++]);
//	    		
//	    		if (required_delta > pid_active_range)  {
//	    			if (velocity_step >= S_Curve.length) m_state = ControlStates.Fast_Up;
//	    		} else {
//	    			m_state = ControlStates.PID;
//	    		}	    		
//	    		break;
//	    	case Fast_Up:
//	    		// full output up....
//	    		Robot.m_Lift.raise(1);
//	    		if (required_delta <= pid_active_range)  {
//	    			m_state = ControlStates.PID;
//	    		}
//	    		break;
//	    	case PID:
//	    		Robot.m_Lift.enable();
//	    		break;
//	    	case Fast_Down:
//	    		 // full output down....
//	    		Robot.m_Lift.lower(1);  
//	    		if (required_delta <= pid_active_range)  {
//	    			m_state = ControlStates.PID;
//	    		}
//	    		break;
//	    	case Accelerate_Down:	
//	    		// follow velocity curve to get to full output
//	    		Robot.m_Lift.lower(S_Curve[velocity_step++]);
//	    		
//	    		if (required_delta > pid_active_range)  {
//	    			if (velocity_step >= S_Curve.length) m_state = ControlStates.Fast_Down;
//	    		} else {
//	    			m_state = ControlStates.PID;
//	    		}	
//	    		break;
//    	}
//    }
//
//	// Make this return true when this Command no longer needs to run execute()
//	@Override
//	protected boolean isFinished() {
//		return Robot.m_Lift.onTarget();
//	}
//}