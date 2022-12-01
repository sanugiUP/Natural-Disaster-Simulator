package edu.curtin.emergencysim.observers;

import java.util.logging.*;
import edu.curtin.emergencysim.states.*;
import edu.curtin.emergencysim.responders.*;

public class ChemicalSpill extends Emergency 
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(Low.class.getName());
    	
	private int startTime;
	
	public ChemicalSpill(String location, long startTime, ResponderComm rc)
	{
		super(location, rc, new Start());
		this.startTime = (int) startTime;
		logger.fine(() -> "Initial State : " + "chemical" + " at " + location + " at Start"); 
	}

	@Override
	public String getType() 
	{
		return "chemical";
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
