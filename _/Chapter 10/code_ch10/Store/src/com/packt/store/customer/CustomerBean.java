package com.packt.store.customer;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.packt.domain.store.Customer;
import com.packt.store.AbstractRepository;
import com.packt.store.security.LoggedIn;

@Stateless
public class CustomerBean extends AbstractRepository<Customer> {

	@PersistenceContext(unitName = "StoreBO")
	private EntityManager em;


	@Resource
	private SessionContext sctx;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	@Produces @LoggedIn
	public Customer getCurrentCustomer() {
		CriteriaBuilder cb = this.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> _customer = cq.from(Customer.class);
		
		cq.where(cb.equal( _customer.get("email"), sctx.getCallerPrincipal().getName()));
		
		Customer loggedCustomer = em.createQuery(cq).getSingleResult();
		return loggedCustomer;
	}
}
