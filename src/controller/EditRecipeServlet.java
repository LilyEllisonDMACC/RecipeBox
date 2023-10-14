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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model.Ingredient;
import model.Recipe;

@WebServlet("/editRecipeServlet")
// Class that allows the user to edit or delete a recipe from the database
public class EditRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Constructor
	public EditRecipeServlet() {
		super();
	}

	// Method that handles GET requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Integer id = Integer.parseInt(request.getParameter("id"));
		RecipeHelper rh = new RecipeHelper(null);

		// If the user selected "delete", delete the recipe
		if ("delete".equals(action)) {
			Recipe recipeToDelete = rh.getRecipeById(id);
			rh.deleteRecipe(recipeToDelete);
		} else if ("edit".equals(action)) {
			Recipe recipeToEdit = rh.getRecipeById(id);
			request.setAttribute("recipeToEdit", recipeToEdit);

			// Get all categories to display in the dropdown
			CategoryHelper ch = new CategoryHelper(null);
			List<Category> allCategories = ch.getAllCategories();
			request.setAttribute("allCategories", allCategories);

			// Get all ingredients to display in the dropdown
			RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecipe.jsp");
			dispatcher.forward(request, response);
			return;
		}

		getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);
	}

	// Method that handles POST requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper(null);
		CategoryHelper ch = new CategoryHelper(null);

		// Get form parameters
		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		Category categoryObj;

		// If the user selected "New" for the category, create a new category object
		if ("New".equals(category)) {
			category = request.getParameter("newCategory");
			categoryObj = new Category(category);
		} else {
			categoryObj = ch.getCategoryById(Integer.parseInt(category));
		}

		// Get form parameters
		String ingredientBlock = request.getParameter("ingredients");
		String instructionsBlock = request.getParameter("instructions");
		String[] ingredientsSeparate = ingredientBlock.split("[,]", 0);
		String[] instructionsSeparate = instructionsBlock.split("[,]", 0);

		// Convert ingredientItems to List<Ingredient>
		List<Ingredient> ingredientsList = new ArrayList<>();
		for (String ingredient : ingredientsSeparate) {
			Ingredient j = new Ingredient(ingredient);
			ingredientsList.add(j);
		}

		// Convert instructions to single String
		String instructionText = String.join("\n", instructionsSeparate);

		// Create new Recipe object
		Integer tempId = Integer.parseInt(request.getParameter("id"));
		Recipe recipeToUpdate = rh.getRecipeById(tempId);
		recipeToUpdate.setName(recipeName);
		recipeToUpdate.setServings(servings);
		recipeToUpdate.setPreparationTime(prepTime);
		recipeToUpdate.setCategory(categoryObj);
		recipeToUpdate.setIngredients(ingredientsList);
		recipeToUpdate.setInstructions(instructionText);
		rh.updateRecipe(recipeToUpdate);

		// Redirect to another page
		getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);
	}
}
