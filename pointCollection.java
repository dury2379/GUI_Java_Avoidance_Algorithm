import java.util.ArrayList; 
import java.util.Collections;
import pkg.*;

class pointCollection{
	
	static ArrayList<Point> points = new ArrayList<Point>();
	
	static ArrayList<Edge> edges = new ArrayList<Edge>();
	
	static double margin;
	
	static int DEdgeNum = 0;
	
	static Point ORIGIN = new Point(300, 300.0, -1);
	
	public pointCollection(double Margin){
		margin = Margin;
	}
	
	public void addPoint(double X, double Y)
	{
		int pointNum = points.size();
		points.add(new Point(X, Y, pointNum));
		System.out.println(points.get(points.size()-1));
	}
	
	public void loadArrayList(ArrayList<Point> newPoints)
	{
		points = newPoints;
		// System.out.println(newPoints);
	}
	
	public static double findDerectionTo(Point destination)
	{
		double result;
		double LineA = angle(destination.getX()-ORIGIN.getX(), destination.getY()-ORIGIN.getY());
		Line straightPass = new Line(ORIGIN.getX(), ORIGIN.getY(), destination.getX(), destination.getY());
		straightPass.setColor(new Color(255, 0, 0));
		straightPass.draw();
		Point trouble = findClosestPointToLine(LineA);
		Rectangle troublee = new Rectangle(trouble.getX()-3, trouble.getY()-3, 6, 6);
		troublee.setColor(new Color(255, 0, 255));
		troublee.fill();
		if(lineToPointDistance(LineA, trouble)>margin||distanceInbetween(ORIGIN, destination)<distanceInbetween(ORIGIN, trouble))
			return LineA;
		Point closestToDrone = findClosestPointToORIGIN();
		if(distanceInbetween(ORIGIN, closestToDrone)<margin+0.1)
		{
			
			result = angle(closestToDrone.getX()-ORIGIN.getX(), closestToDrone.getY()-ORIGIN.getY())+180;
			if(result>=360)
				result = result - 360;
			// result = adjust(result);
			return result;
		}
		ArrayList<Double> possiblePath = findPathsAround(trouble);
		int locationOfTheBest = 0;
		double best = possiblePath.get(0);
		for(int i = 0; i < possiblePath.size(); i++)
		{
			if(Math.abs(possiblePath.get(i)-LineA)<Math.abs(possiblePath.get(locationOfTheBest)-LineA))
			{
				locationOfTheBest = i;
				best = possiblePath.get(locationOfTheBest);
			}
		}
		return(best);// DONT FORGET ADJUST!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	private static double adjust(double angl)
	{
		double result = -angl + 90;
		if(result<0)
			result = result+360;
		return result;
	}
	
	private static Point findClosestPointToLine(double angl)
	{
		System.out.println("You got here");
		Point result = points.get(0);
		boolean WasWrittenTo = false;
		for(int i = 0; i < points.size(); i++)
		{
			System.out.println(lineToPointDistance(angl, points.get(i)));
			if(lineToPointDistance(angl, points.get(i))<=margin)
			{
				if(!WasWrittenTo)
				{
					result = points.get(i);
					WasWrittenTo = true;
					System.out.println("Min Distance is: " + lineToPointDistance(angl, points.get(i)));
				}
				else
				{
					if(distanceInbetween(ORIGIN, result)>distanceInbetween(ORIGIN, points.get(i)))
					{
						result = points.get(i);
						System.out.println("Min Distance is: " + lineToPointDistance(angl, points.get(i)));
					}
				}
				// System.out.println("Min Distance is: " + lineToPointDistance(angl, points.get(i)));
			}
		}
		return result;
	}
	
	private static ArrayList<Double> findPathsAround(Point p)
	{
		ArrayList<Double> result = new ArrayList<Double>(); 
		int edgeNumb = p.getEdgeNum();
		double angl1 = angle(edges.get(edgeNumb).getStartPoint().getX()-ORIGIN.getX(), edges.get(edgeNumb).getStartPoint().getY()-ORIGIN.getY());
		double angl2 = angle(edges.get(edgeNumb).getEndPoint().getX()-ORIGIN.getX(), edges.get(edgeNumb).getEndPoint().getY()-ORIGIN.getY());
		result.add(angl1+diviation(edges.get(edgeNumb).getStartPoint()));
		result.add(angl1-diviation(edges.get(edgeNumb).getStartPoint()));
		result.add(angl2+diviation(edges.get(edgeNumb).getEndPoint()));
		result.add(angl2-diviation(edges.get(edgeNumb).getEndPoint()));
		System.out.println(result);
		for(int i = 3; i > -1; i--)
		{
			// Line direction = new Line(200, 100, 200+300*Math.cos(Math.toRadians(result.get(i))), 100+300*Math.sin(Math.toRadians(result.get(i))));
			// direction.draw();
			if(minDistanceToEdge(result.get(i), edgeNumb)<margin-0.0010)
				System.out.println(result.remove(i));
				
		}
		System.out.println(result.size());
		return result;
	}
	
	private static double diviation(Point p)
	{
		if((margin)/(distanceInbetween(ORIGIN, p))>0.5)
		{
			System.out.println("over here");
			return (Math.toDegrees(Math.asin((margin)/(distanceInbetween(ORIGIN, p)))))+7.0;
		}
		return (Math.toDegrees(Math.asin((margin)/(distanceInbetween(ORIGIN, p)))));
	}
	
	private static Point findClosestPointToORIGIN()
	{
		Point result = points.get(0);
		for(int i = 0; i < points.size(); i++)
		{
			if(distanceInbetween(ORIGIN, points.get(i))<distanceInbetween(ORIGIN, result))
				result = points.get(i);
		}
		return result;
	}
	
	public void findEdges()
	{
		int edgeNum = 0;
		
		edges = new ArrayList<Edge>();
		edges.add(new Edge(points.get(0), edgeNum, ORIGIN));
		points.get(0).setEdgeNum(edgeNum);
		// System.out.println(edges + "vvv\n");
		for(int i = 0; i < points.size(); i++)
		{
			int next = findNextFrom(i);
			// System.out.println("next is: " + next);
			if(next>i)
			{
				edges.get(edgeNum).addPoint(points.get(next));
				// System.out.println(edges.get(edgeNum).addPoint(points.get(next))+""+next+ "  " +distance(points.get(i), points.get(next)));
				points.get(next).setEdgeNum(edgeNum);
				i = next-1;
				// System.out.println("mmm" + next);
			}
			else
			{
				if(next==-1&&i!=points.size()-1)
				{
					edgeNum = edges.size();
					edges.add(new Edge(points.get(i+1), edgeNum, ORIGIN));
					points.get(i+1).setEdgeNum(edgeNum);
					// System.out.println("One");
				}
				else
				{
					if(next<i&&next!=-1)
					{
						RemoveAllEdgesPriorTo(next);
						System.out.println(next + " edge num " + points.get(next).getEdgeNum());
						if(points.get(next).getEdgeNum()==-1)
						{
							edges.get(edgeNum).addPoint(points.get(next));
							points.get(next).setEdgeNum(edgeNum);
							i=points.size();
							// System.out.println("two" + next);
						}
						else
						{
							combineEdges(points.get(next).getEdgeNum(), edges.size()-1);
							i=points.size();
							// System.out.println("three"+next);
						}
					}
				}
			}
				
			// System.out.println(j);
			
			// if(i<points.size()-1)
			// {
				// if(distanceInbetween(points.get(i),points.get(i+1))<=margin*2.0)
				// {
					// edges.get(edges.size()-1).addPoint(points.get(i+1));
					// points.get(i+1).setEdgeNum(edgeNum);
				// }
				// else
				// {
					// edgeNum = edges.size();
					// edges.add(new Edge(points.get(i+1), edgeNum));
					// points.get(i+1).setEdgeNum(edgeNum);
				// }
			// }
			// else
			// {
				// if(distanceInbetween(points.get(i),points.get(0))<=margin*2.0)
				// {
					// combineEdges(0, edges.size()-1);
				// }
			// }
		}
		checkEdges();
		// drawEdges();
		// System.out.println(edges);
	}
	
	public void combineEdges(int edgeNumA, int edgeNumB)
	{
		//  older    newer
		int older = 0;
		int newer = 0;
		if(edgeNumA<edgeNumB)
		{
			older = edgeNumA;
			newer = edgeNumB;
		}
		else if(edgeNumB<edgeNumA)
			{
				older = edgeNumB;
				newer = edgeNumA;
			}
			else
			{
				edges.get(edgeNumA).loop();
			}
			
		if(edgeNumA!=edgeNumB)
		{
			if(distanceInbetween(edges.get(newer).getEndPoint(), edges.get(older).getEndPoint()) < 
				distanceInbetween(edges.get(newer).getEndPoint(), edges.get(older).getStartPoint()))
				{
					edges.get(older).addEndToEnd(edges.get(newer));
					System.out.println("ee");
				}
			else if(distanceInbetween(edges.get(older).getStartPoint(), edges.get(newer).getEndPoint()) < 
				distanceInbetween(edges.get(older).getEndPoint(), edges.get(newer).getEndPoint()))
				{
					edges.get(older).addStartToEnd(edges.get(newer));
					System.out.println("se");
				}
			edges.remove(edgeNumB);
		}
	}
	
	private void RemoveAllEdgesPriorTo(int k)
	{
		ArrayList<Integer> edgesToRemove= new ArrayList<Integer>();
		for(int i = 0; i < k; i++)
		{
			if(points.get(i).getEdgeNum()>-1&&points.get(i).getEdgeNum()!=points.get(k).getEdgeNum())
			{
				if(!contains(edgesToRemove, points.get(i).getEdgeNum()))
				{
					edgesToRemove.add(points.get(i).getEdgeNum());
				}
			}
			if(points.get(i).getEdgeNum()==points.get(k).getEdgeNum())
			{
				edges.get(points.get(k).getEdgeNum()).removePoint(points.get(k));
			}
			points.get(i).setEdgeNum(-1);
		}
		Collections.sort(edgesToRemove);
		for(int i = edges.size()-1; i > -1; i--)
		{
			for(int j = 0;j < edgesToRemove.size();j++)
			{
				if(edges.get(i).getEdgeNum()==edgesToRemove.get(j))
				{
					edges.remove(i);
				}
			}
		}
		for(int i = 0; i<edges.size(); i++)
		{
			edges.get(i).setEdgeNum(i);
		}
		
	}
	
	private int findNextFrom(int p)
	{
		for(int i = 1; i < 150; i++)
		{
			if(p+i<points.size())
			{
				if(distanceInbetween(points.get(p), points.get(p+i))<=margin*2)
				{
					// System.out.print("1  ");
					return (p+i);
				}
			}
			else
			{
				int k = p + i - points.size();
				if(distanceInbetween(points.get(p), points.get(k))<=margin*2)
				{
					System.out.print("2  "+points.get(k).getEdgeNum());
					return (k);
				}
				
			}
		}	
		return -1;
	}
	
	private static double lineToPointDistance(double Aangle, Point p)
	{
		double pAngle = angle(p.getX()-ORIGIN.getX(), p.getY()-ORIGIN.getY());
		double theta = Math.toRadians(Math.abs(Aangle-pAngle));
		double pDistance = distanceInbetween(ORIGIN, p);
		return Math.abs(pDistance*Math.sin(theta));
	}
	
	private static double minDistanceToEdge(double Angle, int edgeNumb)
	{
		double minDistance = lineToPointDistance(Angle, edges.get(edgeNumb).getPoints().get(0));
		for(int i = 0; i < edges.get(edgeNumb).getPoints().size(); i++)
		{
			if(lineToPointDistance(Angle, edges.get(edgeNumb).getPoints().get(i))<minDistance)
				minDistance = lineToPointDistance(Angle, edges.get(edgeNumb).getPoints().get(i));
		}
		return minDistance;
		
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
	
	public boolean contains(ArrayList<Integer> edgeNums, int edgeNum)
	{
		boolean result = false;
		for(int i = 0; i < edgeNums.size(); i++)
		{
			if(edgeNums.get(i) == edgeNum)
				result = true;
		}
		return result;
	}
	
	private void checkEdges()
	{
		for(int i = 0; i < edges.size(); i++)
		{
			for(int j = 0; j < edges.get(i).getPoints().size(); j++)
			{
				if(edges.get(i).getPoints().get(j).getEdgeNum()!=edges.get(i).getEdgeNum())
					System.out.println("ooops");
			}
		}
	}
	
	public static void stepDraw()
	{
		if(DEdgeNum<edges.size()&&!edges.get(DEdgeNum).stepDraw())
			DEdgeNum++;
	}

	public static void drawEdges()
	{
		for(int i = 0; i < edges.size(); i++)
		{
			edges.get(i).drawEdge();
		}
		
	}
	
	private Point findFreePoint()
	{
		for(int i = 0; i < points.size(); i++)
		{
			if(points.get(i).getEdgeNum() == -1)
				return points.get(i);
		}
		return null;
	}
	
	private static double distanceInbetween(Point A, Point B)
	{
		double dx = B.getX() - A.getX();
		double dy = B.getY() - A.getY();
		double distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}
	
	// private double distance(Point one, Point two)
	// {
		// double dx = one.getX() - two.getX();
		// double dy = one.getY() - two.getY();
		// double distance = Math.sqrt(dx*dx + dy*dy);
		// return distance;
	// }
	
	public String toString()
	{
		return edges + "";
	}
}