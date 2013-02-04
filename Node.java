public class Node 
{
    private Point center;
	private Item item;
	
	public Node(Point center, Item item)
	{
		this.center = center;
		this.item = item;
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	public void setCenter(Point newCenter)
	{
		center = newCenter;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public void setItem(Item newItem)
	{
		item = newItem;
	}
}
