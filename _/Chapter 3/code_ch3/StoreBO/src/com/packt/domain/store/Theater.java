package com.packt.domain.store;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the theater database table.
 * 
 */
@Entity
@NamedQuery(name=Theater.findAll, query="SELECT t FROM Theater t")
public class Theater implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String findAll = "Theater.FindAll";
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	private String address;

	private String city;

	private String name;

	private String phoneNumber;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="theater")
	private List<Ticket> tickets;

	public Theater() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setTheater(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setTheater(null);

		return ticket;
	}

}