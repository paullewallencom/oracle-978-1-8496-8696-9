package com.packt.store.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITokenService extends Remote {
	  public static final String JNDI_ENTRY = "TokenService";
	  public String generate() throws RemoteException;
	}

