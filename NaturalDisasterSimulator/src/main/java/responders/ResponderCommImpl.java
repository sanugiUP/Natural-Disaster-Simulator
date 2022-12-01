package edu.curtin.emergencysim.responders;

import java.util.*;
import java.util.regex.*;

/**
 * Mock implementation of ResponderComm. Be very, very sure you've understood the point of this
 * class before you start designing or coding anything!
 */
public class ResponderCommImpl implements ResponderComm
{
    /**
     * Convenience class for storing a fake incoming 'poll' message, and the time at which it is 
     * scheduled to be provided.
     */
    private static class REvent
    {
        private long time;
        private String type;
        private boolean arriving;
        private String location;
        
        public REvent(long time, String type, boolean arriving, String location)
        {
            this.time = time;
            this.type = type;
            this.arriving = arriving;
            this.location = location;
        }
    }

    // A regular expression for validating and extracting parts of outgoing ('send') messages.
    private static final Pattern SEND_REGEX = Pattern.compile(
        "(?<emergency>fire|flood|chemical) ((?<status>start|end|low|high)|(?<lossType>casualty|damage|contam) (?<lossCount>[0-9]+)) (?<location>.+)");

    // We'll provide an 'end' message (to shut down the simulation) after this many seconds.
//     private static final long DURATION = 1000;
    private static final long DURATION = 120;
    
    // We need to keep track of time here, to work out what message to return from poll() at which 
    // point.
    private long startTime;
    
    // These are the responder messages that will be retrieved via the poll() method, at their 
    // scheduled times. They must be in chronological order.        
    private List<REvent> events = new LinkedList<>();

    public ResponderCommImpl()
    {
        startTime = System.currentTimeMillis();  
	  events.add(new REvent(3, "chemical", true, "Westown")); 
	  events.add(new REvent(5, "fire", true, "Midtown"));  
	  events.add(new REvent(10, "chemical", false, "Westown"));
	  events.add(new REvent(20, "fire", false, "Midtown"));
	  events.add(new REvent(20, "fire", true, "Easttown"));
	  events.add(new REvent(35, "fire", true, "Midtown"));	  
	  events.add(new REvent(35, "chemical", true, "Northedge"));
	  events.add(new REvent(35, "flood", true, "Midtown"));
        events.add(new REvent(50, "fire", false, "Easttown"));
	  events.add(new REvent(56, "chemical", false, "Northedge"));
	  events.add(new REvent(70, "flood", false, "Midtown"));
	  events.add(new REvent(70, "chemical", true, "Westown"));
	  events.add(new REvent(70, "fire", false, "Midtown"));	

        /* According to this test data, all the emergencies except chemical at
	     Northedge will end. This is because the response team at Northedge leave
	     before the clean up process is over. */  
    }


    /**
     * Return any fake incoming messages scheduled to occur, and which have not already been 
     * provided.
     */
    @Override
    public List<String> poll()
    {
        long elapsedSecs = (System.currentTimeMillis() - startTime) / 1000L;
        if(DURATION <= elapsedSecs)
        {
            return List.of("end");
        }
        else
        {
            List<String> newEvents = new ArrayList<>();            
            while(!events.isEmpty() && events.get(0).time <= elapsedSecs)
            {
                REvent ev = events.remove(0);        
                String msg = String.format(
                    "%s %c %s",
                    ev.type,
                    ev.arriving ? '+' : '-',
                    ev.location);                
                newEvents.add(msg);
            }
            return newEvents;
        }
    }
    
    /**
     * Accepts an outgoing message, validates it, and prints out the information.
     */     
    @Override
    public void send(String s) 
    {
        Matcher m = SEND_REGEX.matcher(s);
        if(!m.matches())
        {
            throw new IllegalArgumentException("Invalid message format: '" + s + "'");
        }
        
        String emergency = m.group("emergency");
        String status = m.group("status");
        String lossType = m.group("lossType");
        String lossCount = m.group("lossCount");
        String location = m.group("location");
                
        System.out.printf("%s at %s: ", emergency, location);
        if(status != null)
        {
            System.out.println(status);
        }
        else
        {
            System.out.printf("%s #%s", lossType, lossCount);
        }
    }
}
