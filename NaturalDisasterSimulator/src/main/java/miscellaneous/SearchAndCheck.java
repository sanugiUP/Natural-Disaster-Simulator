package edu.curtin.emergencysim.miscellaneous;
import java.util.*;

public class SearchAndCheck<T>
{
	private boolean contains;
	private T found;
	
	public boolean contains(Collection<T> collection, T searchFor)
	{
		if(collection.contains(searchFor)) {contains = true;}
		else { contains = false;}
		return contains;
	}
	
	public T searchFor(Collection<T> collection, String searchFor)
	{
		for(T t : collection)
		{
			if(t.toString().equals(searchFor)) { found = t; }
		}
		return found;
	}
}