/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 6, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 6, 2023
 */

package controller;

import exceptions.DatabaseAccessException;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

// Class that handles database access for Ingredient objects
public class IngredientHelper {

	// EntityManager object that allows access to the database
	private final EntityManager em;

	// Constructor that initializes the EntityManager
	public IngredientHelper(EntityManager em) {
		this.em = em;
	}

	// Inserts a new ingredient into the database
	public void insertIngredient(Ingredient toAdd) throws DatabaseAccessException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(toAdd);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DatabaseAccessException("Error inserting ingredient: " + e.getMessage());
		}
	}

	// Updates an existing ingredient
	public void updateIngredient(Ingredient toUpdate) throws DatabaseAccessException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(toUpdate);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DatabaseAccessException("Error updating ingredient: " + e.getMessage());
		}
	}

	// Edits an existing ingredient
	public void editIngredient(Ingredient toEdit, String newName) throws DatabaseAccessException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			toEdit.setName(newName);
			em.merge(toEdit);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DatabaseAccessException("Error editing ingredient: " + e.getMessage());
		}
	}

	// Deletes an ingredient from the database
	public void deleteIngredient(Ingredient toDelete) throws DatabaseAccessException {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.remove(em.find(Ingredient.class, toDelete.getId()));
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DatabaseAccessException("Error deleting ingredient: " + e.getMessage());
		}
	}

	// Checks if an ingredient is used in other recipes
	public boolean isIngredientUsedInOtherRecipes(Ingredient ingredient) throws DatabaseAccessException {
		TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r WHERE :ingredient MEMBER OF r.ingredients",
				Recipe.class);
		query.setParameter("ingredient", ingredient);
		return !query.getResultList().isEmpty();
	}

	// Retrieves all ingredients from the database
	public List<Ingredient> showAllIngredients() throws DatabaseAccessException {
		TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i", Ingredient.class);
		return query.getResultList();
	}

	// Finds an ingredient by its name
	public Ingredient findIngredientByName(String name) throws DatabaseAccessException {
		TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i WHERE LOWER(i.name) = :name",
				Ingredient.class);
		query.setParameter("name", name.toLowerCase());
		List<Ingredient> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}
}
