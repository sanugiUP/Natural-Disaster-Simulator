package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.observers.*;

/* All TIMEs are in seconds*/

public interface EmergencyState 
{
	static final double FIRE_LOW_CASUALTY_PROB = 0.02;	
	static final double FIRE_LOW_DAMAGE_PROB = 0.05;
	static final double FIRE_HIGH_CASUALTY_PROB = 0.08;
	static final double FIRE_HIGH_DAMAGE_PROB = 0.1;
	static final double FLOOD_DAMAGE_PROB = 0.05;
	static final double FLOOD_CASUALTY_PROB = 0.02;
	static final long FIRE_LOW_TO_HIGH_TIME = 10; 		
	static final long FIRE_LOW_CLEANP_TIME = 12;		
	static final long FIRE_HIGH_TO_LOW_TIME = 8;		
	static final long FLOOD_END_TIME = 30;              
	static final long CHEM_CLEANUP_TIME = 30;           
	static final double CHEM_CASUALTY_PROB = 0.025;
	static final double CHEM_CONTAM_PROB = 0.05;

	public void run(Emergency emergency); // run function basically contains what happens in each emergency per second. run function is what triggers state changes.
	public String getMessage(String type, String location);
	public String getCasualtyMessage(String type, String location, int casualty);
	public String getDamageMessage(String type, String location, int damage);
	
}
