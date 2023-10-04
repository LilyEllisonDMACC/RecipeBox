/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

/**
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Recipe;

public class RecipeHelper {

	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");

	// Inserts a new recipe into the database
	public void insertRecipe(Recipe toAdd) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(toAdd);
		em.getTransaction().commit();
		em.close();
	}

	// Deletes a recipe from the database
	public void deleteRecipe(Recipe toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Recipe result = em.find(Recipe.class, toDelete.getId());
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	// Updates an existing recipe in the database
	public void updateRecipe(Recipe toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
	}

	// Searches for a recipe by its ID
	public Recipe searchForRecipeById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Recipe found = em.find(Recipe.class, idToEdit);
		em.close();
		return found;
	}

	// Retrieves a list of all recipes from the database
	public List<Recipe> showAllRecipes() {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Recipe> typedQuery = em.createQuery("SELECT r FROM Recipe r", Recipe.class);
		List<Recipe> allRecipes = typedQuery.getResultList();

		em.getTransaction().commit();
		em.close();

		return allRecipes;
	}

	// Searches for recipes by their name
	public List<Recipe> searchForRecipeByTitle(String recipeName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em.createQuery("SELECT rb FROM Recipe rb WHERE rb.name = :selectedName",
				Recipe.class);
		typedQuery.setParameter("selectedName", recipeName);

		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	// Searches for recipes by their category
	public List<Recipe> searchForRecipeByCategory(String category) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em
				.createQuery("SELECT rb FROM Recipe rb WHERE :selectedCategory MEMBER OF rb.categories", Recipe.class);
		typedQuery.setParameter("selectedCategory", category);

		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	// Searches for recipes by their ingredient
	public List<Recipe> searchForRecipeByIngredient(String ingredient) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em.createQuery(
				"SELECT rb FROM Recipe rb WHERE :selectedIngredient MEMBER OF rb.ingredients", Recipe.class);
		typedQuery.setParameter("selectedIngredient", ingredient);

		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	// Searches for recipes by their serving size
	public List<Recipe> searchForRecipeByServingSize(int servingSize) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em
				.createQuery("SELECT rb FROM Recipe rb WHERE rb.servings = :selectedServingSize", Recipe.class);
		typedQuery.setParameter("selectedServingSize", servingSize);

		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	// Closes the EntityManagerFactory to release resources
	public void cleanUp() {
		emfactory.close();
	}
}
