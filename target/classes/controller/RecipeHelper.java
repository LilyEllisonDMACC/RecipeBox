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
import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

// This class is used to perform CRUD operations on the Recipe table
public class RecipeHelper {

	private final EntityManager em;

	public RecipeHelper(EntityManager em) {
		this.em = em;
	}

	private void beginTransaction() {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
	}

	private void commitTransaction() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public void insertRecipe(Recipe recipe, List<Ingredient> ingredients) throws DatabaseAccessException {
		try {
			// Begin a new transaction
			beginTransaction();

			// Set the ingredients for the recipe
			recipe.setIngredients(ingredients);

			// Persist the recipe
			em.persist(recipe);

			// Commit the transaction
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new DatabaseAccessException("Error inserting recipe: " + e.getMessage());
		}
	}

	// Updates a recipe in the database
	public void updateRecipe(Recipe updatedRecipe) throws DatabaseAccessException {
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

	// Deletes a recipe from the database
	public void deleteRecipe(Recipe toDelete) throws DatabaseAccessException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			Recipe result = em.find(Recipe.class, toDelete.getId());
			em.remove(result);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
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

	// Retrieves a recipe by its ID
	public Recipe getRecipeById(int id) throws DatabaseAccessException {
		try {
			Recipe found = em.find(Recipe.class, id);
			return found;
		} catch (Exception e) {
			throw new DatabaseAccessException("Error getting recipe by ID: " + e.getMessage());
		}
	}

	// Searches for recipes by their name
	public List<Recipe> searchForRecipeByTitle(String recipeName) throws DatabaseAccessException {
		try {
			TypedQuery<Recipe> typedQuery = em
					.createQuery("SELECT rb FROM Recipe rb WHERE LOWER(rb.name) = :selectedName", Recipe.class);
			typedQuery.setParameter("selectedName", recipeName.toLowerCase());

			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by title: " + e.getMessage());
		}
	}

	// Searches for recipes by their category
	public List<Recipe> searchForRecipeByCategory(String categoryName) throws DatabaseAccessException {
		try {
			CategoryHelper categoryHelper = new CategoryHelper(em);
			// Capitalize the first letter of the category name
			categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
			Category category = categoryHelper.getCategoryByName(categoryName);
			if (category == null) {
				return new ArrayList<>(); // return an empty list if the category is not found
			}

			// Create a query that searches for recipes that have the specified category
			TypedQuery<Recipe> typedQuery = em
					.createQuery("SELECT rb FROM Recipe rb WHERE rb.category = :selectedCategory", Recipe.class);
			typedQuery.setParameter("selectedCategory", category);

			// Return the results of the query
			return typedQuery.getResultList();
		} catch (Exception e) {
			throw new DatabaseAccessException("Error searching for recipe by category: " + e.getMessage());
		}
	}

	// Searches for recipes by their ingredient
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

	// Closes the EntityManager
	public void closeEntityManager() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
