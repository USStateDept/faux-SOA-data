package faux.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface Echo
{
	public String echoText(@WebParam(name="message") String msg);

	public Object echoXML(@WebParam(name="message") Object msg);
}
