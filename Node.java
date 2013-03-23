
// TODO: these getters/setters aren't doing anything, so either 
// add something to them, or get rid of them

public class Node implements Comparable<Node>
{
    private String id;
    private String name;
    private String description;
    private Point center;
	
	// This constructor shouldn't be called, it's just here for
	// testing purposes
	public Node(){
        this.id = this.name = this.description = null;
    }
    
    public Node(String id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
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
