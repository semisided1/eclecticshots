package com.eclecticshots;

import java.util.List;
import java.util.LinkedList;

import javax.persistence.*;

import com.eclecticshots.model.*;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import java.util.logging.Level;


@SuppressWarnings("unchecked")
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
	public ECAlbum getECAlbumbyName(String name) { synchronized (this) {
		EntityManager em = null;
		ECAlbum e = null;
		try {
			em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			Query q = em.createQuery("select m from ECAlbum m where m.name = :name");
			q.setParameter("name", name);
			e = (ECAlbum) q.getSingleResult();
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return e;
	}}
	
	public ECAlbum getECAlbum(String id) { synchronized (this) {
		EntityManager em = null;
		ECAlbum e = null;
		try {
			em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			e = em.find(ECAlbum.class, Long.parseLong(id));
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return e;
	}}	
	
public void swap(String aid, String bid ) {
	synchronized (this){
		
		invalidate("listAlbums");
		
		 EntityManager em = EMFService.get().createEntityManager();
		 try{
			 em.getTransaction().begin();
			 //find 
			 ECAlbum eca = em.find(ECAlbum.class, Long.parseLong(aid));
			 ECAlbum ecb = em.find(ECAlbum.class, Long.parseLong(bid));
			 
			 //set
			 String s = eca.getAorder();			 
			 eca.setAorder(ecb.getAorder());
			 em.persist(eca);
			 
			 ecb.setAorder(s);
			 em.persist(ecb);
			 em.flush();
			 em.getTransaction().commit();
			 
			 // trying to force a reload of the backend datastore into
			 // memory cache, this is not working and its an issue for later
			 // live with write latency on sdk for now, we will see if HRD has same issue
			 
			// em.getTransaction().begin();
			// em.refresh(eca);
			 //em.getTransaction().commit();
			// em.close();
		 }
		 catch(Exception e){
			 System.out.println(e.getMessage());
		 }
	 }} 


private void invalidate(String key) {		
	MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

    if (key.compareTo("all") == 0) {
    	syncCache.clearAll();
    	return;
    }
    syncCache.delete(key);
}	
	
public List<ECAlbum> listECAlbums() {
	synchronized (this) {
	
		String key = "listAlbums";

		LinkedList<ECAlbum> serList = null;	
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    serList = (LinkedList<ECAlbum>) syncCache.get(key);
		
	    if (serList != null) {
	        return serList;
	      }
		
		
	List<ECAlbum> ECAlbums = null;
	EntityManager em = EMFService.get().createEntityManager();
	try{
		em.getTransaction().begin();
		Query q = em.createQuery("select m from ECAlbum m order by aorder");
		ECAlbums = q.getResultList();
		em.getTransaction().commit();
		em.close();
	} catch(Exception e){
		System.out.println(e.getMessage());
	}
	
	serList = new LinkedList<ECAlbum>();
	for (ECAlbum a:ECAlbums) {
		serList.add(a);
	}
	syncCache.put(key, serList); // populate cache
	
	
	return ECAlbums;
	}
}

public void addECAlbum(String name, String coverurl, String picasaurl,String feedurl, String aorder ) {
	synchronized (this) {
		
		invalidate("all");
		
		EntityManager em = EMFService.get().createEntityManager();
		try{
			em.getTransaction().begin();
			ECAlbum ecalbum = new ECAlbum(name,coverurl,picasaurl,feedurl,aorder);
			em.persist(ecalbum);
			em.flush();
			em.getTransaction().commit();
			em.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}

public void removeECAlbum(long id) { synchronized (this) {
	try {
		
		invalidate("all");
		
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		ECAlbum ecAlbum = em.find(ECAlbum.class, id);
		em.remove(ecAlbum);
		em.getTransaction().commit();
		em.close();
	} catch(Exception e){
		System.out.println(e.getMessage());
	}
}}
	
public void removeAlbumCascade(String albumname, String id){ synchronized (this) {
	try {	
		
		invalidate("all");
		
		EntityManager em = EMFService.get().createEntityManager();	
		em.getTransaction().begin();
		Query q = em
				.createQuery("select t from ECPhoto t where t.albumname = :albumname");
		q.setParameter("albumname", albumname);
		List<ECPhoto> ecPhotos = q.getResultList();
		for ( ECPhoto p : ecPhotos) {
			em.remove(p);
			//removeECPhoto(p.getId());
		}		
		//ECAlbum todel = getECAlbumbyName(albumname);
		removeECAlbum( Long.parseLong(id));
		em.getTransaction().commit();
		em.close();
	} catch(Exception e){
		System.out.println(e.getMessage());
	}
}
}

public void clearAlbum(String albumname) { synchronized (this) {
try {

	invalidate("all");
	
	EntityManager em = EMFService.get().createEntityManager();	
	em.getTransaction().begin();
	Query q = em
			.createQuery("select t from ECPhoto t where t.albumname = :albumname");
	q.setParameter("albumname", albumname);
	List<ECPhoto> ecPhotos = q.getResultList();
	for ( ECPhoto p : ecPhotos) {
		em.remove(p);
		//removeECPhoto(p.getId());
	}		
	
	
} catch (Exception e) {
	System.out.println(e.getMessage());
	e.printStackTrace(System.out);
}
	
	
}
}

		
public List<ECPhoto> listECPhotos() { synchronized (this) {
	List<ECPhoto> ECPhotoss = null;
	try {	
		EntityManager em = EMFService.get().createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select m from ECPhoto m");
		ECPhotoss = q.getResultList();
		em.getTransaction().commit();
		em.close();
	} catch(Exception e){
		System.out.println(e.getMessage());
	}		
	return ECPhotoss;
}
}

public void addECPhoto(String photourl, String notes, String albumname ) { 
	synchronized (this) {
		try {
			EntityManager em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			ECPhoto ecphoto = new ECPhoto( photourl,  notes, albumname);
			em.persist(ecphoto);
			em.getTransaction().commit();
			em.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
}

public List<ECPhoto> getECPhotos(String albumname) { 
	synchronized (this) {
		
		String key = "getPhotos" + albumname;
		LinkedList<ECPhoto> serList = null;
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    serList = (LinkedList<ECPhoto>) syncCache.get(key);
	    
	    if (serList != null) {
	        return serList;
	      }

		
		
		List<ECPhoto> ecPhotos = null;
		try {
			EntityManager em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			Query q = em.createQuery("select t from ECPhoto t where t.albumname = :albumname");
			q.setParameter("albumname", albumname);
			ecPhotos = q.getResultList();
			em.getTransaction().commit();
			em.close();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}	
		
		

		serList = new LinkedList<ECPhoto>();
		for (ECPhoto p:ecPhotos) {
			serList.add(p);
		}
		syncCache.put(key, serList); // populate cache

		
		
		return ecPhotos;
	}
}

public void removeECPhoto(long id) { 
	synchronized (this) {
		
		invalidate("all");
		
		EntityManager em = null;
		try {
			em = EMFService.get().createEntityManager();
			em.getTransaction().begin();
			ECPhoto ecPhoto = em.find(ECPhoto.class, id);
			em.remove(ecPhoto);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}

}



