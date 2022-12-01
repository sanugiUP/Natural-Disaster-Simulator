package edu.curtin.emergencysim.controller;

import java.util.*;
import java.util.logging.*;
import edu.curtin.emergencysim.miscellaneous.*;
import edu.curtin.emergencysim.observers.*;
import edu.curtin.emergencysim.responders.*;
import edu.curtin.emergencysim.factories.*;

/* This class is the Subject or the event source which generates events that the Observers need to recieve and it also controls the simulation*/ 

public class Simulator 
{
	@SuppressWarnings("PMD.FieldNamingConventions")
    private static final Logger logger = Logger.getLogger(Simulator.class.getName());
    
	private List<String> expectedPollData;  // An entry in this is created for each event that occurs. It shows the expected responder message for each emergency.
	private List<String> emFromFile;
	private List<String> pollData;
	private List<Emergency> events; // List of All Observers 
	private ResponderComm rc;
	private EmergencyDataRetriever edr;
	private PollDataRetriever pdr;
	private EmergencyFactory ef;
	
	public Simulator(ResponderComm rc, List<String> emFromFile, EmergencyDataRetriever edr, PollDataRetriever pdr, EmergencyFactory ef)
	{
		this.pdr = pdr;
		this.rc = rc;
		this.emFromFile = emFromFile;
		this.edr = edr;
		this.ef = ef;
		expectedPollData = new ArrayList<>();
		pollData = new ArrayList<>();
		events = new ArrayList<>();
	}
	
	public void simulation() throws InterruptedException // catch this in main
	{
		long simStartTime = System.currentTimeMillis();  // The time the simulation starts 
		boolean end = false;
		
		logger.info("Simulation Begins");
		while(end == false)
		{
			long elapsedSeconds = ((System.currentTimeMillis() - simStartTime) / 1000); // The Seconds passed since the simulation began.
			
			for(String emergency : emFromFile) 	// Using a for loop to check for emergency in the file against each second
			{
				long timeOfEmergency = edr.getEmergencyTime(emergency);
				if(elapsedSeconds == timeOfEmergency)
				{
					Emergency em = ef.makeEmergency(edr, rc, emergency);
					events.add(em);
					em.sendMessage("normal");
					expectedPollData.add(edr.getEmergencyType(emergency) + " + " + edr.getEmergencyLocation(emergency));
				}
			}
			
			notifyAllObservers(elapsedSeconds);   // Notify all observers that a second has passed.
			List<String> information = rc.poll(); // Get messages from responders
			List<String> receivedCom = pdr.validatePollData(information); //Validate messages from responders
			
			if(!receivedCom.isEmpty())
			{
				end = extractPolledInformation(receivedCom);
			}
			
			checkForPreviousResponses();  //Checking for previously arrived response teams.
			executeEvents();
			Thread.sleep(1000); // Sleep for 1 second		
		}
		logger.info("Simulation Ends");
	}
	
	public void checkForPreviousResponses() /* This method is used to identify and handle cases where response teams arrive before the time of the emergency */
	{
		if(!pollData.isEmpty() && !events.isEmpty())
		{
			SearchAndCheck<String> contString = new SearchAndCheck<>();
			for(Emergency em : events) 
			{	
				boolean contains = contString.contains(pollData, em.toString());
				if(contains == true) { em.setAttended(true); pollData.remove(em.toString()); }
			}
		}
	}
	
	public boolean extractPolledInformation(List<String> receivedCom) // This function is what signals each emergency that a responder team had arrived, left or that the simulation must end. 
	{
		SearchAndCheck<String> contString = ef.parameterizeString(); //Using generic class
		boolean end = contString.contains(receivedCom, "end");
		
		if(end == false)
		{
			for(String com : receivedCom)
			{
				boolean contains = contString.contains(expectedPollData, com);
				if(contains == false) { pollData.add(com); }

				String search = pdr.getPollType(com) + " " + pdr.getPollLocation(com);

				SearchAndCheck<Emergency> contEm = ef.parameterizeEmergency(); // Using generic class
				Emergency e = contEm.searchFor(events, search);
				if(e != null && pdr.getArriveOrLeft(com).equals("+")) 
				{ 
					e.setAttended(true); logger.fine(() -> "Responders Who have Arrived at the Emergency Location :" + com); 
				}
				else if( e != null && pdr.getArriveOrLeft(com).equals("-")) 
				{ 
					e.setAttended(false); // Setting attended as false indicate that the team had left the scene.
					logger.fine(() -> "Responders Who have Left the Emergency Location :" + com); 
				}
			}
			
		}
		return end;
	}
	
	public void executeEvents() // run function basically contains what happens in each emergency per second. run function is what triggers state changes. 
	{
		for(Emergency event : events) 
		{
			event.run();
		}
	}

	public void notifyAllObservers(long elapsedSeconds)
	{
		for(Emergency event : events)
		{
			event.updateTimeElapsed(elapsedSeconds); // Update observers of the elapsedSecond of each emergency
		}
	}

}
