package lord.of.rome;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

/** Class which simulates the concept of empire capital. Rome is the capital of the empire and the capital of the empire is Rome.
 * 
 * @author Carlos
 *
 */
public final class Capital extends Settlement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Image which represents Rome*/
	private static final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(BarbarianHorde.class.getResource("graphics/rome_icon.png")));

	/** Empty constructor*/ 
	public Capital()
	{
		super();
		beRome();
	}
	
	/** Constructor with given x and y coordinates of Rome location.
	 * 
	 * @param x X coordinate.
	 * @param y y coordinate.
	 */
	public Capital(int x, int y)
	{
		super(x,y);
		beRome();
	}
	
	/** Sends roman legion from the capital to the threatened settlement.
	 * 
	 */
	@Deprecated
	public void sendRomanLegion()
	{
		;
	}
	
	/** Sets Rome attributes.*/
	private void beRome()
	{
		this.name = "Rome";
		population = 1000;
		maxPopulation = 1000;
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
		return image;
	}
}
