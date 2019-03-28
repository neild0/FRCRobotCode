package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.HermiteSpline;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestSpline extends DrivePath {
	
	
	private static final double speed = 0.01;
	private static final double unit_step_second = 0.02;

	
	
    public TestSpline() {   	    
		super();
		
		
		
		
		m_path = new HermiteSpline();
		// define the path we want for this instance of DrivePath 
        m_path.Size(2);
        
        m_path.P(0).x = 0;
        m_path.P(0).y = 0;
        m_path.P(0).z = 0;		// angle
		
        m_path.T(0).x = 50;
        m_path.T(0).y = 0;
        m_path.T(0).z = 0;		// angle
		
        m_path.P(1).x = 0;
        m_path.P(1).y = 3;
        m_path.P(1).z = 0;

        m_path.T(1).x = -15;
        m_path.T(1).y = 0;
        m_path.T(1).z = 0;		// angle

        
        PreparePath(m_path,  speed,  unit_step_second);
   

    }
}
