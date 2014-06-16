package lord.of.rome;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;

import lord.of.rome.Stock.StockType;


/** Class which simulates the concept of merchant. It travels between empire settlements and trades with then. Of course it can speak too. 
 * Its movement speed depends on weight of traiding wagon, the inner object of merchant instance. Merchant dies when encounters some barbarian horde fraction.
 * It's unable to fight.
 * @author Carlos
 *
 */
public final class Merchant extends Creature
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Graphically presentation of merchant.*/
	private static final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Merchant.class.getResource("graphics/merchant_icon.png")));
	
	/** Array of all possible merchant names.*/
	private static final String names[] = {"Aertius", "Albus", "Augustus", "Aurelius", "Brutus", "Cornelius", "Fabius", "Festus", "Gauis", 
											"Longinus", "Lucius", "Marcellus", "Marcus", "Marius", "Maximus", "Octavius", "Petronius", "Plinius", 
											"Pompeius", "Ponitus", "Publius", "Sextus", "Tertius", "Valerius"};
	
	/** Array of all possible merchant surnames.*/
	private static final String surnames[] = {"Acidinus", "Agelastus", "Asellio", "Atticus", "Blasio", "Bursio", "Caepio", "Casca", "Cato", 
												"Censorius", "Corvinus", "Dorso", "Geminus", "Isauricus", "Labeo", "Lupus", "Murena", "Nasica", 
												"Orca", "Russus", "Scaeva", "Serapio", "Tegula", "Varus", "Vulso"};
	
	/** Merchant surname.*/
	protected String surname;
	//protected Settlement targetSettlement;
	
	/** Merchants traiding wagon.*/
	protected final TraidingWagon traidingWagon = new TraidingWagon();
	
	/** Cloned merchant for external use.*/
	protected Merchant clonedMerchant;
	
	/** List of settlement between whom travels the merchant.*/
	protected final ArrayList<Settlement> settlementList = new ArrayList<>();
	
	/** Information whether merchant should move between settlement.*/
	protected volatile boolean shoudMove = true;
	/** Movement guard for synchronization.*/
	protected final Object movingGuard = new Object();
	
	/** Information whether merchant is in a safe place.*/
	protected volatile boolean isSafe = false;
	/** Safe place guard for synchronization*/
	protected final Object safeGuard = new Object();
	
	/** Class constructor. It provides use ready merchant object. Merchant born in some random roman settlement and begins his mercantile travel between emipre settlement.
	 * 
	 */
	public Merchant()
	{
		super();
			
		this.name = names[random.nextInt(names.length)];
		this.surname = surnames[random.nextInt(surnames.length)];
		int width = Empire.getInstance().SETTLEMENTS.get("Rome").getXPosition();
		int height = Empire.getInstance().SETTLEMENTS.get("Rome").getYPosition(); 
		
		this.setPosition(width, height);
		this.setMovementSpeed(70);
		
		this.life = 50;
		this.maxLife = 50;
		
		
		int goodCount = 0;
		while(goodCount < 3)
		{
			StockType stype = StockType.values()[random.nextInt(StockType.values().length)];
			if(! traidingWagon.storedGoods.containsKey(stype))
			{
				traidingWagon.storedGoods.put(stype, new Stock(stype));
				goodCount++;
			}
			
		}

		
		
		for(Stock merchantsStock : this.traidingWagon.storedGoods.values())
		{
			Stock.StockType stockType = merchantsStock.getType();
			for(Settlement prodSettle : Empire.getInstance().SETTLEMENTS.values())
			{
				if(prodSettle.getWarehouseGoods().containsKey(stockType))
				{
					if(!settlementList.contains(prodSettle))
					{
						settlementList.add(prodSettle);
						for(Settlement consSettle : Empire.getInstance().SETTLEMENTS.values())
						{
							if(consSettle.getNeededGoodsList().containsKey(stockType))
							{
								if(! settlementList.contains(consSettle))
								{
									settlementList.add(consSettle);
									break;
								}
							}
						}
						break;
					}
				}
			}
			
		}
		

		
		
		clonedMerchant = new Merchant(0);
		clonedMerchant.setPosition(width, height);
		clonedMerchant.setMovementSpeed(this.movementSpeed);
		
		clonedMerchant.traidingWagon.storedGoods = this.traidingWagon.storedGoods;
		
		this.occupation = Creature.Occupation.ALLY;
		clonedMerchant.occupation = Creature.Occupation.ALLY;
		
		Empire.getInstance().CREATURES.add(clonedMerchant);
		
		Runnable runner = this;
		Thread t = new Thread(runner);
		t.setName("Merchant " + t.getName() );
		t.start();
	}
	
	/** Private constructor used for creating cloned version of merchant which could be added to some collections.
	 * 
	 * @param i Not important. Used for distinguish from other constructors.
	 */	
	private Merchant(int i)
	{
		super();
		this.name = names[random.nextInt(names.length)];
		this.surname = surnames[random.nextInt(surnames.length)];
		
		this.life = 50;
		this.maxLife = 50;
	}
	
	/** Merchant begins his travel between empire settlement. He trades with them, earning gold for him and (as the taxes) for the empire.
	 * 
	 */
	public void travel()
	{
		while(this.isLiving == true)
		{
			if(settlementList.size() > 1)
			{
				Settlement from;
				Settlement to;
				for(int i=0; i<settlementList.size() -1; i++)
				{
					from = settlementList.get(i);
					to = settlementList.get(i+1);
					if(to.getPopulation() >0)
					{
						this.move(from, to);
						this.makeDeal(to);
						this.calculateSpeed();
					}
					else
					{
						settlementList.remove(to);
						continue;
					}
					
				}
				from = settlementList.get(settlementList.size()-1);
				to = settlementList.get(0);
				
				isSafe = false;
				this.move(from, to);
				isSafe = true;
				
				
				this.makeDeal(to);
				this.calculateSpeed();
			}
		}
	}
	
	/** Adds new settlement to the merchant's trade settlement list.
	 * 
	 * @param settle Added settlement.
	 */
	public void addSettlement(Settlement settle)
	{
		if(!settlementList.contains(settle))
		{
			settlementList.add(settle);
		}
	}
	
	/** Trades with specified settlement.
	 * 
	 * @param settle Given settlement.
	 */
	public void makeDeal(Settlement settle)
	{
		for(Stock merchantStock : traidingWagon.getStoredGoods().values())
		{
			int removed = settle.sellStockToSettlement(merchantStock.getType(), merchantStock.volume);
			merchantStock.removeVolume(removed);
			traidingWagon.usedSpace -= removed;
			traidingWagon.availableSpace = TraidingWagon.capacity - traidingWagon.usedSpace;
			
			
			int gained = settle.buyStockFromSettlement(merchantStock.getType(), traidingWagon.availableSpace/(traidingWagon.storedGoods.size()*merchantStock.getType().getThickness()));
			merchantStock.addVolume(gained);
			traidingWagon.usedSpace += gained;
			traidingWagon.availableSpace = TraidingWagon.capacity - traidingWagon.usedSpace;
			
		}
	}

	/*public void buyItems()
	{
		;
	}
	
	public void sellItems()
	{
		;
	}*/
	
	@Override
	public void run() 
	{
		for (int c=0; c<3; c++)
		{
			int size = Empire.getInstance().SETTLEMENTS.size();
			String[] cities = new String[size];
			int i =0;
			for(String s : Empire.getInstance().SETTLEMENTS.keySet())
			{
				cities[i] = s;
				i++;
			}
			
			//this.addSettlement(Empire.getInstance().SETTLEMENTS.get(cities[new Random().nextInt(size)]));
		}
		
		
		this.travel();
	}
	

	@Override
	public void move(Place A, Place B)
	{
		if(this.isLiving == true)
		{
			List<Road> list = this.findPath(A, B);
			for(Road r : list)
			{
				move(r);
				if(isLiving == false)
				{
					return;
				}
			}
		}
	}
	
	@Override
	public void move(Road r)
	{
		this.setMovementDirection(r.getDirection());
		
		int pX=0, pY=0;
		
		int eX = r.getBPosition().getXPosition();
		int eY  = r.getBPosition().getYPosition();
		
		for(Point p : r.road)
		{
			if(clonedMerchant.isLiving == false)
			{
				this.kill();
				Empire.getInstance().CREATURES.remove(clonedMerchant);
				return;
			}
	
			
			int x = p.getXPosition();
			int y = p.getYPosition();

			
			MapPoint occ = Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y));
			while(occ.getOccupancy() != MapPoint.Occupancy.FREE)
			{
				if(occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE)
				{
					//if(isSafe == false)
					{
						//Empire.getInstance().POINTS[x][y].setCollised(true);
						Empire.getInstance().CREATURES.remove(clonedMerchant);
						Empire.SoundsOfEmpire.playDeathSound();
						this.kill();
						return;
					}
					
				}
				else
				{
					if((occ.getDirection() == this.getMovementDirection()) || (eY==y && eX == x))
					{
						Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setOccupancy(MapPoint.Occupancy.MERCHANT);	
						Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setDirection(this.getMovementDirection());
						
						try
						{
							Thread.sleep(1000);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						
						Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setOccupancy(MapPoint.Occupancy.FREE);
						Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setDirection(Road.Direction.NONE);
					}
					else
					{
						break;
					}
				}
			
			}
			
			pX = x;
			pY=y;
			
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.MERCHANT);
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(this.getMovementDirection());
			

			
			this.setPosition(x, y);
			clonedMerchant.setPosition(x, y);
			
			while(!clonedMerchant.shoudMove)
			{
				if (clonedMerchant.isLiving == false)
				{
					this.kill();
					Empire.getInstance().CREATURES.remove(clonedMerchant);
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.FREE);
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(Road.Direction.NONE);
					return;
				}
				try
				{
					Thread.sleep(100);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			
			try 
			{
				Thread.sleep(this.movementSpeed);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.FREE);
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(Road.Direction.NONE);

			if(Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).isCollised() == true)
			{
				
				//if(isSafe == false)
				{
					Empire.getInstance().CREATURES.remove(clonedMerchant);
					this.kill();
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(false);
					return;
				}
				
			}
			
		}
	}
	
	/** Stops merchant travel.
	 * 
	 */
	public void stop()
	{
		synchronized(movingGuard)
		{
			shoudMove=false;
		}
		
	}
	
	/** Resumes merchant travel.
	 * 
	 */
	public void resume()
	{
		synchronized(movingGuard)
		{
			shoudMove=true;
		}
	}
	
	/** Return information whether merchant should travel.
	 * 
	 * @return Information whether merchant should travel.
	 */
	public boolean shoudMove()
	{
		return shoudMove;
	}

	@Override
	@Deprecated
	public void draw() 
	{
		;
	}

	public String getSurname()
	{
		return surname;
	}

	/** Returns merchant's traiding wagon.
	 * 
	 * @return Merchant's traiding wagon.
	 */
	public TraidingWagon getTraidingWagon() 
	{
		return traidingWagon;
	}
	
	/*public void setTargetSettlement(Settlement settlement)
	{
		this.targetSettlement = settlement;
	}
	
	public Settlement getTargetSettlement()
	{
		return this.targetSettlement;
	}*/
	
	@Override
	public ImageIcon getImage() 
	{
		return image;
	}
	
	/** Returns information whether merchant is in a safe place.
	 * 
	 * @return Information whether merchant is in a safe place.
	 */
	public boolean isSafe()
	{
		synchronized(safeGuard)
		{
			return isSafe;
		}
	}
	
	@Override
	public void speak()
	{
		Empire.SoundsOfEmpire.playSoundOfMerchant();
	}
	
	/** Calculates its movement speed.
	 * 
	 */
	private void calculateSpeed()
	{
		
		traidingWagon.calculateWeight();
		
		if(traidingWagon.weight <= 50)
		{
			Merchant.this.setMovementSpeed(70);
		}
		else if (traidingWagon.weight < 50 && traidingWagon.weight <= 500)
		{
			Merchant.this.setMovementSpeed(65);
		}
		else if(traidingWagon.weight < 500 && traidingWagon.weight <= 5000)
		{
			Merchant.this.setMovementSpeed(60);
		}
		else if(traidingWagon.weight < 50000 && traidingWagon.weight <=50000)
		{
			Merchant.this.setMovementSpeed(55);
		}
		else
		{
			Merchant.this.setMovementSpeed(50);
		}
		
		clonedMerchant.setMovementSpeed(this.movementSpeed);
	}
	
	/** Inner class of Merchant. It represents concept of merchant's traiding wagon.
	 * 
	 * @author Carlos
	 *
	 */
	 final class TraidingWagon
	{
		/** Traiding wagon capacity*/
		private final static int capacity = 1000;
		/** Current available space of traiding wagon.*/
		private int availableSpace = 1000;
		/** Current used space.*/
		private int usedSpace = 0;
		/** Weight of the traiding wagon.*/
		private int weight=0;
		
		/** Goods stored in merchant's traiding wagon.*/
		protected HashMap<Stock.StockType, Stock> storedGoods = new HashMap<>();	
		
		/** Calculates weight of traiding wagon.*/
		private void calculateWeight()
		{
			int w = 0;
			for(Stock s : storedGoods.values())
			{
				w += s.getVolume()*s.getType().getThickness();
			}
			
			weight = w;
		}
		
		/*public void breakIt()
		{
			
		}
		
		public void repairIt()
		{
			;
		}*/
		
		/** Returns goods stored in merchant's traiding wagon.
		 * 
		 * @return Goods stored in merchant's traiding wagon.
		 */
		public HashMap<Stock.StockType, Stock> getStoredGoods()
		{
			return storedGoods;
		}
		
	}
	
}
