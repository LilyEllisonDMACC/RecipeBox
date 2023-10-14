package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Recipe;

/**
 * Servlet implementation class ViewRecipeServlet
 */
@WebServlet("/viewAllRecipesServlet")
public class ViewAllRecipesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecipeHelper recipeHelper = new RecipeHelper(null);
		List<Recipe> allRecipes = recipeHelper.showAllRecipes();
		request.setAttribute("allRecipes", allRecipes);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listRecipes.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
