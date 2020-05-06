import java.util.ArrayList;
public class Loader
{
	double x = 0;
	double y = 0;
	double z = 0;
	double a = 0;
	double azimuth = 0;
	ArrayList<Point> goodPoints = new ArrayList<Point>();
	ArrayList<Point> markedPoints = new ArrayList<Point>();
	boolean primed = false;
	
	int counter = 0;
		
    public Loader()
    {
		
		
	}
	
	public ArrayList<Point> readFile(String fileName, double dx, double dy, double scale)
	{
		EasyReader in = new EasyReader(fileName);
		in.readLine();
		while(in.eof()==false)
		{
			in.readWord();
			in.readWord();
			in.readWord();
			if(in.eof()==false)
			{
				x = Double.parseDouble(in.readWord());
				y = Double.parseDouble(in.readWord());
				z = Double.parseDouble(in.readWord());
			}
			in.readWord();
			in.readWord();
			if(in.eof()==false)
			{
				azimuth = Double.parseDouble(in.readWord());
			}
			in.readWord();
			in.readWord();
			in.readWord();
			if(in.eof()==false)
			{
				a = Double.parseDouble(in.readWord());
			}
			if(counter<1000)
				// System.out.println(primed);
			if(azimuth>180&&!primed)
			{
				primed = true;
				// System.out.println(azimuth);
			}
			
			if(a==-1&&!(azimuth<180&&primed)&&(Math.abs(x)>1.0&&Math.abs(y)>1.0))
			{
				goodPoints.add(new Point((x*scale)+dx, (y*scale)+dy, goodPoints.size()));
			}
			counter++;
			// System.out.println("Angle: " + (angle(x,y)) + ", Azimuth: " + azimuth);
			if(azimuth%90<=2||azimuth%90>=88)
			{
				// primed = true;
				// System.out.println(azimuth);
				// markedPoints.add(goodPoints.get(goodPoints.size()-1));
			}
			// System.out.println(x);
			// System.out.println(y);
			// System.out.println(z);
			// System.out.println(a);
			
		}
		in.close();
		return goodPoints;
	}
	
	public void clear()
	{
		goodPoints = new ArrayList<Point>();
		markedPoints = new ArrayList<Point>();
		primed = false;
	
		counter = 0;
	}
	
	public ArrayList<Point> getMarkedPoints()
	{
		return markedPoints;
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
