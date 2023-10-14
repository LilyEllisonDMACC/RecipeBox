package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Recipe;

/**
 * Servlet implementation class NavigationServlet
 */
@WebServlet("/navigationServlet")
public class NavigationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavigationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper(null);
		String act = request.getParameter("doThisToRecipe");
		
		String path = "/viewAllRecipesServlet";
		
		if(act.equals("Delete")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Recipe recipeToDelete = rh.getRecipeById(tempId);
				rh.deleteRecipe(recipeToDelete);				
			} catch(NumberFormatException e) {
				System.out.println("Forgot to select a recipe.");
			}
		} else if(act.equals("Edit")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Recipe recipeToEdit = rh.getRecipeById(tempId);
				request.setAttribute("recipeToEdit", recipeToEdit);
				path = "/editRecipe.jsp";
				CategoryHelper ch = new CategoryHelper(null);
				
				request.setAttribute("allCategories", ch.getAllCategories());
				
				if(ch.getAllCategories().isEmpty()) {
					request.setAttribute("allCategories", " ");
				}
			} catch(NumberFormatException e) {
				System.out.println("Forgot to select a recipe.");
			}
		}else if(act.equals("Add")) {
			path = "/addRecipe.jsp";
		}else if(act.equals("View")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Recipe recipeToView = rh.getRecipeById(tempId);
				request.setAttribute("recipeToView", recipeToView);
				path = "/viewRecipe.jsp";
			} catch(NumberFormatException e) {
				System.out.println("Forgot to select a recipe.");
			}
			
		}
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
