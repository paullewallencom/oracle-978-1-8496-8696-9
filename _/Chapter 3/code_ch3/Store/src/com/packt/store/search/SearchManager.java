package com.packt.store.search;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Movie;
import com.packt.domain.store.Theater;

@Named("search")
@SessionScoped
public class SearchManager implements Serializable {

	private static final long serialVersionUID = 5369504610659191247L;

	@PersistenceContext(unitName="StoreBO")
	EntityManager em;
	
	private List<Theater> theaters;
	private List<Movie> movies;

	private int movie;
	private int theater;
	
	@SuppressWarnings("unchecked")
   public List<Theater> getTheaters() {
	   if (theaters == null)
	      theaters = em.createNamedQuery(Theater.findAll).
                    getResultList();

	   return theaters;
   }
    
	@SuppressWarnings("unchecked")
   public List<Movie> getMovies() {
	   if (movies == null)
	      movies = em.createNamedQuery(Movie.findAll).
                    getResultList();

	   return movies;
   }   

	public int getTheater() {
	   return theater;
	}

	public void setTheater(int theater) {
	   this.theater = theater;
	}

	public int getMovie() {
		return movie;
	}

	public void setMovie(int movie) {
		this.movie = movie;
	}


}
