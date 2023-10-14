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
public class EditRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditRecipeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Integer id = Integer.parseInt(request.getParameter("id"));
		RecipeHelper rh = new RecipeHelper(null);

		if ("delete".equals(action)) {
			Recipe recipeToDelete = rh.getRecipeById(id);
			rh.deleteRecipe(recipeToDelete);
		} else if ("edit".equals(action)) {
			Recipe recipeToEdit = rh.getRecipeById(id);
			request.setAttribute("recipeToEdit", recipeToEdit);

			CategoryHelper ch = new CategoryHelper(null);
			List<Category> allCategories = ch.getAllCategories();
			request.setAttribute("allCategories", allCategories);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecipe.jsp");
			dispatcher.forward(request, response);
			return;
		}

		getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper(null);
		CategoryHelper ch = new CategoryHelper(null);

		String recipeName = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		Category categoryObj;

		if ("New".equals(category)) {
			category = request.getParameter("newCategory");
			categoryObj = new Category(category);
		} else {
			categoryObj = ch.getCategoryById(Integer.parseInt(category));
		}

		String ingredientBlock = request.getParameter("ingredients");
		String instructionsBlock = request.getParameter("instructions");
		String[] ingredientsSeparate = ingredientBlock.split("[,]", 0);
		String[] instructionsSeparate = instructionsBlock.split("[,]", 0);

		List<Ingredient> ingredientsList = new ArrayList<>();
		for (String ingredient : ingredientsSeparate) {
			Ingredient j = new Ingredient(ingredient);
			ingredientsList.add(j);
		}

		String instructionText = String.join("\n", instructionsSeparate);

		Integer tempId = Integer.parseInt(request.getParameter("id"));
		Recipe recipeToUpdate = rh.getRecipeById(tempId);
		recipeToUpdate.setName(recipeName);
		recipeToUpdate.setServings(servings);
		recipeToUpdate.setPreparationTime(prepTime);
		recipeToUpdate.setCategory(categoryObj);
		recipeToUpdate.setIngredients(ingredientsList);
		recipeToUpdate.setInstructions(instructionText);
		rh.updateRecipe(recipeToUpdate);

		getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);
	}
}
