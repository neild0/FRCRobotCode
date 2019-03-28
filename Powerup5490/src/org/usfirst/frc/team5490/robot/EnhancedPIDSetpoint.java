package org.usfirst.frc.team5490.robot;



import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Enhanced PID mechanism that uses PID as outlined by WPILib, but also ramps up the motor's speed in the beginning.
 * Edits need to be made to clean up the code, hopefully by using lambda expressions.
 * Otherwise, however, all should work properly.
 *
 */
public class EnhancedPIDSetpoint {
	
	private double m_setpoint;
	
	private int velocity_step;
	private double[] S_Curve;
	private int rampLength;
	private double pid_active_range;
	private PIDSubsystem pid_subsystem;

	private enum ControlStates {
		Accelerate_Up,
		Fast_Up,
		PID,
		Fast_Down,		
		Accelerate_Down,
	}
	
	private ControlStates m_state;
	
	public EnhancedPIDSetpoint(PIDSubsystem pid_subsystem, double pid_active_range, int rampLength) {
    	this.pid_active_range = pid_active_range;
    	this.pid_subsystem = pid_subsystem;
    	this.rampLength = rampLength;
    }
	
	private void create_ramp() {
		// divides [0,pi/2] into "ramp_length" pieces
		double step = (Math.PI / 2) / S_Curve.length;
		
		// values of sin(step) are stored into S_Curve
		for(int i = 0; i < S_Curve.length; i++) {
			S_Curve[i] = Math.sin(step);
			step = (Math.PI / 2) / S_Curve.length * (i+1);
		}	
	}
	
	/**
	 * Determines the initial state for the move request.
	 */
	private ControlStates CheckState()
	{
		double required_delta = pid_subsystem.getSetpoint() - pid_subsystem.getPosition();
		
		if (required_delta > 0)  {
			if (required_delta > pid_active_range)  {
				return ControlStates.Accelerate_Up;
			} else {
				return ControlStates.PID;
			}			
		} else {
			if (required_delta < (-1 * pid_active_range))  {
				return  ControlStates.Accelerate_Down;
			} else {
				return  ControlStates.PID;
			}
		}		
	}
	
	public void initialize(double setpoint) {
		S_Curve = new double[rampLength];
		create_ramp();
		
		pid_subsystem.disable();
		m_setpoint = setpoint;
		pid_subsystem.setSetpoint(m_setpoint);
		
		velocity_step = 0;		

		m_state = CheckState();
	}
	
	public void execute() {
		// use absolute value to make comparisons less confusing..
    	double required_delta = Math.abs(pid_subsystem.getSetpoint() - pid_subsystem.getPosition());
    	
    	switch(m_state) {
	    	case Accelerate_Up:
	    		// follow velocity curve to get to full output
	    		if (pid_subsystem == Robot.m_Lift) {
	    			Robot.m_Lift.raise(S_Curve[velocity_step++]);
	    		} 	    		
	    		if (required_delta > pid_active_range)  {
	    			if (velocity_step >= S_Curve.length) m_state = ControlStates.Fast_Up;
	    		} else {
	    			m_state = ControlStates.PID;
	    		}
	    		break;
	    	case Fast_Up:
	    		// full output up....
	    		if (pid_subsystem == Robot.m_Lift) {
	    			Robot.m_Lift.raise(1);
	    		} 
	    		
	    		if (required_delta <= pid_active_range)  {
	    			m_state = ControlStates.PID;
	    		}
	    		break;
	    	case PID:
	    		pid_subsystem.enable();
	    		break;
	    	case Fast_Down:
	    		 // full output down....
	    		if (pid_subsystem == Robot.m_Lift) {
	    			Robot.m_Lift.lower(1);
	    		} 
	    		
	    		if (required_delta <= pid_active_range)  {
	    			m_state = ControlStates.PID;
	    		}
	    		break;
	    	case Accelerate_Down:	
	    		// follow velocity curve to get to full output
	    		if (pid_subsystem == Robot.m_Lift) {
	    			Robot.m_Lift.lower(S_Curve[velocity_step++]);
	    		} 
	    		
	    		if (required_delta > pid_active_range)  {
	    			if (velocity_step >= S_Curve.length) m_state = ControlStates.Fast_Down;
	    		} else {
	    			m_state = ControlStates.PID;
	    		}	
	    		break;
    	}
	}
	
	public boolean isFinished() {
		return pid_subsystem.onTarget();
		
	}
}
