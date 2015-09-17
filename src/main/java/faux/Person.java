package faux;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Person
{
	public static void main(String[] args)
	{
		Person p = new Person();
		p.setGivenName("Jason");
		p.setSurName("Pyeron");
		System.out.println(p.getDob());
		System.out.println(p.getSsn());
		System.out.println(BigInteger.probablePrime(512, p.getRandom()));
	}

	boolean gender;
	static MessageDigest md;
	String givenName;
	String surName;
	String middleName;
	String ssn = null;
	String username=null;
	String email=null;
	
	public String getUsername()
	{
		if (username==null)
		{
			String pend=getGivenName()+"."+getSurName()+".test";
			pend=pend.toLowerCase();
			username=pend.replaceAll("[^a-z0-9\\.]", "");			
		}
		return username;
	}

	public String getEmail()
	{
		if (email==null)
		{
			email=getUsername()+"@ad.poc.local";
		}
		return email;
	}

	public Random getRandom()
	{
		hash();
		return new FauxRandom(hash);
	}

	public String getSsn()
	{
		if (ssn == null)
		{
			hash();
			ssn = makeSSN(hash);
		}
		return ssn;
	}

	private String makeSSN(byte[] seven2fourteen)
	{
		int s = 0;
		s = s << 8 | ((int) (seven2fourteen[3]) & 0xff);
		s = s << 8 | ((int) (seven2fourteen[4]) & 0xff);
		s = s << 8 | ((int) (seven2fourteen[5]) & 0xff);
		s = s << 8 | ((int) (seven2fourteen[6]) & 0xff);
		s&=0x7fFFffFF;
		String c = ""+ s % 10000;
		s /= 10000;
		String b = ""+ s % 100;
		s /= 100;
		String a = ""+ s % 1000;
		while (a.length()<3) a="0"+a;
		while (b.length()<2) b="0"+b;
		while (c.length()<4) c="0"+c;

		return a + "-" + b + "-" + c;
	}

	byte[] hash = null;

	void hash()
	{
		if (hash != null) return;
		try
		{
			if (md == null)
			{
				md = MessageDigest.getInstance("SHA-1");
			}
			md.reset();
			StringBuilder sb = new StringBuilder();
			if (givenName != null) sb.append(givenName.toLowerCase());
			if (middleName != null && sb.length() > 0)
			{
				sb.append(" ");
				sb.append(middleName.toLowerCase());
			}
			if (surName != null && sb.length() > 0)
			{
				sb.append(" ");
				sb.append(surName.toLowerCase());
			}
			md.update(sb.toString().getBytes("utf8"));
			hash = md.digest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			hash = new byte[]
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		}
	}

	Date dob = null;

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
		hash = null;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
		hash = null;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
		hash = null;
	}

	public Date getDob()
	{
		if (dob == null)
		{
			hash();
			dob = Year.makeDOB(hash);
		}
		return dob;
	}
}
