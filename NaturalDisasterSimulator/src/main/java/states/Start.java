package edu.curtin.emergencysim.states;

import java.util.logging.*;
import edu.curtin.emergencysim.observers.*;

public class Start implements EmergencyState
{	
	@SuppressWarnings("PMD.FieldNamingConventions")
    	private static final Logger logger = Logger.getLogger(High.class.getName());
    	
	private int cleanUpTime = 0;
	
	@Override
	public void run(Emergency emergency)
	{
		int secondsPassed = emergency.getElapsedSeconds() - emergency.getStartTime();
		calEffectsPerSecond(emergency, "damage");
		switch(emergency.getType())
		{
			case "flood" : 
				if(secondsPassed == FLOOD_END_TIME) { emergency.setState(new End()); emergency.sendMessage("normal"); logger.fine(() -> "State Changed : " + emergency.getType() + " at " 					+ emergency.getLocation() + " to End"); }
				if(emergency.getAttended() == false) { calEffectsPerSecond(emergency, "casualty"); }
				break;
			case "chemical" :  
				calEffectsPerSecond(emergency, "casualty");
				if(emergency.attended == true) { cleanUp(emergency); }
				else if(emergency.attended == false) { cleanUpTime = 0; } // If clean up crew leaves in the middle, the clean up process starts from scratch.
				break;
			default: break;
		}
	}
	
	public void cleanUp(Emergency emergency)
	{
		if(cleanUpTime == CHEM_CLEANUP_TIME)
		{
			emergency.setState(new End()); 
			emergency.sendMessage("normal");
		}
		else
		{
			cleanUpTime++;
		}		
	}
	
	public double getProbability(String emeregencyType, String eventType) /* This methods returns the relavant probability constant given an eventType and emeregencyType */
	{
		double p = 0;
		switch(emeregencyType)
		{
			case "flood" : switch(eventType) { case "casualty" :  p = FLOOD_CASUALTY_PROB;break; case "damage" : p = FLOOD_DAMAGE_PROB; break; default : break;} break;
			case "chemical" : switch(eventType) { case "casualty" :  p = CHEM_CASUALTY_PROB;break; case "damage" : p = CHEM_CONTAM_PROB; break; default : break;} break;
			default : break;
		}
		return p;
	}
	
	public void calEffectsPerSecond(Emergency em, String eventType)
	{
		int events = (int) (1.0 / getProbability(em.getType(), eventType));  // events equals the number of possibilities
		double random = (Math.random() * (events - 1)) + 1;
		int probability = (int) random;  // probability is a integer number between 1 and the events
		
		if(probability == 1)  // If probability is equal to 1 out of events of possibilities, then a casualty or a damage occurs
		{
			if(eventType.equals("casualty")) { em.updateCasualty(); }  // Increment Casualty Count
			else if(eventType.equals("damage")) { em.updateDamage(); } // Increment Damage Count
			em.sendMessage(eventType);
			System.out.println();  // A new line is added to create space before printing the next message sent to the responders. 
		}
	}
	
	@Override
	public String getMessage(String type, String location) 
	{
		String msg = type + " start " + location;
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
