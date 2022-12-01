package edu.curtin.emergencysim.miscellaneous;

/*This class is responsible for breaking-down a single emergency entry from the input file into parts (Time, Type and Location)*/

public class EmergencyDataRetriever 
{	
	public long getEmergencyTime(String emergencyData)
	{
		int spaceIdx = emergencyData.indexOf(" ");
		String time = emergencyData.substring(0, spaceIdx);
		return Long.parseLong(time);
	}
	
	public String getEmergencyLocation(String emergencyData)
	{
		int spaceIdx = emergencyData.indexOf(" ");
		String uniqueData = emergencyData.substring(spaceIdx + 1);
		int locationIdx = uniqueData.indexOf(" ");
		String location = uniqueData.substring(locationIdx + 1);
		return location;
	}
	
	public String getEmergencyType(String emergencyData)
	{
		int spaceIdx = emergencyData.indexOf(" ");
		String uniqueData = emergencyData.substring(spaceIdx + 1);
		int typeIdx = uniqueData.indexOf(" ");
		String type = uniqueData.substring(0, typeIdx);
		return type;
	}
	
}
