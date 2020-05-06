
class Point{

	double x;
	double y;
	double angle;
	
	int edgeNum = -1;
	
	int pointNum;

	public Point(double X, double Y, int PointNum)
	{
		x = X;
		y = Y;
		pointNum = PointNum;
		angle = angle(x, y);

	}
	
	public boolean equals(Point otherPoint)
	{
		if(x==otherPoint.getX()&&y==otherPoint.getY())
			return true;
		return false;
	}
	
	public static double angle(double xe, double ye)
	{
		double angle = 0;
		if(xe>0.0 && ye>=0.0)
			angle = Math.toDegrees(Math.atan(ye/xe));
		if(xe>0.0 && ye<0.0)
			angle = Math.toDegrees(Math.atan(ye/xe))+360;
		if(xe<0)
			angle = Math.toDegrees(Math.atan(ye/xe))+180;
		if(xe==0)
		{
			if(ye<0)
				angle = 270;
			if(ye>0)
				angle = 90;
		}
		return angle;
	}
	
	public void setEdgeNum(int EdgeNum)
	{
		edgeNum = EdgeNum;
	}
	
	public int getEdgeNum()
	{
		return edgeNum;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public void setAngle(double Angle)
	{
		angle = Angle;
	}
	
	public int getPointNum()
	{
		return pointNum;
	}

	public String toString ()
	{
		return "(" + x + ", " + y + "), EdgeNum: " + edgeNum + ", Point Number: " + pointNum;
	}
}
