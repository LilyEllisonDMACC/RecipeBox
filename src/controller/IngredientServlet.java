package controller;

import exceptions.DatabaseAccessException;
import model.Ingredient;
import model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IngredientServlet", value = "/ingredientServlet")
public class IngredientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IngredientHelper ingredientHelper;

	public void init() {
		ingredientHelper = new IngredientHelper(null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		Ingredient ingredient = new Ingredient(name);
		ingredient.setId(id);

		try {
			if (action.equals("add")) {
				ingredientHelper.insertIngredient(ingredient);
			} else if (action.equals("edit")) {
				ingredientHelper.updateIngredient(ingredient);
			} else if (action.equals("delete")) {
				ingredientHelper.deleteIngredient(ingredient);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ingredientName = request.getParameter("name"); // Assuming you pass the name as a parameter
		Ingredient ingredient = null;
		List<Recipe> recipes = null;
		try {
			ingredient = ingredientHelper.findIngredientByName(ingredientName);
			recipes = ingredientHelper.getRecipesUsingIngredient(ingredient);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
		request.setAttribute("ingredient", ingredient);
		request.setAttribute("recipes", recipes);
		getServletContext().getRequestDispatcher("/viewIngredient.jsp").forward(request, response);
	}

}
