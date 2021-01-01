package com.packt.store;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractRepository<T> {
	private Class<T> entityClass;
	
	public AbstractRepository() {
		
	}
	
	public AbstractRepository(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	protected abstract EntityManager getEntityManager();
	
	public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T find(int id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll() {
    	CriteriaQuery<T> cq = (CriteriaQuery<T>) getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }
}
