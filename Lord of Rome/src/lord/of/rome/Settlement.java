package lord.of.rome;


import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.ImageIcon;

import lord.of.rome.Stock.StockType;


/** Class which simulates concept of roman empire settlement.
 * 
 * @author Carlos
 *
 */
public class Settlement extends Place
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Population of settlement.*/
	protected volatile int population;
	/** Population guard for synchronization.*/
	protected final Object populationGuard = new Object();
	/** Maximal population of settlement.*/
	protected int maxPopulation;
	
	/** Information whether settlement is attacked.*/
	protected volatile boolean isAttacked = false;
	/** Attack guard for synchronization.*/
	protected final Object attackGuard = new Object();

	/** Information whether settlement should produces goods.*/
	protected volatile boolean shoudProduce = true;
	/** Production guard for synchronization.*/
	protected final Object productionGuard = new Object();
	
	//protected final ArrayList<Settlement> settlementList = new ArrayList<>();
	
	/** Settlement treasury*/
	protected final Treasury treasury = new Treasury();
	/** Settlement warehouse*/
	protected final Warehouse warehouse = new Warehouse();

	/** Icon that graphically represents given settlement.*/
	private static final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(BarbarianHorde.class.getResource("graphics/latifundium_icon.png")));
	
	/** Empty constructor.*/
	public Settlement()
	{
		super();
		population = 100;
		maxPopulation =100;
		this.produceGoods();
	}
	
	/** Constructor with given x and y coordinates of settlement location.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Settlement(int x, int y)
	{
		super(x, y);
		population = 100;
		maxPopulation =100;
		this.produceGoods();
	}
	
	/** Constructor with given settlement name and location's coordinates.
	 * 
	 * @param name Settlement name.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Settlement(String name, int x, int y)
	{
		super(name, x,y);
		population = 100;
		maxPopulation =100;
		this.produceGoods();
	}
	
	/** Sells to settlement concrete stock with given volume.
	 * 
	 * @param type Stock type.
	 * @param volume Given stock volume to sell.
	 * @return Real sold stock volume.
	 */
	public int sellStockToSettlement(StockType type, int volume)
	{
		int maxVolume = 0;
		if (this.warehouse.neededGoods.containsKey(type))
		{
			Stock stock = this.warehouse.neededGoods.get(type);
			
			if(warehouse.availableSpace >= type.getThickness()*volume)
			{
				maxVolume = volume;
			}
			else
			{
				maxVolume = warehouse.availableSpace/type.getThickness();
			}
			
			stock.addVolume(maxVolume);
			warehouse.usedSpace += type.getThickness()*maxVolume;
			warehouse.availableSpace = Warehouse.capacity - warehouse.usedSpace;
		}
		
		return maxVolume;
	}
	
	/** Buy from settlement concrete stock with given volume.
	 * 
	 * @param type Stock type.
	 * @param volume Given stock volume to buy.
	 * @return Real bought sock volume.
	 */
	public int buyStockFromSettlement(StockType type, int volume)
	{
		int maxVolume = 0;
		if(this.warehouse.storedGoods.containsKey(type))
		{
			Stock stock = this.warehouse.storedGoods.get(type);
			
			if(volume <= stock.getVolume())
			{
				maxVolume = volume;
			}
			else
			{
				maxVolume = stock.getVolume();
			}
			
			stock.removeVolume(maxVolume);
			warehouse.usedSpace -= type.getThickness()*maxVolume;
			warehouse.availableSpace = Warehouse.capacity - warehouse.usedSpace;
		}
		
		return maxVolume;
	}
	
	/** Checks expiration date of all goods stored in warehouse. Not implemented yet.
	 * 
	 */
	@Deprecated
	public void checkExpirationDateofStoredGoods()
	{
		;
	}
	
	/** Decreases settlement population by given value called 'damage'.
	 * 
	 * @param damage Reduction value.
	 */
	public void decreasePopulation(int damage)
	{
		synchronized (populationGuard)
		{
			this.population -=damage;
		}
	}
	
	/** Decreases settlement population by constant value.
	 * 
	 */
	public void decreasePopulationConst()
	{
		synchronized(populationGuard)
		{
			this.population -= 1;
		}
	}
	
	/** Returns information whether settlement is attacked by enemy forces.
	 * 
	 * @return Information whether settlement is attacked.
	 */
	public boolean isAttacked()
	{
		synchronized(attackGuard)
		{
			return this.isAttacked;
		}
	}
	
	/** Attacks settlement. Information about attack is set to true.
	 * 
	 */
	public void setAttacked()
	{
		synchronized(attackGuard)
		{
			this.isAttacked = true;
		}
	}
	
	/** Liberates settlement. Information about attack is set to false.
	 * 
	 */
	public void setFreed()
	{
		synchronized(attackGuard)
		{
			this.isAttacked = false;
		}
	}
	
	@Override
	@Deprecated
	public void draw()
	{
		;
	}
	
	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public final void setName(String name) 
	{
		this.name = name;
	}

	/** Returns current population of settlement.
	 * 
	 * @return Current population of settlement.
	 */
	public int getPopulation() 
	{
		synchronized(populationGuard)
		{
			return population;
		}
	}
	
	/** Returns maximal population of settlement.
	 * 
	 * @return Maximal population of settlement.
	 */
	public int getMaxPopulation()
	{
		return maxPopulation;
	}

	/*public void setPopulation(int population) 
	{
		synchronized(populationGuard)
		{
			this.population = population;
		}
	}*/
	
	/** Sets information whether settlement should produce goods.
	 * 
	 * @param is Information whether settlement should produce goods.
	 */
	protected void setShoudProduce(boolean is)
	{
		synchronized(productionGuard)
		{
			this.shoudProduce = is;
		}
	}
	
	/** Stops production of goods.
	 * 
	 */
	public void stopProduction()
	{
		setShoudProduce(false);
	}
	
	/** Resumes production of goods.
	 * 
	 */
	public void resumeProduction()
	{
		setShoudProduce(true);
	}
	
	/** Returns information whether settlement should produce goods.
	 * 
	 * @return Information whether settlement should produce goods.
	 */
	public boolean isShoudProduce()
	{
		synchronized(productionGuard)
		{
			return shoudProduce;
		}
	}
	
	/** Returns available space in settlement warehouse.
	 * 
	 * @return Available space in settlement warehouse.
	 */
	public int getAvailableWarehouseSpace()
	{
		return warehouse.availableSpace;
	}
	
	/** Adds stock type to settlement produced goods list.
	 * 
	 * @param s Type of added stock.
	 */
	public void addProducingGood(Stock.StockType s)
	{
		if(!warehouse.storedGoods.containsKey(s))
		{
			warehouse.storedGoods.put(s, new Stock(s));
		}
	}
	
	/** Add stock type to settlement needed goods list.
	 * 
	 * @param s Type of added stock.
	 */
	public void addNeededGood(Stock.StockType s)
	{
		if(! warehouse.neededGoods.containsKey(s))
		{
			Stock stock = new Stock(s);
			//stock.addVolume(10);
			//warehouse.usedSpace += s.getThickness()*10;
			//warehouse.availableSpace = Warehouse.capacity - warehouse.usedSpace;
			warehouse.neededGoods.put(s,  stock);
		}
	}
	
	/** Returns settlement produced goods.
	 * 
	 * @return Settlement produced goods.
	 */
	public HashMap<Stock.StockType, Stock> getWarehouseGoods()
	{
		return warehouse.storedGoods;
	}
	
	/** Returns settlement needed goods.
	 * 
	 * @return Settlement needed goods.
	 */
	public HashMap<Stock.StockType, Stock> getNeededGoodsList()
	{
		return warehouse.neededGoods;
	}
	
	/** Consumes settlement needed goods decreasing them by a constant value.
	 * 
	 */
	private void consumeGoods()
	{
		for(Stock s : warehouse.neededGoods.values())
		{
			Stock.StockType type = s.getType();
			if(s.getVolume() > 0)
			{
				s.removeVolume(1);
				warehouse.usedSpace -= type.getThickness()*1;
				warehouse.availableSpace = Warehouse.capacity - warehouse.usedSpace;
				Empire.getInstance().increaseGold(1*type.getPrice());
			}			
		}
	}
	
	/** Produces settlement stored goods incrementing them by a constant value. This method creates new thread.
	 * 
	 */
	public void produceGoods()
	{
		new Thread("produkcja: " + this.getName())
		{
			public void run()
			{
				while(population > 0)
				{
					consumeGoods();
					
					if(isShoudProduce())
					{
						for(Stock s : warehouse.storedGoods.values())
						{
							Stock.StockType type = s.getType();
							if(warehouse.availableSpace >= type.getThickness()*type.getProductionSpeed())
							{
								s.addVolume(type.getProductionSpeed());
								warehouse.usedSpace += type.getThickness()*type.getProductionSpeed();
								warehouse.availableSpace = Warehouse.capacity - warehouse.usedSpace;
							}
							
							
						}
					}
					
					try 
					{
						Thread.sleep(5000);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				if(population<=0)
				{
					Empire.getInstance().SETTLEMENTS.remove(name);
					//System.out.println(Empire.getInstance().SETTLEMENTS.size());
				}
			}
		}.start();
	}

	/** Class that represents concept of settlement treasury.
	 * 
	 * @author Carlos
	 *
	 */
	protected final class Treasury
	{
		/** Treasury state.*/
		protected int state; 
	}
	
	/** Class that represents concept of settlement warehouse.
	 * 
	 * @author Carlos
	 *
	 */
	protected final class Warehouse
	{
		/** Warehouse capacity.*/
		protected static final int capacity = 100000;
		/** Current warehouse available space.*/
		protected int availableSpace=100000;
		/** Current warehouse used space.*/
		protected int usedSpace =0;
		
		/** Warehouse stored - produced goods.*/
		protected final HashMap<Stock.StockType, Stock> storedGoods = new HashMap<>();	
		//protected final List<Stock.StockType> ProducedGoods = new LinkedList<>();
		/** Warehouse needed goods.*/
		protected final HashMap<Stock.StockType, Stock> neededGoods = new HashMap<>();
		
		/** Empty class constructor*/
		public Warehouse()
		{
			
		}
		

	}

	@Override
	public ImageIcon getImage() 
	{
		return image;
	}

}
