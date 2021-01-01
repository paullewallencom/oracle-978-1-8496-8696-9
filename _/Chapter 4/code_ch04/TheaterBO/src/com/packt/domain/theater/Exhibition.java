package com.packt.domain.theater;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the exhibition database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Exhibition.findAll, 
               query="SELECT r FROM Exhibition r"),
	@NamedQuery(name=Exhibition.findById, 
               query="SELECT r FROM Exhibition r WHERE r.id = :id"),
@NamedQuery(name=Exhibition.findByMovie, 
            query="SELECT r FROM Exhibition r WHERE r.movie.id = :movieId")
   })
@XmlRootElement(name="Exhibition")
public class Exhibition implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String findAll = "Exhibition.findAll";
	public final static String findById = "Exhibition.findById";
	public final static String findByMovie = " Exhibition.findByMovie";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="available_seats")
	private int availableSeats;

	@Temporal(TemporalType.DATE)
	private Date date;

	private int hour;

	//bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name="movieRef")
	private Movie movie;

	//bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name="roomRef")
	private Room room;

	public Exhibition() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvailableSeats() {
		return this.availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Date getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.set(Calendar.HOUR_OF_DAY, this.hour / 100);
		cal.set(Calendar.MINUTE, this.hour % 100);
		
		//return this.date;
		return cal.getTime();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHour() {
		return this.hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}