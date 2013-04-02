// TODO: these getters/setters aren't doing anything, so either 
// add something to them, or get rid of them

public class Node implements Comparable<Node>
{
    private String id;
    private String name;
    private String description;
    private Point center;
    private double weight;
    
    // This is used for animations
    public Point oldCenter;
    public Point newCenter;
	
	// This constructor shouldn't be called, it's just here for
	// testing purposes
	public Node(){
        this.id = this.name = this.description = null;
        this.weight = 0;
    }
    
    public Node(String id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = 0;
    }
	
	public Point getCenter()
	{
		return center;
	}
	
	public void setCenter(Point newCenter)
	{
		center = newCenter;
	}
	
	public String getID()
    {
        return id;
    }
    
    public void setID(String newID)
    {
        id = newID;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public void setWeight(double newWeight)
    {
        weight = newWeight;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String newDescription)
    {
        description = newDescription;
    }

    @Override
    public int compareTo(Node other) {
        return name.compareTo(other.getName());
    }
    
}
