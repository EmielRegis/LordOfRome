package lord.of.rome;


/** Class that simulates concept of fraction, armed version of creature which is able to fight.
 * 
 * @author Carlos
 *
 */
public abstract class Fraction extends Creature
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Number of people belonging to fraction.*/
	protected volatile int numberOfPeople;
	/** People guard for synchronization.*/
	protected final Object peopleGuard = new Object();
	
	
	/** Empty constructor which creates blank fraction.*/
	public Fraction()
	{
		super();
	}
	
	/** Constructor which sets x and y coordinates of creature.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Fraction(int x, int y)
	{
		super(x,y);
	}
	
	/** Returns number of people in a fraction.
	 * 
	 * @return Number of people in a fraction.
	 */
	public int getNumberOfPeople() 
	{
		synchronized(peopleGuard)
		{
			return numberOfPeople;
		}
	}
	
	/** Sets number of people in a fraction.
	 * 
	 * @param numberOfPeople Sets number of people in a fraction.
	 */
	public void setNumberOfPeople(int numberOfPeople) 
	{
		synchronized (peopleGuard)
		{
			this.numberOfPeople = numberOfPeople;
		}
	}

}
