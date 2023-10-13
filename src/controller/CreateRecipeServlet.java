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

@WebServlet("/createRecipeServlet")
public class CreateRecipeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CreateRecipeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		EntityManager em = emf.createEntityManager();
		RecipeHelper rh = new RecipeHelper();
		IngredientHelper ih = new IngredientHelper();
		CategoryHelper ch = new CategoryHelper();
		
		
		
		// Initialize EntityManager and RecipeHelper
		/**EntityManagerFactory emf = null;
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
*/
		// Get form parameters
		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		Category categoryObj;
		if(category.equals("New")) {
			category = request.getParameter("newCategory");
			categoryObj = new Category(category);
		} else {
			
			categoryObj = ch.getCategoryByName(category);
		}
		String ingredientBlock = request.getParameter("ingredients");
		//String[] ingredientAmounts = request.getParameterValues("ingredientAmt");
		String instructionsBlock = request.getParameter("instructions");

		String[] ingredientsSeparate = ingredientBlock.split("[,]", 0);
		String[] instructionsSeparate = instructionsBlock.split("[,]", 0);
		// Convert ingredientItems to List<Ingredient>
		List<Ingredient> ingredientsList = new ArrayList<>();
		
		
		for(int i=0; i<ingredientsSeparate.length; i++) {
			Ingredient j = new Ingredient(ingredientsSeparate[i]);
			ingredientsList.add(j);
		}
		
		/**
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
*/
		// Convert instructions to single String
		String instructionText = String.join("\n", instructionsSeparate);

		// Create new Recipe object
		//Category cat = new Category(category); // Assuming you have a way to get the Category object
		Recipe newRecipe = new Recipe(recipeName, servings, prepTime, categoryObj, instructionText);
		newRecipe.setIngredients(ingredientsList);

		// Insert new Recipe into database
		rh.insertRecipe(newRecipe);
		



		// Redirect to another page (e.g., viewAllRecipes.jsp)
		getServletContext().getRequestDispatcher("viewRecipeServlet").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}