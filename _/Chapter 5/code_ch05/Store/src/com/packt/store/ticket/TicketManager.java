package com.packt.store.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Ticket;
import com.packt.store.AbstractRepository;

@Named("ticket")
@RequestScoped
public class TicketManager extends AbstractRepository<Ticket> {

	private List<Ticket> tickets;
	
	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;
	
	public TicketManager() {
		super(Ticket.class);
	}
	
	@PostConstruct
	public void init() {
		this.tickets = this.findAllDesc("id");
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
