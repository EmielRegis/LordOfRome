package lord.of.rome;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;


import javax.swing.ImageIcon;



/** Class that simulates the concept of roman empire legion. It can move, speak, fight and rescue attacked settlement. It dies when encounters barbarian horde fraction.
 * 
 * @author Carlos
 *
 */
public final class RomanLegion extends Fraction implements Armed
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** All possible legion names.*/
	private final static String names[] = {"I Adiutrix", "II Adiutrix" , "I Germanica", "I Italica", "II Italica", "III Italica",  
											"I Macriana", "I Minervia", "I Parthica", "II Parthica", "III Parthica", "II Augusta", 
											"III Augusta", "V Augusta", "VIII Augusta", "II Traiana", "III Cyrenaica", "III Gallica", "IV Flavia Felix", "IV Macedonica",
											"V Macedonica", "IV Scythica", "V Alaudae", "VI Ferrata", "VI Hispana", "XVI Flavia Firma", "XX Valeria Vict.", "XXI Rapax", "XXI Deiotariana", "XXII Primigenia", "Ulpia Victrix", "XII Fulminata"};
	
	/** Graphically presentation of legion.*/
	private static final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(RomanLegion.class.getResource("graphics/legionist_icon.png")));
	
	/** Weapon used by specified legion.*/
	private final Weapon weapon = Weapon.values()[random.nextInt(2)];
	
	/** Cloned roman legion for external use*/
	private RomanLegion clonedLegionist;
	
	
	
	/** Default class constructor. It creates new roman legion and locates it in Rome as the beginning legion position.
	 * 
	 */
	public RomanLegion()
	{
		super();
		this.name = names[random.nextInt(names.length)];
		int width = Empire.getInstance().SETTLEMENTS.get("Rome").getXPosition();
		int height = Empire.getInstance().SETTLEMENTS.get("Rome").getYPosition();
		
		this.setPosition(width, height);
		this.setMovementSpeed(80);
		
		clonedLegionist = new RomanLegion(0);
		clonedLegionist.setPosition(width, height);
		
		this.occupation = Creature.Occupation.ALLY;
		clonedLegionist.occupation = Creature.Occupation.ALLY;
		
		Empire.getInstance().CREATURES.add(clonedLegionist);
		
		Runnable runner = this;
		Thread t = new Thread(runner);
		t.setName("Roman Legion " + t.getName() );
		t.start();

	}
	
	/** Private constructor used for creating cloned version of roman legion which could be added to some collections.
	 * 
	 * @param i Not important. Used for distinguish from other constructors.
	 */
	private RomanLegion(int i)
	{
		super();
		this.name = names[random.nextInt(names.length)];
	}
	
	/** Public class constructor. It allows to put new roman legion in selected place of an empire map.
	 * 
	 * @param width X parameter on empire map.
	 * @param height y parameter on empire map.
	 */
	public RomanLegion(int width, int height)
	{
		super(width, height);
		this.name = names[random.nextInt(names.length)];
		this.setMovementSpeed(80);
		
		clonedLegionist = new RomanLegion(0);
		clonedLegionist.setPosition(width, height);
		
		Empire.getInstance().CREATURES.add(clonedLegionist);
		
		Runnable runner = this;
		Thread t = new Thread(runner);
		t.start();
	}
	
	/** Rescues target settlement from barbarian terror from given place.
	 * 
	 * @param p Given beginning place, where actually is the roman legion.
	 * @param s Target settlement to rescue from barbarian terror.
	 */
	public void rescueSettlement(Place p, Settlement s)
	{
		s.setFreed();
		
		if(this.isLiving)
		{
			move(p,s);
		}
		if((this.isLiving) || (this.getXPosition() >= s.getXPosition()-1 && this.getXPosition() <= s.getXPosition()+1 && this.getYPosition() >= s.getYPosition()-1 && this.getYPosition() <= s.getYPosition()+1))
		{
			s.setFreed();
		}
		else
		{
			s.setAttacked();
		}
		if(this.isLiving)
		{
			move(s,p);
		}
		
		
		
		
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
			int x = p.getXPosition();
			int y = p.getYPosition();
			
			
			MapPoint occ = Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y));
			while(occ.getOccupancy() != MapPoint.Occupancy.FREE)
			{
				if(occ.getOccupancy() == MapPoint.Occupancy.BARBARIAN_HORDE)
				{
					Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(true);
					Empire.SoundsOfEmpire.playFightSound();
					this.kill();
					clonedLegionist.kill();
					Empire.getInstance().CREATURES.remove(clonedLegionist);
					return;
				}
				else
				{
					if((occ.getDirection() == this.getMovementDirection()) || (eY==y && eX == x))
					{
						Empire.getInstance().POINTS.get(String.valueOf(pX) + "-" + String.valueOf(pY)).setOccupancy(MapPoint.Occupancy.ROMAN_LEGION);	
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
			
			pX = x; pY=y;
			
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setOccupancy(MapPoint.Occupancy.ROMAN_LEGION);
			Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setDirection(this.getMovementDirection());

			this.setPosition(x, y);
			clonedLegionist.setPosition(x, y);

			
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
				this.kill();
				clonedLegionist.kill();
				Empire.getInstance().POINTS.get(String.valueOf(x) + "-" + String.valueOf(y)).setCollised(false);
				Empire.getInstance().CREATURES.remove(clonedLegionist);
				return;
			}
		}
	}
	

	@Override
	public void run() 
	{
		for(Settlement settlement : Empire.getInstance().SETTLEMENTS.values())
		{
			if (settlement.isAttacked == true)
			{
				this.rescueSettlement(Empire.getInstance().SETTLEMENTS.get("Rome"), settlement);
				break;
			}
		}		
		
		
		
		ArrayList<Road> streetsToPatrol = new ArrayList<>();

		ArrayList<Place> permuttedCrossroadsList = new ArrayList<>();
		ArrayList<Place> copiedCrossroadsList = new ArrayList<>();
		copiedCrossroadsList.addAll(Empire.getInstance().CROSSROADS.values());
		int rand;
		
		int size = copiedCrossroadsList.size();
		for(int i = 0; i<size; i++)
		{
			rand = random.nextInt(copiedCrossroadsList.size());
			permuttedCrossroadsList.add(copiedCrossroadsList.get(rand));
			copiedCrossroadsList.remove(rand);
		}
		
		for(int i=0; i<permuttedCrossroadsList.size()-1; i++)
		{
			streetsToPatrol.addAll(this.findPath(permuttedCrossroadsList.get(i), permuttedCrossroadsList.get(i+1)));
		}
		streetsToPatrol.addAll(this.findPath(permuttedCrossroadsList.get(permuttedCrossroadsList.size()-1), permuttedCrossroadsList.get(0)));
		
		this.move(Empire.getInstance().SETTLEMENTS.get("Rome"), permuttedCrossroadsList.get(0));
		
		while(this.isLiving)
		{
			for(Road r: streetsToPatrol)
			{		
				if(this.isLiving)
				{
					for(Settlement settlement : Empire.getInstance().SETTLEMENTS.values())
					{
						if (settlement.isAttacked)
						{
							
							Point roadPoint = r.getAPosition();
							for(Place crossroad : permuttedCrossroadsList)
							{
								Point crossroadPoint = crossroad.getPosition();
								if((crossroadPoint.getXPosition() == roadPoint.getXPosition()) &&(crossroadPoint.getYPosition() == roadPoint.getYPosition()))
								{
									this.rescueSettlement(crossroad, settlement);
									break;
								}
							}
							break;
						}
					}
					
					
					this.move(r);
					
					
				}
				else
				{
					break;
				}
			}
		}
		
		
		//this.rescueSettlement(Empire.getInstance().SETTLEMENTS.get(cities[new Random().nextInt(size)]));
		//System.out.println(cities[new Random().nextInt(size)]);
		
		//this.kill();
		//clonedLegionist.kill();
		//Empire.getInstance().CREATURES.remove(this.clonedLegionist);
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

	/** Returns weapon used by legion.
	 * 
	 * @return Weapon used by legion.
	 */
	public Weapon getWeapon() 
	{
		return weapon;
	}

	@Override
	public ImageIcon getImage() 
	{
		return image;
	}
	
	@Override
	public void speak()
	{
		Empire.SoundsOfEmpire.playSoundOfRomanLegion();
	}
	


}
