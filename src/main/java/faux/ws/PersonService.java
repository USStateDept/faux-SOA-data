package faux.ws;

import java.util.Collection;

import javax.jws.WebService;

import faux.Person;

@WebService
public interface PersonService
{
	public Collection<Person> getPersons();
	public Collection<Person> getPersonsSmaller(int recordLimit);
	public Collection<Person> getADPersons();
	
}
