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
	private EntityManager em;

	@Override
	public void init() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		em = emf.createEntityManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		try {
			if ("view".equals(action)) {
				Recipe recipe = getRecipeById(Integer.parseInt(id));
				request.setAttribute("recipe", recipe);
				getServletContext().getRequestDispatcher("/viewRecipe.jsp").forward(request, response);
			} else if ("edit".equals(action)) {
				Recipe recipe = getRecipeById(Integer.parseInt(id));
				request.setAttribute("recipe", recipe);
				getServletContext().getRequestDispatcher("/addRecipe.jsp").forward(request, response);
			} else if ("delete".equals(action)) {
				Recipe recipeToDelete = getRecipeById(Integer.parseInt(id));
				deleteRecipe(recipeToDelete);
				getServletContext().getRequestDispatcher("/manageRecipes.jsp").forward(request, response);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Initialize helpers
		RecipeHelper rh = new RecipeHelper(em);
		IngredientHelper ih = new IngredientHelper(em);

		// Get parameters from the form
		String recipeId = request.getParameter("recipeId");
		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		String[] ingredientItems = request.getParameterValues("ingredients");
		String[] instructions = request.getParameterValues("instructions");

		// Create a list to hold ingredients
		List<Ingredient> ingredients = new ArrayList<>();
		if (ingredientItems != null) {
			for (String item : ingredientItems) {
				if (item != null && !item.isEmpty()) {
					String[] parts = item.split("\\|");
					if (parts.length > 0) {
						String name = parts[0];
						Ingredient ingredient = null;
						try {
							ingredient = ih.findIngredientByName(name);
						} catch (DatabaseAccessException e) {
							e.printStackTrace();
							getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
						}
						if (ingredient == null) {
							ingredient = new Ingredient(name);
							try {
								ih.insertIngredient(ingredient);
							} catch (DatabaseAccessException e) {
								e.printStackTrace();
								getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
							}
						}
						ingredients.add(ingredient);
					}
				}
			}
		}

		// Combine instructions into a single string
		String instructionText = "";
		if (instructions != null && instructions.length > 0) {
			instructionText = String.join("\n", instructions);
		}

		// Get or create the category
		CategoryHelper ch = new CategoryHelper(em);
		Category cat = null;
		try {
			cat = ch.getCategoryByName(category);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
		if (cat == null) {
			cat = new Category(category);
			try {
				ch.addCategory(cat);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
				getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
			}
		}

		// Create or update the recipe
		Recipe recipe = new Recipe(recipeName, servings, prepTime, cat, instructionText);
		recipe.setIngredients(ingredients);

		try {
			if (recipeId == null || recipeId.isEmpty()) {
				rh.insertRecipe(recipe, ingredients);
			} else {
				recipe.setId(Integer.parseInt(recipeId));
				rh.updateRecipe(recipe);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		getServletContext().getRequestDispatcher("/manageRecipes.jsp").forward(request, response);
	}

	private Recipe getRecipeById(int id) throws DatabaseAccessException {
		try {
            return em.find(Recipe.class, id);
		} catch (Exception e) {
			throw new DatabaseAccessException("Error getting recipe by ID: " + e.getMessage());
		}
	}

	private void deleteRecipe(Recipe toDelete) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			Recipe result = em.find(Recipe.class, toDelete.getId());
			em.remove(result);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new DatabaseAccessException("Error deleting recipe: " + e.getMessage());
		}
	}
}
