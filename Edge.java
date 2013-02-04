public class Edge 
{
    double weight;
	Item item1, item2;
	
	public Edge(double weight, Item item1, Item item2)
	{
		this.weight = weight;
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double newWeight)
	{
		weight = newWeight;
	}
	
	public Item getFirstItem()
	{
		return item1;
	}
	
	public void setFirstItem(Item newItem)
	{
		item1 = newItem;
	}
	
	public Item getSecondItem()
	{
		return item2;
	}
	
	public void setSecondItem(Item newItem)
	{
		item2 = newItem;
	}
}
