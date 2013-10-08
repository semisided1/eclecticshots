package com.eclecticshots;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFService {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("eclecticshots");

	private EMFService() {
		
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}