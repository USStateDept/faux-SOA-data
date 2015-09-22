package faux.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import faux.Person;
import faux.PersonCollection;

@WebService(serviceName="person", targetNamespace="gov.state")
@Component
public class PersonServiceImpl implements PersonService
{
	private static List<String> lnames;
	private static List<String> fnames;

	static
	{
		try
		{
			//http://stackoverflow.com/questions/7785973/loading-files-in-jar-in-tomcat-using-getresourceasstream
			InputStream lin = Thread.currentThread().getContextClassLoader().getResourceAsStream("faux/ws/lnames.txt");
			InputStream fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("faux/ws/fnames.txt");
			
			
			lnames=new FileArray(lin);
			fnames=new FileArray(fin);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			lnames=Arrays.asList(new String[]{e.toString()});
			fnames=lnames;
		}
	}
	
	PersonCollection pc=new PersonCollection(fnames, lnames);

	public Collection<Person> getPersons()
	{
		return pc;
	}

}
