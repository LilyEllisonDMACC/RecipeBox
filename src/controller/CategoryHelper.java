/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 5, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 5, 2023
 */

package controller;

import model.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

// This class is used to perform CRUD operations on the Category table
public class CategoryHelper {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");

    // Inserts a new category into the database
    public void addCategory(Category category) {
    	EntityManager em = emfactory.createEntityManager();
    	em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
        em.close();        
    }

    // Edits a category in the database
    public void updateCategory(Category existingCategory)  {
    	EntityManager em = emfactory.createEntityManager();
    	EntityTransaction tx = null;
       
            tx = em.getTransaction();
            tx.begin();
            em.merge(existingCategory);
            tx.commit();
       
    }
    

    // Deletes a category from the database
    public void deleteCategory(Category category)  {
    	EntityManager em = emfactory.createEntityManager();
    	EntityTransaction tx = null;
       
            tx = em.getTransaction();
            tx.begin();
            Category foundCategory = em.find(Category.class, category.getId());
            em.remove(foundCategory);
            tx.commit();
       
    }
    

    // Retrieves a list of all categories from the database
    public List<Category> getAllCategories() {
    	EntityManager em = emfactory.createEntityManager();
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
    }

    // Retrieves a category by its ID
    public Category getCategoryById(int id)  {
    	EntityManager em = emfactory.createEntityManager();
    	
            Category found = em.find(Category.class, id);
            return found;
        
    }

    // Retrieves a category by its name, or returns null if not found
    public Category getCategoryByName(String categoryName) {
    	EntityManager em = emfactory.createEntityManager();
    	em.getTransaction().begin();
    	TypedQuery<Category> typedQuery = em.createQuery("SELECT c FROM Category c WHERE LOWER(c.name) = :selectedName", Category.class);
            typedQuery.setParameter("selectedName", categoryName.toLowerCase());
            typedQuery.setMaxResults(1);
            
            Category foundCategory;
            try {
            	foundCategory = typedQuery.getSingleResult();
            } catch (NoResultException ex) {
            	foundCategory = new Category(categoryName);
            }
            em.close();

            return foundCategory; // Return the first matching category
            
        
    }

}
