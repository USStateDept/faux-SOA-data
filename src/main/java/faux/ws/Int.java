package faux.ws;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;

import lombok.extern.java.Log;

import org.apache.cxf.annotations.SchemaValidation.SchemaValidationType;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;

@Log
public class Int extends AbstractPhaseInterceptor<Message>
{
	public Int()
	{
		super(Phase.UNMARSHAL);
		log.log(Level.INFO,"Constructed");
	}

	
    private DepthXMLStreamReader getXMLStreamReader(Message message) {
        XMLStreamReader xr = message.getContent(XMLStreamReader.class);
        if (xr == null) {
            return null;
        }
        if (xr instanceof DepthXMLStreamReader) {
            return (DepthXMLStreamReader) xr;
        }
        DepthXMLStreamReader dr = new DepthXMLStreamReader(xr);
        message.setContent(XMLStreamReader.class, dr);
        return dr;
    }

    private MessageInfo getMessageInfo(Message message, BindingOperationInfo operation, boolean requestor) {
        MessageInfo msgInfo;
        OperationInfo intfOp = operation.getOperationInfo();
        if (requestor) {
            msgInfo = intfOp.getOutput();
            message.put(MessageInfo.class, intfOp.getOutput());
        } else {
            msgInfo = intfOp.getInput();
            message.put(MessageInfo.class, intfOp.getInput());
        }
        return msgInfo;
    }

    
    /**
     * Returns a BindingOperationInfo if the operation is indentified as 
     * a wrapped method,  return null if it is not a wrapped method 
     * (i.e., it is a bare method)
     * 
     * @param exchange
     * @param name
     * @param client
     */
	private BindingOperationInfo supergetBindingOperationInfo(Exchange exchange, QName name, boolean client)
	{
		String local = name.getLocalPart();
		if (client && local.endsWith("Response"))
		{
			local = local.substring(0, local.length() - 8);
		}

		// TODO: Allow overridden methods.
		BindingOperationInfo bop = ServiceModelUtil.getOperation(exchange, local);

		if (bop != null)
		{
			exchange.put(BindingOperationInfo.class, bop);
		}
		return bop;
	}
    
	private BindingOperationInfo getBindingOperationInfo(Exchange exchange, QName name, boolean client)
	{
		BindingOperationInfo bop = ServiceModelUtil.getOperationForWrapperElement(exchange, name, client);
		if (bop == null)
		{
			bop = supergetBindingOperationInfo(exchange, name, client);
		}

		if (bop != null)
		{
			exchange.put(BindingOperationInfo.class, bop);
		}
		return bop;
	}
    
    private void setOperationSchemaValidation(Message message) {
        SchemaValidationType validationType = ServiceUtils.getSchemaValidationType(message);
        message.put(Message.SCHEMA_VALIDATION_ENABLED, validationType);
    }
    

	private MessageInfo setMessage(Message message, BindingOperationInfo operation, boolean requestor,
			ServiceInfo si, MessageInfo msgInfo)
	{
		message.put(MessageInfo.class, msgInfo);

		Exchange ex = message.getExchange();

		ex.put(BindingOperationInfo.class, operation);
		ex.setOneWay(operation.getOperationInfo().isOneWay());

		// Set standard MessageContext properties required by JAX_WS, but not
		// specific to JAX_WS.
		boolean synthetic = Boolean.TRUE.equals(operation.getProperty("operation.is.synthetic"));
		if (!synthetic)
		{
			message.put(Message.WSDL_OPERATION, operation.getName());
		}

		// configure endpoint and operation level schema validation
		setOperationSchemaValidation(message);

		QName serviceQName = si.getName();
		message.put(Message.WSDL_SERVICE, serviceQName);

		QName interfaceQName = si.getInterface().getName();
		message.put(Message.WSDL_INTERFACE, interfaceQName);

		EndpointInfo endpointInfo = ex.getEndpoint().getEndpointInfo();
		QName portQName = endpointInfo.getName();
		message.put(Message.WSDL_PORT, portQName);

		URI wsdlDescription = endpointInfo.getProperty("URI", URI.class);
		if (wsdlDescription == null)
		{
			String address = endpointInfo.getAddress();
			try
			{
				wsdlDescription = new URI(address + "?wsdl");
			}
			catch (URISyntaxException e)
			{
				// do nothing
			}
			endpointInfo.setProperty("URI", wsdlDescription);
		}
		message.put(Message.WSDL_DESCRIPTION, wsdlDescription);

		return msgInfo;
	}

