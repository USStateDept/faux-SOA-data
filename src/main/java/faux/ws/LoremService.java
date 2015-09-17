package faux.ws;

import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService
public interface LoremService
{
	@XmlElement(name="P")
	public List<String> getText(long id);
}
