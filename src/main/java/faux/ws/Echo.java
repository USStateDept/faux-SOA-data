package faux.ws;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.transform.Source;

import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Node;

@WebService
public interface Echo
{
	public String echoText(@WebParam(name="message") String msg);

	public String echoXML(@WebParam(name="message") String msg);
}
