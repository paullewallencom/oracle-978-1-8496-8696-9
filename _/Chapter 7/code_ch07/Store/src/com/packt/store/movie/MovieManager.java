package com.packt.store.movie;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Movie;
import com.packt.store.AbstractRepository;

@Named("movie")
@RequestScoped
public class MovieManager extends AbstractRepository<Movie> {

	private static final long serialVersionUID = -7471428136464561053L;
	private List<Movie> movies = null;
	private Movie currentMovie;

	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;

	public MovieManager() {
		super(Movie.class);
	}

	public Movie find(String id) {
		return super.find(Integer.parseInt(id));
	}
	
	public List<Movie> getMovies() {
		return movies;
	}

	@PostConstruct
	public void init() {
		movies = this.findAll();
	}

	public Movie getCurrentMovie() {
		return currentMovie;
	}

	public void setCurrentMovie(Movie movie) {
		this.currentMovie = movie;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
