package edu.curtin.emergencysim.fileio;

/*
 * Validation Exceptions are thrown for errors that can occur during validating polled messages or reading input file
 * REFERENCE : https://www.javatpoint.com/custom-exception
 */
 
public class ValidationException extends Exception
{
    public ValidationException(String msg) 
    { 
        super(msg); 
    }
    
    public ValidationException(String msg, Throwable cause) 
    { 
        super(msg, cause); 
    }
}
