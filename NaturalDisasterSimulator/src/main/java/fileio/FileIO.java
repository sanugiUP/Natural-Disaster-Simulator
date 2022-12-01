package edu.curtin.emergencysim.fileio;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileIO 
{
	private List<String> fileContent;
	private String fileName;
	
	public FileIO(String fileName)
	{
		fileContent = new ArrayList<>();
		this.fileName = fileName;
	}
	
	public void readFile() throws FileNotFoundException
	{	
	    	File file = new File(fileName);
	    	Scanner scanner = new Scanner(file); //NOPMD
	    	while(scanner.hasNextLine()) 
	    	{
	    		String data = scanner.nextLine();
	    		boolean correctFormat = checkIfCorrrectFormat(data);
	        	boolean contains = checkIfContains(data);
	        
		    	if(correctFormat == true && contains == false) //ignore file entries that doesn't match the format or is repetitive. 
		    	{
		    		fileContent.add(data);
		    	} 
	    	}
	    	scanner.close();
	}
	
	public boolean checkIfCorrrectFormat(String data)
	{
		boolean correct = false;
		Pattern format = Pattern.compile("[0-9]+ (fire|flood|chemical) .+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = format.matcher(data); 
		
		 if(matcher.find()) //ignore file entries that doesn't match the format.  
		 {
			 correct = true;
		 } 
		 return correct;
	}
	
	public boolean checkIfContains(String data)
	{
		boolean contains = false;
		int spaceIdx = data.indexOf(" ");
        	String uniqueData = data.substring(spaceIdx + 1);
 
        	for(String i : fileContent) //Check for repetitive file entries.
		{
			if(i.contains(uniqueData))
			{
				contains = true;
			}
		}  
        	return contains;
	}
	
	public List<String> getFileData() throws ValidationException
	{
		if(fileContent.isEmpty()) 
		{
			throw new ValidationException("Input File Does Not Contain Any Valid Entries! Simulation Ends!\n"); 
		}
		return fileContent; 
	}
	
}