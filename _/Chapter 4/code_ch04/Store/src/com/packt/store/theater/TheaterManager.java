package com.packt.store.theater;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Theater;
import com.packt.store.AbstractRepository;

@Named("theater")
@RequestScoped
public class TheaterManager extends AbstractRepository<Theater> {

	private List<Theater> theaters;
	
	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;

	public TheaterManager() {
		super(Theater.class);
	}
	
	@Override
	public Theater find(Object id) {
		return super.find(new Integer((String) id));
	}
	
	@PostConstruct
	public void init() {
		theaters = this.findAll();
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<Theater> getTheaters() {
		return theaters;
	}

}
