package org.usfirst.frc.team5490.robot;

//
//Implements a Hermite Cubic Curve
//
//

public class HermiteSpline {
	
	// how many points to use to find the path length estimate..
	private int EstimateGranularity = 100;

	private Point3D[] m_P;
	private Point3D[] mm_T;
	
	
	private Point3D m_Point = new Point3D();
	
	
	

	
	public void Size(int points)
	{        
        m_P = new Point3D[points];
        mm_T = new Point3D[points];

        for (int i = 0; i < points; i++)
        {
			if (m_P[i] == null)
            {
                m_P[i] = new Point3D();
            }
            if (mm_T[i] == null)
            {
                mm_T[i] = new Point3D();
            }
        }
	}
	
	
	public Point3D P(int i)
	{
	    return m_P[i];
	}
	
	public Point3D T(int i)
	{
	    return mm_T[i];
	}
	
	public int segments()
	{
		return m_P.length;
	}
	
	
	// calculate a point in the span of control points i-1..i 
	// i is index of control points
	// t is percentage between points  (i.e. range is 0..1)
	public Point3D Calc(int segment, double percent)
	{
	    double percent2 = 0;
	    double percent3 = 0;
	    double c = 0;
	    double a = 0;
	    double b = 0;
	    double d = 0;
	    
	    if (percent > 1) percent = 1;
	    if (percent < 0) percent = 0;
	
	    if ((segment > 0) && (segment < m_P.length))
	    {	
		    percent2 = percent * percent;
		    percent3 = percent2 * percent;
		    
		    //Curve.x = cx(0) * t3 + cx(1) * t2 + cx(2) * t + cx(3)
		    //Curve.y = cy(0) * t3 + cy(1) * t2 + cy(2) * t + cy(3)
		    //Curve.z = cz(0) * t3 + cz(1) * t2 + cz(2) * t + cz(3)
		
		    a = 2 * percent3 - 3 * percent2 + 1;		//h1
		    b = -2 * percent3 + 3 * percent2;			//h2	    	
		    c = percent3 - 2 * percent2 + percent;		//h3
		    d = (percent3 - percent2); 					//h4
		
		    m_Point.x = a * m_P[segment - 1].x + b * m_P[segment].x + c * mm_T[segment - 1].x + d * mm_T[segment].x;
		    m_Point.y = a * m_P[segment - 1].y + b * m_P[segment].y + c * mm_T[segment - 1].y + d * mm_T[segment].y;
		    
		    // we *may* want to just calculate slope here instead of a 3 dimension so that we can
		    // get the "twist" output the mechanum drive wants..
		    // for now use the 3rd dimension
		    m_Point.z = a * m_P[segment - 1].z + b * m_P[segment].z + c * mm_T[segment - 1].z + d * mm_T[segment].z;
	    }
	    
	    return m_Point;	
	}
	

	// get the length of all the segments...
	public double TotalPathLength()
	{
    	double estimated_move_length = 0;
    	for(int i = 0; i < m_P.length; i++)  {
    		estimated_move_length += PathLength(i);
    	}	
    	return estimated_move_length;
	}

	
	// calculate the XY path length (in whatever units the points are in) so that we can scale the path according to 
	// to the robots speed.
	// numerically integrate the path to estimate the path length
	public double PathLength(int segment)
	{
		double length = 0;
		
		
		if ((segment > 0) && (segment < m_P.length))  {
			Point3D LastPoint = new Point3D(m_P[segment-1]);
			Point3D Point;
			
			double scale = 1.0 / (double)EstimateGranularity;
			double percent = scale;
			
			// start at 1 cause we know the first point is 0,0
			for(int i = 1; i < EstimateGranularity; i++) {			
				//  get the next point on the path
				Point = Calc(segment, percent);
				// find delta X and Y for the segment
				double deltaX = Point.x - LastPoint.x;
				double deltaY = Point.y - LastPoint.y;
				// use the Pythagorean theorem to get distance..
				// and add it to our sum
				length += Math.sqrt(deltaX*deltaX + deltaY*deltaY); 
				// move on to the span ...
				percent += scale;
			}	
		}
		return length;	
	}
}
