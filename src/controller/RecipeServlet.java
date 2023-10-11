package controller;

import exceptions.DatabaseAccessException;
import model.Category;
import model.Recipe;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeServlet", value = "/recipeServlet")
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecipeHelper recipeHelper;

	public void init() {
		recipeHelper = new RecipeHelper(null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("add")) {
			String name = request.getParameter("name");
			int servings = Integer.parseInt(request.getParameter("servings"));
			int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
			String category = request.getParameter("category");
			String instructions = request.getParameter("instructions");
			Category categoryObj = new Category(category);
			Recipe newRecipe = new Recipe(name, servings, preparationTime, categoryObj, instructions);
			try {
				recipeHelper.insertRecipe(newRecipe, null);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Recipe recipeToUpdate = new Recipe();
			recipeToUpdate.setId(id);
			try {
				recipeHelper.updateRecipe(recipeToUpdate);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Recipe recipeToDelete = new Recipe();
			recipeToDelete.setId(id);
			try {
				recipeHelper.deleteRecipe(recipeToDelete);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Recipe> recipes = null;
		try {
			recipes = recipeHelper.showAllRecipes();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allRecipes", recipes);
		getServletContext().getRequestDispatcher("/listRecipes.jsp").forward(request, response);
	}
}
