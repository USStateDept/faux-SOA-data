package faux.ws;

import java.util.logging.Level;

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
	}

	@Override
	public void handleMessage(Message message) throws Fault
	{
		log.log(Level.INFO,"message={0}",message);
	}

}
