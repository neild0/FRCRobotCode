package org.usfirst.frc.team5490.robot.commands;

import org.usfirst.frc.team5490.robot.PathRecord;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestSequencer extends PathSequence {

	private double speed = 1;
	
    public TestSequencer() {
    	super();
    	
    	PathRecord[] 	p =  {    			
    			new PathRecord( 0, 1, 0, speed, 1.5),
    			/*
        		new PathRecord(-1, 0, 0, speed, 1),
        		new PathRecord( 0, 1, 0, speed, 1),
            	new PathRecord( 0,-1, 0, speed, 1),
            	new PathRecord( 1, 0, 0, speed, 1),
            	// spin and back
            	new PathRecord( 0, 0, 1, speed, 1),
            	new PathRecord( 0, 0, -1, speed, 1),
            	
            	// diagonal
            	new PathRecord( 1, 1, 0, speed, 1),
            	new PathRecord( 1, -1, 0, speed, 1),
            	new PathRecord( -1, 1, 0, speed, 1),
            	new PathRecord( -1, -1, 0, speed, 1)
            	*/    			
        	};
    	path = p;

    }

}
