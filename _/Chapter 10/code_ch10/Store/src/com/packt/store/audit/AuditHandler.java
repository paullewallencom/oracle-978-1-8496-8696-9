package com.packt.store.audit;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Named;

@Stateless
@Named
public class AuditHandler {	
   private static final String PREFIX = " [ AUDIT ] ";		
   private static final String ENTER = "[ Entering ]";
   private static final String EXIT = "[ Exiting ]";

   private Logger log = Logger.getLogger(AuditHandler.class.getCanonicalName());
   
   public void logEnter(@Observes @Enter AuditEvent event) {      
	   log.info(PREFIX + ENTER + event);
   }
   public void logExit(@Observes @Exit AuditEvent event) {
	   log.info(PREFIX + EXIT + event);
   }
}
