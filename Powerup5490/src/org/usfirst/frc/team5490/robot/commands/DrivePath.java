package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.Point3D;
import org.usfirst.frc.team5490.robot.HermiteSpline;
import org.usfirst.frc.team5490.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DrivePath extends Command {
	
	
	
	
	protected HermiteSpline m_path;
	private double[] percent_step_per_tick;	// percent per tick in a segment
	private double scan_rate = 0.02;	// 20 ms update rate
	private double scan_freq = 1.0 / scan_rate;
	private double speed;
	
	protected double max_motor = 5300;		// RPM
	protected double gear_ratio = 1 / 70.0; 	// gear
	protected double wheel_diameter = 8;		// in inches 

	private double adjusted_in_per_sec;
	
										// units per tick
	private int segment;				// what segment are we in
	private double percent;				// percent of start to end of segment
	
	private boolean done;
	private Point3D last;
	
	private int passes;
	
	

    public DrivePath() {
    	requires(Robot.m_Chassis);       	
    	
    }
    
    // Allow us to set the initial position (current)
    public void Init(Point3D start_pos)
    {
    	last.Copy(start_pos);    	
    }
    
    protected void PreparePath(HermiteSpline path, double speed, double unit_step_second) {
    	
    	last = new 	Point3D();
    	m_path = path;
    	segment = 1;
    	done = false;
    	    	
    	this.speed = speed;
    	
    	Robot.m_Chassis.segment = segment;
    	Robot.m_Chassis.percent = percent;
    	Robot.m_Chassis.tick = 0.0;
    	
    	
    	passes = 0;
    	
    	// mechanum wheels mess this up due to slip but it's a start
    	double max_in_per_sec = max_motor * gear_ratio * (2 * Math.PI * (wheel_diameter/2)) / 60.0;
    	
    	// nominal velocity at the users speed setting..
    	adjusted_in_per_sec = max_in_per_sec;
    	SmartDashboard.putNumber("Path adjusted_in_per_sec: ", adjusted_in_per_sec);

    	// calculate the estimate path length
    	percent_step_per_tick = new double[m_path.segments()];	// percent per tick in a segment
    	
    	
    	for(int i = 1; i < m_path.segments(); i++)  {
    		double estimated_move_length = m_path.PathLength(i);    		
    		// so we want the percent to move in the segment for a tick update 
    		percent_step_per_tick[i] =  adjusted_in_per_sec / estimated_move_length;
    	}
    	
    	SmartDashboard.putNumber("Path estimated_move_length[1]: ", m_path.PathLength(1));
    	//SmartDashboard.putNumber("Path estimated_move_length[2]: ", m_path.PathLength(2));
    	 
    	
    	SmartDashboard.putNumber("Path percent_step_per_tick[1]: ", percent_step_per_tick[1]);
    	//SmartDashboard.putNumber("Path percent_step_per_tick[2]: ", percent_step_per_tick[2]);
    	SmartDashboard.putNumber("Path scan_freq: ", scan_freq);
    	SmartDashboard.putNumber("Path scan_rate: ", scan_rate);
    }
    
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {   
    	
    	passes++;
    	SmartDashboard.putNumber("Path passes: ", passes);
    	
    	// generate our new point on the path..
    	Point3D current = m_path.Calc(segment, percent);
    	
    	// turn our new calculated point into a slope (i.e. speed)
    	double sx = (current.x - last.x) * scan_freq;  // (avoid / by using frequency)
    	double sy = (current.y - last.y) * scan_freq;
    	double sz = (current.z - last.z) * scan_freq;
    	
    	
    	// ToDo - figure out slope to motor output mapping
    	// need way to normalize slopes here
    	
    	// send the path velocities to the motors.. 
    	Robot.m_Chassis.Drive(sx, sy, sz, speed);    
    	
    	// determine what the next calculation will be (or if we are done)
    	if ((segment >= 0) && (segment <  percent_step_per_tick.length)) {
    		percent += percent_step_per_tick[segment];
    	} else {
    		percent = 1;
    	}
    	if (percent >= 1) {  // bump to next path segment
    		segment++;		// are we done..
        	
    		if (segment >= m_path.segments()) {    			
    			done = true;
    		} else {
    			//	percent -= 1.0;  // start the next segment with the remainder
    			percent = 0;	   // start the next segment at 0
    		}
    	}
    	Robot.m_Chassis.segment = segment;
    	Robot.m_Chassis.percent = percent;

    	// save our current position till next time...
    	last.Copy(current);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_Chassis.StopMotors();
    	SmartDashboard.putNumber("Path passes: ", passes);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }

}
