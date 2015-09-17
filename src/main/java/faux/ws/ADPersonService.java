package faux.ws;

import java.util.Collection;

import javax.jws.WebService;

import faux.Person;

@WebService
public interface ADPersonService
{
	public Collection<Person> getADPersons();
}
