/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 13, 2023
 * 
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 13, 2023
 */

package controller;

import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

// Class that handles database access for Ingredient objects
public class IngredientHelper {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");
	// EntityManager object that allows access to the database
	//private final EntityManager em;

	// Constructor that initializes the EntityManager
	//public IngredientHelper(EntityManager em) {
		//this.em = em;
	//}

	// Constructor that initializes the EntityManager
	public IngredientHelper(EntityManager em) {
	}

	// Inserts a new ingredient into the database
	public void insertIngredient(Ingredient toAdd) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(toAdd);
		em.getTransaction().commit();
	}

	// Updates an existing ingredient
	public void updateIngredient(Ingredient toUpdate) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toUpdate);
		em.getTransaction().commit();
	}

	// Edits an existing ingredient
	public void editIngredient(Ingredient toEdit, String newName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		toEdit.setName(newName);
		em.merge(toEdit);
		em.getTransaction().commit();
		
	}

	// Deletes an ingredient from the database
	public void deleteIngredient(Ingredient toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Ingredient.class, toDelete.getId()));
		em.getTransaction().commit();
	}

	// Checks if an ingredient is used in other recipes
	public boolean isIngredientUsedInOtherRecipes(Ingredient ingredient) {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r WHERE :ingredient MEMBER OF r.ingredients",
				Recipe.class);
		query.setParameter("ingredient", ingredient);
		return !query.getResultList().isEmpty();
	}

	// Gets a list of recipes that use a given ingredient
	public List<Recipe> getRecipesUsingIngredient(Ingredient ingredient) {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Recipe> query = em.createQuery("SELECT r FROM Recipe r WHERE :ingredient MEMBER OF r.ingredients",
				Recipe.class);
		query.setParameter("ingredient", ingredient);
		return query.getResultList();
	}

	// Retrieves all ingredients from the database
	public List<Ingredient> showAllIngredients() {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i", Ingredient.class);
		return query.getResultList();
	}

	// Finds an ingredient by its name
	public Ingredient findIngredientByName(String name) {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i WHERE LOWER(i.name) = :name",
				Ingredient.class);
		query.setParameter("name", name.toLowerCase());
		List<Ingredient> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}
}
