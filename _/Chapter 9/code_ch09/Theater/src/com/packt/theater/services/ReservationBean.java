package com.packt.theater.services;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import weblogic.transaction.TransactionHelper;

import com.packt.domain.theater.Exhibition;

@WebService(serviceName = "ReservationService", targetNamespace = "http://com.packt.wls12c")
public class ReservationBean {
	 public static final String OK = "ok";
	
	@PersistenceContext
	EntityManager em;

	public String execute(@WebParam(name = "exhibitionId") int exhibitionId,
			@WebParam(name = "reservationCode") String reservationCode,
			@WebParam(name = "seats") Map<Integer, Integer> seats) throws ReservationException {
		
		/*
		 *  Call to the partner's system to register the
		 *  reservation at their site
		 */
		// partnerSystem.storeReservation(exhibition, reservationCode, seats)
		
		// Find the total number of seats for this reservation
		int seatsTotal = 0;

		for (Iterator<Entry<Integer, Integer>> it = seats.entrySet().iterator(); it
				.hasNext();) {
			seatsTotal = seatsTotal + it.next().getValue();
		}

		// and subtract then from the Exhibition instance
		UserTransaction ut = TransactionHelper.getTransactionHelper()
				.getUserTransaction();

		//
		// The find method _must_ be inside the transaction!
		//
		try {
			ut.begin();

			Exhibition exhibition = em.find(Exhibition.class, exhibitionId,
					LockModeType.PESSIMISTIC_WRITE);

			if (null == exhibition) {
				throw new ReservationException("Exhibition not found");
			}

			exhibition.setAvailableSeats(exhibition.getAvailableSeats() - seatsTotal);

			ut.commit();
		} catch (Exception e) {
			if (e instanceof ReservationException)
				throw (ReservationException) e;
			else 
				throw new ReservationException();
		}
		return ReservationBean.OK;

	}

}
