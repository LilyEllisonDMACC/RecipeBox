/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 *
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import exceptions.DatabaseAccessException;
import model.Recipe;

public class RecipeHelper {

	private final EntityManager em;

	// Constructor that takes an EntityManager as a parameter
	public RecipeHelper(EntityManager em) {
		this.em = em;
	}

	// Inserts a new recipe into the database
	public void insertRecipe(Recipe toAdd) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			// Insert data into the database
			em.persist(toAdd);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DatabaseAccessException("Error inserting recipe: " + e.getMessage());
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	// Updates a recipe in the database
	public void updateRecipe(Recipe updatedRecipe) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			em.merge(updatedRecipe); // Use merge to update the existing recipe
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DatabaseAccessException("Error updating recipe: " + e.getMessage());
		}
	}

	// Deletes a recipe from the database
	public void deleteRecipe(Recipe toDelete) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			Recipe result = em.find(Recipe.class, toDelete.getId());
			em.remove(result);
			em.getTransaction().commit();

			// Retrieve all recipes ordered by their IDs
			TypedQuery<Recipe> renumberQuery = em.createQuery("SELECT r FROM Recipe r ORDER BY r.id", Recipe.class);
			List<Recipe> recipesToRenumber = renumberQuery.getResultList();

			// Renumber recipes consecutively starting from 1
			int newId = 1;
			em.getTransaction().begin();
			for (Recipe recipeToRenumber : recipesToRenumber) {
				recipeToRenumber.setId(newId++);
				em.merge(recipeToRenumber);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DatabaseAccessException("Error deleting recipe: " + e.getMessage());
		}
	}

	// Retrieves a list of all recipes from the database
	public List<Recipe> showAllRecipes() throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em.createQuery("SELECT r FROM Recipe r", Recipe.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error retrieving recipes: " + e.getMessage());
		}
	}

	// Searches for recipes by their name
	public List<Recipe> searchForRecipeByTitle(String recipeName) throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em.createQuery("SELECT rb FROM Recipe rb WHERE rb.name = :selectedName",
					Recipe.class);
			typedQuery.setParameter("selectedName", recipeName);

			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by title: " + e.getMessage());
		}
	}

	// Searches for recipes by their category
	public List<Recipe> searchForRecipeByCategory(String category) throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em
					.createQuery("SELECT rb FROM Recipe rb WHERE rb.category = :selectedCategory", Recipe.class);
			typedQuery.setParameter("selectedCategory", category);

			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by category: " + e.getMessage());
		}
	}

	// Searches for recipes by their ingredient
	public List<Recipe> searchForRecipeByIngredient(String ingredient) throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em.createQuery(
					"SELECT rb FROM Recipe rb WHERE :selectedIngredient MEMBER OF rb.ingredients", Recipe.class);
			typedQuery.setParameter("selectedIngredient", ingredient);

			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by ingredient: " + e.getMessage());
		}
	}

	// Searches for recipes by their serving size
	public List<Recipe> searchForRecipeByServingSize(int servingSize) throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em
					.createQuery("SELECT rb FROM Recipe rb WHERE rb.servings = :selectedServingSize", Recipe.class);
			typedQuery.setParameter("selectedServingSize", servingSize);

			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by serving size: " + e.getMessage());
		}
	}

	// Closes the EntityManager
	public void closeEntityManager() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
