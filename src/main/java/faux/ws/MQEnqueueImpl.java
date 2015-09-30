package faux.ws;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.Getter;
import lombok.extern.java.Log;

import org.springframework.stereotype.Component;

import faux.config.JMSContextListenerClass;

@WebService(serviceName="queue", targetNamespace="gov.state")
@Component
@Log
public class MQEnqueueImpl implements MQEnqueue
{
	@Resource
	LoremServiceImpl docsvc;

	public MQEnqueueImpl()
	{
//		Connection connection = connectionFactory.createConnection();
//		connection.start();
	}

	@Data
	@XmlRootElement
	private static class Doc
	{
		long docId;
		
		@Getter(onMethod=@__({ @XmlElementWrapper(namespace="http://ws.faux/"),@XmlElement(name="P",namespace="http://ws.faux/")}))
		List<String> getTextResponse;
	}
	
	public static void main(String[] args) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Doc.class);
		Doc doc = new Doc();
		doc.docId=1l;
		doc.getTextResponse=new ArrayList<String>();
		doc.getTextResponse.add("XYZZY");
		doc.getTextResponse.add("Bob Barker");
		String s = asString(jaxbContext, doc);
		System.out.println(s);
		
	}

	public static String asString(JAXBContext pContext, Object pObject) throws JAXBException
	{

		java.io.StringWriter sw = new StringWriter();

		Marshaller marshaller = pContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(pObject, sw);

		return sw.toString();
	}
	
	@Override
	public void enqueue(long docid)
	{
		Doc doc=new Doc();
		doc.docId=docid;
		doc.getTextResponse= docsvc.getText(docid);
		
		log.log(Level.INFO,"doc={0}",doc);
		JMSContextListenerClass x = JMSContextListenerClass.getLastInstance();
		try
		{
			Connection conn = x.getConnection();
			conn.start();
			try
			{
				Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("DOC");

				// Create a MessageProducer from the Session to the Topic or Queue
				MessageProducer producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				
		        // Create a messages
				JAXBContext jaxbContext = JAXBContext.newInstance(Doc.class);
				String text = asString(jaxbContext, doc);

				TextMessage message = session.createTextMessage(text);        
		        
		        // Tell the producer to send the message
		        producer.send(message);

		        // Clean up
		        session.close();
				
			}
			finally
			{
				conn.stop();
			}
			
		}
		catch (JMSException|JAXBException e)
		{
			log.log(Level.WARNING,"JMS???",e);
		}
		
	}

}
