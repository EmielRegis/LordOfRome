package lord.of.rome;


/** Public interface for classes that can fight. Provides 'fight' function and enumeration class that contains the possible weapons.
 * 
 * @author Carlos
 *
 */
public interface Armed
{
	/** Method that allows implementing classes to fight.*/
	public void fight();
	
	/** Enumeration as the possible types of weapon for classes that implement Armed interface.
	 * 
	 * @author Carlos
	 *
	 */
	public enum Weapon
	{
		GLADIUS("gladius"),
		PILLUM("pillum"),
		SWORD("sword"),
		AXE("axe"),
		HAMMER("hammer"),
		MAGICAL_DRINK("magical drink"),
		LEG_OF_WIRT("leg of wirt"),
		BOW("bow");
		
		/** The name of used weapon.*/
		private String name;
		
		/** Enum constructor with the string 'name' as the parameter
		 * 
		 * @param inputName
		 */
		Weapon (String inputName)
		{
			this.name = inputName;
		}
		
		/** Returns the string name of a weapon.
		 * 
		 * @return string name of a weapon.
		 */
		public String getName()
		{
			return this.name;
		}
		
	}
	
}
