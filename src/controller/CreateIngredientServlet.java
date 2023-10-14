package controller;

import model.Ingredient;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IngredientServlet", value = "/ingredientServlet")
public class CreateIngredientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateIngredientServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IngredientHelper ingredientHelper = new IngredientHelper(null);
		
		String ingredientName = request.getParameter("ingredientName"); // Assuming you pass the name as a parameter
		//String ingredientAmount = request.getParameter("ingredientAmt");
		Ingredient ingredient = new Ingredient(ingredientName);
		
		ingredientHelper.insertIngredient(ingredient);
		
		/**
		String action = request.getParameter("action");

		if (action.equals("add")) {
			String name = request.getParameter("name");
			Ingredient newIngredient = new Ingredient(name);
			try {
				ingredientHelper.insertIngredient(newIngredient);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Ingredient ingredientToUpdate = new Ingredient(); // Replace with actual method to get Ingredient by ID
			ingredientToUpdate.setId(id);
			try {
				ingredientHelper.updateIngredient(ingredientToUpdate);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Ingredient ingredientToDelete = new Ingredient(); // Replace with actual method to get Ingredient by ID
			ingredientToDelete.setId(id);
			try {
				ingredientHelper.deleteIngredient(ingredientToDelete);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		doGet(request, response);
		*/
	}
/**
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IngredientHelper ingredientHelper = new IngredientHelper();
		String ingredientName = request.getParameter("name"); // Assuming you pass the name as a parameter
		Ingredient ingredient = ;
		List<Recipe> recipes = null;
		try {
			ingredient = ingredientHelper.findIngredientByName(ingredientName);
			recipes = ingredientHelper.getRecipesUsingIngredient(ingredient);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
		request.setAttribute("ingredient", ingredient);
		request.setAttribute("recipes", recipes);
		getServletContext().getRequestDispatcher("/viewIngredient.jsp").forward(request, response);
	}
*/
}
