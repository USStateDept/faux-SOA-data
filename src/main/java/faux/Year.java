package faux;

import java.util.Calendar;

import java.util.Date;

public class Year
{

	public static Date makeDOB(byte[] one2six)
	{

		int i = 0;
		// i=i<<4 | ((int)(one2six[0]>>4) & 0x0f);
		// i=i<<4 | ((int)(one2six[0]>>0) & 0x0f);
		i = i << 8 | ((int) (one2six[0]) & 0xff);
		// i=i<<4 | ((int)(one2six[1]>>4) & 0x0f);
		// i=i<<4 | ((int)(one2six[1]>>0) & 0x0f);
		i = i << 8 | ((int) (one2six[1]) & 0xff);
		int d = 0;
		d = d << 4 | ((int) (one2six[1] >> 0) & 0x0f);
		d = d << 8 | ((int) (one2six[2]) & 0xff);
		// d=d<<4 | ((int)(one2six[2]>>4) & 0x0f);
		// d=d<<4 | ((int)(one2six[2]>>0) & 0x0f);
		return makeDOB(i, d);
	}

	public static Date makeDOB(int i, int d)
	{
		d += 238;
//		System.out.println("i:" + i + ", d:" + d);
		int a = 2015 - age(i);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, a);
		c.set(Calendar.DAY_OF_YEAR, d % 365);
		return c.getTime();
	}

	public static void main(String[] args)
	{
		int i = 0x344c;
		int d = 0xc9e + 238;
		System.out.println("i:" + i + ", d:" + d);
		int a;
		// for (int i=0; i<=65535; ++i)
		{
			a = age(i);
			// if(i%180==0 || i>65500)
			System.out.println(i + "\t" + (2015 - a));
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2015 - a);
		c.set(Calendar.DAY_OF_YEAR, d % 365);
		System.out.println(c.getTime());
	}

	private static int age(int i)
	{
		i = (i + 9000) % 65536;
		if (i <= 0xB147)
		{
			return normalAge(i);
		}
		else
		{
			return oldAge(i);
		}
	}

	private static int oldAge(int i)
	{
		if (i <= 61333)
		{
			// <81
			if (i <= 55558)
			{
				// <71
				if (i <= 50913)
				{
					// <66
					if (i <= 46366) return 60;
					if (i <= 47325) return 61;
					if (i <= 48259) return 62;
					if (i <= 49168) return 63;
					if (i <= 50053) return 64;
					return 65;
				}
				else
				{
					// >=66
					if (i <= 51748) return 66;
					if (i <= 52559) return 67;
					if (i <= 53346) return 68;
					if (i <= 54108) return 69;
					return 70;
				}
			}
			else
			{
				// >=71
				if (i <= 58163)
				{
					// <76
					if (i <= 55558) return 71;
					if (i <= 56246) return 72;
					if (i <= 56909) return 73;
					if (i <= 57548) return 74;
					return 75;
				}
				else
				{
					// >=76
					if (i <= 58753) return 76;
					if (i <= 59318) return 77;
					if (i <= 59858) return 78;
					if (i <= 60375) return 79;
					return 80;
				}
			}
		}
		else
		{
			// >=81
			if (i <= 64430)
			{
				// <91
				if (i <= 62955)
				{
					// <86
					if (i <= 61333) return 81;
					if (i <= 61775) return 82;
					if (i <= 62193) return 83;
					if (i <= 62586) return 84;
					return 85;
				}
				else
				{
					// >=86
					if (i <= 63299) return 86;
					if (i <= 63619) return 87;
					if (i <= 63913) return 88;
					if (i <= 64184) return 89;
					return 90;
				}
			}
			else
			{
				// >=91
				if (i <= 65290)
				{
					// <96
					if (i <= 64651) return 91;
					if (i <= 64847) return 92;
					if (i <= 65019) return 93;
					if (i <= 65167) return 94;
					return 95;
				}
				else
				{
					// >=96
					if (i <= 65388) return 96;
					if (i <= 65462) return 97;
					if (i <= 65511) return 98;
					if (i <= 65534) return 99;
					return 100;
				}
			}
		}
	}

	private static int normalAge(int i)
	{
		return 44 * i / 45384 + 16;
	}

}
