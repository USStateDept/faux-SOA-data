package faux.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import faux.Person;

@WebService(serviceName = "adperson", targetNamespace = "gov.state")
@Component
public class ADPersonServiceImpl implements ADPersonService
{
	final static List<Person> adUSers;

	static
	{
		List<Person> x;
		try
		{
			//http://stackoverflow.com/questions/7785973/loading-files-in-jar-in-tomcat-using-getresourceasstream
			InputStream adin = Thread.currentThread().getContextClassLoader().getResourceAsStream("faux/ws/users7500.txt");
			x= new PersonFileArray(adin);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			String s = e.toString();
			Person p = new Person();
			p.setGender(true);
			p.setSurName(s);
			p.setGivenName(s);
			x=Arrays.asList(new Person[]{p});
		}
		adUSers=x;
	}

	
	public Collection<Person> getADPersons()
	{
		return adUSers;
	}

}
