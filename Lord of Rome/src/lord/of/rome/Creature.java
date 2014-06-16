package lord.of.rome;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** Class which represents concept of creature. It is alive, it can move, speak and be drawn. Unfortunately, it references to some Empire global fields and it should be corrected as soon as possible.
 * 
 * @author Carlos
 *
 */
public abstract class Creature extends DrawableObject implements Moveable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Field which holds information whether creature is living.*/
	protected boolean isLiving = true;
	/** Field which holds information whether creature should move.*/
	protected boolean shouldMove = true;
	/** Movement speed of creature.*/
	protected int movementSpeed;
	/** Maximal life value of creature.*/
	protected int maxLife;
	/** Current life value.*/
	protected int life;
	/** Life guard for synchronization.*/
	protected final Object lifeGuard = new Object();
	/** Movement direction of creature.*/
	protected volatile Road.Direction movementDirection = Road.Direction.NONE;
	/** Enumeration field which represents empire allies or enemies membership.*/
	protected Occupation occupation = Creature.Occupation.NEUTRAL;
	
	/** Creature name*/
	protected String name;
	
	/** Field that enables randomization of some operations and values.*/
	protected Random random = new Random();
	
	/** Empty constructor which creates blank, but use-ready creature.*/
	public Creature()
	{
		super();
		this.life = 100;
		this.maxLife = 100;
		this.name = "Unititled Creature";
		this.isLiving = true;
	}
	
	/** Constructor which sets initial x and y coordinates of creature.
	 * 
	 * @param x X coordinate.
	 * @param y y coordinate.
	 */
	public Creature(int x, int y)
	{
		super(x, y);
		this.life  =100;
		this.maxLife = 100;
		this.name = "unititled creature";
		this.isLiving = true;
	}
	
	/** Returns empire allies or enemies membership of creature.
	 * 
	 * @return Empire allies or enemies membership.
	 */
	public Occupation getOccupation()
	{
		return this.occupation;
	}
	
	/** Returns creature name.
	 * 
	 * @return Creature name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/** Returns maximal life value of creature.
	 * 
	 * @return Maximal life value.
	 */
	public int getMaxLife()
	{
		return this.maxLife;
	}
	
	/** Returns current life value of creature.
	 * 
	 * @return Current life value.
	 */
	public int getLife()
	{
		synchronized (lifeGuard)
		{
			return this.life;
		}
	}
	
	/** Reduces life value of creature by hurting it.
	 * 
	 * @param damage Damage value.
	 */
	public void decreaseLife(int damage)
	{
		synchronized(lifeGuard)
		{
			this.life -= damage;
		}
	}
	
	/** Reduces life value of creature by constant value.*/
	public void decreaseLife()
	{
		this.life -=1;
	}
	
	/** Finds shortest way between two places.
	 * 
	 * @param A Beginning place.
	 * @param B Ending place.
	 * @return List of roads between beginning and ending place.
	 */
	protected List <Road> findPath(Place A, Place B)
	{
		LinkedList <Road> list = new LinkedList<>();
		
		HashMap<String, Boolean> visited = new HashMap<>();
		for(String s : Empire.getInstance().CROSSROADS.keySet())
		{
			visited.put(s, Boolean.FALSE);
		}
		int[] i = new int[1];
		i[0] = Integer.MAX_VALUE;
		bruteForceForPath(A, B, list, visited, i, 0);
		
		return list;
	}
	
	/** Brute force algorithm using to find shortest Place between two places. It references to some Empire global fields.
	 * 
	 * @param currentPlace Beginning place.
	 * @param destinationPlace Ending, destination place.
	 * @param path List of roads between beginning and ending places.
	 * @param vis Map of places that are visited in current algorithm cycle.
	 * @param minLength Actual shortest way between beginning and ending places.
	 * @param currentLength Actual way between beginning and ending places.
	 * @return Information about shortest path searching.
	 */
	protected boolean bruteForceForPath(Place currentPlace, Place destinationPlace, LinkedList<Road> path, HashMap<String, Boolean> vis, int[] minLength, int currentLength)
	{
		String current = new String(currentPlace.getName());
		String destination = new String(destinationPlace.getName());
		String expectedKey = current+destination;
		
		if(currentLength > minLength[0])
		{
			//System.out.println("git");
			return false;
		}
		
		if(Empire.getInstance().ROADS.containsKey(expectedKey))
		{
			if(currentLength < minLength[0])
			{
				//System.out.println(currentLength);
				minLength[0] = currentLength;
				if(path.size() != 0)
				{
					path.removeAll(path);
				}
				path.addLast(Empire.getInstance().ROADS.get(expectedKey));
				return true;
			}
			else
			{
				return false;	
			}
			
		}
		{
			boolean bo = false;
			@SuppressWarnings("unused")
			int counter=0;
			Road shortest = null;
			for(String s : Empire.getInstance().CROSSROADS.keySet())
			{
				String next = s;
				if(((vis.get(next)) == Boolean.FALSE) && (Empire.getInstance().ROADS.containsKey(current+next)))
				{
					int cur = currentLength + Empire.getInstance().ROADS.get(current + next).getLength();
					vis.put(next, Boolean.TRUE);
					boolean bobo = bruteForceForPath(Empire.getInstance().CROSSROADS.get(s), destinationPlace, path, vis, minLength, cur);
					
					if (bobo == true)
					{
						
						shortest = Empire.getInstance().ROADS.get(current + next);
						bo = true;
						counter++;
					}
					vis.put(next, Boolean.FALSE);
				}	
			}
			if(bo==true)
			{
				path.addFirst(shortest);
				return true;
			}
		}
		
		return false;
	}
	
	/** Kill creature.*/
	public void kill()
	{
		isLiving = false;
	}
	
	/** Returns information whether creature is still alive.
	 * 
	 * @return Information whether creature is still alive.
	 */
	public boolean isLiving()
	{
		return isLiving;
	}
	
	/** Returns information whether creature should move.
	 * 
	 * @return information whether creature should move.
	 */
	public boolean isShouldMove()
	{
		return shouldMove;
	}
	
	/** Moves or retains creature.
	 * 
	 * @param keepMoving Moves or retains creature.
	 */
	public void setShouldMove(boolean keepMoving)
	{
		shouldMove = keepMoving;
	}
	
	/** Returns movement speed of creature.
	 * 
	 * @return Movement speed of creature.
	 */
	public int getMovementSpeed() 
	{
		return movementSpeed;
	}
	
	/** Sets movement direction of creature.
	 * 
	 * @param d Given movement direction.
	 */
	public void setMovementDirection(Road.Direction d)
	{
		this.movementDirection = d;
	}
	
	/** Return movement direction of creature.
	 * 
	 * @return movement direction of creature.
	 */
	public Road.Direction getMovementDirection()
	{
		return this.movementDirection;
	}
	
	/** Sets movement speed of creature.
	 * 
	 * @param movementSpeed Movement speed.
	 */
	public void setMovementSpeed(int movementSpeed) 
	{
		if(movementSpeed <10)
		{
			this.movementSpeed = 100;
		}
		else if(movementSpeed >100)
		{
			this.movementSpeed = 10;
		}
		else
		{
			this.movementSpeed = 110 - movementSpeed;
		}
		
	}
	
	/** Allows creature to speak or emit other voices.
	 * 
	 */
	abstract public void speak();
	
	/** Enumeration class specifying membership of creature to empire allies or enemies.
	 * 
	 * @author Carlos
	 *
	 */
	public enum Occupation
	{
		ALLY("ally"),
		ENEMY("enemy"),
		NEUTRAL("neutral");
		
		/** Occupation name*/
		private String name;
		
		/** Constructor with given occupation name.
		 * 
		 * @param inputName Occupation name.
		 */
		Occupation (String inputName)
		{
			this.name = inputName;
		}
		
		/** Returns occupation name.
		 * 
		 * @return Occupation name.
		 */
		public String getName()
		{
			return this.name;
		}
	}

}
