package edu.curtin.emergencysim.states;

import java.util.logging.*;
import edu.curtin.emergencysim.observers.*;

/* This state is only for Fire Emergency. A Fire Emergency is in Low State initially and after a High State fire is attended after FIRE_HIGH_TO_LOW_TIME. Once in Low State, if a fire is attended, then the fire is taken care of by the responders after FIRE_LOW_CLEANP_TIME. Then the fire ends. A fire can only end in Low State. */

public class Low implements EmergencyState
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(Low.class.getName());
    	
	private double cleanUpTime = 0;
	
	@Override
	public void run(Emergency emergency) 
	{
		int secondsPassed = emergency.getElapsedSeconds() - emergency.getStartTime();
		calEffectsPerSecond(emergency, "casualty"); 
		calEffectsPerSecond(emergency, "damage"); 
		if(emergency.getAttended() == false && secondsPassed == FIRE_LOW_TO_HIGH_TIME)
		{
			logger.fine(() -> "State Changed : " + emergency.getType() + " at " + emergency.getLocation() + " to High"); 
			emergency.setState(new High());
			emergency.sendMessage("normal");
			cleanUpTime = 0;  // If clean up crew leaves in the middle, the clean up process starts from scratch.
		}
		else if(emergency.getAttended() == true)
		{
			cleanUp(emergency);
		}
	}
	
	public void cleanUp(Emergency emergency)
	{
		if(cleanUpTime == FIRE_LOW_CLEANP_TIME)
		{
			logger.fine(() -> "State Changed : " + emergency.getType() + " at " + emergency.getLocation() + " to End"); 
			emergency.setState(new End()); 
			emergency.sendMessage("normal");
		}
		else
		{
			cleanUpTime++;
		}		
	}
	
	public double getProbability(String eventType) /* This methods returns the relavant probability constant given an eventType*/
	{
		double p = 0;
		switch(eventType) 
		{ 
			case "casualty" :  p = FIRE_LOW_CASUALTY_PROB;break; 
			case "damage" : p = FIRE_LOW_DAMAGE_PROB; break;
			default : break;
		}
		return p;
	}
	
	public void calEffectsPerSecond(Emergency em, String eventType)
	{
		int events = (int) (1.0 / getProbability(eventType));  // events equals the number of possibilities
		double random = (Math.random() * (events - 1)) + 1;
		int probability = (int) random;   // probability is a integer number between 1 and the events
		
		if(probability == 1) // If probability is equal to 1 out of events of possibilities, then a casualty or a damage occurs
		{
			if(eventType.equals("casualty")) { em.updateCasualty(); }  // Increment Casualty Count
			else if(eventType.equals("damage")) { em.updateDamage(); } // Increment Damage Count
			em.sendMessage(eventType);
			System.out.println(); // A new line is added to create space before printing the next message sent to the responders.
		}
	}

	@Override
	public String getMessage(String type, String location) 
	{
		String msg = type + " low " + location;
		return msg;
	}

	@Override
	public String getCasualtyMessage(String type, String location, int casualty) 
	{
		String msg = type + " casualty " +  casualty + " " + location;
		return msg;
	}

	@Override
	public String getDamageMessage(String type, String location, int damage) 
	{
		String msg = type + " damage " + damage + " " + location;
		return msg;
	}

}