	private MessageInfo setMessage(Message message, BindingOperationInfo operation, boolean requestor, ServiceInfo si)
	{
		MessageInfo msgInfo = getMessageInfo(message, operation, requestor);
		return setMessage(message, operation, requestor, si, msgInfo);
	}
	
	
	@Override
	public void handleMessage(Message message) throws Fault
	{
		log.log(Level.FINE,"message={0}",message);
		
		InterceptorChain ic = message.getInterceptorChain();
		for (Interceptor<? extends Message> i:ic)
		{
			log.log(Level.FINE,"Interceptor<? extends Message> i={0}",i);
			if (i!=null && i instanceof DocLiteralInInterceptor)
			{
				log.log(Level.FINE,"FOUND IT!!!!!!!!!!!");
				ic.remove(i);				
			}
		}
		
		
		for (Class<?> c:message.getContentFormats())
		{
			log.log(Level.FINE,"Format:{0}",c);
		}
		
		
/*
		InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            CachedOutputStream bos = new CachedOutputStream();
            try {
                IOUtils.copy(is, bos);

                bos.flush();
                is.close();
                message.setContent(InputStream.class, bos.getInputStream());
                bos.close();
                String soapMessage = new String(bos.getBytes());
                
                log.log(Level.INFO,"SOAP:\n{0}",soapMessage);
                
            } catch (IOException e) {
                throw new Fault(e);
            }
        }
		
*/		
        if (isGET(message) && message.getContent(List.class) != null) {
            log.log(Level.FINE, "BareInInterceptor skipped in HTTP GET method");
            return;
        }

        DepthXMLStreamReader xmlReader = getXMLStreamReader(message);
//        DataReader<XMLStreamReader> dr = getDataReader(message);
        MessageContentsList parameters = new MessageContentsList();

        Source source;
        String xml;
		try
		{
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			source = new StAXSource(xmlReader);
			StringWriter sw = new StringWriter();
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter;
			xmlStreamWriter = outputFactory.createXMLStreamWriter(sw);
			StAXResult result = new StAXResult(xmlStreamWriter);
			t.transform(source, result);
			xml = sw.toString();
			log.log(Level.INFO,"XML=\n{0}",xml);
			
			source = new StreamSource(new java.io.StringReader(xml));
		}
		catch (XMLStreamException | TransformerException e)
		{
			source=null;
			xml=null;
			log.log(Level.WARNING,"cant convert to sting",e);
		}

        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();

        boolean client = isRequestor(message);

        //if body is empty and we have BindingOperationInfo, we do not need to match 
        //operation anymore, just return
        if (bop != null && !StaxUtils.toNextElement(xmlReader)) {
            // body may be empty for partial response to decoupled request
            return;
        }

//        Service service = ServiceModelUtil.getService(message.getExchange());
        //bop = getBindingOperationInfo(xmlReader, exchange, bop, client);
		bop = getBindingOperationInfo(exchange, new QName("http://ws.faux/", "echoXML"), client);

        boolean forceDocLitBare = false;
        if (bop != null && bop.getBinding() != null) {
            forceDocLitBare = Boolean.TRUE.equals(bop.getBinding().getService().getProperty("soap.force.doclit.bare"));
        }
        
//        Node v = message.getContent(org.w3c.dom.Node.class);
//        log.log(Level.INFO,"Node class is:{0}",v!=null?v.getClass():null);
        		
        try {
            if (!forceDocLitBare && bop != null && bop.isUnwrappedCapable()) {
                ServiceInfo si = bop.getBinding().getService();
                // Wrapped case
                MessageInfo msgInfo = setMessage(message, bop, client, si);
                //setDataReaderValidation(service, message, dr);
                
                // Determine if we should keep the parameters wrapper
                if (true /*|| shouldWrapParameters(msgInfo, message)*/) {
//                    QName startQName = xmlReader.getName();
                    MessagePartInfo mpi = msgInfo.getFirstMessagePart();
//                    if (!mpi.getConcreteName().equals(startQName)) {
//                        throw new Fault("UNEXPECTED_WRAPPER_ELEMENT", log, null, startQName,
//                                        mpi.getConcreteName());
//                    }
//                    //Object wrappedObject = dr.read(mpi, xmlReader);
//                    //parameters.put(mpi, wrappedObject);
                    parameters.put(mpi, xml);
                } /*else {
                    // Unwrap each part individually if we don't have a wrapper
    
                    bop = bop.getUnwrappedOperation();
    
                    msgInfo = setMessage(message, bop, client, si);
                    List<MessagePartInfo> messageParts = msgInfo.getMessageParts();
                    Iterator<MessagePartInfo> itr = messageParts.iterator();
    
                    // advance just past the wrapped element so we don't get
                    // stuck
                    if (xmlReader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                        StaxUtils.nextEvent(xmlReader);
                    }
    
                    // loop through each child element
                    //getPara(xmlReader, dr, parameters, itr, message);
                }
    
*/
        }/* else {
                //Bare style
                BindingMessageInfo msgInfo = null;

    
                Endpoint ep = exchange.getEndpoint();
                ServiceInfo si = ep.getEndpointInfo().getService();
                if (bop != null) { //for xml binding or client side
                    if (client) {
                        msgInfo = bop.getOutput();
                    } else {
                        msgInfo = bop.getInput();
                        if (bop.getOutput() == null) {
                            exchange.setOneWay(true);
                        }
                    }
                    if (msgInfo == null) {
                        return;
                    }
                    setMessage(message, bop, client, si, msgInfo.getMessageInfo());
                }
    
                Collection<OperationInfo> operations = null;
                operations = new ArrayList<OperationInfo>();
                operations.addAll(si.getInterface().getOperations());
    
                if (xmlReader == null || !StaxUtils.toNextElement(xmlReader)) {
                    // empty input
                    getBindingOperationForEmptyBody(operations, ep, exchange);
                    return;
                }

                //setDataReaderValidation(service, message, dr);
                
                int paramNum = 0;
    
                do {
                    QName elName = xmlReader.getName();
                    Object o = null;
    
                    MessagePartInfo p;
                    if (!client && msgInfo != null && msgInfo.getMessageParts() != null 
                        && msgInfo.getMessageParts().size() == 0) {
                        //no input messagePartInfo
                        return;
                    }
                    
                    if (msgInfo != null && msgInfo.getMessageParts() != null 
                        && msgInfo.getMessageParts().size() > 0) {
                        if (msgInfo.getMessageParts().size() > paramNum) {
                            p = msgInfo.getMessageParts().get(paramNum);
                        } else {
                            p = null;
                        }
                    } else {
                        p = findMessagePart(exchange, operations, elName, client, paramNum, message);
                    }
                    
                    if (!forceDocLitBare) {
                        //Make sure the elName found on the wire is actually OK for 
                        //the purpose we need it
                        //validatePart(p, elName, message);
                    }
             
//                    o = dr.read(p, xmlReader);
                    o=source;
                    if (true) return;
                    if (forceDocLitBare && parameters.isEmpty()) {
                        // webservice provider does not need to ensure size
                        parameters.add(o);
                    } else {
                        parameters.put(p, o);
                    }
                    
                    paramNum++;
                    if (message.getContent(XMLStreamReader.class) == null || o == xmlReader) {
                        xmlReader = null;
                    }
                } while (xmlReader != null && StaxUtils.toNextElement(xmlReader));
    
            }
*/    
            message.setContent(List.class, parameters);
        } catch (Fault f) {
            if (!isRequestor(message)) {
                f.setFaultCode(Fault.FAULT_CODE_CLIENT);
            }
            throw f;
        }
		
	}

}
