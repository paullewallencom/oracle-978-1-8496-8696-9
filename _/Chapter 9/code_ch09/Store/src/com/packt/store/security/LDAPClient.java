package com.packt.store.security;

import java.util.Properties;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.packt.domain.store.Customer;

@Named
@Dependent
public class LDAPClient {

	Logger log = Logger.getLogger(LDAPClient.class.getCanonicalName());

	final static String ldapServerName = "localhost";
	final static String rootdn = "cn=Manager,dc=example,dc=com";
	final static String rootpass = "welcome1"; // not recommended for production
	final static String rootContext = "ou=people,dc=example,dc=com";

	private DirContext ldapCtx;

	private DirContext getLdapCtx() throws NamingException {
		return this.ldapCtx;
	}

	private void setLdapCtx(DirContext ctx) {
		this.ldapCtx = ctx;
	}
	
	public DirContext connect() throws NamingException {
		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		// consider SSL
		env.put(Context.PROVIDER_URL, "ldap://" + ldapServerName + "/"
				+ rootContext);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, rootdn);
		env.put(Context.SECURITY_CREDENTIALS, rootpass);

		setLdapCtx(new InitialDirContext(env));
		return getLdapCtx();
	}

	public void createUser(Customer customer) throws NamingException {
		this.connect();
		
		Attributes attrs = prepareUserObject(customer);
		try {
			getLdapCtx().bind("cn=" + customer.getEmail(), null, attrs);
			log.info("User created in LDAP server");
		} catch (NameAlreadyBoundException nae) {
			log.severe("User already exists on LDAP server.");
			throw nae;
		} catch (NamingException ne) {
			log.severe("Unknown error occured with LDAP communication");
			throw ne;
		} 
	}

	private Attributes prepareUserObject(Customer customer) {
		Attributes attrs = new BasicAttributes(true);
		Attribute basicObjectClass = new BasicAttribute("objectclass");

		basicObjectClass.add("inetOrgPerson");
		basicObjectClass.add("organizationalPerson");
		basicObjectClass.add("person");
		basicObjectClass.add("top");
		attrs.put(basicObjectClass);
		attrs.put("sn", customer.getEmail());
		attrs.put("userPassword", customer.getPassword());

		return attrs;
	}
}