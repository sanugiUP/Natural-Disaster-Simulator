package edu.curtin.emergencysim.observers;

/* This is the Observer Interface implemented by Emergency Class */

public interface TimeObserver 
{
	public void updateTimeElapsed(long elapsedSeconds);  // This method updates the number of seconds since the simulation started in each Emergency object
}
