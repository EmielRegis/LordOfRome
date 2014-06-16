package lord.of.rome;


/** Composition class that represents concept of point on a map. It contains occupancy and direction enumeration fields and internal, enclosed coordinates point.
 * 
 * @author Carlos
 *
 */
public final class MapPoint
{
	/** Occupancy guard for synchronization.*/
	private final Object occupancyGuard = new Object();
	/** Point occupancy enumeration.*/
	private volatile Occupancy occ = Occupancy.FREE;
	/** Position guard for synchronization.*/
	private final Object positionGuard = new Object();
	/** Internal, enclosed position - point variable.*/
	private volatile Point position;
	/** Field that holds information about point initialization. */
	private boolean isInitialized = false;
	/** Collision guard for synchronization. */
	private final Object collisionGuard = new Object();
	/** Field that holds information about moveable objects collisions in specified map point.*/
	private volatile boolean isCollised = false;
	/** Direction guard for synchronization.*/
	private final Object directionGuard = new Object();
	/** Field that holds information about movement direction of previous moveable object which occupied specified map point.*/
	private volatile Road.Direction direction = Road.Direction.NONE;

	/** Public empty constructor*/
	public MapPoint()
	{
		position = new Point();
	}
	
	/** Public constructor that sets to map point given x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public MapPoint(int x, int y)
	{
		position = new Point(x, y);
		isInitialized = true;
		occ = Occupancy.FREE;
	}
	
	/** Returns x coordinate of point.
	 * 
	 * @return X coordinate.
	 */
	public int getXPosition()
	{
		synchronized(positionGuard)
		{
			return position.getXPosition();
		}
	}
	
	/** Returns y coordinate of point.
	 * 
	 * @return Y coordinate.
	 */
	public int getYPosition()
	{
		synchronized(positionGuard)
		{
			return position.getYPosition();
		}
	}
	
	/** Sets to map point x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void setPosition(int x, int y)
	{
		if(isInitialized == false)
		{
			position.setXPosition(x);
			position.setYPosition(y);
			
			isInitialized = true;
		}
	}
	
	/** Returns position of map point.
	 * 
	 * @return Position.
	 */
	public Point getPosition()
	{
		synchronized(positionGuard)
		{
			return new Point(position);
		}	
	}
	
	/** Sets direction of moveable object which occupies this map point.
	 * 
	 * @param d Movement direction.
	 */
	public void setDirection(Road.Direction d)
	{
		synchronized (directionGuard)
		{
			direction = d;
		}
	}
	
	/** Returns direction of moveable object which occupies this map point.
	 * 
	 * @return Movement direction.
	 */
	public Road.Direction getDirection()
	{
		synchronized(directionGuard)
		{
			return direction;
		}
	}
	
	/** Sets information about possible collisions of moveable objects at this map point.
	 * 
	 * @param b Information about possible collisions of moveable objects.
	 */
	public void setCollised(boolean b)
	{
		synchronized(collisionGuard)
		{
			isCollised = b;
		}	
	}
	
	/** Return information about collisions of moveable objects at this map point.
	 * 
	 * @return Information about collisions of moveable objects.
	 */
	public boolean isCollised()
	{
		synchronized(collisionGuard)
		{
			return isCollised;
		}
	}
	
	/** Sets information about type of unit which occupies this map point.
	 * 
	 * @param o Information about type of unit.
	 */
	public void setOccupancy(Occupancy o)
	{
		synchronized(occupancyGuard)
		{
			occ = o;
		}
	}
	
	/** Returns information about type of unit which occupies this map point.
	 * 
	 * @return Information about type of unit.
	 */
	public Occupancy getOccupancy()
	{
		synchronized(occupancyGuard)
		{
			return occ;	
		}
	}
	
	/** Public enumeration class that holds information about type of unit which occupies specified map point or region.
	 * 
	 * @author Carlos
	 *
	 */
	public enum Occupancy
	{
		FREE("free"),
		MERCHANT("merchant"),
		ROMAN_LEGION("roman legion"),
		BARBARIAN_HORDE("barbarian horde");
		
		/** Name of the unit type.*/
		private String occupancy;
		
		/** Constructor that sets name of an unit.*/
		Occupancy (String inputName)
		{
			this.occupancy = inputName;
		}
		
		/** Returns name of unit type.*/
		public String getOccupancy()
		{
			return this.occupancy;
		}
	}
	
	
}
