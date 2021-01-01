package com.packt.store.search;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;

import com.packt.domain.store.Movie;
import com.packt.domain.store.Theater;
import com.packt.domain.theater.Exhibition;
import com.packt.domain.theater.Seat;
import com.packt.theater.client.TheaterClient;
import com.packt.util.ReservationCodeBean;
import com.packt.util.gen.Execute.Seats;
import com.packt.util.gen.Execute.Seats.Entry;
import com.packt.util.gen.ReservationException_Exception;
import com.packt.util.gen.ReservationService;

@Named("search")
@SessionScoped
public class SearchManager implements Serializable {

	private static final long serialVersionUID = 5369504610659191247L;

	@PersistenceContext(unitName = "StoreBO")
	EntityManager em;

	private List<Theater> theaters;
	private List<Movie> movies;
	private List<Exhibition> exhibitions;
	private List<Seat> seats;
	private String[] quantities;
	private static final String RETRY_MSG = "There's an error communicating with the theater. Please try again.";

	@Min(value = 1, message = "Please select a movie")
	private int movie;
	@Min(value = 1, message = "Please select a theater")
	private int theater;
	@Min(value = 1, message = "Please select an exhibition")
	private int exhibition;

	@Inject
	private transient ReservationCodeBean controlBean;

	@Inject
	private transient TheaterClient theaterClient;

	@Resource(lookup = "reservationServiceEndpoint")
	private String RESERVATION_SVC_ENDPOINT;
	
	private transient ReservationService reservationService;

	@PostConstruct
	public void init() {
		try {
			reservationService =  new ReservationService(new URL(RESERVATION_SVC_ENDPOINT));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Theater> getTheaters() {
		if (theaters == null)
			theaters = em.createNamedQuery(Theater.findAll).getResultList();

		return theaters;
	}

	public void query() {
		if (exhibition != 0) {
			seats = theaterClient.getSeatsByExhibition(exhibition);

			quantities = new String[seats.size()];

			/*
			 * Set the variable that holds the selection done by the user to
			 * zero
			 */
			for (int i = 0; i < seats.size(); i++) {
				quantities[i] = "0";
			}
		} else {
			seats = null;
		}
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

	public String reserve() {
		Seats seats = new Seats();

		// The Entry class referenced here is declared inside
		// com.packt.util.gen.Execute
		List<Entry> entries = seats.getEntry();
		int numSeats = 0;
		/*
		 * Creates an Entry object to send to the theater module, one for each
		 * seat type that the user entered a # of tickets.
		 */
		for (int i = 0; i < quantities.length; i++) {
			String quantity = quantities[i];
			int iqty = Integer.parseInt(quantity); 
			if (iqty > 0) {
				Entry entry = new Entry();

				entry.setKey(this.seats.get(i).getType());
				entry.setValue(iqty);
				entries.add(entry);
			}
			numSeats += iqty;
		}

		try {

			String reservationCode = controlBean.generate(this.theater,
					this.exhibition);
			/*
			 * Submit the info to theater module
			 */
			String response = reservationService.getReservationBeanPort()
					.execute(this.getExhibition(), reservationCode, seats);

			/*
			 * If there's an error, displays a message and exists the method,
			 * leaving the user at the same page
			 */
			if (response.equals("ok")) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getFlash().setKeepMessages(true);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								"New reservation of "+ numSeats +" seats completed. Number is "
										+ reservationCode));

				resetSearch();
				return "reservation?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								RETRY_MSG, null));
				// stays at search page (current view)
				return null;
			}

		} catch (ReservationException_Exception re) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, re
							.getMessage(), null));
			resetSearch();
			// stays at search page (current view)
			return null;
		}
	}

	public void resetSearch() {
		this.theater = 0;
		this.movie = 0;
		this.exhibition = 0;
		this.seats = null;

	}

	@SuppressWarnings("unused")
	private String getSeatDescription(int type) {
		switch (type) {
		case 1:
			return "Regular";
		case 2:
			return "Comfort";
		case 3:
			return "Disability";
		default:
			return "Unknown";
		}
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

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String[] getQuantities() {
		return quantities;
	}

	public void setQuantities(String[] quantities) {
		this.quantities = quantities;
	}

	public ReservationService getService() {
		return reservationService;
	}

	public void setService(ReservationService service) {
		this.reservationService = service;
	}

}
