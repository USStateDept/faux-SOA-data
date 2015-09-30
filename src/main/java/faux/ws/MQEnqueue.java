package faux.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface MQEnqueue
{
	public void enqueue(@WebParam(name="docId") long docid);
}
