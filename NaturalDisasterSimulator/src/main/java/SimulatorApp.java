package edu.curtin.emergencysim;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import edu.curtin.emergencysim.miscellaneous.*;
import edu.curtin.emergencysim.responders.*;
import edu.curtin.emergencysim.fileio.*;
import edu.curtin.emergencysim.controller.*;
import edu.curtin.emergencysim.factories.*;

/*
 * REFERENCE PURPOSE	: Inspiration for Logging, Error Handling and Command Line Arguments  
 * AUTHOR 			: Dr David Cooper
 * REFERENCE SOURCE 	: https://github.com/shyam3001/oose-solutions/tree/a1-david
 */

public class SimulatorApp
{
	@SuppressWarnings("PMD.FieldNamingConventions")
   	private static final Logger logger = Logger.getLogger(SimulatorApp.class.getName());
    
	public static void main(String[] args)
	{
		String fileName;
		System.out.println(); //Creating space
        	Scanner scanner = new Scanner(System.in); //NOPMD
        	
		if(args.length == 1) // When Filename is provided as a command line argument
		{
			fileName = args[0];
		}
		else // When Filename is not provided as a command line argument, requesting it from the command line. 
		{
			System.out.println("Please Enter the File Name : ");
			fileName= scanner.nextLine();
			
		}
		scanner.close(); // Closing the scanner after reading the file name
		 
		ResponderCommImpl rc = new ResponderCommImpl();
		EmergencyDataRetriever edr = new EmergencyDataRetriever();
		PollDataRetriever pdr = new PollDataRetriever();
		try
		{
			EmergencyFactory eFactory = new EmergencyFactory();
			FileIO fileOperations = new FileIO(fileName);
			fileOperations.readFile();
			logger.info("Reading Input File");
			List<String> emFromFile = fileOperations.getFileData(); // Emergencies Extracted From File
			logger.fine(() -> "Data From Input File : " + emFromFile);
		
			Simulator sim = new Simulator(rc, emFromFile, edr, pdr, eFactory);
			sim.simulation();
		}
		catch(InterruptedException ie)
		{
			System.out.println(ie.getMessage());
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File : " + fileName + " Specified Cannot Be Found!");
			logger.info("Input File Cannot Be Found");
		}
		catch (ValidationException ve)
		{
			System.out.println("Simulation Failed to Proceed! " + ve.getMessage());	
		}		
	}
}
