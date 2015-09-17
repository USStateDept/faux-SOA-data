package faux.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import net._01001111.text.LoremIpsum;

import org.springframework.stereotype.Component;

@WebService(serviceName="lorem", targetNamespace="gov.state")
@Component
public class LoremServiceImpl implements LoremService
{
	public List<String> getText(long seed)
	{
		Random r=new Random(seed);
		LoremIpsum li=new LoremIpsum(r);
		int p = 3+r.nextInt(4);
		List<String> res=new ArrayList<String>(p);
		boolean useStandard=true;
		while (p-- > 0) {
			res.add(li.paragraph(useStandard));
			useStandard = false;
		}
		return res;
	}
	
	public static void main(String[] args)
	{
		LoremServiceImpl lsi = new LoremServiceImpl();
		System.out.println(lsi.getText(6l));
	}
}
