package org.usfirst.frc.team5490.robot;

public class Point3D 
{

    public double x;
    public double y;
    public double z;

    public Point3D()
    {
    	x = y = z = 0.0;
    }
    
    // make a copy...
    public Point3D(Point3D source)
    {
    	Copy(source);
    }
    
    public void Copy(Point3D source)
	{
		x = source.x;
		y = source.y;
		z = source.z;
	}
}
