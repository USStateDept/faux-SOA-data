package faux.ws;

import java.util.logging.Level;

import javax.jws.WebService;

import lombok.extern.java.Log;

import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.stereotype.Component;

@WebService(serviceName = "echo", targetNamespace = "gov.state")
@Component
//@EndpointProperties({@EndpointProperty(key="faultStackTraceEnabled",value="true"),@EndpointProperty(key="exceptionMessageCauseEnabled",value="true")})
@InInterceptors (interceptors = {"faux.ws.Int" })
@Log
public class EchoImpl implements Echo
{

	@Override
	public String echoText(String msg)
	{
		log.log(Level.INFO,"msg={0}",msg);
		return msg;
	}

	@Override
	public Object echoXML(Object msg)
	{
		log.log(Level.INFO,"msg={0}",msg);
		//http://stackoverflow.com/questions/17018714/what-is-the-simplest-way-to-extract-an-xml-node-for-jaxb-unmarshal
		//http://docs.oracle.com/javaee/6/tutorial/doc/bnazc.html
		//http://stackoverflow.com/questions/4212608/getting-raw-xml-parameter-in-jax-ws-webservice-method
		//http://stackoverflow.com/questions/22450051/how-do-i-drop-an-inbound-xml-element-with-a-transform-in-cxf
		return msg;
	}
	
}
