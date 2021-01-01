package com.packt.store.exhibition;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.theater.Exhibition;
import com.packt.store.AbstractRepository;

@Named("exhibition")
@RequestScoped
public class ExhibitionManager extends AbstractRepository<Exhibition> {

	@PersistenceContext(unitName = "TheaterBO")
	EntityManager em;

	public ExhibitionManager() {
		super(Exhibition.class);
	}

	public Exhibition find(String id) {
		return super.find(Integer.parseInt(id));
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
