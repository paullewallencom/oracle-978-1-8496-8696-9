package com.packt.store.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
@Asynchronous
public class LogService {
   // Receives the class name decorated with @Log 
   public void log(final String clazz, final LogLevel level, final String message) {
      // Logger from package java.util.logging
      Logger log = Logger.getLogger(clazz);
      log.log(Level.parse(level.toString()), message);
   }
}
