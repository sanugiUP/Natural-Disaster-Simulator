package edu.curtin.emergencysim.observers;

import java.util.logging.*;
import edu.curtin.emergencysim.states.*;
import edu.curtin.emergencysim.responders.*;

/* This class is the Observer. All emergencies observe for elapsed seconds from the Simulator class*/

public abstract class Emergency implements TimeObserver 
{  
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(Low.class.getName());
    	  
	protected ResponderComm rc;
	protected String location;
	protected EmergencyState state;
	public boolean attended;
	private int casualty;
	private int damage;
	private int elapsedSeconds;
	
	public Emergency(String location, ResponderComm rc, EmergencyState state)
	{
		this.location = location;
		this.rc = rc;
		this.state = state;
		this.attended = false; 
	}
	
	public abstract String getType();
	public abstract int getStartTime();
	public abstract void resetStartTime(int newStime);
	
	@Override
	public String toString()
	{
		return getType() + " " + this.location;
	}
	
	public int getElapsedSeconds()
	{
		return this.elapsedSeconds;
	}

	@Override
	public void updateTimeElapsed(long elapsedSeconds) 
	{
		this.elapsedSeconds = (int) elapsedSeconds;
	}
	
	public void setState(EmergencyState newState)
	{
		this.state = newState;
	}
	
	public boolean getAttended() 
	{
		return attended;
	}
		
	public String getLocation()
	{
		return this.location;
	}
	
	public int getCasualty()
	{
		return casualty;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void run()
	{
		state.run(this);
	}
	
	public void setAttended(boolean attended)
	{
		this.attended = attended;
	}
	
	public void sendMessage(String msgType)
	{
		switch(msgType)
		{
			case "normal" : rc.send(state.getMessage(getType(), location)); break;
			case "casualty" : rc.send(state.getCasualtyMessage(getType(), location, casualty)); break;
			case "damage" : rc.send(state.getDamageMessage(getType(), location, damage)); break;
			default : break;
		}	
	}
	
	public void updateCasualty()
	{
		this.casualty++;
		logger.fine(() -> "Casualty Count Updated : " + getType() + " at " + location + " #" + casualty);
	}
	
	public void updateDamage()
	{
		this.damage++;
		logger.fine(() -> "Damage Count Updated : " + getType() + " at " + location + " #" + damage);
	}
	
}
