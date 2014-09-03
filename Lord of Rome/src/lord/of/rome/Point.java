package lord.of.rome;

import java.io.Serializable;


/** Class that represents concept of point, the smallest single element on map or coordinate system.
 * 
 * @author Carlos
 *
 */
public class Point implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Position guard for synchronization*/
	protected final Object positionGuard = new Object();
	/** X coordinate.*/
	protected volatile int xPosition;
	/** Y coordinate*/
	protected volatile int yPosition;
	
	/** Public blank constructor.*/
	public Point(){}
	
	/** Public constructor with given x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Point(int x, int y)
	{
		this.xPosition = x;
		this.yPosition = y;
	}
	
	/** Copying constructor that clones given point.
	 * 
	 * @param p Base point.
	 */
	public Point(Point p)
	{
		this.xPosition = p.xPosition;
		this.yPosition = p.yPosition;
	}
	
	/** Sets x coordinate of point.
	 * 
	 * @param x X coordinate.
	 */
	public void setXPosition(int x)
	{
		synchronized(positionGuard)
		{
			this.xPosition = x;
		}	
	}	
	
	/** Returns x coordinate of point.
	 * 
	 * @return X coordinate.
	 */
	public int getXPosition()
	{
		synchronized(positionGuard)
		{
			return xPosition;
		}
	}
	
	/** Sets y coordinate of point.
	 * 
	 * @param y Y coordinate.
	 */
	public void setYPosition(int y)
	{
		synchronized(positionGuard)
		{
			this.yPosition = y;	
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
			return yPosition;
		}
	}
	
	/** Sets x and y coordinates of point.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void setPosition(int x, int y)
	{
		synchronized(positionGuard)
		{
			this.xPosition = x;
			this.yPosition = y;
		}
	}
	
	/** Returns an two elements array of x and y coordinates.
	 * 
	 * @return [0] X coordinate, [1]   Y coordinate.
	 */
	public int[] getPosition()
	{
		synchronized(positionGuard)
		{
			int pos[] = new int[2];
			pos[0] = xPosition;
			pos[1] = yPosition;
			
			return pos;
		}	
	}
}
