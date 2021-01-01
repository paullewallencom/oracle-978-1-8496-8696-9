package com.packt.theater.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.theater.Exhibition;
import com.sun.istack.internal.logging.Logger;

/**
 * Message-Driven Bean implementation class for: ExhibitionConsumer
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms.tickets.exhibition")
		}, 
		mappedName = "jms.tickets.exhibition")
public class ExhibitionConsumer implements MessageListener {

	@PersistenceContext(unitName = "TheaterBO")
	EntityManager em;
	
	private final Logger logger = Logger.getLogger(ExhibitionConsumer.class);

    /**
     * Default constructor. 
     */
    public ExhibitionConsumer() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	ObjectMessage om = (ObjectMessage) message;  

    	try {
    	   Exhibition ex = (Exhibition) om.getObject();

    	   StringBuilder msg = new StringBuilder();
    	   msg.append(ex.getMovie().getId())
    	   .append(", ")
    	   .append(ex.getRoom().getId())
    	   .append(", ")
    	   .append(ex.getDate())
    	   .append(", ")
    	   .append(ex.getHour());
    	   
    	   logger.info(msg.toString());
    	    
    	   em.persist(ex);

    	} catch (JMSException e) {
    	    e.printStackTrace();
    	}

        
    }

}
