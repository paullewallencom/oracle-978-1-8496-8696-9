package com.packt.store.security;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import com.packt.domain.store.Customer;
import com.packt.store.customer.CustomerBean;

@Named("login")
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Customer customer = new Customer();

	@Resource(lookup = "signupUser")
	private String signupUser;
	
	@Resource(lookup = "signupPassword")
	private String signupPassword;
	
	@Resource(mappedName = "jms.userQueue")
	private Queue queue;

	@Resource(mappedName = "weblogic.jms.XAConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@EJB
	CustomerBean customerBean;
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) context
			.getExternalContext().getRequest(); 

	public String login() {
		try {
			request.login(getCustomer().getEmail(), getCustomer().getPassword());

			addSuccessMessage(String.format("Welcome back, %s !", getCustomer()
					.getEmail()));

			return "index?faces-redirect=true";
		} catch (ServletException ex) {
			ex.printStackTrace();
			addErrorMessage("Wrong username or password, please try again.");
			return "login?faces-redirect=true";
		}
	}
	
	public void publish(Customer entity) throws JMSException {
		Connection con = null;
		Session session = null;
		MessageProducer sender = null;

		try {
			con = connectionFactory.createConnection();
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createProducer(queue);

			Message message = session.createObjectMessage(entity);

			sender.send(message);
		} catch (JMSException e) {
			// do something with exception
			e.printStackTrace();
			throw e;
		} finally {
			// Doesn't support try-with-resources yet...
			try {
				sender.close();
			} catch (Exception e) {
			}
			try {
				session.close();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}
	
	public void signup() {
		try {
			// generate hashed password
			getCustomer().setPassword(generatePassword(getCustomer().getPassword()));
			// authentication to publish to a protected JMS queue
			request.login(signupUser, signupPassword);
			
			// publish to the JMS queue
		    publish(getCustomer());
			
			// store customer data on the database
			customerBean.create(getCustomer());
			addSuccessMessage("Thanks! Your user should be ready in a few seconds. Try to log in using the form below.");
		} catch (Exception ex) {
			ex.printStackTrace();
			addErrorMessage("An unknown error occurred and your user was not created.");
		} finally {
			try {
				request.logout();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String generatePassword(String text) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] hash = new byte[40];
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
			hash = md.digest();
			
			//new String(Base64.encode(hash)
			
			return "{SHA}" + DatatypeConverter.printBase64Binary(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return text;
	}

	public String logout() throws ServletException {
		request.logout();
		addSuccessMessage("You have logged out.");

		return "index?faces-redirect=true";
	}

	private void addErrorMessage(String msg) {
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, null));

	}

	private void addSuccessMessage(String msg) {
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg, null));
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
