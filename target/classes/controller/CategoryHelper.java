/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 5, 2023
 *
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 5, 2023
 */

package controller;

import exceptions.DatabaseAccessException;
import model.Category;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// This class is used to perform CRUD operations on the Category table
public class CategoryHelper {

	private final EntityManager em;

	public CategoryHelper(EntityManager em) {
		this.em = em;
	}

	// Inserts a new category into the database
	public void addCategory(Category category) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			// Insert data into the database
			em.persist(category);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DatabaseAccessException("Error adding category: " + e.getMessage());
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	// Deletes a category from the database
	public void deleteCategory(Category category) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			Category foundCategory = em.find(Category.class, category.getId());
			em.remove(foundCategory);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DatabaseAccessException("Error deleting category: " + e.getMessage());
		}
	}

	// Retrieves a list of all categories from the database
	public List<Category> getAllCategories() throws DatabaseAccessException {
		try {
			TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error retrieving categories: " + e.getMessage());
		}
	}

	// Retrieves a category by its name, or returns null if not found
	public Category getCategoryByName(String categoryName) throws DatabaseAccessException {
		try {
			TypedQuery<Category> typedQuery = em.createQuery("SELECT c FROM Category c WHERE c.name = :selectedName",
					Category.class);
			typedQuery.setParameter("selectedName", categoryName);
			List<Category> categories = typedQuery.getResultList();

			if (categories.isEmpty()) {
				return null; // Category not found
			} else {
				return categories.get(0); // Return the first matching category
			}
		} catch (Exception e) {
			throw new DatabaseAccessException("Error getting category by name: " + e.getMessage());
		}
	}
}
