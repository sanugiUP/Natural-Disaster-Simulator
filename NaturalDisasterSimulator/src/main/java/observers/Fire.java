package edu.curtin.emergencysim.observers;

import java.util.logging.*;
import edu.curtin.emergencysim.states.*;
import edu.curtin.emergencysim.responders.*;

public class Fire extends Emergency
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(Low.class.getName());
    	
	private int startTime;
	
	public Fire(String location, long startTime, ResponderComm rc)
	{
		super(location, rc, new Low());
		this.startTime = (int) startTime;
		logger.fine(() -> "Initial State : " + "fire" + " at " + location + " at Low");
	}

	@Override
	public String getType() 
	{
		return "fire";
	}

	@Override
	public int getStartTime() 
	{
		return this.startTime;
	}
	
	@Override
	public void resetStartTime(int newStime) 
	{
		this.startTime = newStime;
	}
}
