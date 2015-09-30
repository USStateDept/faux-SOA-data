package faux.config;

import java.util.logging.Level;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lombok.extern.java.Log;

import org.apache.activemq.ActiveMQConnectionFactory;

@Log
public final class JMSContextListenerClass implements ServletContextListener
{

	private static JMSContextListenerClass lastInstance;
	

	public static JMSContextListenerClass getLastInstance()
	{
		return lastInstance;
	}
	
	public JMSContextListenerClass()
	{
		lastInstance=this;
	}
	
	private ActiveMQConnectionFactory connectionFactory;

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		log.log(Level.INFO,"unloading JMS connections");
		for (Connection conn:connections.getAll())
		{
			if (conn!=null)
			{
				try
				{
					conn.close();
				}
				catch (JMSException e)
				{
					log.log(Level.WARNING,"could not close",e);
				}
			}
		}
	}

	private KnowingThreadLocal<Connection> connections=new KnowingThreadLocal<Connection>();
	
	public Connection getConnection() throws JMSException
	{
		Connection connection = connections.get();
		if (connection==null)
		{
			connection = connectionFactory.createConnection();			
			connections.set(connection);		
		}
		return connection;

	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");		
		log.log(Level.INFO,"JMS connection factory ready");
	}

}
