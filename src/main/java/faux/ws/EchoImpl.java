package faux.ws;

import java.util.logging.Level;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Use;

import lombok.extern.java.Log;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.stereotype.Component;

@WebService(serviceName = "echo", targetNamespace = "gov.state")
@Component
@EndpointProperties(
		{
			@EndpointProperty(key="soap.force.doclit.bare",value="true"),
			@EndpointProperty(key="soap.no.validate.parts",value="true"),
			@EndpointProperty(key="faultStackTraceEnabled",value="true"),
			@EndpointProperty(key="exceptionMessageCauseEnabled",value="true")
		}
	)
@InInterceptors (interceptors = {"faux.ws.Int"})

@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE, use=Use.ENCODED)
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
	public String echoXML(String msg)
	{
		log.log(Level.INFO,"msg={0}",msg);
		//http://stackoverflow.com/questions/17018714/what-is-the-simplest-way-to-extract-an-xml-node-for-jaxb-unmarshal
		//http://docs.oracle.com/javaee/6/tutorial/doc/bnazc.html
		//http://stackoverflow.com/questions/4212608/getting-raw-xml-parameter-in-jax-ws-webservice-method
		//http://stackoverflow.com/questions/22450051/how-do-i-drop-an-inbound-xml-element-with-a-transform-in-cxf
		//http://stackoverflow.com/questions/6053019/wsdl-file-for-apache-cxf-with-any-xml-body
		
		//log.log(Level.INFO,"msg as xml:\n{0}",nodeToString(msg));
		
		//return new DOMSource(msg);
		return msg;
	}
	
}
