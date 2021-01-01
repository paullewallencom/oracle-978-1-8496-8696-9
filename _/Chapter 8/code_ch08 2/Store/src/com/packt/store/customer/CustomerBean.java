package com.packt.store.customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Customer;
import com.packt.store.AbstractRepository;

@Stateless
public class CustomerBean extends AbstractRepository<Customer> {

	@PersistenceContext(unitName = "StoreBO")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
}
