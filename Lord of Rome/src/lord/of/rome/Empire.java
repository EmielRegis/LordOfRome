package lord.of.rome;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import lord.of.rome.Stock.StockType;






/** The most important, magnum opus class of whole package - class which simulates the concept of ancient Roman Empire. It is a singleton class type.
 * There is only one Rome and only one Lord of Rome and he does not share power. In this class is created whole empire. It has also two big nested classes representing visual and sound aspect of empire.
 * 
 * @author Carlos
 *
 */
public final class Empire extends DrawableObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Static instance of the empire.*/
	private final static Empire EMPIRE = new Empire();
	
	/** Smaug the Dragon, guardian of empire treasury. This big, nasty, greedy and wicked dragon is ironically very helpful in synchronization. */
	private final Object smaug = new Object();
	/** The content of empire treasury.*/
	private int gold = 3000;
	
	/** Name of actual emperor. Initially set to 'Julies Caesar'.*/ 
	protected String emperorName = "Julies Caesar";
	
	/** Native dimension of empire.*/
	public final static Dimension empireDimension = new Dimension(1024,1024);
	
	/** Graphically representation of Rome Empire.*/
	private final static ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Empire.class.getResource("graphics/map2.png")));
	
	/** The moment from which it all began.*/
	private static Date initializationTime;
	
	/** Field that enables randomization of some operations and values.*/
	private static final Random random = new Random();
	
	/** The capital of empire, Rome.*/
	public Capital rome;
	/** List of all settlement in empire.*/
	public final HashMap<String, Settlement> SETTLEMENTS = new HashMap<>();
	/** List of all strategic crossroads in empire.*/
	public final HashMap<String, Place> CROSSROADS = new HashMap<>();
	/** List of all important roads in empire.*/
	public final HashMap<String, Road> ROADS = new HashMap<>();
	/** List of all important points in empire, on which can move people and other creatures.*/
	public  HashMap<String, MapPoint> POINTS = new HashMap<>();
	/** List of all living people and other creatures on the lands of the empire.*/
	public final  ArrayList<Creature> CREATURES = new ArrayList<>();
	
	//private final static Object creaturesListGuard = new Object();
	
	
	/** Private constructor of the empire.*/	
	private Empire()
	{
		super();		
	}
	
	/** Returns one and the same instance of the empire.
	 * 
	 * @return Instance of the empire.
	 */
	public static Empire getInstance()
	{
		return EMPIRE;
	}
	
	/** Creates main elements of the empire.*/
	public void properEmpire()
	{
		initializePoints();
		createSettlements();
		createCrossroads();
		createRoads();
	}
	
	/** Begins proper life of the empire.*/
	public void createEmpire()
	{
		beginGame();
		
		
		properEmpire();
		
		initializeTime();
		
		EmpireDrawer.createDrawableEmpire();
		
		
		SoundsOfEmpire.playBackgroundMusic();

		
		
		createBarbarianMotherfuckers();
		
		
		
		while(true)
		{
			if(Empire.getInstance().SETTLEMENTS.size() >0)
			{
				
			}
			else
			{
				break;
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		endGame();

	}
	
	/** Begins users interaction with empire.*/
	private void beginGame()
	{
		
		Empire.SoundsOfEmpire.playIntroMusic();
		
		final JFrame begFrame = new JFrame();
		begFrame.setSize(new Dimension (20,20));
		begFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(RomanLegion.class.getResource("graphics/legionist_icon.png")));
		begFrame.setVisible(false);
		
		
		final JWindow begWindow = new JWindow(begFrame);
		begWindow.setFocusable(true);
		begWindow.requestFocus();
		final JButton begButton = new JButton("The Die is Cast...");
		
		begWindow.setSize(new Dimension(480, 640));
		begWindow.setForeground(Empire.EmpireDrawer.COLOR_BLACK);
		begWindow.setBackground(Empire.EmpireDrawer.COLOR_BLACK);
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		begWindow.setLocation((displaySize.width -begWindow.getWidth())/2, (displaySize.height - begWindow.getHeight())/2);
		
		JPanel begPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		begPanel.setForeground(Empire.EmpireDrawer.COLOR_BLACK);
		begPanel.setBackground(Empire.EmpireDrawer.COLOR_BLACK);
		
		begPanel.add(new JLabel(Empire.EmpireDrawer.MAIN_TITLE));
		begPanel.add(new JLabel(Empire.EmpireDrawer.EMPEROR));
		
		JPanel namePanel = new JPanel ();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.setBackground(Empire.EmpireDrawer.COLOR_BLACK);
		namePanel.setForeground(Empire.EmpireDrawer.COLOR_BLACK);
		
		JLabel nameLabel = new JLabel("Please type your name:   ");
		nameLabel.setFont(Empire.EmpireDrawer.TIMES_BOLD_17);
		nameLabel.setForeground(Empire.EmpireDrawer.COLOR_WHITE);
		namePanel.add(nameLabel);
		
		final JTextField inputName = new JTextField(15);
		inputName.setFont(Empire.EmpireDrawer.TIMES_BOLD_17);
		inputName.setForeground(Empire.EmpireDrawer.COLOR_BLACK);
		//inputName.setBackground(Empire.EmpireDrawer.COLOR_BLACK);
		inputName.setText("");
		inputName.requestFocus();
		inputName.setEditable(true);
		inputName.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) 
			{
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				//inputName.add
				if(inputName.getText().length() >= 3)
				{
					begButton.setEnabled(true);
				}
				else
				{
					begButton.setEnabled(false);
				}
			}
			
		});
		namePanel.add(inputName);
		
		begPanel.add(namePanel);
		
		
		begButton.setFont(Empire.EmpireDrawer.TIMES_BOLD_24);
		begButton.setBackground(Empire.EmpireDrawer.COLOR_GOLD);
		begButton.setEnabled(false);
		begButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Empire.getInstance().setEmperor(inputName.getText());
				begWindow.setVisible(false);
				Empire.SoundsOfEmpire.stopIntroMusic();
				begFrame.setVisible(false);
				begWindow.dispose();
				begFrame.dispose();
				
			}
			
		});
		
		
		begPanel.add(begButton);
		
		
		begWindow.getContentPane().add(BorderLayout.CENTER, begPanel);
		//begWindow.pack();
		begWindow.setVisible(true);
		begFrame.setLocation(begWindow.getLocation());
		begFrame.setVisible(true);
		

		while(begWindow.isVisible())
		{
			try
			{
				Thread.sleep(100);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		
	}
	
	/** Ends users interaction with empire.*/
	private static void endGame()
	{
		for(Creature c : Empire.getInstance().CREATURES)
		{
			c.kill();
		}
		
		Empire.SoundsOfEmpire.closeBackgroundMusic();
		Empire.SoundsOfEmpire.playEndgameMusic();
		//System.out.println("the game is over bitches!");
		Saver saver = new Saver();
		saver.save();
		String gameTime = saver.getGameTime();
		
		JPanel endPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
		endPanel.setForeground(Empire.EmpireDrawer.COLOR_BLACK);
		endPanel.setBackground(Empire.EmpireDrawer.COLOR_BLACK);
	
		//endPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		final JFrame begFrame = new JFrame();
		begFrame.setSize(new Dimension (20,20));
		begFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(RomanLegion.class.getResource("graphics/legionist_icon.png")));
		begFrame.setVisible(false);	
		
		
		final JWindow endingFrame = new JWindow(begFrame);
		endingFrame.setSize(new Dimension(480, 320));
		//endingFrame.
		
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		endingFrame.setLocation((displaySize.width - endingFrame.getWidth())/2, (displaySize.height - endingFrame.getHeight())/2);
		
		JLabel header = new JLabel("Congratulations, " + Empire.getInstance().getEmperor());
		header.setForeground(Empire.EmpireDrawer.COLOR_GOLD);
		header.setFont(Empire.EmpireDrawer.TIMES_BOLD_24);
		endPanel.add(header);
		
		JLabel title = new JLabel("You have finished 'The Lord of Rome' !  ");
		endPanel.add(new JLabel("   "));
		title.setForeground(Empire.EmpireDrawer.COLOR_GOLD);
		title.setFont(Empire.EmpireDrawer.TIMES_BOLD_24);
		endPanel.add(title);
		endPanel.add(new JLabel(" "));
		
		JLabel gameTimeLabel = new JLabel("Your time is:   " + gameTime);
		gameTimeLabel.setFont(Empire.EmpireDrawer.TIMES_BOLD_20);
		gameTimeLabel.setForeground(Empire.EmpireDrawer.COLOR_WHITE);
		endPanel.add(gameTimeLabel);
		endPanel.add(new JLabel("   "));
		endPanel.add(new JLabel("   "));
		
		endPanel.add(new JLabel(Empire.EmpireDrawer.END_IMAGE));
	
		
		JButton endButton = new JButton("   The End   ");
		endButton.setFont(Empire.EmpireDrawer.TIMES_BOLD_24);
		endButton.setBackground(Empire.EmpireDrawer.COLOR_GOLD);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(endButton);
		//endPanel.add(buttonPanel);
		endPanel.add(endButton);
		endButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				endingFrame.setVisible(false);
				endingFrame.dispose();
			
				begFrame.setVisible(false);
				Empire.EmpireDrawer.empireFrame.dispose();
				begFrame.dispose();
				
				System.exit(0);
				
			}
		});
		
		endingFrame.add(BorderLayout.CENTER, endPanel);
		
		Empire.EmpireDrawer.empireFrame.setVisible(false);
		endingFrame.setVisible(true);
		begFrame.setLocation(endingFrame.getLocation());
		begFrame.setVisible(true);
	}
	
	/** Initializes game and empire beginning time.*/
	private static void initializeTime()
	{
		initializationTime = new Date();
	}
	
	/** Returns actual game and empire time.
	 * 
	 * @return Actual game and empire time.
	 */
	public static String getActualTimeName()
	{
		//Date actualTime = new Date();
		long intTime = (System.currentTimeMillis() - initializationTime.getTime())/1000;
		return ((intTime/3600) + " h " + (intTime/60)%60 + " m " + intTime%60 + " sec.");
	}
	
	/** Monitors applications threads.*/
	@SuppressWarnings("unused")
	private static void monitorThreads()
	{
		new Thread("monitoring thread")
		{
			public void run()
			{
				while(true)
				{
					System.out.println(" ");
					System.out.println("----------------------");
					System.out.println(" ");
					System.out.println("monitoring: ");
					
					Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

					for (Thread thread: threadSet) 
					{
						if(thread.getName().contains("Roman"))
						{
							System.out.println(thread.getName());
						}
						
					}
					
					try
					{
						Thread.sleep(10000);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}	
				}
			}
			
		}.start();
	}
	
	/** Creates barbarian thread, that creates barbarian hordes who appear with random time on empire map. The longer emperor plays, the more barbarian hordes appear.
	 * 
	 */
	private static void createBarbarianMotherfuckers()
	{
		new Thread("barbarian thread")
		{
			public void run()
			{
				try
				{
					Thread.sleep(100000);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				Random rand = new Random();
				int time = 20000;
				while(time > 100)
				{
					try
					{
						Thread.sleep(rand.nextInt(time)+time);
						time -= 750;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					finally
					{
						if(Empire.getInstance().SETTLEMENTS.size() > 0)
						{
							new BarbarianHorde(new Random().nextInt(900)+20,new Random().nextInt(400)+20);
						}
						
					}		
				}
			}
			
		}.start();
	}
	
	/** Initializes points for creatures motion.*/
	private void initializePoints()
	{
		//POINTS = new HashMap<String, MapPoint>();
		POINTS.put("0-0", new MapPoint(0,0));

	}
	
	/** Creates empire settlement.*/
	private void createSettlements()
	{	
		Capital theRome = new Capital(268, 764);
		theRome.addProducingGood(StockType.BRONZE);
		theRome.addProducingGood(StockType.SILVER);
		theRome.addProducingGood(StockType.ROCK);
		theRome.addNeededGood(StockType.GOLD);
		theRome.addNeededGood(StockType.SLAVE);
		theRome.addNeededGood(StockType.BREAD);
		SETTLEMENTS.put(theRome.getName(), theRome);
		
		Settlement interpromium = new Settlement("Interpromium", 625, 799);
		interpromium.addProducingGood(Stock.StockType.WINE);
		interpromium.addProducingGood(Stock.StockType.BREAD);
		interpromium.addProducingGood(StockType.WEAPON);
		interpromium.addNeededGood(StockType.BRONZE);
		interpromium.addNeededGood(StockType.CLOTHES);
		SETTLEMENTS.put(interpromium.getName(), interpromium);

		Settlement neapolis = new Settlement("Neapolis", 303, 994);
		neapolis.addProducingGood(Stock.StockType.MEAT);
		neapolis.addProducingGood(Stock.StockType.FISH);
		neapolis.addProducingGood(Stock.StockType.SLAVE);
		neapolis.addNeededGood(StockType.HONEY);
		neapolis.addNeededGood(StockType.SILVER);
		SETTLEMENTS.put(neapolis.getName(), neapolis);
		
		Settlement beneventum = new Settlement("Beneventum", 951, 954);
		beneventum.addProducingGood(Stock.StockType.OLIVES);
		beneventum.addProducingGood(Stock.StockType.MILK);
		beneventum.addProducingGood(StockType.DIAMONDS);
		beneventum.addNeededGood(StockType.CLOTHES);
		beneventum.addNeededGood(StockType.SPICES);
		SETTLEMENTS.put(beneventum.getName(), beneventum);

		Settlement narnia = new Settlement("Narnia", 647, 501);
		narnia.addProducingGood(Stock.StockType.HONEY);
		narnia.addProducingGood(Stock.StockType.BREAD);
		narnia.addNeededGood(StockType.WOOD);
		narnia.addNeededGood(StockType.WEAPON);
		SETTLEMENTS.put(narnia.getName(), narnia);
		
		Settlement populonium = new Settlement("Populonium", 65, 500);
		populonium.addProducingGood(Stock.StockType.FISH);
		populonium.addProducingGood(Stock.StockType.SLAVE);
		populonium.addProducingGood(StockType.SALT);
		populonium.addNeededGood(StockType.DIAMONDS);
		populonium.addNeededGood(StockType.BRONZE);
		SETTLEMENTS.put(populonium.getName(), populonium);
		
		Settlement ancona = new Settlement("Ancona", 1003, 521);
		ancona.addProducingGood(Stock.StockType.FISH);
		ancona.addProducingGood(Stock.StockType.SPICES);
		ancona.addNeededGood(StockType.OLIVES);
		ancona.addNeededGood(StockType.COAL);
		SETTLEMENTS.put(ancona.getName(), ancona);
		
		Settlement florentia = new Settlement("Florentia", 302, 293);
		florentia.addProducingGood(Stock.StockType.HONEY);
		florentia.addProducingGood(Stock.StockType.MARBLE);
		florentia.addProducingGood(Stock.StockType.WINE);
		florentia.addProducingGood(Stock.StockType.SALT);
		florentia.addNeededGood(StockType.ROCK);
		florentia.addNeededGood(StockType.MEAT);
		SETTLEMENTS.put(florentia.getName(), florentia);
		
		Settlement parma = new Settlement("Parma", 543, 206);
		parma.addProducingGood(Stock.StockType.MEAT);
		parma.addProducingGood(Stock.StockType.SALT);
		parma.addProducingGood(StockType.IRON);
		parma.addNeededGood(StockType.MILK);
		parma.addNeededGood(StockType.MARBLE);
		SETTLEMENTS.put(parma.getName(), parma);
		
		Settlement mediolanium = new Settlement("Mediolanium", 195, 40);
		mediolanium.addProducingGood(Stock.StockType.CLOTHES);
		mediolanium.addProducingGood(StockType.WOOD);
		mediolanium.addProducingGood(StockType.IRON);
		mediolanium.addProducingGood(StockType.WINE);
		mediolanium.addNeededGood(StockType.HONEY);
		mediolanium.addNeededGood(StockType.FISH);
		SETTLEMENTS.put(mediolanium.getName(), mediolanium);
		
		Settlement ravenna = new Settlement("Ravenna", 872, 228);
		ravenna.addProducingGood(Stock.StockType.FISH);
		ravenna.addProducingGood(Stock.StockType.SPICES);
		ravenna.addNeededGood(StockType.COAL);
		ravenna.addNeededGood(StockType.IRON);
		SETTLEMENTS.put(ravenna.getName(), ravenna);
		
		Settlement genua = new Settlement("Genua", 730, 40);
		genua.addProducingGood(Stock.StockType.CLOTHES);
		genua.addProducingGood(StockType.COAL);
		genua.addProducingGood(StockType.GOLD);
		genua.addNeededGood(StockType.BRONZE);
		genua.addNeededGood(StockType.MILK);
		SETTLEMENTS.put(genua.getName(), genua);
	}
	
	/** Cretes empire crossroads.*/
	private void createCrossroads()
	{
		Place crossroad_1 = new Place("Crossroad_1", 132, 305);
		CROSSROADS.put(crossroad_1.getName(), crossroad_1);
		
		Place crossroad_2 = new Place("Crossroad_2", 709, 222);
		CROSSROADS.put(crossroad_2.getName(), crossroad_2);
		
		Place crossroad_3 = new Place("Crossroad_3", 338, 437);
		CROSSROADS.put(crossroad_3.getName(), crossroad_3);
		
		Place crossroad_4 = new Place("Crossroad_4", 833, 433);
		CROSSROADS.put(crossroad_4.getName(), crossroad_4);
		
		Place crossroad_5 = new Place("Crossroad_5", 490, 692);
		CROSSROADS.put(crossroad_5.getName(), crossroad_5);
		
		Place crossroad_6 = new Place("Crossroad_6", 866, 757);
		CROSSROADS.put(crossroad_6.getName(), crossroad_6);
		
		Place crossroad_7 = new Place("Crossroad_7", 517, 962);
		CROSSROADS.put(crossroad_7.getName(), crossroad_7);	
	}
	
	/** Creates empire roads.*/
	private void createRoads()
	{
		addRoadAndReverseRoad(SETTLEMENTS.get("Populonium"), CROSSROADS.get("Crossroad_1"));
		//addRoad(CROSSROADS.get("Crossroad_1"), SETTLEMENTS.get("Populonium"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Mediolanium"), CROSSROADS.get("Crossroad_1"));
		//addRoad(CROSSROADS.get("Crossroad_1"), SETTLEMENTS.get("Mediolanium"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Florentia"), CROSSROADS.get("Crossroad_1"));
		//addRoad(CROSSROADS.get("Crossroad_1"), SETTLEMENTS.get("Florentia"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Parma"), CROSSROADS.get("Crossroad_2"));
		//addRoad(CROSSROADS.get("Crossroad_2"), SETTLEMENTS.get("Parma"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Genua"), CROSSROADS.get("Crossroad_2"));
		//addRoad(CROSSROADS.get("Crossroad_2"), SETTLEMENTS.get("Genua"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Ravenna"), CROSSROADS.get("Crossroad_2"));
		//addRoad(CROSSROADS.get("Crossroad_2"), SETTLEMENTS.get("Ravenna"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Florentia"), CROSSROADS.get("Crossroad_3"));
		//addRoad(CROSSROADS.get("Crossroad_3"), SETTLEMENTS.get("Florentia"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Parma"), CROSSROADS.get("Crossroad_3"));
		//addRoad(CROSSROADS.get("Crossroad_3"), SETTLEMENTS.get("Parma"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Narnia"), CROSSROADS.get("Crossroad_3"));
		//addRoad(CROSSROADS.get("Crossroad_3"), SETTLEMENTS.get("Narnia"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Rome"), CROSSROADS.get("Crossroad_3"));
		//addRoad(CROSSROADS.get("Crossroad_3"), SETTLEMENTS.get("Rome"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Ancona"), CROSSROADS.get("Crossroad_4"));
		//addRoad(CROSSROADS.get("Crossroad_4"), SETTLEMENTS.get("Ancona"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Narnia"), CROSSROADS.get("Crossroad_4"));
		//addRoad(CROSSROADS.get("Crossroad_4"), SETTLEMENTS.get("Narnia"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Rome"), CROSSROADS.get("Crossroad_5"));
		//addRoad(CROSSROADS.get("Crossroad_5"), SETTLEMENTS.get("Rome"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Interpromium"), CROSSROADS.get("Crossroad_5"));
		//addRoad(CROSSROADS.get("Crossroad_5"), SETTLEMENTS.get("Interpromium"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Narnia"), CROSSROADS.get("Crossroad_5"));
		//addRoad(CROSSROADS.get("Crossroad_5"), SETTLEMENTS.get("Narnia"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Interpromium"), CROSSROADS.get("Crossroad_6"));
		//addRoad(CROSSROADS.get("Crossroad_6"), SETTLEMENTS.get("Interpromium"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Beneventum"), CROSSROADS.get("Crossroad_6"));
		//addRoad(CROSSROADS.get("Crossroad_6"), SETTLEMENTS.get("Beneventum"));
		
		addRoadAndReverseRoad(SETTLEMENTS.get("Interpromium"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), SETTLEMENTS.get("Interpromium"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Beneventum"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), SETTLEMENTS.get("Beneventum"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Neapolis"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), SETTLEMENTS.get("Neapolis"));
		addRoadAndReverseRoad(SETTLEMENTS.get("Rome"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), SETTLEMENTS.get("Rome"));
		
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_1"), CROSSROADS.get("Crossroad_3"));
		//addRoad(CROSSROADS.get("Crossroad_3"), CROSSROADS.get("Crossroad_1"));
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_2"), CROSSROADS.get("Crossroad_4"));
		//addRoad(CROSSROADS.get("Crossroad_4"), CROSSROADS.get("Crossroad_2"));
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_3"), CROSSROADS.get("Crossroad_4"));
		//addRoad(CROSSROADS.get("Crossroad_4"), CROSSROADS.get("Crossroad_3"));
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_3"), CROSSROADS.get("Crossroad_5"));
		//addRoad(CROSSROADS.get("Crossroad_5"), CROSSROADS.get("Crossroad_3"));
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_4"), CROSSROADS.get("Crossroad_5"));
		//addRoad(CROSSROADS.get("Crossroad_5"), CROSSROADS.get("Crossroad_4"));
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_4"), CROSSROADS.get("Crossroad_6"));
		//addRoad(CROSSROADS.get("Crossroad_6"), CROSSROADS.get("Crossroad_4"));
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_5"), CROSSROADS.get("Crossroad_6"));
		//addRoad(CROSSROADS.get("Crossroad_6"), CROSSROADS.get("Crossroad_5"));
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_5"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), CROSSROADS.get("Crossroad_5"));
		
		addRoadAndReverseRoad(CROSSROADS.get("Crossroad_6"), CROSSROADS.get("Crossroad_7"));
		//addRoad(CROSSROADS.get("Crossroad_7"), CROSSROADS.get("Crossroad_6"));
	}
	
	/** Add new road to empire road list.*/
	public void addRoad(Place A, Place B)
	{
		Road r = new Road(A, B);
		
		ROADS.put(A.getName() + B.getName(), r);
	}
	
	/** Add two new roads with the same run but reverse direction.*/
	private void addRoadAndReverseRoad(Place A, Place B)
	{
		Road r = new Road(A,B);
		Road rr = r.createReverseRoad();
		ROADS.put(A.getName() + B.getName(), r);
		ROADS.put(B.getName()+A.getName(), rr);
	}
	
	/** Waiting for Chuck's 6th season...*/
	public void createRiversAndRoads()
	{
		;
	}
	
	/** Returns state of empire treasury.
	 * 
	 * @return State of empire treasury.
	 */
	public int getGold()
	{
		synchronized(smaug)
		{
			return Empire.getInstance().gold;
		}
	}
	
	/** Increases empire treasury state by given value.
	 * 
	 * @param gold Earned gold.
	 */
	public void increaseGold(int gold)
	{
		synchronized(smaug)
		{
			this.gold += gold;
		}
	}
	
	/** Decreases empire treasury state by given value.
	 * 
	 * @param gold Spent gold.
	 */
	public void decreaseGold(int gold)
	{
		synchronized(smaug)
		{
			if(gold >= this.gold)
			{
				this.gold = 0;
			}
			else
			{
				this.gold -= gold;
			}
		}
	}

	@Override
	@Deprecated
	public void draw()
	{
		
	}
	
	/** Sets new powerful emperor of Roman Empire.
	 * 
	 * @param emperor Emperor name.
	 */
	public void setEmperor(String emperor)
	{
		this.emperorName = emperor;
	}
	
	/** Returns name of Roman Emperor.
	 * 
	 * @return Name of Roman Emperor.
	 */
	public String getEmperor()
	{
		return this.emperorName;
	}
	
	@Override
	public ImageIcon getImage()
	{
		return image;
	}
	
	/** Returns initialization time of game and the empire.
	 * 
	 * @return Initialization time of game and the empire.
	 */
	public Date getInitializationTime()
	{
		return initializationTime;
	}
	
	
	
	/** Class which represents all possible sounds of empire and living creatures.
	 * 
	 * @author Carlos
	 *
	 */
	public final static class SoundsOfEmpire implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/** All possible sounds of roman legion.*/
		private static final SoundsOfEmpire[] ROMAN_LEGION = {new SoundsOfEmpire("sounds/legion_1.wav"), new SoundsOfEmpire("sounds/legion_1.wav"), new SoundsOfEmpire("sounds/legion_2.wav"), new SoundsOfEmpire("sounds/legion_3.wav"), new SoundsOfEmpire("sounds/legion_4.wav"), new SoundsOfEmpire("sounds/legion_5.wav"), new SoundsOfEmpire("sounds/legion_6.wav"), new SoundsOfEmpire("sounds/legion_7.wav")};
		/** All possible sounds of barbarian horde.*/
		private static final SoundsOfEmpire[] BARBARIAN_HORDE = {new SoundsOfEmpire("sounds/barbarian_1.wav"), new SoundsOfEmpire("sounds/barbarian_2.wav"), new SoundsOfEmpire("sounds/barbarian_3.wav"), new SoundsOfEmpire("sounds/barbarian_4.wav"), new SoundsOfEmpire("sounds/barbarian_5.wav"), new SoundsOfEmpire("sounds/barbarian_6.wav") };
		/** All possible sounds of merchant.*/
		private static final SoundsOfEmpire[] MERCHANT = {new SoundsOfEmpire("sounds/peasant_1.wav"), new SoundsOfEmpire("sounds/peasant_2.wav"), new SoundsOfEmpire("sounds/peasant_3.wav"), new SoundsOfEmpire("sounds/peasant_4.wav"), new SoundsOfEmpire("sounds/peasant_5.wav")};
		/** All possible fight sounds.*/
		private static final SoundsOfEmpire[] FIGHT = {new SoundsOfEmpire("sounds/fight_1.wav"), new SoundsOfEmpire("sounds/fight_2.wav")};
		/** Creature death sound.*/
		private static final SoundsOfEmpire DEATH = new SoundsOfEmpire("sounds/peasant_death.wav");
		/** Game beginning sound.*/
		private static final SoundsOfEmpire ROOTS_AT_BEGINING = new SoundsOfEmpire("sounds/begining.wav");
		/** End game sound.*/
		private static final SoundsOfEmpire ENDING_STATS = new SoundsOfEmpire("sounds/endgame.wav");
		/** All possible sounds of attacked settlement.*/
		private static final SoundsOfEmpire[] ATTACKED_SETTLEMENT = {new SoundsOfEmpire("sounds/attacked_city_1.wav"), new SoundsOfEmpire("sounds/attacked_city_2.wav")};

		/** Path to soundtrack songs.*/
		private static final String[] SOUNDTRACK_PATHS = {"sounds/soundtrack_1.wav", "sounds/soundtrack_2.wav", "sounds/soundtrack_4.wav", "sounds/soundtrack_5.wav", "sounds/soundtrack_6.wav"};
		/** Soundtrack songs durations.*/
		private static final int[] SOUNDTRACK_TIMES = {240, 190, 349, 159, 138};
		
		/** Class audio track holder.*/
		private AudioClip clip;
		/** Soundtrack song holder.*/
		private static AudioClip soundtrackClip;
		
		/** Class constructor with given audio track path.
		 * 
		 * @param file Audio track path.
		 */
		private SoundsOfEmpire(String file)
		{
			try
			{
				//System.out.println(SoundsOfEmpire.class.getResource(file));
				clip = Applet.newAudioClip(SoundsOfEmpire.class.getResource(file));
				
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		private void stop()
		{
			clip.stop();
		}
		
		private void play()
		{
			try 
			{
				new Thread()
				{
					public void run()
					{
						clip.play();
					}
				}.start();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	
		/** Plays background music.*/
		private static void playBackgroundMusic()
		{
			try
			{
				new Thread()
				{
					public void run()
					{
						try
						{
							while(true)
							{
								int choose = random.nextInt(SOUNDTRACK_PATHS.length);
								soundtrackClip = Applet.newAudioClip(SoundsOfEmpire.class.getResource(SOUNDTRACK_PATHS[choose]));
								soundtrackClip.play();
								Thread.sleep(SOUNDTRACK_TIMES[choose]*1000);
								soundtrackClip = null;
							}	
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					
				}.start();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			
		}
		
		/** Stops background music.*/
		public static void stopBackgroundMusic()
		{
			if(soundtrackClip != null)
			{
				soundtrackClip.stop();
			}
		}
		
		/** Resumes background music.*/
		public static void resumeBackgroundMusic()
		{
			if(soundtrackClip != null)
			{
				soundtrackClip.play();
			}
		}
		
		/** Definitely stops background music.*/
		private static void closeBackgroundMusic()
		{
			if(soundtrackClip != null)
			{
				soundtrackClip.stop();
				soundtrackClip = null;
			}
		}
		
		/** Plays randomized attacked settlement sound.*/
		public static void playAttackedSettlementSound()
		{
			ATTACKED_SETTLEMENT[random.nextInt(2)].play();
		}
		
		/** Plays randomized fight sound.*/
		public static void playFightSound()
		{
			FIGHT[random.nextInt(2)].play();
		}
		
		/** Plays creature death sound.*/
		public static void playDeathSound()
		{
			DEATH.play();
		}
		
		/** Plays game intro music.*/
		public static void playIntroMusic()
		{
			ROOTS_AT_BEGINING.play();
		}
		
		/** Stops game intro music.*/
		public static void stopIntroMusic()
		{
			ROOTS_AT_BEGINING.stop();
		}
		
		/** Plays game outro music.*/
		public static void playEndgameMusic()
		{
			ENDING_STATS.play();
		}
		
		/** Stops game outro music.*/
		public static void stopEndgameMusic()
		{
			ENDING_STATS.stop();
		}
		
		/** Plays randomized roman legion sound.*/
		public static void playSoundOfRomanLegion()
		{
			ROMAN_LEGION[random.nextInt(ROMAN_LEGION.length)].play();
		}
		
		/** Plays randomized barbarian horde sound.*/
		public static void playSoundOfBarbarianHorde()
		{
			BARBARIAN_HORDE[random.nextInt(BARBARIAN_HORDE.length)].play();
		}
		
		/** Plays randomized merchant sound.*/
		public static void playSoundOfMerchant()
		{
			MERCHANT[random.nextInt(MERCHANT.length)].play();
		}
		
		
	}
	
	
	/** Class which represents graphic path of empire. It is singleton class type.
	 * 
	 * @author Carlos
	 *
	 */
	static final class EmpireDrawer extends JPanel implements Runnable, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/** Static instance of Empire Drawer.*/
		protected static final EmpireDrawer DRAWED_EMPIRE = new EmpireDrawer();
		/** End game image.*/
		protected final static ImageIcon END_IMAGE = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Empire.class.getResource("graphics/end_image.png")));
		/** Emperor image.*/
		protected final static ImageIcon EMPEROR = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Empire.class.getResource("graphics/emperor.png")));
		/** Lord of Rome title image.*/
		protected final static ImageIcon MAIN_TITLE = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Empire.class.getResource("graphics/main_title.png")));
		
		/** Information about whether selecting rectangle should be drawn.*/
		private static volatile boolean shoudDrawRectangle;
		
		/** Static JFrame - the main window of application.*/
		protected final static JFrame empireFrame = new JFrame();
		
		/** Side panel of main window. Holds all important information about units and settlement (if some is actually selected).*/
		static protected ActionWindow properties;
		
		/** Panel which belongs to side panel of main window and shows basic informations about current game.*/
		static protected ActionWindow gameInformations;
		/** Label which holds informations about actual game time.*/
		static protected JLabel timeLabel = new JLabel();
		/** Label which holds informations about actual empire treasury state.*/
		static protected JLabel goldLabel = new JLabel();
		/** Button for creating new merchant units.*/
		static protected JButton newMerchantButton = new JButton();
		/** Button for creating new roman legion units.*/
		static protected JButton newRomanLegionButton = new JButton();
		
		/** Pretty gold color.*/
		protected static final Color COLOR_GOLD = new Color(255,204,51);
		/** Pretty white color.*/
		protected static final Color COLOR_WHITE = new Color(255,255,255);
		/** Pretty black color.*/
		protected static final Color COLOR_BLACK = new Color(0,0,0);
		/** Color of neutral units. It is some sort of pretty yellow color.*/
		protected static final Color COLOR_NEUTRAL = new Color(255, 230, 102);
		/** Color of enemy units. It is some sort of pretty red color.*/
		protected static final Color COLOR_ENEMY = new Color(204, 5, 5);
		/** Color of ally units. It is some sort of pretty green color.*/
		protected static final Color COLOR_ALLY = new Color(77, 204, 51);
		
		/** 15, bold, times new roman font.*/
		protected static final Font TIMES_BOLD_15 = new Font("Times New Roman", Font.BOLD, 15);
		/** 17, bold, times new roman font.*/
		protected static final Font TIMES_BOLD_17 = new Font("Times New Roman", Font.BOLD, 17);
		/** 20, bold, times new roman, font.*/
		protected static final Font TIMES_BOLD_20 = new Font("Times New Roman", Font.BOLD, 20);
		/** 24, bold, times new roman, font.*/
		protected static final Font TIMES_BOLD_24 = new Font("Times New Roman", Font.BOLD, 24);
		
		/** Side menu of main window.*/
		static protected JPanel sideMenu = new JPanel();

		
		/** Private class constructor.*/
		private EmpireDrawer()
		{
			super();	
		}
		
		/** Returns unique instance of Empire Drawer.
		 * 
		 * @return Unique instance of Empire Drawer.
		 */
		public static EmpireDrawer getInstance()
		{
			prepareYourself();
			return DRAWED_EMPIRE;
		}
		
		/** Prepares frame of Empire Drawer singleton instance to correctly work.*/
		private static void prepareFrame()
		{
			EmpireDrawer empire = EmpireDrawer.getInstance();
			
			
			properties = empire.new ActionWindow();
			gameInformations = empire.new InformationActionWindow() ;
				
			empireFrame.setSize((int)empireDimension.getWidth() + (int)empireDimension.getWidth()/4, (int)empireDimension.getHeight());
			empireFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			empireFrame.getContentPane().add(BorderLayout.WEST, empire);
			Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
			empireFrame.setLocation((displaySize.width - empireFrame.getWidth())/2, (displaySize.height - empireFrame.getHeight())/2);
			empireFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(RomanLegion.class.getResource("graphics/legionist_icon.png")));
			empireFrame.setTitle("<<I AM the senate!>>, said " + Empire.getInstance().getEmperor() + " once...");
			empireFrame.addWindowListener(new WindowListener(){

				@Override
				public void windowActivated(WindowEvent arg0) {}

				@Override
				public void windowClosed(WindowEvent arg0) {}

				@Override
				public void windowClosing(WindowEvent arg0) 
				{
					Empire.getInstance().SETTLEMENTS.clear();
					//endGame();
				}

				@Override
				public void windowDeactivated(WindowEvent arg0) {}

				@Override
				public void windowDeiconified(WindowEvent arg0) {}

				@Override
				public void windowIconified(WindowEvent arg0) {		}

				@Override
				public void windowOpened(WindowEvent arg0) {}
				
			});
				
			timeLabel.setFont(TIMES_BOLD_15);
			timeLabel.setForeground(COLOR_GOLD);
			//timeLabel.setMaximumSize(new Dimension(240, 50));
			//timeLabel.setPreferredSize(new Dimension(240,50));
			
			goldLabel.setFont(TIMES_BOLD_15);
			goldLabel.setForeground(COLOR_GOLD);
			
			newMerchantButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Merchant.class.getResource("graphics/merchant_icon.png"))));
			newMerchantButton.setFont(TIMES_BOLD_17);
			newMerchantButton.setText("Create new Merchant");
			newMerchantButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					int counter = 0;
					//synchronized(creaturesListGuard)
					{
						for(Creature c : Empire.getInstance().CREATURES)
						{
							if (c.getClass() == Merchant.class)
							{
								counter++;
							}
						}
					}
					
					if (counter <= 20)
					{
						if(Empire.getInstance().SETTLEMENTS.get("Rome") != null)
						{
							{
								//final JWindow merchantWindow = new JWindow(Empire.EmpireDrawer.empireFrame);
								
								
							}
							
							
							
							
							
							
							
							
							
							
							
							
							
							if(Empire.getInstance().getGold() >= 100)
							{
								new Merchant();
								Empire.getInstance().decreaseGold(100);
								
								if(Empire.getInstance().getGold() >= 100)
								{
									newMerchantButton.setEnabled(true);
								}
								else
								{
									newMerchantButton.setEnabled(false);
								}
								
								if(Empire.getInstance().getGold() >= 1000)
								{
									newRomanLegionButton.setEnabled(true);
								}
								else
								{
									newRomanLegionButton.setEnabled(false);
								}
							}
							
							newMerchantButton.setToolTipText("");
						}
						else
						{
							newMerchantButton.setEnabled(false);
						}
						
					}
					else
					{
						newMerchantButton.setToolTipText("To many merchants...");
					}
					
				}
				
			});
			
			newRomanLegionButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(RomanLegion.class.getResource("graphics/legionist_icon.png"))));
			newRomanLegionButton.setFont(TIMES_BOLD_17);
			newRomanLegionButton.setText("  Create new Legion    ");
			newRomanLegionButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(Empire.getInstance().SETTLEMENTS.get("Rome") != null)
					{
						if(Empire.getInstance().getGold() >= 1000)
						{
							new RomanLegion();
							Empire.getInstance().decreaseGold(1000);
							
							if(Empire.getInstance().getGold() >= 100)
							{
								newMerchantButton.setEnabled(true);
							}
							else
							{
								newMerchantButton.setEnabled(false);
							}
							
							if(Empire.getInstance().getGold() >= 1000)
							{
								newRomanLegionButton.setEnabled(true);
							}
							else
							{
								newRomanLegionButton.setEnabled(false);
							}
						}
					}
					else
					{
						newRomanLegionButton.setEnabled(false);
					}
				}
				
			});
			
			sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
			sideMenu.setAlignmentX(RIGHT_ALIGNMENT);
			sideMenu.setMaximumSize(new Dimension(240,1024));
			sideMenu.setPreferredSize(new Dimension(240,1024));
			sideMenu.setBackground(COLOR_BLACK);
			
			gameInformations.add(timeLabel);
			gameInformations.add(goldLabel);
			gameInformations.add(newMerchantButton);
			gameInformations.add(newRomanLegionButton);
			
			sideMenu.add(gameInformations);
			sideMenu.add(properties);
			empireFrame.getContentPane().add(BorderLayout.EAST,sideMenu);
			
			MyMouseListener m = new MyMouseListener();
			empire.addMouseListener(m);
			empire.addMouseMotionListener(m);
			
			empireFrame.setVisible(true);
		}
		
		/** Prepares Empire Drawer singleton instance to correctly work.*/
		private static void prepareYourself()
		{
			DRAWED_EMPIRE.setSize(empireDimension);
			DRAWED_EMPIRE.setPreferredSize(empireDimension);
		}
		
		/** Creates independent thread of empire drawer.*/
		public static void createDrawableEmpire()
		{
			Runnable runner = EmpireDrawer.getInstance();
			Thread empireThread = new Thread(runner, "graphic thread");
			empireThread.start();
			
		}
		
		/** Set information whether selecting units rectangle should be drawn.
		 * 
		 * @param b Information whether selecting units rectangle should be drawn.
		 */
		synchronized public static void setShoudDrawRectangle(boolean b)
		{
			shoudDrawRectangle = b;
		}
		
		/** Returns information whether selecting units rectangle should be drawn.
		 * 
		 * @return information whether selecting units rectangle should be drawn.
		 */
		synchronized public static boolean isShouldDrawRectangle()
		{
			return shoudDrawRectangle;
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D)g;

			g2d.drawImage(Empire.image.getImage(), 0, 0, this);
			
			//synchronized(creaturesListGuard)
			{
				for(Creature d : Empire.getInstance().CREATURES)
				{
					int x = d.getXPosition();
					int y = d.getYPosition();
					g2d.drawImage(d.getImage().getImage(), x - 18, y - 22, this);	
					
					if(d.isSelected() == true)		
					{
						
						if(d.getOccupation().equals(Creature.Occupation.ALLY))
						{
							g2d.setColor(COLOR_ALLY);
						}
						else if (d.getOccupation().equals(Creature.Occupation.ENEMY))
						{
							g2d.setColor(COLOR_ENEMY);
						}
						else
						{
							g2d.setColor(COLOR_NEUTRAL);
						}
						
						g2d.fillRect(x-15, y-35, 30, 5);
						g2d.setColor(Color.BLACK);
						g2d.drawRect(x-16, y-36, 31, 6);
					}
				}
			}
			
			
			for(Settlement s : Empire.getInstance().SETTLEMENTS.values())
			{
				int x = s.getXPosition();
				int y = s.getYPosition();
				int c = s.getPopulation();
				
				if(s.getPopulation() >0)
				{
					g2d.drawImage(s.getImage().getImage(), x-28, y-27, this);
					
					if(s.isSelected() == true)
					{
						g2d.setColor(COLOR_ALLY);
						g2d.fillRect(x-((25*s.getMaxPopulation())/s.getMaxPopulation()), y-38, (50*c)/s.getMaxPopulation(), 5);
						
						g2d.setColor(Color.BLACK);
						g2d.drawRect(x-26, y-39, 51, 6);
					}
				}
				else
				{
					//String name = s.getName();
					//Empire.getInstance().SETTLEMENTS.remove(name);
				}
				
				
			}
			
			
			if(EmpireDrawer.isShouldDrawRectangle() == true)
			{
				int aX = MyMouseListener.a.getXPosition(), bX = MyMouseListener.b.getXPosition(), aY = MyMouseListener.a.getYPosition(), bY = MyMouseListener.b.getYPosition();
				
				if(aX > bX)
				{
					int h = aX;
					aX = bX;
					bX = h;
				}
				
				if(aY > bY)
				{
					int h = aY;
					aY = bY;
					bY = h;
				}
				
				g2d.drawRect(aX, aY, bX-aX, bY-aY);
			}
		}

		@Override
		public void run() 
		{
			int i = 0;
			prepareFrame();
			
			new Thread(){
			public void run()
			{
				try
				{
					Thread.sleep(100);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				while(true)
				{
					EmpireDrawer.sideMenu.repaint();
					if(Empire.getInstance().SETTLEMENTS.get("Rome") == null)
					{
						newMerchantButton.setEnabled(false);
						newRomanLegionButton.setEnabled(false);
					}
					
					if(Empire.getInstance().getGold() >= 100)
					{
						newMerchantButton.setEnabled(true);
					}
					else
					{
						newMerchantButton.setEnabled(false);
					}
					
					if(Empire.getInstance().getGold() >= 1000)
					{
						newRomanLegionButton.setEnabled(true);
					}
					else
					{
						newRomanLegionButton.setEnabled(false);
					}
					
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			}.start();
			
			
			while(true)
			{	
				i++;
				
				//EmpireDrawer.properties.revalidate();
				
				
				this.repaint();
				try
				{
					Thread.sleep(33);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				if(i <=30)
				{
					EmpireDrawer.timeLabel.setText("Game Time:   " + Empire.getActualTimeName());
					EmpireDrawer.goldLabel.setText("Gold:   " + Empire.getInstance().getGold());
					i=0;
				}
				
			}
			
		}
		
		/** Customized Empire Drawer MouseListener.
		 * 
		 * @author Carlos
		 *
		 */
		private static final class MyMouseListener implements MouseListener, MouseMotionListener
		{
			private static Point a = new Point();
			private static Point b = new Point();
			
			
			@Override
			public void mouseClicked(MouseEvent mouse) 
			{
				int x = mouse.getX(), y = mouse.getY();
				
				for(Settlement s : Empire.getInstance().SETTLEMENTS.values())
				{
					s.setSelected(false);
				}
				
				
				for(Settlement s : Empire.getInstance().SETTLEMENTS.values())
				{
					if(s.getXPosition() >= x - 25 && s.getXPosition() <= x + 25 && s.getYPosition() >= y - 25 && s.getYPosition() <= y + 25)
					{
						s.setSelected(true);
						sideMenu.remove(properties);
						sideMenu.revalidate();
						properties = EmpireDrawer.getInstance().new SettlementActionWindow(s);
						sideMenu.add(properties);
						sideMenu.revalidate();
						empireFrame.repaint();
						
						return;
					}
					
				}
				
				ArrayList<Creature> selectedList = new ArrayList<>();
				
				//synchronized(creaturesListGuard)
				{
					for(Creature c : Empire.getInstance().CREATURES)
					{
						if(c.getXPosition() >= x - 15 && c.getXPosition() <= x + 15 && c.getYPosition() >= y - 15 && c.getYPosition() <= y + 15)
						{
							/*if(c.getClass() == BarbarianHorde.class)
							{
								c.setSelected(true);
								c.speak();
							}
							else*/
							{
								c.setSelected(true);
								c.speak();
								selectedList.add(c);
								
								sideMenu.remove(properties);
								sideMenu.revalidate();
								
								if(c.getClass() == Merchant.class)
								{
									properties = EmpireDrawer.getInstance().new MerchantActionWindow(selectedList);
								}
								else if (c.getClass() == RomanLegion.class)
								{
									properties = EmpireDrawer.getInstance().new RomanLegionActionWindow(selectedList);
								}
								else if(c.getClass() == BarbarianHorde.class)
								{
									properties = EmpireDrawer.getInstance().new BarbarianHordeActionWindow(selectedList);
								}
								
								sideMenu.add(properties);
								sideMenu.revalidate();
								empireFrame.repaint();
							}
							return;
						}
					}
				}
				
				
				sideMenu.remove(properties);
				sideMenu.revalidate();
				properties = EmpireDrawer.getInstance().new ActionWindow();
				sideMenu.add(properties);
				sideMenu.revalidate();
				empireFrame.repaint();

				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				;
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				;
			}

			@Override
			public void mousePressed(MouseEvent mouse) 
			{
				
				a.setPosition(mouse.getX(), mouse.getY());
				b.setPosition(mouse.getX(), mouse.getY());
				EmpireDrawer.setShoudDrawRectangle(true);
			}

			@Override
			public void mouseReleased(MouseEvent mouse) 
			{
				EmpireDrawer.setShoudDrawRectangle(false);
				
				b.setPosition(mouse.getX(), mouse.getY());
				
				int aX = MyMouseListener.a.getXPosition(), bX = MyMouseListener.b.getXPosition(), aY = MyMouseListener.a.getYPosition(), bY = MyMouseListener.b.getYPosition();
				
				if(aX > bX)
				{
					int h = aX;
					aX = bX;
					bX = h;
				}
				
				if(aY > bY)
				{
					int h = aY;
					aY = bY;
					bY = h;
				}
				
				
				
				for(Settlement s : Empire.getInstance().SETTLEMENTS.values())
				{
					s.setSelected(false);
				}
				
				
				boolean isRomanLegionSelected  =false, isMerchantSelected=false;
				
				ArrayList<Creature> selectedList = new ArrayList<>();
				
				//synchronized(creaturesListGuard)
				{
					for(Creature c : Empire.getInstance().CREATURES)
					{
						c.setSelected(false);
						
						if(c.getXPosition() >= aX && c.getXPosition() <= bX && c.getYPosition() >= aY && c.getYPosition() <= bY)
						{
							if(c.getClass() == BarbarianHorde.class)
							{
								
							}
							else
							{
								c.setSelected(true);
								selectedList.add(c);
								
								if(c.getClass() == Merchant.class)
								{
									isMerchantSelected = true;
								}
								else if (c.getClass() == RomanLegion.class)
								{
									isRomanLegionSelected = true;
								}
							}
						}
					}
				}
				
				
				sideMenu.remove(properties);
				sideMenu.revalidate();
				if(isMerchantSelected && isRomanLegionSelected)
				{
					properties = EmpireDrawer.getInstance().new UnitActionWindow(selectedList);
				}
				else if(isMerchantSelected)
				{
					properties = EmpireDrawer.getInstance().new MerchantActionWindow(selectedList);
				}
				else if(isRomanLegionSelected)
				{
					properties = EmpireDrawer.getInstance().new RomanLegionActionWindow(selectedList);
				}
				sideMenu.add(properties);
				sideMenu.revalidate();
				empireFrame.repaint();
				
				//synchronized(creaturesListGuard)
				{
					for(Creature c : Empire.getInstance().CREATURES)
					{
						if(c.getXPosition() >= aX && c.getXPosition() <= bX && c.getYPosition() >= aY && c.getYPosition() <= bY)
						{
							if(c.getClass() == BarbarianHorde.class)
							{
								
							}
							else
							{
								c.speak();
							}
							return;
						}
					}
				}
				
				
				sideMenu.remove(properties);
				sideMenu.revalidate();
				properties = EmpireDrawer.getInstance().new ActionWindow();
				sideMenu.add(properties);
				sideMenu.revalidate();
				empireFrame.repaint();
			}

			@Override
			public void mouseDragged(MouseEvent mouse)
			{
				b.setPosition(mouse.getX(), mouse.getY());
			}

			@Override
			public void mouseMoved(MouseEvent arg0) 
			{
			    ;
			}
			
		}
		
		/** Customized JPanel for game information and handling empire living forms (units, creatures, settlement, etc.).
		 * 
		 * @author Carlos
		 *
		 */
		class ActionWindow extends JPanel
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			/** Preferred dimension of action window.
			 * 
			 */
			private final Dimension PREFFERED_WINDOW_DIMENSION = new Dimension(240,320);
			//private final Dimension MAXIMAL_WINDOW_DIMENSION = new Dimension(240,500);
		
			
			/** Class constructor.*/
			public ActionWindow()
			{
				super();
				
				this.setSize(PREFFERED_WINDOW_DIMENSION);
				this.setPreferredSize(PREFFERED_WINDOW_DIMENSION);
				this.setMaximumSize(PREFFERED_WINDOW_DIMENSION);
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_BLACK);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
			
		}
		
		/** Customized panel for displaying actual game informations.*/
		class InformationActionWindow extends ActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			/** Preferred dimension of information action window.*/
			private final Dimension PREFFERED_WINDOW_DIMENSION = new Dimension(240,150);
			
			/** Class constructor.*/
			public InformationActionWindow()
			{
				super();
				
				this.setSize(PREFFERED_WINDOW_DIMENSION);
				this.setPreferredSize(PREFFERED_WINDOW_DIMENSION);
				this.setMaximumSize(PREFFERED_WINDOW_DIMENSION);
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_BLACK);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
		}
		
		/** Customized panel for handling settlement.*/
		class SettlementActionWindow  extends ActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/** Reference to selected settlement.*/
			protected Settlement settlement;
			/** Label which holds selected settlement name.*/
			protected JLabel nameLabel = new JLabel();
			/** Label which holds selected settlement population count.*/
			protected JLabel populationLabel = new JLabel();
			/** Label which holds settlements warehouse available space.*/
			protected JLabel warehouseSpaceLabel = new JLabel();	
			/** Map of labels who hold stored goods names.*/
			protected final HashMap<Stock.StockType, JLabel> goodsLabels = new HashMap <>();
			/** Field that holds informations about settlement production state. */
			boolean productionVariable;
			protected JButton stopProductionButton = new JButton("stop production");
			
			/** Class constructor.*/
			public SettlementActionWindow(Settlement selectedSettlement)
			{
				super();
				settlement = selectedSettlement;
				productionVariable  = settlement.isShoudProduce();
				this.prepareWindow();
				
			}
			
			/** Prepares window to proper work.*/
			private void prepareWindow()
			{
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				this.addMouseListener(new SettlementActionWindowMouseListener());
				
				nameLabel.setText(settlement.getName());
				nameLabel.setForeground(COLOR_WHITE);
				nameLabel.setIcon(settlement.getImage());
				nameLabel.setFont(TIMES_BOLD_20);
				this.add(nameLabel);
					
				//this.add(new JLabel("   "));
				populationLabel.setText("Population:   " + settlement.getPopulation());
				this.add(populationLabel);
				
				this.add(new JLabel("   "));	
				warehouseSpaceLabel.setText("Available warehouse space: " + String.valueOf(settlement.getAvailableWarehouseSpace()));
				this.add(warehouseSpaceLabel);
				
				this.add(new JLabel("Warehouse content:"));
				this.add(new JLabel("Produced goods:"));
				for(Stock s : settlement.getWarehouseGoods().values())
				{
					JLabel hL = new JLabel(s.getType().getName() + ":   " + s.getVolume());
					goodsLabels.put(s.getType(), hL);
					this.add(hL);
				}
				
				
				
				this.add(stopProductionButton);
				if(productionVariable)
				{
					stopProductionButton.setText("stop production");
				}
				else
				{
					stopProductionButton.setText("resume production");
				}
				stopProductionButton.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						productionVariable = ! productionVariable;

						if(productionVariable)
						{
							stopProductionButton.setText("stop production");
							settlement.resumeProduction();
						}
						else
						{
							stopProductionButton.setText("resume production");
							settlement.stopProduction();
						}
					}
					
				});

				
				this.add(new JLabel(" "));
				this.add(new JLabel("Needed goods:"));
				for(Stock s : settlement.getNeededGoodsList().values())
				{
					JLabel nL = new JLabel(s.getType().getName() + ":   " + s.getVolume());
					goodsLabels.put(s.getType(), nL);
					this.add(nL);
				}
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				warehouseSpaceLabel.setText("Available warehouse space: " + String.valueOf(settlement.getAvailableWarehouseSpace()));
				populationLabel.setText("Population:   " + settlement.getPopulation());
				
				for(Stock s : settlement.getWarehouseGoods().values())
				{
					JLabel hL = goodsLabels.get(s.getType());
					hL.setText(s.getType().getName() + ":   " + s.getVolume());
				}
				
				for(Stock s: settlement.getNeededGoodsList().values())
				{
					JLabel hL = goodsLabels.get(s.getType());
					hL.setText(s.getType().getName() + ":   " + s.getVolume());
				}
				
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
			/** Customized SettlementActionWindow MouseListener
			 * 
			 * @author Carlos
			 *
			 */
			class SettlementActionWindowMouseListener implements MouseListener
			{

				@Override
				public void mouseClicked(MouseEvent e) 
				{
					
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					;
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					;
				}

				@Override
				public void mousePressed(MouseEvent e) 
				{
					;
				}

				@Override
				public void mouseReleased(MouseEvent e) 
				{
					;
				}
				
			}
			
		}
		
		/** Customized panel for handling empire's capital.
		 * 
		 * @author Carlos
		 *
		 */
		class CapitalActionWindow  extends SettlementActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			/** Class constructor.*/
			public CapitalActionWindow(Settlement selectedSettlement)
			{
				super(selectedSettlement);
				
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
			/** Customized CapitalActionWindow MouseListener.
			 * 
			 * @author Carlos
			 *
			 */
			class CapitalActionWindowMouseListener implements MouseListener
			{

				@Override
				public void mouseClicked(MouseEvent e) 
				{
					
				}

				@Override
				public void mouseEntered(MouseEvent e) 
				{
					;
				}

				@Override
				public void mouseExited(MouseEvent e) 
				{
					;
				}

				@Override
				public void mousePressed(MouseEvent e) 
				{
					;
				}

				@Override
				public void mouseReleased(MouseEvent e) 
				{
					;
				}
				
			}
			
		}
		
		/** Customized panel for handling ally units.
		 * 
		 */
		class UnitActionWindow  extends ActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/** Label which holds information about.*/
			protected JLabel unitCount = new JLabel();
			/** Label which holds information about unit(s) name.*/
			protected JLabel name = new JLabel();
			/** List that stores list of selected units.*/
			protected ArrayList<Creature> unitList;
			/** List of units that died when they were selected.*/
			protected ArrayList<Creature> creaturesToRemove = new ArrayList<>();
			
			/** Class constructor with given list of selected units.
			 * 
			 * @param unitList List of selected units.
			 */
			public UnitActionWindow(ArrayList<Creature> unitList)
			{
				super();
				this.unitList = unitList;
				
				name.setForeground(COLOR_WHITE);
				name.setFont(TIMES_BOLD_20);
				name.setText("The People of Rome");
		
				this.add(name);
				this.add(new JLabel("   "));
				this.add(unitCount);
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				int i =0;
				creaturesToRemove.clear();
				//synchronized(Empire.creaturesListGuard)
				{		
					for(Creature c : unitList)
					{
						if (c.isLiving() == true)
						{
							i++;
						}
						else
						{
							creaturesToRemove.add(c);
						}
					}
					unitList.removeAll(creaturesToRemove);	
				}
				
				unitCount.setText("Unit count:   " + i);
				
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
		}
		
		/** Customized panel for handling roman legion units.
		 * 
		 * @author Carlos
		 *
		 */
		class RomanLegionActionWindow  extends UnitActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/** Field which holds an information whether only one legion unit is selected.*/
			private boolean isSingle;
			/** Label which represents actual legion life value (if it's single selected).*/
			private final JLabel lifeLabel = new JLabel();
			/** label which represents actual legion weapon (if it's single selected).*/
			private final JLabel weaponLabel = new JLabel();
			/** Reference to selected legion.*/
			private RomanLegion r;
			
			/** Class constructor with given list of selected legions.*/
			public RomanLegionActionWindow(ArrayList<Creature> unitList)
			{
				super(unitList);
				this.prepareWindow(); 
				if(unitList.size() == 1)
				{
					r = (RomanLegion)unitList.get(0);
					name.setText(r.getName());
					this.add(lifeLabel);
					this.add(new JLabel("   "));
					this.add(weaponLabel);
					isSingle = true;
				}
				else
				{
					name.setText("Roman Legions");
					isSingle = false;
				}
			}
			
			/** Prepares window to proper work.*/
			private void prepareWindow()
			{
				
				name.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Merchant.class.getResource("graphics/legionist_icon.png"))));
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				if(isSingle)
				{
					lifeLabel.setText("Life:   " + r.getLife() + " / " + r.getMaxLife());
					weaponLabel.setText("Weapon:   " + r.getWeapon().getName());
				}
				else
				{
					int i =0;
					creaturesToRemove.clear();
					//synchronized(Empire.creaturesListGuard)
					{		
						for(Creature c : unitList)
						{
							if (c.isLiving() == true)
							{
								i++;
							}
							else
							{
								creaturesToRemove.add(c);
							}
						}
						unitList.removeAll(creaturesToRemove);	
					}
					
					unitCount.setText("Legions count:   " + i);
				}
				
				
				
				
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
		}
		
		/** Customized panel for handling barbarian hordes.*/
		class BarbarianHordeActionWindow  extends UnitActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/** Field which holds an information whether only one barbarian horde unit is selected.*/
			private boolean isSingle;
			/** Label which represents actual barbarian horde life value (if it's single selected).*/
			private final JLabel lifeLabel = new JLabel();
			/** label which represents actual barbarian weapon (if it's single selected).*/
			private final JLabel weaponLabel = new JLabel();
			/** Reference to selected horde.*/
			private BarbarianHorde b;
			
			/** Class constructor with given list of selected barbarians.*/
			public BarbarianHordeActionWindow(ArrayList<Creature> unitList)
			{
				super(unitList);
				this.prepareWindow(); 
				if(unitList.size() == 1)
				{
					b = (BarbarianHorde)unitList.get(0);
					name.setText(b.getName());
					this.add(lifeLabel);
					this.add(new JLabel("   "));
					this.add(weaponLabel);
					isSingle = true;
				}
				else
				{
					name.setText("Barbarian Hordes");
					isSingle = false;
				}
			}
			
			/** Prepares window to proper work.*/
			private void prepareWindow()
			{
				
				name.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Merchant.class.getResource("graphics/barbarian_icon.png"))));
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				if(isSingle)
				{
					lifeLabel.setText("Life:   " + b.getLife() + " / " + b.getMaxLife());
					weaponLabel.setText("Weapon:   " + b.getWeapon().getName());
				}
				else
				{
					int i =0;
					creaturesToRemove.clear();
					//synchronized(Empire.creaturesListGuard)
					{		
						for(Creature c : unitList)
						{
							if (c.isLiving() == true)
							{
								i++;
							}
							else
							{
								creaturesToRemove.add(c);
							}
						}
						unitList.removeAll(creaturesToRemove);	
					}
					
					unitCount.setText("Hordes count:   " + i);
				}
				
				
				
				
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
		}
		
		/** Customized panel for handling merchants.
		 * 
		 * @author Carlos
		 *
		 */
		class MerchantActionWindow  extends UnitActionWindow
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			/** Button for handling 'stop/resume merchant move' commands.*/
			private JButton movingButton = new JButton();
			/** Button for handling 'kill merchant' command.*/
			private JButton killButton = new JButton();
			/** Label which represents actual legion life value (if it's single selected).*/
			private final JLabel lifeLabel = new JLabel();
			/** Field which holds information about merchant movement status.*/
			protected boolean movingVariable = ((Merchant)unitList.get(0)).shoudMove();
			/** Label which holds information about merchants actual movement speed (if it's single selected).*/
			protected JLabel movementSpeed = new JLabel();
			/** Holds names of actual traded goods.*/
			protected final HashMap<Stock.StockType, JLabel> goodsLabels = new HashMap <>();
			
			/** Field which hold an information whether merchant is single selected.*/
			private boolean isSingle;
			/** Reference to selected merchant.*/
			private Merchant m;
			
			/** Class constructor.*/
			public MerchantActionWindow(ArrayList<Creature> unitList)
			{
				super(unitList);
				if(unitList.size() == 1)
				{
					isSingle = true;
					this.add(lifeLabel);
					this.add(new JLabel("   "));
					m = (Merchant)unitList.get(0);
					name.setText(m.getName() + " " + m.getSurname());
					this.add(new JLabel("   "));
					
					movementSpeed.setText("Movement speed:   " + m.getMovementSpeed());   
					this.add(movementSpeed);
					
					this.add(new JLabel("   "));
					
					this.add(new JLabel("Traded goods:"));
					for(Stock s : m.getTraidingWagon().getStoredGoods().values())
					{
						JLabel hL = new JLabel(s.getType().getName() + ":   " + s.getVolume());
						goodsLabels.put(s.getType(), hL);
						this.add(hL);
					}
					this.add(new JLabel("   "));
					this.add(new JLabel("   "));
				}
				else
				{
					isSingle = false;
					name.setText("Merchants");
					this.add(unitCount);
					this.add(new JLabel("   "));
				}
				prepareWindow();
				
			}
			
			/** Prepares window to proper work.*/
			private void prepareWindow()
			{
				name.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Merchant.class.getResource("graphics/merchant_icon.png"))));
				
				this.add(movingButton);
				if(movingVariable)
				{
					movingButton.setText("    stop  ");
				}
				else
				{
					movingButton.setText("resume");
				}
				
				movingButton.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						movingVariable = ! movingVariable;
						
						for(Creature c : unitList)
						{
							Merchant m = (Merchant) c;
							if(movingVariable)
							{
								movingButton.setText("    stop   ");
								m.resume();
							}
							else
							{
								movingButton.setText("resume");
								m.stop();
							}
						}
					}
					
				});
				
				this.add(killButton);
				killButton.setText("     kill    ");
				killButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						for(Creature c : unitList)
						{
							//Merchant m = (Merchant) c;
							c.kill();
						}

					}
				});
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				if(isSingle)
				{
					lifeLabel.setText("Life:   " + m.getLife() + " / " + m.getMaxLife());
					
					for(Stock s : m.getTraidingWagon().getStoredGoods().values())
					{
						JLabel hL = goodsLabels.get(s.getType());
						hL.setText(s.getType().getName() + ":   " + s.getVolume());
						movementSpeed.setText("Movement speed:   " + m.getMovementSpeed());
					}
				}
				else
				{
					//int i =0;
					creaturesToRemove.clear();
					//synchronized(Empire.creaturesListGuard)
					{		
						for(Creature c : unitList)
						{
							if (c.isLiving() == true)
							{
								//i++;
							}
							else
							{
								creaturesToRemove.add(c);
							}
						}
						unitList.removeAll(creaturesToRemove);	
					}

					unitCount.setText("Number of merchants: " + unitList.size());
				}
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(COLOR_GOLD);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}
			
		}
	}

	

}
