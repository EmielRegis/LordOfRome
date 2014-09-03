package lord.of.rome;


/** An interface that allows implementing classes to move.
 * 
 * @author Carlos
 *
 */
public interface Moveable extends Runnable
{
	/** Moves from one place to another.
	 * @param A Current place.
	 * @param B Target place.
	 * 
	 * 
	 * 
	 */
	public void move(Place A, Place B);
	
	/** Moves along the selected road.
	 * @param road Selected road to move.
	 */
	public void move(Road road);	
}
