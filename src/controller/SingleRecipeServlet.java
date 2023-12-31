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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Recipe;

@WebServlet("/singleRecipeServlet")
// View a single recipe
public class SingleRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Get recipe by id and forward to viewRecipe.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper(null);
		String path = "/viewRecipe.jsp";
		Integer tempId = Integer.parseInt(request.getParameter("id"));
		Recipe recipeToView = rh.getRecipeById(tempId);
		request.setAttribute("recipeToView", recipeToView);

		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	// Post recipe by id and forward to viewRecipe.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper(null);
		String path = "/viewAllRecipesServlet";

		try {
			Integer tempId = Integer.parseInt(request.getParameter("id"));
			Recipe recipeToView = rh.getRecipeById(tempId);
			request.setAttribute("recipeToView", recipeToView);
			path = "/viewRecipe.jsp";
		} catch (NumberFormatException e) {
			System.out.println("Forgot to select a recipe.");
		}

		getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}
