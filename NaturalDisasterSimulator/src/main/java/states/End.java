package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.observers.*;

/* End is a State common to all Emergency types if they are attended, except in the case of a Flood where it natuarally ends after FLOOD_END_TIME. */

public class End implements EmergencyState
{
	@Override
	public void run(Emergency emergency) 
	{
		//do nothing
	}
	
	@Override
	public String getMessage(String type, String location) 
	{
		String msg = type + " end " + location;
		return msg;
	}

	@Override
	public String getCasualtyMessage(String type, String location, int casualty) 
	{
		return null;
	}

	@Override
	public String getDamageMessage(String type, String location, int damage) 
	{
		return null;
	}

}
