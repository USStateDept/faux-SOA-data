package faux.ws;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;

import faux.Person;
import faux.PersonCollection;

@WebService(serviceName="person", targetNamespace="gov.state")
public class PersonServiceImpl implements PersonService
{
	List<String> lnames=Arrays.asList(new String[]{"Pyeron", "Li", "Smith", "Klien", "Barker"});
	List<String> fnames = Arrays.asList(new String[]{"Jason", "Joseph", "Ning", "Bob"});
	PersonCollection pc=new PersonCollection(fnames, lnames);

	public Collection<Person> getPersons()
	{
		return pc;
	}
}
