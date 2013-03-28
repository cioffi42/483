public class Point {

	double x,y;
	
	Point()
	{
		x = 0.0; 
		y = 0.0;
	}
	
	Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	
	public double getX() 
	{
		return this.x;
	}

	public double getY() 
	{
		return this.y;
	}
}

