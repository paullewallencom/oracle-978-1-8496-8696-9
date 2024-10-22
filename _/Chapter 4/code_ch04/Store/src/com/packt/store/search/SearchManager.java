package com.packt.store.search;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.packt.domain.store.Movie;
import com.packt.domain.store.Theater;
import com.packt.domain.theater.Exhibition;
import com.packt.theater.client.TheaterClient;

@Named("search")
@SessionScoped
public class SearchManager implements Serializable {

	private static final long serialVersionUID = 5369504610659191247L;

	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;

	private List<Theater> theaters;
	private List<Movie> movies;

	private int movie;
	private int theater;

	private List<Exhibition> exhibitions;
	private int exhibition;

	@Inject
	private transient TheaterClient theaterClient;

	@SuppressWarnings("unchecked")
	public List<Theater> getTheaters() {
		if (theaters == null)
			theaters = em.createNamedQuery(Theater.findAll).getResultList();

		return theaters;
	}

	public String search () {
		System.out.println("Search fired!");
		return "index";
	}
	
	private TheaterClient getTheaterClient() {
		return theaterClient == null ? new TheaterClient() : theaterClient;
	}
	
	public void handleMovieChange() {
		if (movie != 0)
			setExhibitions(getTheaterClient().getExhibitionByMovie(movie));
		else
			setExhibitions(null);
	}

	@SuppressWarnings("unchecked")
	public List<Movie> getMovies() {
		if (movies == null)
			movies = em.createNamedQuery(Movie.findAll).getResultList();

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

	public List<Exhibition> getExhibitions() {
		return exhibitions;
	}

	public void setExhibitions(List<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public int getExhibition() {
		return exhibition;
	}

	public void setExhibition(int exhibition) {
		this.exhibition = exhibition;
	}

}
