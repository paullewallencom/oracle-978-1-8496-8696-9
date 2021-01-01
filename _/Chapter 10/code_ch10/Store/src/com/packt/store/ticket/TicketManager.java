package com.packt.store.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.packt.domain.store.Customer;
import com.packt.domain.store.Ticket;
import com.packt.store.AbstractRepository;
import com.packt.store.security.LoggedIn;

@Named("ticket")
@RequestScoped
public class TicketManager extends AbstractRepository<Ticket> {

	private List<Ticket> tickets;
	
	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;
	
	@Inject @LoggedIn
	Customer currentCustomer;
	
	public TicketManager() {
		super(Ticket.class);
	}
	
	@PostConstruct
	public void init() {
		this.tickets = getTicketsByCustomer(currentCustomer.getId());
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
	
	
	public List<Ticket> getTicketsByCustomer(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
		Root<Ticket> _ticket = cq.from(Ticket.class);
		
		cq.where(cb.equal( _ticket.get("customer"), currentCustomer));
		
		return em.createQuery(cq).getResultList();
	}

}
