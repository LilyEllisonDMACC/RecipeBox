/**
 * @author tehli - lbellison
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

/**
 * @author LILY ELLISON - LBELLISON
 * CIS175 - FALL 2023
 * Oct 2, 2023
 */
public class RecipeHelper {

	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("RecipeBox");

	/**
	 * @param toAdd
	 */
	public void insertRecipe(Recipe toAdd) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(toAdd);
		em.getTransaction().commit();
		em.close();	
		
	}

	/**
	 * @param toDelete
	 */
	public void deleteRecipe(Recipe toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em.createQuery("select rb from Recipe rb where rb.name = :selectedName and rb.id = :selectedId", Recipe.class);
		typedQuery.setParameter("selectedName", toDelete.getName());
		typedQuery.setParameter("selectedId", toDelete.getId());
		
		typedQuery.setMaxResults(1);
		
		Recipe result = typedQuery.getSingleResult();
		
		em.remove(result);
		em.getTransaction().commit();
		em.close();
		
	}

	/**
	 * @param idToEdit
	 * @return
	 */
	public Recipe searchForRecipeById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Recipe found = em.find(Recipe.class, idToEdit);
		em.close();
		return found;
	}

	/**
	 * @param recipeName
	 * @return
	 */
	public List<Recipe> searchForRecipeByTitle(String recipeName) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em.createQuery("select rb from Recipe rb where rb.name = :selectedName", Recipe.class);
		typedQuery.setParameter("selectedName", recipeName);
		
		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}

	/**
	 * @param recipeType
	 * @return
	 */
	/**
	public List<Recipe> searchForRecipeByType(String recipeType) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Recipe> typedQuery = em.createQuery("select rb from Recipe rb where rb.type = :selectedType", Recipe.class);
		typedQuery.setParameter("selectedType", recipeType);
		
		List<Recipe> foundRecipes = typedQuery.getResultList();
		em.close();
		return foundRecipes;
	}
	*/

	/**
	 * @param toEdit
	 */
	public void updateRecipe(Recipe toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
		
	}

	/**
	 * 
	 */
	public void cleanUp() {
		emfactory.close();
	}

	/**
	 * @return
	 */
	public List<Recipe> showAllRecipes() {
		EntityManager em = emfactory.createEntityManager();
		List<Recipe> allRecipes = em.createQuery("SELECT a FROM Recipe a").getResultList();
		return allRecipes;
	}
}
