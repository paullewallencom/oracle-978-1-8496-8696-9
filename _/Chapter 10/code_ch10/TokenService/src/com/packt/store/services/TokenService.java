package com.packt.store.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import weblogic.cluster.singleton.SingletonService;

public class TokenService implements ITokenService, SingletonService {

	  // ideally would be moved to an external source, like a DB or a cache server
	  private int counter = 0; 
	  
	  private Logger logger = Logger.getLogger(
	                 TokenService.class.getCanonicalName());
	  private SimpleDateFormat now = new 
	                   SimpleDateFormat("yyyyMMdd-hhmmss");

	  @Override
	  public String generate() {
	    return String.format("%1$s-%2$06d", now.format(new Date()), ++counter);
	  }

	  @Override
	  public void activate() {
	    logger.fine("Attempting to bind TokenService...");

	    Context jndiCtx = null;
	    try {
	      jndiCtx = new InitialContext();
	      jndiCtx.rebind(JNDI_ENTRY, this);

	      logger.info("TokenService activated on this server.");
	    } catch (NamingException e) {
	      logger.severe("Error during TokenService activation: " + e.getMessage());
	    } finally {
	      if (jndiCtx != null)
	        try {
	          jndiCtx.close();
	        } catch (NamingException e) {
	          e.printStackTrace();
	        }
	    }
	  }

	  @Override
	  public void deactivate() {
	    logger.fine("Attempting to unbind TokenService...");

	    Context jndiCtx = null;
	    try {
	      jndiCtx = new InitialContext();
	      jndiCtx.unbind(JNDI_ENTRY);

	      logger.fine("TokenService was deactivated on this server.");
	    } catch (NamingException e) {
	      e.printStackTrace();
	    } finally {
	      if (jndiCtx != null)
	        try {
	          jndiCtx.close();
	        } catch (NamingException e) {
	          e.printStackTrace();
	        }
	    }
	  }

	
}
