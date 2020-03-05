import java.util.ArrayList;
import pkg.*;

class Edge
{
	ArrayList<Point> points = new ArrayList<Point>();
	Point startPoint;
	Point endPoint;
	Point ORIGIN;
	
	int edgeNum;
	
	int pointNum = 0;
	
	public Edge(Point StartPoint, int EdgeNum, Point origin)
	{
		edgeNum = EdgeNum;
		startPoint = StartPoint;
		endPoint = StartPoint;
		ORIGIN = origin;
		points.add(startPoint);
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

	public void drawEdge()
	{
		for(int i = 0; i < points.size()-1; i++)
		{
			Line a = new Line(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
			a.draw();
		}
		
		Ellipse start = new Ellipse(startPoint.getX()-3, startPoint.getY()-3, 6, 6);
		start.setColor(new Color(255, 0, 0));
		start.fill();
		Ellipse end = new Ellipse(endPoint.getX()-3, endPoint.getY()-3, 6, 6);
		end.setColor(new Color(0, 0, 255));
		end.fill();
		Text num = new Text(startPoint.getX(), startPoint.getY(), " "+edgeNum);
		num.draw();
		
		
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
	
	public boolean stepDraw()
	{
		System.out.println("fdff");
		if(pointNum+1<points.size())
		{
			Line a = new Line(points.get(pointNum).getX(), points.get(pointNum).getY(), points.get(pointNum+1).getX(), points.get(pointNum+1).getY());
			a.draw();
			pointNum++;
			return true;
		}
		return false;
	}
	
	private double distanceInbetween(Point A, Point B)
	{
		double dx = B.getX() - A.getX();
		double dy = B.getY() - A.getY();
		double distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}
	
	public ArrayList<Point> getPoints()
	{
		return points;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
	
	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public String toString()
	{
		return points+""+ startPoint + endPoint + "\n";
	}
	
}