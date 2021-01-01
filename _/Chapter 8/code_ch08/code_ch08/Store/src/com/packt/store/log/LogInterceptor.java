package com.packt.store.log;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class LogInterceptor implements Serializable {
   private static final long serialVersionUID = 1L;

   @Inject
   LogService logger;
	
   @AroundInvoke
   public Object logMethod(InvocationContext ic) throws Exception 
   {
      final Method method = ic.getMethod();

      // check if annotation is on class or method
      LogLevel logLevel = method.getAnnotation(Log.class) != null ? 
                            method.getAnnotation(Log.class).value() : 
                            method.getDeclaringClass().getAnnotation(Log.class).value();

      // invoke LogService
      logger.log(ic.getClass().getCanonicalName(), logLevel, method.toString());	
      return ic.proceed();
   }
}
