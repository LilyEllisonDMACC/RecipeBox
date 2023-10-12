package controller;

import exceptions.DatabaseAccessException;
import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/recipeServlet")
public class RecipeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RecipeServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		//EntityManager em = emf.createEntityManager();
		//RecipeHelper rh = new RecipeHelper(em);
		//IngredientHelper ih = new IngredientHelper(em);
		
		
		
		// Initialize EntityManager and RecipeHelper
		EntityManagerFactory emf = null;
		EntityManager em = null;
		RecipeHelper rh = null;
		IngredientHelper ih = null;
		CategoryHelper ch = null;
		try {
			emf = Persistence.createEntityManagerFactory("RecipeBox");
			em = emf.createEntityManager();
			rh = new RecipeHelper(em);
			ih = new IngredientHelper(em);
			ch = new CategoryHelper(em);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get form parameters
		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		Category categoryObj;
		if(category != "New") {
			
			categoryObj = new Category(category);
		} else {
			category = request.getParameter("newCategory");
			categoryObj = ch.getCategoryByName(category);
		}
		String[] ingredientItems = request.getParameterValues("ingredients");
		String[] instructions = request.getParameterValues("instructions");

		// Convert ingredientItems to List<Ingredient>
		List<Ingredient> ingredients = new ArrayList<>();
		for (String item : ingredientItems) {
			String[] parts = item.split("|");
			String name = parts[0];
			// Utilize IngredientHelper
			try {
				Ingredient ingredient = ih.findIngredientByName(name);
				if (ingredient == null) {
					ingredient = new Ingredient(name);
					ih.insertIngredient(ingredient);
				}
				ingredients.add(ingredient);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
			}
		}

		// Convert instructions to single String
		String instructionText = String.join("\n", instructions);

		// Create new Recipe object
		//Category cat = new Category(category); // Assuming you have a way to get the Category object
		Recipe newRecipe = new Recipe(recipeName, servings, prepTime, categoryObj, instructionText);
		newRecipe.setIngredients(ingredients);

		// Insert new Recipe into database
		try {
			rh.insertRecipe(newRecipe, ingredients);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}

		// Close EntityManager
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}

		// Redirect to another page (e.g., viewAllRecipes.jsp)
		getServletContext().getRequestDispatcher("/viewRecipe.jsp").forward(request, response);
	}
}