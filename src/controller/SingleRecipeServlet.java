package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Recipe;

/**
 * Servlet implementation class SingleRecipeServlet
 */
@WebServlet("/singleRecipeServlet")
public class SingleRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleRecipeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper();
		
		String path = "/viewRecipe.jsp";
		
		
			Integer tempId = Integer.parseInt(request.getParameter("id"));
			Recipe recipeToView = rh.getRecipeById(tempId);
			request.setAttribute("recipeToView", recipeToView);
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper();
		
		String path = "/viewAllRecipesServlet";
		
		try {
			Integer tempId = Integer.parseInt(request.getParameter("id"));
			Recipe recipeToView = rh.getRecipeById(tempId);
			request.setAttribute("recipeToView", recipeToView);
			path = "/viewRecipe.jsp";
		} catch(NumberFormatException e) {
			System.out.println("Forgot to select a recipe.");
		}
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
