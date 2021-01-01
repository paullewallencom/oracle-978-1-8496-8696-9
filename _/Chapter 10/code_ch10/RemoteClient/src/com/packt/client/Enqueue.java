package com.packt.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.packt.domain.theater.Exhibition;
import com.packt.domain.theater.Movie;
import com.packt.domain.theater.Room;

public abstract class Enqueue {
	/*
	 * Direct connection: "t3://localhost:7001" Using SAF client:
	 * "file:/opt/packt/etc/SAFClient.xml"
	 */
	static final String WLS_ADDRESS = "t3://deathstar:7001";

	/*
	 * Direct connection: "weblogic.jndi.WLInitialContextFactory"; Using SAF
	 * client: "weblogic.jms.safclient.jndi.InitialContextFactoryImpl"
	 */
	static final String WLS_CTX_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	static final String JMS_QUEUE_FACTORY = "weblogic.jms.ConnectionFactory";
	static final String JMS_QUEUE_NAME = "jms.tickets.exhibition";

	public static void main(String[] args) throws NamingException,
			ParseException {
		Context ct;
		Hashtable<String, String> env = new Hashtable<>();

		QueueConnectionFactory qcf;
		QueueConnection qc = null;
		QueueSession qs = null;
		QueueSender sender = null;

		Queue queue;
		ObjectMessage msg;

		env.put(Context.PROVIDER_URL, WLS_ADDRESS);
		env.put(Context.INITIAL_CONTEXT_FACTORY, WLS_CTX_FACTORY);
		env.put(Context.SECURITY_PRINCIPAL, "weblogic");

		/*
		 * Direct connection: "welcome1"; Using SAF client: "packt"
		 */
		env.put(Context.SECURITY_CREDENTIALS, "welcome1");

		// Get a server connection
		ct = new InitialContext(env);

		/*
		 * Argument sequence and format: 0: Movie Id 1: Room Id 2: Exhibition
		 * date - MM.DD.YYYY 3: Exhibition time - HHMM
		 */
		Movie movie = new Movie();
		movie.setId(Integer.parseInt(args[0]));

		Room room = new Room();
		room.setId(Integer.parseInt(args[1]));

		Exhibition exhibition = new Exhibition();

		exhibition.setMovie(movie);
		exhibition.setRoom(room);
		exhibition.setDate(new SimpleDateFormat("MM.dd.yyyy").parse(args[2]));
		exhibition.setHour(Integer.parseInt(args[3]));

		// This should be retrieved from Room, but to keep it simple,
		// we're using a fixed value here.
		exhibition.setAvailableSeats(50);

		try {
			// Set up JMS components
			qcf = (QueueConnectionFactory) ct.lookup(JMS_QUEUE_FACTORY);
			qc = qcf.createQueueConnection();
			qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// Get a handle to the JMS Queue
			queue = (javax.jms.Queue) ct.lookup(JMS_QUEUE_NAME);

			// Create ...
			msg = qs.createObjectMessage();
			msg.setObject(exhibition);

			// ... and send the message
			sender = qs.createSender(queue);
			sender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Doesn't support try-with-resources yet...
			try {
				sender.close();
			} catch (Exception e) {
			}
			try {
				qc.close();
			} catch (Exception e) {
			}
			try {
				qs.close();
			} catch (Exception e) {
			}
			try {
				ct.close();
			} catch (Exception e) {
			}
		}
	}
}
