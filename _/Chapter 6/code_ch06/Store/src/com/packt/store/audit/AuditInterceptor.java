package com.packt.store.audit;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Audit
@Interceptor
public class AuditInterceptor implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @Inject @Enter
   // The Event referenced here is javax.enterprise.event.Event
   Event<AuditEvent> enterEvent;
	
   @Inject @Exit

   Event<AuditEvent> exitEvent;
		
   @AroundInvoke
   public Object auditMethod(InvocationContext ic) throws Exception {
      enterEvent.fire(new AuditEvent(ic.getMethod().toString(),     
                     (ic.getParameters().length > 0 ? ic.getParameters() : null)));
			
      Object obj = ic.proceed();
		
      exitEvent.fire(new AuditEvent(ic.getMethod().toString(),
                    (ic.getParameters().length > 0 ? 
                     ic.getParameters() : null)));
		
      return obj;
   }
}
