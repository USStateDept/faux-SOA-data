package faux.test;

import java.util.Arrays;
import java.util.List;

import faux.Person;
import faux.PersonCollection;

public class AllPeople
{
	public static void main(String[] args)
	{
		List<String> lnames=Arrays.asList(new String[]{"Pyeron", "Li", "Smith", "Klien", "Barker"});
		List<String> fnames = Arrays.asList(new String[]{"MJason", "MJoseph", "FNing", "MBob", "FMary-Lou"});
		PersonCollection pc=new PersonCollection(fnames, lnames);
		
		for (Person p:pc)
		{
			System.out.println(p);
		}
	}
}
