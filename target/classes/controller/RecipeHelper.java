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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Recipe;

public class RecipeHelper {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");
	
	public RecipeHelper(EntityManager em) {
	}

	public void insertRecipe(Recipe recipe) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(recipe);
		em.getTransaction().commit();
		em.close();
	}

	public void updateRecipe(Recipe updatedRecipe) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(updatedRecipe);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteRecipe(Recipe toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Recipe result = em.find(Recipe.class, toDelete.getId());
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	public List<Recipe> showAllRecipes() {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Recipe> typedQuery = em.createQuery("SELECT r FROM Recipe r", Recipe.class);
		List<Recipe> allRecipes = typedQuery.getResultList();
		em.close();
		return allRecipes;
	}

	public Recipe getRecipeById(int id) {
		EntityManager em = emfactory.createEntityManager();
		Recipe found = em.find(Recipe.class, id);
		em.close();
		return found;
	}

	public List<Recipe> searchForRecipeByTitle(String recipeName) {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Recipe> typedQuery = em.createQuery("SELECT rb FROM Recipe rb WHERE rb.name = :selectedName",
				Recipe.class);
		typedQuery.setParameter("selectedName", recipeName);
		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	public List<Recipe> searchForRecipeByCategory(String categoryName) {
		EntityManager em = emfactory.createEntityManager();
		TypedQuery<Recipe> typedQuery = em
				.createQuery("SELECT rb FROM Recipe rb WHERE rb.category.name = :selectedCategory", Recipe.class);
		typedQuery.setParameter("selectedCategory", categoryName);
		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}
}
