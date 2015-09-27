package faux.ws;

import java.util.logging.Level;

import javax.xml.transform.Source;
import javax.xml.ws.Provider;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;

import lombok.extern.java.Log;

import org.springframework.stereotype.Component;

@Component
@WebServiceProvider(serviceName = "echoXml2", portName = "EchoPort", /*wsdlLocation="echoxml2",*/ targetNamespace="gov.state")
@ServiceMode(value = Mode.PAYLOAD)
@Log
public class EchoXMLImpl implements Provider<Source>
{

	//http://stackoverflow.com/questions/20386220/spring-autowiring-webserviceprovider
	//http://mail-archives.apache.org/mod_mbox/cxf-users/200910.mbox/%3C9bbf67fa0910090227w5f755f1y233b0e4f50232088@mail.gmail.com%3E
	static
	{
		log.log(Level.WARNING,"Loaded class: {0}",EchoXMLImpl.class);
	}

	public EchoXMLImpl()
	{
		log.log(Level.WARNING,"instantiated class: {0}",this);
	}
	
	public Source invoke(Source request) throws WebServiceException
	{
		// just echo back
		return request;
	}
}