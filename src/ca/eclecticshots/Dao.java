package ca.eclecticshots;

import ca.eclecticshots.model.*;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.*;

public enum Dao {
	INSTANCE;

	
	public String xml() {
		StringBuilder out = new StringBuilder();
		
		List<ECAlbum> albums = listECAlbums();
		List<ECPhoto> photos = listECPhotos();
		
		out.append("<ecdata>");
		
		
		for (ECAlbum album : albums) {
			out.append( album.toString() );
		}
		
		
		for (ECPhoto photo : photos) {
			out.append(photo.toString());
		}
	
		out.append( "</ecdata>" );
		
		return out.toString();
			 
	}
	/*
	Query q = em
			.createQuery("select t from POOrder t where t.customeremail = :customeremail order by orderdate desc");
	q.setParameter("customeremail", customeremail);
	*/
	 public ECAlbum getECAlbumbyName(String name) {
		 EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select m from ECAlbum m where m.name = :name");
		 	q.setParameter("name", name);
			//List<ECAlbum> le = q.getResultList(); 
			//ECAlbum e = (ECAlbum) le.get(0);  
		 	ECAlbum e = (ECAlbum) q.getSingleResult();
			return e;
	 }
		
	public List<ECAlbum> listECAlbums() {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries
		Query q = em.createQuery("select m from ECAlbum m");
		List<ECAlbum> ECAlbums = q.getResultList();
		return ECAlbums;
	}

	public void addECAlbum(String name, String cover, String description ) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			ECAlbum ecalbum = new ECAlbum(  name, cover, description );
			em.persist(ecalbum);
			em.close();
		}
	}

	public void removeECAlbum(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
		ECAlbum ecAlbum = em.find(ECAlbum.class, id);
			em.remove(ecAlbum);
		} finally {
			em.close();
		}
	}
	

		
	public List<ECPhoto> listECPhotos() {
		EntityManager em = EMFService.get().createEntityManager();
		// Read the existing entries
		Query q = em.createQuery("select m from ECPhoto m");
		List<ECPhoto> ECPhotoss = q.getResultList();
		return ECPhotoss;
	}

	public void addECPhoto(String photourl, String notes, String albumname ) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			ECPhoto ecphoto = new ECPhoto( photourl,  notes, albumname);
			em.persist(ecphoto);
			em.close();
		}
	}

	public List<ECPhoto> getECPhotos(String albumname) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from ECPhoto t where t.albumname = :albumname");
		q.setParameter("albumname", albumname);
		List<ECPhoto> ecPhotos = q.getResultList();
		return ecPhotos;
	}

	public void removeECPhoto(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			ECPhoto ecPhoto = em.find(ECPhoto.class, id);
			em.remove(ecPhoto);
		} finally {
			em.close();
		}
	}
	
	
	
}




