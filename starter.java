import pkg.*;
import java.util.ArrayList;
public class starter implements InputControl, InputKeyControl 
{
		static pointCollection pointControl;
		static Loader downloader;
		
		// 1m = 100px
		// margin is in pixels
		// due to the fact that 0,0 is the top left quorner, i have to shift points 3 meters down and 3 metrs right
		static double margin = 3;
		static Point finaldestination = new Point(200, 100, -1);
		
		
        public static void main(String args[])
        {
			pointControl = new pointCollection(margin);
			downloader = new Loader();
			// following line is necessary for onMouseClick, don't change
			MouseController mC = new MouseController(Canvas.getInstance(),new starter());
			
			// please leave following line alone, necessary for keyboard input
			KeyController kC = new KeyController(Canvas.getInstance(),new starter());
			// put code here
			
			Line scalee = new Line(295, 220, 305, 220);
			scalee.setColor(new Color(255, 0, 255));
			scalee.draw();
			
		}
		
		public void onMouseClick(double x, double y){
			// and/or here
			// pointControl.addPoint(x,y);
			ArrayList<Point> points = downloader.readFile("1.csv", 300.0, 300.0, 20.0); /* loads points from .csv in form of ArrayList of points 
																							second snd third numbers are x and y of where the center is going to be at
																							last number is the acale, now everything is X50 bigger*/
			System.out.println(points.size());                                        
			Line l = new Line(300, 300, 300, 600);   // creating a line that indicates drines straright ahead direction
			l.setColor(new Color(255, 255, 0));
			l.draw();
			pointControl.loadArrayList(points);     // loads Arraylist of points into pointCollection class
			Ellipse center = new Ellipse(297, 297, 6, 6);    // mark center with Cyan dot
			center.setColor(new Color(25, 255, 255));
			center.fill();
			
			
	
		}
		
		public void onMousePress(double x, double y)
		{
			
		}
		
		public void onMouseRelease(double x, double y)
		{
			
		}
		
		public void onMouseEnter(double x, double y)
		{
			
		}
		
		public void onMouseExit(double x, double y)
		{
			
		}
		
		public void onMouseDrag(double x, double y)
		{
			
		}
		
		public void onMouseMove(double x, double y)
		{
			
		}
		
		public void keyPress(String s)
		{
			// System.out.println("You pressed: " + s);
			char sc = s.toCharArray()[0];
			if(sc == 'p')
				System.out.println(pointControl);  // prints array of points
			else if ((int)(sc) == 10)
			{
				pointControl.findEdges();
				System.out.println("Searching for Edges");  // uses loaded array of points to find lines of obstacles
			}
			else if (sc == 's')
			{
				pointCollection.drawEdges();  // draws lines and start and end points
				System.out.println("Printed");
			}
			else if(sc == 'd')
			{
				pointCollection.stepDraw(); // draws lines step by step (not working properly)
			}
			else if(sc == 'f')
			{
				double vector = pointCollection.findDerectionTo(finaldestination);
				Line direction = new Line(300, 300, 300+300*Math.cos(Math.toRadians(vector)), 300+300*Math.sin(Math.toRadians(vector)));
				direction.draw();  // draws straight line path and direction to fly.
			}
	
		}
		
		public void keyReleased(String s)
		{
			// System.out.println("You released: " + s);
	
		}
		
		public void keyTyped(String s)
		{
			// System.out.println("You typed: " + s);
	
		}

}
