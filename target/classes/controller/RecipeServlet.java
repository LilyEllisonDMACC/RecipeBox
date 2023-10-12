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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		EntityManagerFactory emf = null;
		EntityManager em = null;
		RecipeHelper rh = null;

		try {
			emf = Persistence.createEntityManagerFactory("RecipeBox");
			em = emf.createEntityManager();
			rh = new RecipeHelper(em);

			if ("view".equals(action)) {
				Recipe recipe = rh.getRecipeById(Integer.parseInt(id));
				request.setAttribute("recipe", recipe);
				getServletContext().getRequestDispatcher("/viewRecipe.jsp").forward(request, response);
			} else if ("edit".equals(action)) {
				Recipe recipe = rh.getRecipeById(Integer.parseInt(id));
				request.setAttribute("recipe", recipe);
				getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(request, response);
			} else if ("delete".equals(action)) {
				Recipe recipeToDelete = rh.getRecipeById(Integer.parseInt(id)); // Retrieve Recipe by ID
				rh.deleteRecipe(recipeToDelete); // Delete using Recipe object
				getServletContext().getRequestDispatcher("/manageRecipes.jsp").forward(request, response);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		} finally {
			// Close EntityManager
			if (em != null && em.isOpen()) {
				em.close();
			}
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Initialize EntityManager and RecipeHelper
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		EntityManager em = emf.createEntityManager();
		RecipeHelper rh = new RecipeHelper(em);
		IngredientHelper ih = new IngredientHelper(em);

		// Get form parameters
		String recipeId = request.getParameter("recipeId");
		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		String[] ingredientItems = request.getParameterValues("ingredients");
		String[] instructions = request.getParameterValues("instructions");

		// Convert ingredientItems to List<Ingredient>
		List<Ingredient> ingredients = new ArrayList<>();
		for (String item : ingredientItems) {
			String[] parts = item.split("\\|");
			String name = parts[0];
			try {
				Ingredient ingredient = ih.findIngredientByName(name);
				if (ingredient == null) {
					ingredient = new Ingredient(name);
					ih.insertIngredient(ingredient);
				}
				ingredients.add(ingredient);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
				getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
			}
		}

		// Convert instructions to single String
		String instructionText = String.join("\n", instructions);

		// Create new Recipe object
		Category cat = new Category(category);
		Recipe recipe = new Recipe(recipeName, servings, prepTime, cat, instructionText);
		recipe.setIngredients(ingredients);

		try {
			if (recipeId == null || recipeId.isEmpty()) {
				// Insert new Recipe into database
				rh.insertRecipe(recipe, ingredients);
			} else {
				// Update existing Recipe
				recipe.setId(Integer.parseInt(recipeId));
				rh.updateRecipe(recipe);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		// Close EntityManager
		em.close();
		emf.close();

		// Redirect to another page (e.g., viewAllRecipes.jsp)
		getServletContext().getRequestDispatcher("/viewAllRecipes.jsp").forward(request, response);
	}
}
