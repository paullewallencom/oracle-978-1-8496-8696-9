package com.packt.store.audit;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Named;

@Stateless
@Named
public class AuditHandler {	
   private static final String PREFIX = " [ AUDIT ] ";		
   private static final String ENTER = "[ Entering ]";
   private static final String EXIT = "[ Exiting ]";

   public void logEnter(@Observes @Enter AuditEvent event) {
      System.out.println(PREFIX + ENTER + event);
   }
   public void logExit(@Observes @Exit AuditEvent event) {
      System.out.println(PREFIX + EXIT + event);
   }
}
