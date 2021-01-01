package com.packt.domain.store;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the ticket database table.
 * 
 */
@Entity
@NamedQuery(name=Ticket.findAll, query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String findAll = "Ticket.findAll";
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	@NotNull(message="Control numbers can't be null.")
	private String control;

	@NotNull(message="Exhibition(s) must be assigned to the ticket.")
	private int exhibitionRef;

	@NotNull(message="Seat(s) must be assigned to the ticket.")
	private int seatRef;

	//bi-directional many-to-one association to Theater
	@ManyToOne
	@JoinColumn(name="theaterRef")
	private Theater theater;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customerRef")
	private Customer customer;

	public Ticket() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getControl() {
		return this.control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public int getExhibitionRef() {
		return this.exhibitionRef;
	}

	public void setExhibitionRef(int exhibitionRef) {
		this.exhibitionRef = exhibitionRef;
	}

	public int getSeatRef() {
		return this.seatRef;
	}

	public void setSeatRef(int seatRef) {
		this.seatRef = seatRef;
	}

	public Theater getTheater() {
		return this.theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}