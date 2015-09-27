package faux.ws;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.wsdl.extensions.http.HTTPAddress;

import lombok.extern.java.Log;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

@Log
public class Int extends AbstractPhaseInterceptor<Message>
{
	public Int()
	{
		super(Phase.UNMARSHAL);
		log.log(Level.INFO,"Constructed");
	}

	@Override
	public void handleMessage(Message message) throws Fault
	{
		log.log(Level.INFO,"message={0}",message);
		
		//Sep 27, 2015 8:27:43 AM faux.ws.Int handleMessage
		//INFO: message={
		//org.apache.cxf.message.MessageFIXED_PARAMETER_ORDER=false, 
		//http.base.path=http://tomcat.poc.statedeptproject.com/faux-0.0.1-SNAPSHOT, 
		//HTTP.REQUEST=org.apache.catalina.connector.RequestFacade@5927e0e2, 
		//org.apache.cxf.transport.Destination=org.apache.cxf.transport.servlet.ServletDestination@36e19ac2, 
		//HTTP.CONFIG=org.apache.catalina.core.StandardWrapperFacade@4678b9c, 
		//org.apache.cxf.binding.soap.SoapVersion=org.apache.cxf.binding.soap.Soap11@1bac0a76, 
		//org.apache.cxf.message.Message.QUERY_STRING=null, 
		//org.apache.cxf.message.Message.ENCODING=UTF-8, 
		//HTTP.CONTEXT=org.apache.catalina.core.ApplicationContextFacade@7d6ac911, 
		//Content-Type=text/xml;charset=UTF-8, 
		//org.apache.cxf.security.SecurityContext=org.apache.cxf.transport.http.AbstractHTTPDestination$2@3d0b5fd5, 
		//org.apache.cxf.message.Message.PROTOCOL_HEADERS=
		//{
		// accept-encoding=[gzip,deflate],
		// Cache-Control=[max-age=259200], 
		// connection=[keep-alive], 
		// Content-Length=[298], 
		// content-type=[text/xml;charset=UTF-8], 
		// host=[tomcat.poc.statedeptproject.com], 
		// SOAPAction=[""], 
		// user-agent=[Apache-HttpClient/4.1.1 (java 1.5)], 
		// Via=[1.1 gateway.1.internal.pdinc.us:3128 (squid/2.6.STABLE21)], 
		// X-Forwarded-For=[192.168.4.12]}, 
		// org.apache.cxf.request.url=http://tomcat.poc.statedeptproject.com/faux-0.0.1-SNAPSHOT/cxf/services/echo, 
		// Accept=null, 
		// org.apache.cxf.request.uri=/faux-0.0.1-SNAPSHOT/cxf/services/echo, 
		// org.apache.cxf.message.Message.PATH_INFO=/faux-0.0.1-SNAPSHOT/cxf/services/echo, 
		// org.apache.cxf.transport.https.CertConstraints=null, 
		// HTTP.RESPONSE=org.apache.catalina.connector.ResponseFacade@7fe423ef, 
		// org.apache.cxf.headers.Header.list=[], 
		// org.apache.cxf.request.method=POST, 
		// org.apache.cxf.async.post.response.dispatch=true, 
		// org.apache.cxf.message.Message.IN_INTERCEPTORS=[org.apache.cxf.transport.https.CertConstraintsInterceptor@7334275a], 
		// HTTP_CONTEXT_MATCH_STRATEGY=stem, 
		// http.service.redirection=null, 
		// org.apache.cxf.message.Message.BASE_PATH=/faux-0.0.1-SNAPSHOT/cxf/services/echo, 
		// org.apache.cxf.configuration.security.AuthorizationPolicy=null
		//}
		
		HttpServletRequest req=(HttpServletRequest) message.get("HTTP.REQUEST");
		log.log(Level.INFO,"request={0}",req);

		
		
	}

}
