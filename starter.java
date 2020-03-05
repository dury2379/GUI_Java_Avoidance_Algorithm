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
			ArrayList<Point> points = downloader.readFile("1.csv", 300.0, 300.0, 100.0);
			System.out.println(points.size());
			Line l = new Line(300, 300, 300, 600);
			l.setColor(new Color(255, 255, 0));
			l.draw();
			pointControl.loadArrayList(points);
			Ellipse center = new Ellipse(297, 297, 6, 6);
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
				System.out.println(pointControl);
			else if ((int)(sc) == 10)
			{
				pointControl.findEdges();
				System.out.println("Searching for Edges");
			}
			else if (sc == 's')
			{
				pointCollection.drawEdges();
				System.out.println("Printed");
			}
			else if(sc == 'd')
			{
				pointCollection.stepDraw();
			}
			else if(sc == 'f')
			{
				double vector = pointCollection.findDerectionTo(finaldestination);
				Line direction = new Line(300, 300, 300+300*Math.cos(Math.toRadians(vector)), 300+300*Math.sin(Math.toRadians(vector)));
				direction.draw();
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
