import java.util.ArrayList;

class Edge
{
	ArrayList<Point> points = new ArrayList<Point>();
	Point startPoint;
	Point endPoint;
	Point ORIGIN;
	
	int edgeNum;
	
	int pointNum = 0;
	
	double Near0Angle = 10.0;
	
	public Edge(Point StartPoint, int EdgeNum, Point origin)
	{
		edgeNum = EdgeNum;
		startPoint = StartPoint;
		endPoint = StartPoint;
		ORIGIN = origin;
		points.add(startPoint);
	}
	
	public Edge(ArrayList<Point> Points, int EdgeNum, Point origin)
	{
		edgeNum = EdgeNum;
		startPoint = Points.get(0);
		endPoint = Points.get(Points.size()-1);
		ORIGIN = origin;
		points.addAll(Points);
	}
	
	public void addPoint(Point newPoint)
	{
		String result = "";
		points.add(newPoint);
		// if(distance(endPoint, newPoint)>10)
		// {
			// System.out.println(edgeNum+"");
			// Ellipse start = new Ellipse(endPoint.getX()-3, endPoint.getY()-3, 6, 6);
			// start.setColor(new Color(255, 0, 255));
			// start.fill();
			// start = new Ellipse(newPoint.getX()-3, newPoint.getY()-3, 6, 6);
			// start.setColor(new Color(255, 0, 255));
			// start.fill();
			// Line l = new Line(endPoint.getX(), endPoint.getY(), newPoint.getX(), newPoint.getY());
			// l.setColor(new Color(255, 0, 255));
			// l.draw();
			// result = "Idiot";
		// }
		endPoint = newPoint;
		// return result;
	}
	
	public void addStartToEnd(Edge newSart)
	{
		startPoint = newSart.getStartPoint();
		ArrayList<Point> newSartPoints = newSart.getPoints();
		for(int i = 0; i < newSartPoints.size(); i++)
		{
			newSartPoints.get(i).setEdgeNum(edgeNum);
			points.add(i, newSartPoints.get(i));
		}
		
	}
	
	public void addEndToEnd(Edge newEnd)
	{
		endPoint = newEnd.getStartPoint();
		ArrayList<Point> newEndPoints = newEnd.getPoints();
		for(int i = newEndPoints.size()-1; i >= 0; i--)
		{
			newEndPoints.get(i).setEdgeNum(edgeNum);
			points.add(newEndPoints.get(i));
		}
	}
	
	public double getAveragedistance()
	{
		return sum()/points.size();
	}
	
	private double sum()
	{
		double sum = 0.0;
		for(int i = 0; i < points.size(); i++)
		{
			sum += distanceInbetween(points.get(i), ORIGIN);
		}
		return sum;
	}
	
	public void loop()
	{
		endPoint = startPoint;
		points.add(endPoint);
	}

	
	public void setEdgeNum(int num)
	{
		edgeNum = num;
		for(int i = 0; i < points.size(); i++)
		{
			points.get(i).setEdgeNum(num);
		}
	}
	
	public void removePoint(Point p)
	{
		for(int i = 0; i<points.size(); i++)
		{
			if(points.get(i).getPointNum() == p.getPointNum())
			{
				points.remove(i).setEdgeNum(-1);
				startPoint = points.get(0);
			}
		}
	}
	
	public int getEdgeNum()
	{
		return edgeNum;
	}
	
	
	private double distanceInbetween(Point A, Point B)
	{
		double dx = B.getX() - A.getX();
		double dy = B.getY() - A.getY();
		double distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}
	
	public boolean containsNear0Q1()
	{
		for(int i = 0; i < points.size(); i++)
		{
			if(points.get(i).getAngle()<=Near0Angle)
				return true;
		}
		return false;
	}
	
	public boolean containsNear0Q4()
	{
		for(int i = 0; i < points.size(); i++)
		{
			if(points.get(i).getAngle()>=(360.0-Near0Angle))
				return true;
		}
		return false;
	}
	
	public void sortRadialy()
	{
		updateAngles();
		ArrayList<Point> newList = new ArrayList<Point>();
		while(points.size()>0)
		{
			newList.add(points.remove(minAt(points)));
		}
		points = newList;
	}
	
	private static int minAt(ArrayList<Point> arr)
	{
		int index = 0;
		double min = arr.get(index).getAngle();
		for(int i = 0; i < arr.size(); i++)
		{
			if(min>arr.get(i).getAngle())
			{
				min = arr.get(i).getAngle();
				index = i;
			}
		}
		return index;
	}
	
	public void MinMaxIsEdgepoints()
	{
		startPoint = points.get(0);
		endPoint = points.get(points.size()-1);
	}
	
	public void Near0edgePoints()
	{
		int IndexOfTheStartPoint = findIndexOftheLargestGap();
		startPoint = points.get(IndexOfTheStartPoint);
		endPoint = points.get(IndexOfTheStartPoint+1);
	}
	
	private int findIndexOftheLargestGap()
	{
		double maxGap = 0;
		int indesOfTheLargestGap = 0;
		for(int i = 0; i < points.size()-1; i++)
		{
			if(maxGap<Math.abs(points.get(i).getAngle()-points.get(i+1).getAngle()))
			{
				maxGap = Math.abs(points.get(i).getAngle()-points.get(i+1).getAngle());
				indesOfTheLargestGap = i;
			}
		}
		return indesOfTheLargestGap;
	}
	
	private void updateAngles()
	{
		for(int i = 0; i < points.size(); i++)
		{
			points.get(i).setAngle(angle(points.get(i).getX()-ORIGIN.getX(), points.get(i).getY()-ORIGIN.getY()));
		}
	}

	public ArrayList<Point> getPoints()
	{
		return points;
	}
	
	public void setPoints(ArrayList<Point> newPoints)
	{
		points = newPoints;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
	
	public void setEndPoint(Point NewEnd)
	{
		endPoint = NewEnd;
	}
	
	public void setStartPoint(Point NewStart)
	{
		startPoint = NewStart;
	}
	
	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public String toString()
	{
		return points+""+ startPoint + endPoint + "\n";
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
}