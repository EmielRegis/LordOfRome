package lord.of.rome;


/** Application loader. In this class you can find application 'main' function.
 * 
 * @author Carlos
 *
 */
public final class Loader
{
	/** Private constructor*/
	private Loader(){}
	
	/** Application 'main' function*/
	public static void main (String[] args)
	{
		Empire e = Empire.getInstance();
		//e.properEmpire();
		e.createEmpire();
	}	
}
