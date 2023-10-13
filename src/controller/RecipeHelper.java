/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 6, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 6, 2023
 */

package controller;

import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

// This class is used to perform CRUD operations on the Recipe table
public class RecipeHelper {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");



	// Inserts a new recipe into the database
	public void insertRecipe(Recipe recipe) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(recipe);
		em.getTransaction().commit();
		em.close();
			
	}

	// Updates a recipe in the database
	/**public void updateRecipe(Recipe updatedRecipe) throws DatabaseAccessException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.merge(updatedRecipe);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw new DatabaseAccessException("Error updating recipe: " + e.getMessage());
		}
	}
*/
	// Deletes a recipe from the database
	public void deleteRecipe(Recipe toDelete) {

		EntityManager em = emfactory.createEntityManager();
		EntityTransaction tx = null;
		tx = em.getTransaction();
		tx.begin();
		Recipe result = em.find(Recipe.class, toDelete.getId());
		em.remove(result);
		tx.commit();
	}
	

	// Retrieves a list of all recipes from the database
	public List<Recipe> showAllRecipes() {
		EntityManager em = emfactory.createEntityManager();
		List<Recipe> allRecipes = em.createQuery("SELECT r FROM Recipe r").getResultList();
		em.close();
		return allRecipes;
	}

	// Retrieves a recipe by its ID
	public Recipe getRecipeById(int id) {
		EntityManager em = emfactory.createEntityManager();
		Recipe found = em.find(Recipe.class, id);
		em.close();
		return found;
	}

	// Searches for recipes by their name
	public List<Recipe> searchForRecipeByTitle(String recipeName) {
		EntityManager em = emfactory.createEntityManager();
		try {
			TypedQuery<Recipe> typedQuery = em.createQuery("SELECT rb FROM Recipe rb WHERE rb.name = :selectedName",
					Recipe.class);
			typedQuery.setParameter("selectedName", recipeName);

			return typedQuery.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Searches for recipes by their category
	public List<Recipe> searchForRecipeByCategory(String categoryName) {
		EntityManager em = emfactory.createEntityManager();
		List<Recipe> recipeByCatList;
		try {
			CategoryHelper categoryHelper = new CategoryHelper();
			// Capitalize the first letter of the category name
			categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
			Category category = categoryHelper.getCategoryByName(categoryName);
			
			// Create a query that searches for recipes that have the specified category
			TypedQuery<Recipe> typedQuery = em
					.createQuery("SELECT rb FROM Recipe rb WHERE rb.category = :selectedCategory", Recipe.class);
			typedQuery.setParameter("selectedCategory", category);

			// Return the results of the query
			recipeByCatList = typedQuery.getResultList();
		} catch (NoResultException e) {
			recipeByCatList = null;
		}
		em.close();
		return recipeByCatList;
	}

	// Searches for recipes by their ingredient
	/**
	public List<Recipe> searchForRecipeByIngredient(String ingredientName) throws DatabaseAccessException {
		try {
			IngredientHelper ingredientHelper = new IngredientHelper(em);
			// Capitalize the first letter of the ingredient name
			ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1).toLowerCase();
			Ingredient ingredient = ingredientHelper.findIngredientByName(ingredientName);
			if (ingredient == null) {
				return new ArrayList<>(); // return an empty list if the ingredient is not found
			}
			// Create a query that searches for recipes that contain the specified
			// ingredient
			TypedQuery<Recipe> typedQuery = em.createQuery(
					"SELECT rb FROM Recipe rb WHERE :selectedIngredient MEMBER OF rb.ingredients", Recipe.class);
			typedQuery.setParameter("selectedIngredient", ingredient);

			// Return the results of the query
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
*/
	// Closes the EntityManager
	public void closeEntityManager() {
		EntityManager em = emfactory.createEntityManager();
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
