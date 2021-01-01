package com.packt.util;

import java.io.Serializable;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.packt.domain.store.Customer;
import com.packt.domain.store.Theater;
import com.packt.domain.store.Ticket;
import com.packt.store.security.LoggedIn;
import com.packt.store.services.ITokenService;
import com.packt.store.services.TokenService;
import com.packt.store.ticket.TicketManager;

@Singleton
@Dependent
public class ReservationCodeBean implements Serializable {

	private static final long serialVersionUID = -8432546445976847021L;

	ITokenService tokenService;
	
	@Inject TicketManager ticketManager;
	
	@Inject @LoggedIn
	Customer currentCustomer;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String generate(int theaterRef, int exhibitionRef) {
		
		String control;
		try {
			control = this.getControlNumber();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// Create an instance
		Ticket ticket = new Ticket();

		// Create Mandatory reference (Ticket --> Theater)
		Theater theater = new Theater();
		theater.setId(theaterRef);
		
		// Set
		ticket.setTheater(theater);
		ticket.setExhibitionRef(exhibitionRef);
		ticket.setControl(control);
		ticket.setCustomer(currentCustomer);
		
		ticketManager.create(ticket);
		
		return control;
	}

		private String getControlNumber() throws Exception {
		if (tokenService == null) {
			try {
				Context ctx = new InitialContext();
				tokenService = (ITokenService) ctx
						.lookup(TokenService.JNDI_ENTRY);
			} catch (NamingException ex) {
				ex.printStackTrace();
				throw new Exception("Control number was not generated!");
			}
		}

		return tokenService.generate();
	}

}
