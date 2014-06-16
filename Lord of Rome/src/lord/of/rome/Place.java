package lord.of.rome;


import javax.swing.ImageIcon;


/** Class that simulates  concept of place. It has own localization and name.
 * 
 * @author Carlos
 *
 */
public class Place extends DrawableObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Name of place */
	protected String name = "";
	/** Guard for synchronization.*/
	protected final Object nameGuard = new Object();
	
	/** Non-parameter constructor that creates new blank place.*/
	public Place()
	{
		super();
	}
	
	/** Constructor that creates new place with the specified x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Place(int x, int y)
	{
		super(x, y);
	}
	
	/** Constructor that creates new place with the specified name and x,y coordinates.
	 * 
	 * @param name Name of the place;
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Place(String name, int x, int y)
	{
		super(x,y);
		this.name = name;
	}
	
	/** Sets name of a place.
	 *  
	 * @param s Name of place.
	 */
	public void setName(String s)
	{
		synchronized(nameGuard)
		{
			this.name = s;
		}
	}
	
	/** Returns name of place.
	 * 
	 * @return Name of place;
	 */
	public String getName()
	{
		synchronized(nameGuard)
		{
			return name;
		}
	}

	@Override
	@Deprecated
	public void draw()
	{
		;
	}
	
	@Override
	public ImageIcon getImage()
	{
		return null;
	}
}
