package controller;

import model.Ingredient;
import javax.servlet.*;
import javax.servlet.http.*;

import exceptions.DatabaseAccessException;

import javax.servlet.annotation.*;
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
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Ingredient> ingredients = null;
		try {
			ingredients = ingredientHelper.showAllIngredients();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allIngredients", ingredients);
		getServletContext().getRequestDispatcher("/listIngredients.jsp").forward(request, response);
	}
}
