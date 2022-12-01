package edu.curtin.emergencysim.states;

import java.util.logging.*;
import edu.curtin.emergencysim.observers.*;

/* This state is only for Fire Emergency. A Fire Emergency is in High State after FIRE_LOW_TO_HIGH_TIME and will continue to be in High state until responders arrive after which FIRE_HIGH_TO_LOW_TIME the Fire will go down to Low State.*/

public class High implements EmergencyState
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(High.class.getName());
    	
	private int highToLowTime = 0;
	
	@Override
	public void run(Emergency emergency) 
	{
		calEffectsPerSecond(emergency, "casualty"); 
		calEffectsPerSecond(emergency, "damage");
		if(emergency.getAttended() == true)
		{
			if(highToLowTime == FIRE_HIGH_TO_LOW_TIME)
			{
				logger.fine(() -> "State Changed : " + emergency.getType() + " at " + emergency.getLocation() + " to Low"); 
				emergency.setState(new Low());
				emergency.resetStartTime(emergency.getElapsedSeconds());
				emergency.sendMessage("normal");
			}
			else
			{
				highToLowTime++; // Incrementing the count which keeps track of the seconds spent in High state after responders have arrived.
			}
		}
	}
	
	public double getProbability(String eventType) /* This methods returns the relavant probability constant given an eventType*/
	{
		double p = 0;
		switch(eventType) 
		{ 
			case "casualty" :  p = FIRE_HIGH_CASUALTY_PROB;break; 
			case "damage" : p = FIRE_HIGH_DAMAGE_PROB; break;
			default : break;
		}
		return p;
	}
	
	public void calEffectsPerSecond(Emergency em, String eventType)
	{
		int events = (int) (1.0 / getProbability(eventType)); // events equals the number of possibilities
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
		String msg = type + " high " + location;
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
