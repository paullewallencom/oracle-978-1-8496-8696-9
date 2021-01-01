package com.packt.domain.theater;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name=Room.findAll, query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String findAll = "Room.findAll";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int capacity;

	private String name;

	//bi-directional many-to-one association to Exhibition
	@OneToMany(mappedBy="room")
	@XmlTransient
	private List<Exhibition> exhibitions;

	//bi-directional many-to-one association to Seat
	@OneToMany(mappedBy="room")
	private List<Seat> seats;

	public Room() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<Exhibition> getExhibitions() {
		return this.exhibitions;
	}

	public void setExhibitions(List<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public Exhibition addExhibition(Exhibition exhibition) {
		getExhibitions().add(exhibition);
		exhibition.setRoom(this);

		return exhibition;
	}

	public Exhibition removeExhibition(Exhibition exhibition) {
		getExhibitions().remove(exhibition);
		exhibition.setRoom(null);

		return exhibition;
	}

	public List<Seat> getSeats() {
		return this.seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Seat addSeat(Seat seat) {
		getSeats().add(seat);
		seat.setRoom(this);

		return seat;
	}

	public Seat removeSeat(Seat seat) {
		getSeats().remove(seat);
		seat.setRoom(null);

		return seat;
	}

}