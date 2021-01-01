package com.packt.theater.client;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

import com.packt.domain.theater.Exhibition;
import com.packt.domain.theater.Seat;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Named
public class TheaterClient {
	@Resource(lookup = "theaterServiceEndpoint")
	private String ENDPOINT;
	
	Logger log = Logger.getAnonymousLogger();

	private WebResource getClient() {
		final Client client = Client.create();
		return client.resource(ENDPOINT);
	}

	public List<Exhibition> getExhibitionByMovie(int movieId) {
		if (null == ENDPOINT) {
			log.severe("Theater exhibition service endpoint NOT loaded!");
			return null;
		}

		final List<Exhibition> exhibition = (List<Exhibition>) getClient()
				.path("exhibition").path("q")
				.queryParam("movie", String.valueOf(movieId))
				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class)
				.getEntity(new GenericType<List<Exhibition>>() {
				});

		return exhibition;
	}

	public List<Seat> getSeatsByExhibition(int exhibitionId) {
		final List<Seat> seats = (List<Seat>) getClient().path("exhibition")
				.path(String.valueOf(exhibitionId)).path("seats")
				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class)
				.getEntity(new GenericType<List<Seat>>() {
				});

		return seats;
	}

}
