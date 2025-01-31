package com.packt.theater.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.packt.domain.theater.Exhibition;

@Stateless
@Path("/exhibition")
public class ExhibitionBean {

	@PersistenceContext(unitName = "TheaterBO")
	private EntityManager em;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Exhibition> getAllExhibitions() {
		@SuppressWarnings("unchecked")
		List<Exhibition> result = em.createNamedQuery("Exhibition.findAll")
				.getResultList();
		if (result.size() > 0)
			return result;
		else 
			throw new WebApplicationException(Response.Status.NO_CONTENT);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Exhibition getExhibition(@PathParam("id") int id) {
		try {
			Exhibition entity = (Exhibition) em
					.createNamedQuery("Exhibition.findById")
					.setParameter("id", id).getSingleResult();

			return entity;
		} catch (NoResultException nre) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("/q")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Exhibition> getAllExhibitionsByMovie(@QueryParam("movie") int movieId) {
			if (movieId > 0) {
				Query query = em.createNamedQuery(Exhibition.findByMovie);
				query.setParameter("movieId", movieId);

				@SuppressWarnings("unchecked")
				List<Exhibition> result = query.getResultList();

				if (result.size() > 0)
					return result;
				else 
					throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

	throw new WebApplicationException(Response.Status.BAD_REQUEST);
	}


}
