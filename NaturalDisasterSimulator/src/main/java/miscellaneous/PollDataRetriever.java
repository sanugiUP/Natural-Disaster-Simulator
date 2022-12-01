package edu.curtin.emergencysim.miscellaneous;

import java.util.*;
import java.util.regex.*;
import edu.curtin.emergencysim.observers.*;

/*This class is responsible for breaking-down polled entries from responderCommImpl poll() method*/

public class PollDataRetriever 
{
	public String getPollType(String pollData)
	{
		int spaceIdx = pollData.indexOf(" ");
		String type = pollData.substring(0, spaceIdx);
		return type;
	}
	
	public String getArriveOrLeft(String pollData)
	{
		int spaceIdx = pollData.indexOf(" ");
		String uniqueData = pollData.substring(spaceIdx + 1);
		int eventIdx = uniqueData.indexOf(" ");
		String event = uniqueData.substring(0, eventIdx);
		return event;
	}
	
	public String getPollLocation(String pollData)
	{
		int spaceIdx = pollData.indexOf(" ");
		String uniqueData = pollData.substring(spaceIdx + 1);
		int locationIdx = uniqueData.indexOf(" ");
		String location = uniqueData.substring(locationIdx + 1);
		return location;
	}
	
	public List<String> validatePollData(List<String> pollData)
	{
		List<String> newPollData = new ArrayList<>();
		Pattern format = Pattern.compile("end|(fire|flood|chemical) [+-] .+", Pattern.CASE_INSENSITIVE);
		
		for(String data : pollData)
		{
			Matcher matcher = format.matcher(data);
			if(matcher.find()) // Ignore polled entries that doesn't match the format.  
			{
				newPollData.add(data);
			}
		}
		return newPollData;
	}

	public boolean extractPolledInformation(List<String> receivedCom, List<String> expectedPollData, List<Emergency> events, List<String> pollData)
	{
		boolean end = false;

		if(receivedCom.contains("end"))
		{
			end = true;
		}
		else 
		{
			for(String com : receivedCom)
			{
				if(!expectedPollData.contains(com))
				{
					pollData.add(com);
				}
				
				for(Emergency e : events) 
				{
					if(e.getLocation().equals(getPollLocation(com)) && e.getType().equals(getPollType(com)) && getArriveOrLeft(com).equals("+")) 
					{
						e.setAttended(true);
					}
					else if(e.getLocation().equals(getPollLocation(com)) && e.getType().equals(getPollType(com)) && getArriveOrLeft(com).equals("-"))
					{
						e.setAttended(false);
					}
				}
			}
		}
		return end;
	}
}
