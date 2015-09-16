package faux.ws;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import faux.Person;
import faux.PersonCollection;

@WebService(serviceName="person", targetNamespace="gov.state")
public class PersonServiceImpl implements PersonService
{
	private static String[] lnamesa={"Pyeron", "Li", "Smith", "Klien", "Barker"};
	private static String[] fnamesa={"Jason", "Joseph", "Ning", "Bob"};
	
	private static List<String> lnames=Arrays.asList(lnamesa);
	private static List<String> fnames=Arrays.asList(fnamesa);
	
	PersonCollection pc=new PersonCollection(fnames, lnames);

	public Collection<Person> getPersons()
	{
		return pc;
	}
}
