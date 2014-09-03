package lord.of.rome;

import java.util.LinkedList;


/** Class that represents concept of road. It has own course, beginning and ending points and movement direction.
 * In fact it consists of linked list of points. It is also associated with global field of Empire called 'POINTS'. 
 * For this reason it will be modified as soon as possible.
 * 
 * @author Carlos
 *
 */
public final class Road
{
	/** Field representing length of a road.*/
	private double length;
	
	/** Field representing slope of a road*/
	private double kFactor;
	/** Name of beginning place.*/
	private String aName; 
	/** Name of ending place*/
	private String bName;
	/** Position of beginning place.*/
	private Point aPoint;
	/** Position of ending place*/
	private Point bPoint;
	/** Direction of a road. Single road is always one-way*/
	private Direction direction = Direction.NONE;
	
	/** Linked list which holds points that belong to the road.*/
	public final LinkedList<Point> road = new LinkedList<>();
	
	/** Private constructor for reverse roads.
	 * 
	 */
	private Road()
	{
		
	}
	
	/** Main class constructor which creates new road from one place to another.
	 * 
	 * @param A Beginning place of a road.
	 * @param B Ending place of a road.
	 */
	public Road(Place A, Place B)
	{
		Empire roadEmpire = Empire.getInstance();
		
		int intXA, intYA, intXB, intYB;
		double xA, yA, xB, yB;
		
		intXA = A.getXPosition();
		intYA = A.getYPosition();
		intXB = B.getXPosition();
		intYB = B.getYPosition();
		
		xA = (double) intXA;
		yA = (double) intYA;
		xB = (double) intXB;
		yB = (double) intYB;
		
		aPoint = new Point((int) xA, (int) yA);
		bPoint = new Point((int) xB, (int) yB);
		
		aName = A.getName();
		bName = B.getName();
		
		length =  Math.sqrt((((xA-xB)*(xA-xB) + (yA - yB)*(yA - yB))));
		kFactor = (yB - yA)/(xB-xA);
		
		road.add(new Point(intXA, intYA));
		
		String keyA = String.valueOf(intXA) + "-" + String.valueOf(intYA);
		if(!roadEmpire.POINTS.containsKey(keyA));
		{
			MapPoint m = new MapPoint(intXA,intYA);
			roadEmpire.POINTS.put(keyA, m);
		}
		
		
		
		if((xA == xB) && (yA == yB))
		{
			direction = Direction.NONE;
		}
		else if(xA == xB)
		{
			if(yB > yA)
			{
				direction = Direction.SOUTH;
			}
			else
			{
				direction = Direction.NORTH;
			}		
			
			for (int i=1; i<(int)length; i++)
			{
				int x = (int) xA;
				int y;
				
				if(yB > yA)
				{
					y = ((int)yA) + i;
				}
				else
				{
					y = ((int)yA) - i;
				}
				
				road.add(new Point(x,y));
				
				String key = String.valueOf(x) + "-" + String.valueOf(y);
				if(!roadEmpire.POINTS.containsKey(key));
				{
					MapPoint m = new MapPoint(x,y);
					roadEmpire.POINTS.put(key, m);
				}
				
			}
		}
		else if(yA == yB)
		{
			if(xB > xA)
			{
				direction = Direction.EAST;
			}
			else
			{
				direction = Direction.WEST;
			}
			
			for (int i=1; i<(int)length; i++)
			{
				int x;
				int y = (int)yA;
				
				if(xB > xA)
				{
					x = ((int)xA) + i;
				}
				else
				{
					x = ((int)xA) - i;
				}
				
				road.add(new Point(x,y));
				
				String key = String.valueOf(x) + "-" + String.valueOf(y);
				if(!roadEmpire.POINTS.containsKey(key));
				{
					MapPoint m = new MapPoint(x,y);
					roadEmpire.POINTS.put(key, m);
				}
			}
		}
		else
		{	
			if((xB > xA) && (yB > yA))
			{
				direction = Direction.SOUTH_EAST;
			}
			else if((xB > xA) && (yB < yA))
			{	
				direction = Direction.NORTH_EAST;
			}
			else if((xB < xA) && (yB > yA))
			{
				direction = Direction.SOUTH_WEST;
			}
			else if((xB < xA) && (yB < yA))
			{
				direction = Direction.NORTH_WEST;
			}
				
			for (int i=1; i<(int)length; i++)
			{
				int x;
				int y;
				
				double c = (double) i;
				double a = c/(Math.sqrt((kFactor*kFactor + 1.0))); 
				double b = Math.abs(a*kFactor);
				
				if((xB > xA) && (yB > yA))
				{
					x = (int)(xA + a);
					y = (int)(yA + b);
				}
				else if((xB > xA) && (yB < yA))
				{
					x = (int)(xA + a);
					y = (int)(yA - b);
				}
				else if((xB < xA) && (yB > yA))
				{
					x = (int)(xA - a);
					y = (int)(yA + b);
				}
				else if((xB < xA) && (yB < yA))
				{
					x = (int)(xA - a);
					y = (int)(yA - b);
				}
				else
				{
					x = 100;
					y = 100;
				}
				
				road.add(new Point (x,y));
				
				String key = String.valueOf(x) + "-" + String.valueOf(y);
				if(!roadEmpire.POINTS.containsKey(key));
				{
					MapPoint m = new MapPoint(x,y);
					roadEmpire.POINTS.put(key, m);
				}
				

			}
		}
		
		road.add(new Point(intXB, intYB));
		
		String keyB = String.valueOf(intXB) + "-" + String.valueOf(intYB);
		if(!roadEmpire.POINTS.containsKey(keyB));
		{
			MapPoint m = new MapPoint(intXB,intYB);
			roadEmpire.POINTS.put(keyB, m);
		}
	}
	
