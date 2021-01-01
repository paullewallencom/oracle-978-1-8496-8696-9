package com.packt.domain.theater;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the seat database table.
 * 
 */
@Entity
@NamedQuery(name=Seat.findAll, query="SELECT s FROM Seat s")
public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String findAll = "Seat.findAll";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private BigDecimal price;

	private int type;

	//bi-directional many-to-one association to Room
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="roomRef")
	private Room room;

	public Seat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlTransient
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}