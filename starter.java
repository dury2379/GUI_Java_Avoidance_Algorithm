import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class starter
{
		static pointCollection pointControl;
		static Loader downloader;
		
		// 1m = 100px
		// margin is in pixels
		// due to the fact that 0,0 is the top left quorner, i have to shift points 3 meters down and 3 metrs right
		
		static double margin = 3;
		static Point finaldestination = new Point(200, 100, -1);
		static int pointNumber = 0;
		
		static int PAUSETIME = 1000;
		
        public static void main(String args[])
        {
			pointControl = new pointCollection(margin);
			downloader = new Loader();
			EasyReader out = new EasyReader("Heading.text");
			// following line is necessary for onMouseClick, don't change
			
			
			// please leave following line alone, necessary for keyboard input
			
			// put code here
			ArrayList<Point> points = downloader.readFile("LiDARpoints.csv", 0.0, 0.0, 20.0); 
			System.out.println(points + "\n size " + points.size());
			pointControl.loadArrayList(points);
			pointControl.findEdges();
			double vector = pointCollection.findDerectionTo(finaldestination);
			System.out.println("Direction to go (deg): " + vector);
			out.print(vector);
			out.close();
			double startTime = System.currentTimeMillis();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			while(System.currentTimeMillis()<startTime+(60*1000))
			{
				points.clear();
				EasyReader out = new EasyReader("Heading.text");
				points = downloader.readFile("LiDARpoints.csv", 0.0, 0.0, 20.0); 
				System.out.println(points + "\n size " + points.size());
				pointControl.loadArrayList(points);
				pointControl.findEdges();
				vector = pointCollection.findDerectionTo(finaldestination);
				System.out.println("Direction to go (deg): " + vector);
				out.print(vector);
				out.close();
				pointControl.clear();
				downloader.clear();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
			
		}
		
		
		
/* 		public void onMouseClick(double x, double y){
			// and/or here
			// pointControl.addPoint(x,y);
																							/* loads points from .csv in form of ArrayList of points 
																							second snd third numbers are x and y of where the center is going to be at
																							last number is the acale, now everything is X50 bigger
			// System.out.println(points.size());                                        
			// Line l = new Line(300, 300, 300, 600);   // creating a line that indicates drines straright ahead direction
			// l.setColor(new Color(255, 255, 0));
			// l.draw();
			//      // loads Arraylist of points into pointCollection class
			
			
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
	
		} */

}
