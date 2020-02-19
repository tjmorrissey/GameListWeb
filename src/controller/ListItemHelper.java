package controller;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.ListItem;

public class ListItemHelper {

	static	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("GameList");

	public void insertItem(ListItem li) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(li);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<ListItem> showAllItems() {
		
		EntityManager em = emfactory.createEntityManager();
		List<ListItem> allItems = em.createQuery("SELECT i FROM ListItem i").getResultList();
		
		return allItems;	
	}
	
	public void deleteItem(ListItem	toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.name = :selectedGame", ListItem.class);
		
		//Substitute parameter with actual data	from the toDelete item
		typedQuery.setParameter("selectedGame", toDelete.getName());
		
		//we only want one result
		typedQuery.setMaxResults(1);
		
		//get the result and save it into a new list item
		ListItem result = typedQuery.getSingleResult();
		
		//remove it
		em.remove(result);
		em.getTransaction().commit();
		em.close();
		}

	public ListItem searchForItemById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		ListItem found = em.find(ListItem.class, idToEdit);
		
		em.close();
		
		return found;
	}

	public void updateItem(ListItem toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		em.merge(toEdit);
		
		em.getTransaction().commit();
		em.close();
		
	}

	public List<ListItem> searchForItemByName(String gameName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<ListItem> typedQuery = em.createQuery("select li	from ListItem li where li.name = :selectedGame", ListItem.class);
		typedQuery.setParameter("selectedGame", gameName);
		List<ListItem> foundItems = typedQuery.getResultList();
		
		em.close();
		
		return	foundItems;
	}
	
	public void cleanUp() {
		emfactory.close();
		}
	
}
