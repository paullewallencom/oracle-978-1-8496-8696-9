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
 * The persistent class for the movie database table.
 * 
 */
@Entity
@NamedQuery(name=Movie.findAll, query="SELECT m FROM Movie m")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String findAll = "Movie.findAll";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String description;

	private int length;

	private String name;

	//bi-directional many-to-one association to Exhibition
	@XmlTransient
	@OneToMany(mappedBy="movie")
	private List<Exhibition> exhibitions;

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

	@XmlTransient
	public List<Exhibition> getExhibitions() {
		return this.exhibitions;
	}

	public void setExhibitions(List<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public Exhibition addExhibition(Exhibition exhibition) {
		getExhibitions().add(exhibition);
		exhibition.setMovie(this);

		return exhibition;
	}

	public Exhibition removeExhibition(Exhibition exhibition) {
		getExhibitions().remove(exhibition);
		exhibition.setMovie(null);

		return exhibition;
	}

}