	/** Returns reversed road.
	 * 
	 * @return Reversed road.
	 */
	public Road createReverseRoad()
	{
		Road r = new Road();
		r.aName = this.bName;
		r.bName = this.aName;
		r.aPoint = this.bPoint;
		r.bPoint = this.aPoint;
		r.kFactor = this.kFactor;
		r.length = this.length;
		
		switch(this.direction)
		{
		case NONE:
			r.direction = Direction.NONE;
			break;
		case NORTH:
			r.direction = Direction.SOUTH;
			break;
		case SOUTH:
			r.direction = Direction.NORTH;
			break;
		case WEST:
			r.direction = Direction.EAST;
			break;
		case EAST:
			r.direction = Direction.WEST;
			break;
		case NORTH_WEST:
			r.direction = Direction.SOUTH_EAST;
			break;
		case NORTH_EAST:
			r.direction = Direction.SOUTH_WEST;
			break;
		case SOUTH_WEST:
			r.direction = Direction.NORTH_EAST;
			break;
		case SOUTH_EAST:
			r.direction = Direction.NORTH_WEST;
			break;	
		}
		
		for(Point p : this.road)
		{
			r.road.addFirst(p);
		}
		
		return r;
	}
	
	/** Returns position of beginning place.
	 * 
	 * @return Position of beginning place.
	 */
	public Point getAPosition()
	{
		return aPoint;
	}
	
	/** Returns position of ending place.
	 * 
	 * @return Position of ending place.
	 */
	public Point getBPosition()
	{
		return bPoint;
	}
	
	/** Returns name of beginning position.
	 * 
	 * @return Name of beginning position.
	 */
	public String getAName()
	{
		return aName;
	}
	
	/** Returns name of ending position.
	 * 
	 * @return Name of ending position.
	 */
	public String getBName()
	{
		return bName;
	}
	
	/** Returns length of a road.
	 * 
	 * @return Length of a road.
	 */
	public int getLength()
	{
		return (int) length;
	}
	
	/** Returns direction of a road.
	 * 
	 * @return Direction of a road.
	 */
	public Direction getDirection()
	{
		return this.direction;
	}
	
	/** Public enumeration class that represents all possible movement directions.
	 * 
	 * @author Carlos
	 *
	 */
	public enum Direction
	{
		NONE("none"),
		NORTH("north"),
		SOUTH("south"),
		WEST("west"),
		EAST("east"),
		NORTH_WEST("north-west"),
		NORTH_EAST("north-east"),
		SOUTH_WEST("south-west"),
		SOUTH_EAST("south-east");
		
		/** Name of direction.*/
		private String name;
		
		/** Constructor which sets name of direction.
		 * 
		 * @param inputName Name of direction.
		 */
		Direction (String inputName)
		{
			this.name = inputName;
		}
		
		/** Returns name of direction
		 * 
		 * @return Name of direction.
		 */
		public String getName()
		{
			return this.name;
		}
	}

}
