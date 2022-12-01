package edu.curtin.emergencysim.observers;

import java.util.logging.*;
import edu.curtin.emergencysim.states.*;
import edu.curtin.emergencysim.responders.*;

public class Flood extends Emergency 
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(Low.class.getName());
    	
	private int startTime;
	
	public Flood(String location, long startTime, ResponderComm rc)
	{
		super(location, rc, new Start());
		this.startTime = (int) startTime;
		logger.fine(() -> "Initial State : " + "flood" + " at " + location + " at Start");
	}
	
	@Override
	public String getType() 
	{
		return "flood";
	}
	
	@Override
	public int getStartTime()
	{
		return startTime;
	}
	
	@Override
	public void resetStartTime(int newStime) 
	{
		this.startTime = newStime;
	}
	
	

		
}
