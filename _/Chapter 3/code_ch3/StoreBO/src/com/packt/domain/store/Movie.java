package com.packt.domain.store;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the movie database table.
 * 
 */
@Entity
@NamedQuery(name=Movie.findAll, query="SELECT M FROM Movie M")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String findAll = "Movie.FindAll";


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	private String description;

	private int length;

	private String name;

	public Movie() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}