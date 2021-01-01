package com.packt.store.security;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;

import com.packt.domain.store.Customer;

@MessageDriven(name = "UserConsumer", activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "jms.userQueue")
public class UserConsumer implements MessageListener {
	Logger log = Logger.getLogger(UserConsumer.class.getCanonicalName());

	@Inject
	private LDAPClient client;
	
	@Resource
    private MessageDrivenContext mdc;

	@Override
	public void onMessage(Message inMessage) {
		ObjectMessage msg = null;

		try {
			if (inMessage instanceof ObjectMessage) {
				msg = (ObjectMessage) inMessage;
				Customer customer = (Customer) msg.getObject();

				client.createUser(customer);
			} else {
				log.severe("Message of wrong type: "
						+ inMessage.getClass().getName());
			}
		} catch (JMSException je) {
			mdc.setRollbackOnly();
			je.printStackTrace();
			
		} catch (NamingException e) {
			mdc.setRollbackOnly();
			e.printStackTrace();
		}

	}

}
