package lord.of.rome;


import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;


/** Class that simulates the fraction of barbarian horde.
 * 
 * It can move, fight and attack the empire settlement. It dies when encounters empire armed fraction.
 * 
 * @author Carlos
 *
 */
public final class BarbarianHorde extends Fraction implements Armed
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Name of barbarian horde.*/
	private  String name;
	/**Used weapon.*/
	private Weapon weapon;
	/** Name of attacked settlement.*/
	private String nameOfAttackedSettlement;
	/** Cloned barbarian for external use*/
	private BarbarianHorde clonedBarbarian;
	
	/** Icon of barbarian.*/
	private static final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(BarbarianHorde.class.getResource("graphics/barbarian_icon.png")));
	
	/** Possible names of created barbarian horde*/
	private static final String barbarianTribes[] = {"Angles", "Aviones", "Burgundes", "Eburones", "Francs", "Goths", "Galles", 
													"Lombards", "Ostrogoths", "Ranrikes", "Teutons", "Vagoths", "Varges", "Vandals", "Visigoths", "Usipets"};
	
	/** Public class constructor. It allows to put new barbarian horde in selected place of an empire map.
	 * 
	 * @param width X parameter on empire map.
	 * @param height Y parameter on empire map.
	 */
	public BarbarianHorde(int width, int height)
	{
		super();
		this.name = barbarianTribes[random.nextInt(barbarianTribes.length)];
		weapon = Weapon.values()[random.nextInt(Weapon.values().length-2)+2];
		this.setPosition(width, height);
		this.setMovementSpeed(40);
		
		clonedBarbarian = new BarbarianHorde(0);
		clonedBarbarian.setPosition(width, height);
		
		this.occupation = Creature.Occupation.ENEMY;
		clonedBarbarian.occupation = Creature.Occupation.ENEMY;
		
		clonedBarbarian.weapon = this.weapon;
		
		Empire.getInstance().CREATURES.add(clonedBarbarian);
		
		this.speak();
		
		Runnable runner = this;
		Thread t = new Thread(runner);
		t.setName("Barbarian Horde " + t.getName() );
		t.start();
	}
	
	/** Private constructor used for creating cloned version of barbarian horde which could be added to some collections.
	 * 
	 * @param i Not important. Used for distinguish from other constructors.
	 */
	private BarbarianHorde(int i)
	{
		super();
		this.name = barbarianTribes[random.nextInt(barbarianTribes.length)];
		weapon = Weapon.values()[random.nextInt(Weapon.values().length-2)+2];
	}
	
	/** Switches to attack mode. Barbarian horde looks for settlement to attack and does it.
	 * 
	 */
	private void attack()
	{
		double x = (double) this.getXPosition();
		double y = (double) this.getYPosition();
		
		
		Place choosenCrossroad = new Place(0, 0);
		
		double shortestDistance = Double.MAX_VALUE;
		double h;
		
		for(Place p : Empire.getInstance().CROSSROADS.values())
		{
			double pX = p.getXPosition();
			double pY = p.getYPosition();
			
			if((h = Math.sqrt((pX-x)*(pX-x)+(pY-y)*(pY-y))) < shortestDistance)
			{
				choosenCrossroad = p;
				shortestDistance = h;
			}
		}
		
		this.move(new Road(new Place((int)x, (int)y), choosenCrossroad));
		
		
		
		Place actualPlace;
		Settlement attackedSettlement; 
		
		
		actualPlace = choosenCrossroad;
		while(this.isLiving)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			String[] settlementsNames = new String[Empire.getInstance().SETTLEMENTS.size()];
			settlementsNames = Empire.getInstance().SETTLEMENTS.keySet().toArray(settlementsNames);
			
			if(Empire.getInstance().SETTLEMENTS.size() >0)
			{
				attackedSettlement = Empire.getInstance().SETTLEMENTS.get(settlementsNames[random.nextInt(settlementsNames.length)]);
				if(attackedSettlement.getPopulation() >0)
				{
					this.move(actualPlace, attackedSettlement);
					if(this.isLiving == true)
					{
						if(attackedSettlement.getPopulation() >0)
						{
							attackedSettlement.setAttacked();
							
							Empire.SoundsOfEmpire.playAttackedSettlementSound();
							this.makeTerror(attackedSettlement);
						}
						else
						{
							actualPlace =  attackedSettlement;
							continue;
						}
						
					}
					
					actualPlace =  attackedSettlement;
				}
			}
			else
			{
				this.kill();
				clonedBarbarian.kill();
			}
		}	
	}
	
	/** Plunders settlement's treasury .
	 * 
	 *Not implemented yet! 
	 * 
	 * @param settle Chosen settlement.
	 * 
	 * 
	 */
	public void emptyTreasury(Settlement settle)
	{
		;
	}
	
	/** Kills all citizens in the settlement.
	 * 
	 * @param settle Chosen settlement.
	 */
	public void killCitizens(Settlement settle)
	{
		settle.decreasePopulation(settle.getPopulation());
	}
	
	/** Attacks selected settlement and kill its citizens.
	 * 
	 * @param s Chosen settlement.
	 */
	public void makeTerror(Settlement s)
	{
		int count =0;
		while(Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).isCollised() == false)
		{
			if(s.getPopulation() <=0)
			{
				try
				{
					Thread.sleep(100);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				s.setFreed();
				return;
			}
			
			
			
			count++;
			if(count == 10)
			{
				s.decreasePopulationConst();
				count = 0;
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
		
		int i = 0;
		for(Creature c : Empire.getInstance().CREATURES)
		{
			
			if(c.getXPosition() == this.getXPosition() && c.getYPosition() == this.getYPosition())
			{
				if(c.getClass().equals(this.getClass()))
				{
					i++;
				}
			}
		}
		
		
		
		if(i <= 1)
		{
			Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).setCollised(false);
			Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).setOccupancy(MapPoint.Occupancy.FREE);
			Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).setDirection(Road.Direction.NONE);
		}
		
		Empire.getInstance().CREATURES.remove(clonedBarbarian);
		this.kill();
		clonedBarbarian.kill();
		try
		{
			Thread.sleep(100);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		s.setFreed();
	}
	
	
	@Override
	public void move(Place A, Place B) 
	{
		if(this.isLiving == true)
		{
			List<Road> list = this.findPath(A, B);
			
			int pX=0, pY=0;
			for(Road r : list)
			{
				this.setMovementDirection(r.getDirection());
				
				for(Point p : r.road)
				{
					int x = p.getXPosition();
					int y = p.getYPosition();
					
					int eX = r.getBPosition().getXPosition();
					int eY  = r.getBPosition().getYPosition();
					
					MapPoint occ = Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y));
					while(occ.getOccupancy() != MapPoint.Occupancy.FREE)
					{
						if(occ.getOccupancy() == MapPoint.Occupancy.ROMAN_LEGION)
						{
							Empire.getInstance().CREATURES.remove(clonedBarbarian);
							Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(true);
							Empire.SoundsOfEmpire.playFightSound();
							this.kill();
							clonedBarbarian.kill();
							return;
						}
						else if(occ.getOccupancy() == MapPoint.Occupancy.MERCHANT)
						{
							;
						}
						else
						{
							if((occ.getDirection() == this.getMovementDirection() && occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE) || (eY==y && eX == x && occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE))
							{
								Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
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
					
					pX=x;
					pY=y;
					
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(this.getMovementDirection());
					
					this.setPosition(x, y);
					clonedBarbarian.setPosition(x, y);

					
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
						
						Empire.getInstance().CREATURES.remove(clonedBarbarian);
						clonedBarbarian.kill();
						this.kill();
						Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(false);
						return;
					}
				}
			}
			
			Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
		}
	}
	
	
	@Override
	public void move(Road road)
	{
		if(this.isLiving == true)
		{
			this.setMovementDirection(road.getDirection());
			
			int pX=0, pY=0;
			
			int eX = road.getBPosition().getXPosition();
			int eY  = road.getBPosition().getYPosition();
			
			for(Point p : road.road)
			{
				int x = p.getXPosition();
				int y = p.getYPosition();
				
				MapPoint occ = Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y));
				while(occ.getOccupancy() != MapPoint.Occupancy.FREE)
				{
					if(occ.getOccupancy() == MapPoint.Occupancy.ROMAN_LEGION)
					{
						Empire.getInstance().CREATURES.remove(clonedBarbarian);
						Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(true);
						Empire.SoundsOfEmpire.playFightSound();
						this.kill();
						clonedBarbarian.kill();
						return;
					}
					else if(occ.getOccupancy() == MapPoint.Occupancy.MERCHANT)
					{
						;
					}
					else
					{
						if((occ.getDirection() == this.getMovementDirection() && occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE) || (eY==y && eX == x && occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE))
						{
							Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
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
				
				pX=x;
				pY=y;
				
				Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
				Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(this.getMovementDirection());
				
				this.setPosition(x, y);
				clonedBarbarian.setPosition(x, y);

				
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
					
					Empire.getInstance().CREATURES.remove(clonedBarbarian);
					this.kill();
					clonedBarbarian.kill();
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(false);
					return;
				}
				
			}
			
			Empire.getInstance().POINTS.get(String.valueOf(this.getXPosition()) + "-" + String.valueOf(this.getYPosition())).setOccupancy(MapPoint.Occupancy.BARBARIAN_HORDE);
		}
	}
	
	

	@Override
	public void run() 
	{
		this.attack();
	}

	@Override
	public void fight() 
	{
		;
	}

	@Override
	@Deprecated
	public void draw() 
	{
		;
	}

	/** Returns barbarian brood name.
	 * 
	 */
	public String getName()
	{
		return name;
	}

	/** Sets barbarian brood name.
	 * 
	 * @param name Barbarian brood name.
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/** Returns barbarian horde weapon.
	 * 
	 * @return Barbarian horde weapon.
	 */
	public Weapon getWeapon()
	{
		return weapon;
	}

	/** Sets barbarian horde weapon.
	 * 
	 * @param weapon Barbarian horde weapon.
	 */
	public void setWeapon(Weapon weapon)
	{
		this.weapon = weapon;
	}

	/** Returns name of attacked settlement.
	 * 
	 * @return Name of attacked settlement.
	 */
	public String getNameOfAttackedSettlement() 
	{
		return nameOfAttackedSettlement;
	}

	/** Sets name of attacked Settlement.
	 * 
	 * @param nameOfAttackedSettlement Name of attacked settlement.
	 */
	public void setNameOfAttackedSettlement(String nameOfAttackedSettlement) 
	{
		this.nameOfAttackedSettlement = nameOfAttackedSettlement;
	}

	@Override
	public ImageIcon getImage() 
	{
		return image;
	}
	
	@Override
	public void speak()
	{
		Empire.SoundsOfEmpire.playSoundOfBarbarianHorde();
	}

}
