package lord.of.rome;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;


/** Class that allows to load and save player score.
 * 
 * @author Carlos
 *
 */
public final class Saver implements Serializable, Comparable <Saver>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Path to binary save file.*/
	private final static String pathBinary = System.getProperty("user.dir") + "\\savedScoresBinary.lor";
	/** Path to XML save file.*/
	private final static String pathXML = System.getProperty("user.dir") + "\\savedScoresXML.lor";
	/** Path to XStream save file.*/
	@SuppressWarnings("unused")
	private final static String pathXStream = System.getProperty("user.dir") + "\\savedScoresXStream.lor";
	
	
	/** Descriptive game time.*/
	private String gameTime;
	/** Player name.*/
	private String emperorName;
	/** Game time in miliseconds.*/
	private long intGameTime;
	/** Player score.*/
	private long gameScore;
	
	/** List of best scores.*/
	private static ArrayList<Saver> bestScores;
	
	/** Public empty class constructor*/
	public Saver()
	{
		
	}
	
	/** Public class constructor. 
	 * 
	 * @param score Current player score.
	 */
	public Saver(int score)
	{
		this.setGameScore(score);
		this.setGameTime("Testing constructor");
		this.setIntGameTime(1000);
	}
	
	/** Collects all informations about current game.*/
	private void getGameInformations()
	{
		Date actualTime = new Date();
		setIntGameTime((actualTime.getTime() - Empire.getInstance().getInitializationTime().getTime()) / 1000);
		setGameTime((getIntGameTime()/3600) + " hours " + (getIntGameTime()/60)%60 + " minutes " + getIntGameTime()%60 + " seconds");
		this.setEmperorName(Empire.getInstance().emperorName);
	}
	
	/** Prepares instance of class to proper working*/
	private void  prepareSaver()
	{
		getGameInformations();
		
		setGameScore(getIntGameTime());
	}
	
	/** Updates list of best scores*/
	private void updateBestScores()
	{
		if((bestScores != null) && (bestScores.size() > 0))
		{
			bestScores.add(this);
			Collections.sort(bestScores);
			Collections.reverse(bestScores);
			
			for(int i=0; i<bestScores.size(); i++)
			{
				if (i >= 5)
				{
					bestScores.remove(i);
				}
			}
		}
		else
		{
			bestScores = new ArrayList<>();
			bestScores.add(this);
		}
	}
	
	/** Serializes game state binary.*/
	private void serializeBinary()
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(pathBinary)));
			oos.writeObject(bestScores);
			oos.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	/** Serializes game state to XML.*/
	private void serializeXML()
	{
		try
		{
			XMLEncoder xmle = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(pathXML)));
			xmle.writeObject(bestScores);
			xmle.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*private void serializeXStream()
	{
		
	}*/
	
	/** Deserializes game state from binary file.*/
	@SuppressWarnings("unchecked")
	private void deserializeBinary()
	{
		File file = new File(pathBinary);
		if(file.exists())
		{
		}
		else
		{
			return;	
		}
		
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(pathBinary)));
			bestScores =  (ArrayList<Saver>) ois.readObject();
			ois.close();	
		}
		catch(Exception exe)
		{
			exe.printStackTrace();
		}
		
	}
	
	/** Deserializes game state from XML file.*/
	@SuppressWarnings("unchecked")
	private void deserializeXML()
	{	
		File file = new File(pathXML);
		if(file.exists())
		{
			
		}
		else
		{
			return;	
		}
		
		try
		{
			XMLDecoder xmld = new XMLDecoder(new BufferedInputStream(new FileInputStream(pathXML)));
			bestScores = (ArrayList<Saver>) xmld.readObject();
			xmld.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*private void deserializeXStream()
	{
		
	}*/
	
	/** Saves game state in all Ways*/
	public void save()
	{
		try
		{
			deserializeBinary();
			
			prepareSaver();
			updateBestScores();
			
			serializeXML();
			serializeBinary();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/** Saves game state binary*/
	public void saveToBinary()
	{
		try
		{
			deserializeBinary();
			
			prepareSaver();
			updateBestScores();
			
			serializeBinary();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/** Saves game state as XML.*/
	public void saveToXML()
	{
		try
		{
			deserializeXML();
			
			prepareSaver();
			updateBestScores();
			
			serializeXML();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*public void saveWithXStream()
	{
		try
		{
			deserializeXStream();
			
			prepareSaver();
			updateBestScores();
			
			serializeXStream();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}*/
	
	/*public void load()
	{
		deserializeBinary();
	}*/
	
	/*public void loadFromBinary()
	{
		deserializeBinary();
	}*/
	
	/*public void loadFromXML()
	{
		deserializeXML();
	}*/
	
	/*public void loadWithXStream()
	{
		deserializeXStream();
	}*/
	
	
	@Override
	public int compareTo(Saver comparingSaver)
	{
		if(this.getGameScore() > comparingSaver.getGameScore())
		{
			return 1;
		}
		else if (this.getGameScore() < comparingSaver.getGameScore())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Returns game score.
	 * @return Game score.
	 */
	public long getGameScore()
	{
		return gameScore;
	}

	/** Sets game score.
	 * 
	 * @param gameScore Game score.
	 */
	public void setGameScore(long gameScore) 
	{
		this.gameScore = gameScore;
	}

	/** Returns game time in miliseconds.
	 * 
	 * @return Game time in miliseconds.
	 */
	public long getIntGameTime() 
	{
		return intGameTime;
	}
	
	/** set game time in miliseconds.
	 * 
	 * @param intGameTime Game time in miliseconds.
	 */
	public void setIntGameTime(long intGameTime) 
	{
		this.intGameTime = intGameTime;
	}
	
	/** Returns descriptive game time.
	 * 
	 * @return Descriptive game time.
	 */
	public String getGameTime() 
	{
		return gameTime;
	}

	/** Sets descriptive game time.
	 * 
	 * @param gameTime Descriptive game time.
	 */
	public void setGameTime(String gameTime)
	{
		this.gameTime = gameTime;
	}

	/** Returns player name.
	 * 
	 * @return Player name;
	 */
	public String getEmperorName() 
	{
		return emperorName;
	}

	/** Sets player name.
	 * 
	 * @param emperorName Player name.
	 */
	public void setEmperorName(String emperorName) 
	{
		this.emperorName = emperorName;
	}
}
