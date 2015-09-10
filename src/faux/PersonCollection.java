package faux;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PersonCollection implements Collection<Person>
{

	static List<String> givenNames=new ArrayList<String>();
	static List<String> surNames=new ArrayList<String>();
	
	@Override
	public int size(){return givenNames.size()*surNames.size();}

	@Override
	public boolean containsAll(Collection<?> c){for(Object o:c){if (!contains(o)) return false;}return true;}

	@Override
	public boolean isEmpty(){return false;}

	@Override
	public boolean contains(Object o)
	{
		if (o==null) return false;
		if (!(o instanceof Person)) return false;
		Person p=(Person) o;
		if (!givenNames.contains(p.getGivenName())) return false;
		if (!surNames.contains(p.getSurName())) return false;
		if (p.getMiddleName()!=null) return false;
		return true;
	}

	@Override
	public Iterator<Person> iterator()
	{
		return new PersonIterator();
	}

	private class PersonIterator implements Iterator<Person>
	{
		Iterator<String> x = surNames.iterator();
		Iterator<String> y = givenNames.iterator();

		String xName;
		String yName=y.next();
		
		@Override
		public boolean hasNext()
		{
			return x.hasNext()||y.hasNext();
		}

		@Override
		public Person next()
		{
			if (!x.hasNext())
			{
				yName=y.next();
				x=surNames.iterator();
			}
			xName=x.next();
			Person p = new Person();
			p.setGivenName(yName);
			p.setSurName(xName);
			return p;
		}
		
	}
	
	@Override
	public boolean add(Person e){throw new UnsupportedOperationException();}

	@Override
	public boolean addAll(Collection<? extends Person> c){throw new UnsupportedOperationException();}

	@Override
	public void clear(){throw new UnsupportedOperationException();}

	@Override
	public boolean remove(Object o){throw new UnsupportedOperationException();}

	@Override
	public boolean removeAll(Collection<?> c){throw new UnsupportedOperationException();}

	@Override
	public boolean retainAll(Collection<?> c){throw new UnsupportedOperationException();}

	@Override
	public Object[] toArray(){throw new UnsupportedOperationException();}

	@Override
	public <T> T[] toArray(T[] a){throw new UnsupportedOperationException();}

}
