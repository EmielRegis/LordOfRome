package lord.of.rome;

import java.util.Date;


/** Class which simulates the concept of various kind of stock, products, food, materials and other stuff.
 * 
 * @author Carlos
 *
 */
public class Stock 
{
	/** Stock identifier*/
	protected String id;
	/** Stock type.*/
	protected StockType type;
	/** Stock volume.*/
	protected int volume=0;
	/** Stock weight.*/
	protected int weight=0;
	/** Stock gold value.*/
	protected int value=0;
	/** Stock manufacturing settlement.*/
	protected String manufacturingSettlement;
	/** Stock destination settlement.*/
	protected String destinationSettlement;
	/** Expiration date of stock (if exist).*/
	protected Date expirationDate;
	
	/** Public constructor with given stock type
	 * 
	 * @param stockType stock type;
	 */
	public Stock (StockType stockType)
	{
		type = stockType;
	}
	
	/** Returns stock identifier.
	 * 
	 * @return stock identifier.
	 */
	public String getId() 
	{
		return id;
	}

	/** Sets stock identifier.
	 * 
	 * @param id Stock identifier.
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/** Returns stock type.
	 * 
	 * @return Stock type.
	 */
	public StockType getType()
	{
		return type;
	}

	/*public void setType(StockType type) 
	{
		this.type = type;
	}*/

	/** Returns stock volume.
	 * 
	 * @return Stock volume.
	 */
	public int getVolume()
	{
		return this.volume;
	}

	/*public void setVolume(int volume)
	{
		this.volume = volume;
	}*/
	
	/** Increases stock volume by value.
	 * 
	 * @param vol Increased value.
	 */
	public void addVolume(int vol)
	{
		this.volume += vol;
		this.value = this.volume * this.type.price;
		this.weight = this.volume * this.type.thickness;
	}
	
	/** Decreases stock volume by value.
	 * 
	 * @param vol Decreased value.
	 */
	public void removeVolume(int vol)
	{
		if(vol <= this.volume)
		{
			this.volume -= vol;
			this.value = this.volume * this.type.price;
			this.weight = this.volume * this.type.thickness;
		}
		else
		{
			this.clear();
		}
		
	}
	
	/** Clears stock volume.*/
	public void clear()
	{
		this.volume = 0;
		this.value = 0;
		this.weight = 0;
	}

	/** Returns stock weight.*/
	public int getWeight() 
	{
		return weight;
	}

	/*public void setWeight(int weight) 
	{
		this.weight = weight;
	}*/

	/** Returns manufacturing settlement.
	 * 
	 * @return Manufacturing settlement.
	 */
	public String getManufacturingSettlement() 
	{
		return manufacturingSettlement;
	}

	/** Sets manufacturing settlement
	 * 
	 * @param manufacturingSettlement Manufacturing settlement.
	 */
	public void setManufacturingSettlement(String manufacturingSettlement) 
	{
		this.manufacturingSettlement = manufacturingSettlement;
	}

	/** Returns destination settlement.
	 * 
	 * @return Destination settlement.
	 */
	public String getDestinationSettlement() 
	{
		return destinationSettlement;
	}

	/** Set destination settlement.
	 * 
	 * @param destinationSettlement Destination settlement.
	 */
	public void setDestinationSettlement(String destinationSettlement)
	{
		this.destinationSettlement = destinationSettlement;
	}

	/** Returns expiration date of stock (if exists).
	 * 
	 * @return Expiration date of stock.
	 */
	public Date getExpirationDate()
	{
		return expirationDate;
	}

	/** Set expiration date of stock.
	 * 
	 * @param expirationDate Expiration date of stock.
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/** Enumeration class which contains all possible types of stock.
	 * 
	 * @author Carlos
	 *
	 */
	public enum StockType
	{
		WINE("wine", 12, 10, 3, false),
		HONEY("honey", 6, 9, 4, false),
		BREAD("bread", 2, 4, 6, true),
		SALT("salt", 30, 11, 4, false),
		SPICES("spices", 27, 9, 5, true),
		OLIVES("olives", 7, 7, 6, true),
		FISH("fish", 2, 8, 4, true),
		MEAT("meat", 3, 8 ,4 , true),
		MILK("milk", 4, 10, 6, true),
		WEAPON("wepon", 58, 20, 2, false),
		SLAVE("slave",200, 80, 1, true),
		CLOTHES("clothes", 6, 2, 2, false),
		IRON("iron", 20, 15, 3, false),
		BRONZE("bronze", 22, 15, 2, false),
		SILVER("silver", 30, 15, 2, false),
		GOLD("gold", 70, 15, 1, false),
		ROCK("rock", 7, 9, 4, false),
		MARBLE("marble", 30, 18, 1, false),
		COAL("coal", 6, 7, 2, false),
		DIAMONDS("diamonds", 120, 15, 1, false),
		WOOD("wood", 4, 5, 8, false);
		
		/** Stock type thickness*/
		private int thickness;
		/** Stock type gold price*/
		private int price;
		/** Stock type production speed*/
		private int productionSpeed;
		/** Stock type name*/
		private String name;
		/** Information whether stock type have an expiration date*/
		private boolean haveExpirationDate;
		
		
		/** Constructor with given stock name, price, thickness, production speed and expiration date information.
		 * 
		 * @param inputName Stock type name.
		 * @param inputPrice Stock type gold price.
		 * @param inputThickness Stock type thickness.
		 * @param inputProductionSpeed Stock type production speed.
		 * @param inputHaveExpirationDate Information about stock type expiration date.
		 */
		StockType (String inputName, int inputPrice,int inputThickness, int inputProductionSpeed, boolean inputHaveExpirationDate)
		{
			this.thickness = inputThickness;
			this.price = inputPrice;
			this.productionSpeed = inputProductionSpeed;
			this.name = inputName;
			this.haveExpirationDate = inputHaveExpirationDate;
		}
		
		/** Returns stock type thickness.
		 * 
		 * @return Stock type thickness.
		 */
		public int getThickness()
		{
			return this.thickness;
		}
		
		/** Returns stock type gold price.
		 * 
		 * @return Stock type gold price.
		 */
		public int getPrice()
		{
			return this.price;
		}
		
		/** Returns stock type production speed.
		 * 
		 * @return Stock type production speed.
		 */
		public int getProductionSpeed()
		{
			return productionSpeed;
		}
		
		/** Returns stock type name.
		 * 
		 * @return Stock type name.
		 */
		public String getName()
		{
			return this.name;
		}
		
		/** Returns information about stock type expiration date.
		 * 
		 * @return Information about stock type expiration date.
		 */
		public boolean getHaveExpirationDate()
		{
			return this.haveExpirationDate;
		}
		
	}
}
