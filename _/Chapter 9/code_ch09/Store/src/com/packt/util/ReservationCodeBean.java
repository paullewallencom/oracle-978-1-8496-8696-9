package com.packt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Theater;
import com.packt.domain.store.Ticket;

@Singleton
public class ReservationCodeBean {
	
	
	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;

	private int counter = 0;
	private SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd-hhmmss");

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String generate(int theaterRef, int exhibitionRef) {
		String control = String.format("%1$s-%2$06d", now.format(new Date()),
				++counter);
		
		// Create an instance
		Ticket ticket = new Ticket();

		// Create Mandatory reference (Ticket --> Theater)
		Theater theater = new Theater();
		theater.setId(theaterRef);

		// Set
		ticket.setTheater(theater);
		ticket.setExhibitionRef(exhibitionRef);
		ticket.setControl(control);
		
		em.persist(ticket);

		return control;
	}
}
