public class Item 
{
    String id;
	String name;
	String description;
	
	public Item(String id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
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
}
