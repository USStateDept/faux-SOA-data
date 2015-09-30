package faux.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.WeakHashMap;

public class KnowingThreadLocal<T>
{
	WeakHashMap<Thread, T> locals=new WeakHashMap<Thread, T>();
	
	
	public T get()
	{
		return locals.get(Thread.currentThread());
	}
	
	public T set(T obj)
	{
		return locals.put(Thread.currentThread(), obj);
	}
	
	public Collection<T> getAll()
	{
		Collection<T> results=new ArrayList<T>();
		for (Thread t:locals.keySet())
		{
			if (t!=null)
			{
				T result = locals.get(t);
				results.add(result);
			}
		}
		return results;
	}

	
	
}
