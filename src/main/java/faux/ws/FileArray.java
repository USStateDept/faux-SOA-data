package faux.ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FileArray implements List<String>
{
	private final List<String> cache;

	public FileArray(String resource) throws IOException
	{
		this(FileArray.class.getClass().getClassLoader(),resource);
	}

	private FileArray(ClassLoader classLoader, String resource) throws IOException
	{
		this(classLoader==null?ClassLoader.getSystemClassLoader().getResourceAsStream(resource):classLoader.getResourceAsStream(resource));
	}

	public FileArray(InputStream in) throws IOException
	{
		if (in==null) throw new NullPointerException();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		ArrayList<String> mycache = new ArrayList<String>();
		while ((line=br.readLine())!=null)
			mycache.add(line);
		cache=Collections.unmodifiableList(mycache);
	}

	@Override
	public boolean add(String arg0){throw new UnsupportedOperationException();}

	@Override
	public void add(int arg0, String arg1){throw new UnsupportedOperationException();}

	@Override
	public boolean addAll(Collection<? extends String> arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean addAll(int arg0, Collection<? extends String> arg1){throw new UnsupportedOperationException();}

	@Override
	public void clear(){throw new UnsupportedOperationException();}

	@Override
	public boolean contains(Object arg0)
	{
		return cache.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return cache.containsAll(arg0);
	}

	@Override
	public String get(int arg0)
	{
		return cache.get(arg0);
	}

	@Override
	public int indexOf(Object arg0)
	{
		return cache.indexOf(arg0);
	}

	@Override
	public boolean isEmpty()
	{
		return cache.isEmpty();
	}

	@Override
	public Iterator<String> iterator()
	{
		return cache.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0)
	{
		return cache.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<String> listIterator()
	{
		return cache.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(int arg0)
	{
		return cache.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0){throw new UnsupportedOperationException();}

	@Override
	public String remove(int arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean removeAll(Collection<?> arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean retainAll(Collection<?> arg0){throw new UnsupportedOperationException();}

	@Override
	public String set(int arg0, String arg1){throw new UnsupportedOperationException();}

	@Override
	public int size()
	{
		return cache.size();
	}

	@Override
	public List<String> subList(int arg0, int arg1)
	{
		return cache.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray()
	{
		return cache.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return cache.toArray(arg0);
	}

}
