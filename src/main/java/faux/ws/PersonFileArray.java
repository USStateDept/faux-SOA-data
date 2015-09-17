package faux.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import faux.Person;

public class PersonFileArray implements List<Person>
{
	private final List<Person> cache;

	public PersonFileArray(String resource) throws IOException
	{
		this(PersonFileArray.class.getClass().getClassLoader(),resource);
	}

	private PersonFileArray(ClassLoader classLoader, String resource) throws IOException
	{
		this(classLoader==null?ClassLoader.getSystemClassLoader().getResourceAsStream(resource):classLoader.getResourceAsStream(resource));
	}

	public PersonFileArray(InputStream in) throws IOException
	{
		if (in==null) throw new NullPointerException();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		ArrayList<Person> mycache = new ArrayList<Person>();
		String[] parts;
		while ((line=br.readLine())!=null)
		{
			parts=line.split("\t");
			Person p = new Person();
			p.setGivenName(parts[0]);
			p.setSurName(parts[1]);
			p.setGender("M".equalsIgnoreCase(parts[2]));
			mycache.add(p);
		}
		cache=Collections.unmodifiableList(mycache);
	}

	@Override
	public boolean add(Person arg0){throw new UnsupportedOperationException();}

	@Override
	public void add(int arg0, Person arg1){throw new UnsupportedOperationException();}

	@Override
	public boolean addAll(Collection<? extends Person > arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean addAll(int arg0, Collection<? extends Person > arg1){throw new UnsupportedOperationException();}

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
	public Person get(int arg0)
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
	public Iterator<Person > iterator()
	{
		return cache.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0)
	{
		return cache.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<Person > listIterator()
	{
		return cache.listIterator();
	}

	@Override
	public ListIterator<Person > listIterator(int arg0)
	{
		return cache.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0){throw new UnsupportedOperationException();}

	@Override
	public Person remove(int arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean removeAll(Collection<?> arg0){throw new UnsupportedOperationException();}

	@Override
	public boolean retainAll(Collection<?> arg0){throw new UnsupportedOperationException();}

	@Override
	public Person set(int arg0, Person arg1){throw new UnsupportedOperationException();}

	@Override
	public int size()
	{
		return cache.size();
	}

	@Override
	public List<Person > subList(int arg0, int arg1)
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
