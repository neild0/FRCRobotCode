package org.usfirst.frc.team5490.robot;

public class PathRecord {	
	public double X;
	public double Y;
	public double Z;
	public double speed;
	public double duration;
	
	public PathRecord(double X, double Y, double Z, double speed, double duration)
	{
		this.X =X;
		this.Y =Y;
		this.Z =Z;
		this.speed = speed;
		this.duration =duration;
	}
}
