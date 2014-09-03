package lord.of.rome;

import java.io.Serializable;

import javax.swing.ImageIcon;


/** The basic abstract class of a lord.of.rome package. It allows inheriting classes to be drawn.
 * 
 * @author Carlos
 *
 */
public abstract class DrawableObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Actual position of object.*/
	protected volatile Point position;
	
	/** Drawing guard for synchronization*/
	protected final Object drawingGuard = new Object();
	/** Selection guard for synchronization*/
	protected final Object selectionGuard = new Object();
	/** Position guard for synchronization*/
	protected final Object positionGuard = new Object();
	
	/** Variable which holds an information about whether DrawableObjec should be drawn.*/
	protected volatile boolean shoudDraw = true;
	/** Variable which holds an information about whether DrawableObject is externally selected*/
	protected volatile boolean isSelected = false;
	
	/** Basic non-parameter constructor that creates new blank object.*/
	public DrawableObject()
	{
		position = new Point();
	}
	
	/** Constructor that creates new object with specified point-coordinates.
	 * 
	 * @param p Point-coordinates.
	 */
	public DrawableObject(Point p)
	{
		position = new Point(p);
	}
	
	/** Constructor that creates new object with specified x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public DrawableObject(int x, int y)
	{
		position = new Point(x, y);
	}
	
	/** Draws an object */
	public abstract void draw();

	/** Returns information whether an object is currently selected.
	 * 
	 * @return Information whether an object is currently selected.
	 */
	public boolean isSelected()
	{
		synchronized(selectionGuard)
		{
			return isSelected;
		}
	}
	
	/** Sets information about object selection.
	 * 
	 * @param boo Information whether DrawableObject should be selected.
	 */
	public void setSelected(boolean boo)
	{
		synchronized(selectionGuard)
		{
			isSelected = boo;
		}
	}

	/**
	 * Returns X coordinate of object.
	 * @return X coordinate.
	 */
	public int getXPosition() 
	{
		synchronized (positionGuard)
		{
			return position.getXPosition();
		}		
	}

	/** Sets X coordinate of object.
	 * 
	 * @param xPosition X coordinate.
	 */
	public void setXPosition(int xPosition) 
	{
		synchronized(positionGuard)
		{
			position.setXPosition(xPosition);
		}	
	}

	/** Returns Y coordinate of object.
	 * 
	 * @return Y coordinate.
	 */
	public int getYPosition() 
	{
		synchronized (positionGuard)
		{
			return position.getYPosition();
		}	 
	}

	/** Sets Y coordinate of object.
	 * 
	 * @param yPosition Y coordinate.
	 */
	public void setYPosition(int yPosition) 
	{
		synchronized (positionGuard)
		{
			position.setYPosition(yPosition);
		}
	}
	
	/** Returns current position of object.
	 * 
	 * @return Current position.
	 */
	public  Point getPosition()
	{
		synchronized (positionGuard)
		{
			return new Point(position);
		}	
	}
	
	/** Sets coordinates of object.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void setPosition(int x, int y)
	{
		synchronized (positionGuard)
		{
			position.setXPosition(x);
			position.setYPosition(y);
		}
	}

	/** Returns information whether object should be drawn.
	 * 
	 * @return Information whether object should be drawn.
	 */
	public boolean isShoudDraw() 
	{
		synchronized(drawingGuard)
		{
			return shoudDraw;
		}
	}

	/** Sets information whether object should be drawn.
	 * 
	 * @param shoudDraw Information whether object should be drawn.
	 */
	public void setShoudDraw(boolean shoudDraw) 
	{
		synchronized (drawingGuard)
		{
			this.shoudDraw = shoudDraw;
		}
	}
	
	/** Return image that represents graphically given object.
	 * 
	 * @return Imge that represents graphically given object.
	 */
	public abstract ImageIcon getImage();
	
}
