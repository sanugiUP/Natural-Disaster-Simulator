package edu.curtin.emergencysim.factories;

import edu.curtin.emergencysim.miscellaneous.*;
import edu.curtin.emergencysim.observers.*;
import edu.curtin.emergencysim.responders.*;

/* This class which contains a factory method that creates and returns the relevant Emergency object depending on the requirement.
 * This class also contains two other methods which creates objects of generic class and return them according to requirement. The purpose 
   of these methods is to avoid dependency between classes.*/

public class EmergencyFactory 
{
	//Factory Method
	public Emergency makeEmergency(EmergencyDataRetriever edr, ResponderComm rc, String data)
	{
		Emergency emergency = null;
		switch(edr.getEmergencyType(data).toLowerCase())
        	{
		    case "flood": emergency = new Flood(edr.getEmergencyLocation(data), edr.getEmergencyTime(data), rc); break;
		    case "chemical": emergency = new ChemicalSpill(edr.getEmergencyLocation(data), edr.getEmergencyTime(data), rc); break;
		    case "fire": emergency = new Fire(edr.getEmergencyLocation(data), edr.getEmergencyTime(data), rc); break;
		    default: break;
        	}	
		return emergency;
	}
	
	//Dependency Injector Method
	public SearchAndCheck<String> parameterizeString()
	{
		SearchAndCheck<String> contString = new SearchAndCheck<>();
		return contString;
	}
	
	//Dependency Injector Method
	public SearchAndCheck<Emergency> parameterizeEmergency()
	{
		SearchAndCheck<Emergency> contEm = new SearchAndCheck<>();
		return contEm;
	}
}
