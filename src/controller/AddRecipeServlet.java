package controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DatabaseAccessException;
import model.Category;
import model.Recipe;

/**
 * Servlet implementation class AddRecipeServlet
 */
@WebServlet("/addRecipeServlet")
public class AddRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRecipeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int servings = Integer.parseInt(request.getParameter("servings"));
		int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
		String category = request.getParameter("category");
		String[] ingredientItems = request.getParameterValues("ingredient");
		String[] ingredientAmts = request.getParameterValues("amount");
		String[] instructions = request.getParameterValues("instruction");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		EntityManager em = emf.createEntityManager();

		
		CategoryHelper ch = new CategoryHelper(em);
		
		
		Category newCategory;
		
		if (ch.searchCategoryName(category) == null) {
			newCategory = new Category(category);
			try {
				ch.addCategory(newCategory);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			newCategory = ch.searchCategoryName(category);
		}
		
		
		
		
		Recipe recipe = new Recipe(name, servings, preparationTime, newCategory, String.join("\n", instructions));

		
		//loop through ingredientAmts and ingredientItems to concat them, then String.join("\n", fullIngredient)
		
		getServletContext().getRequestDispatcher("/options.html").forward(request, response);
	}

}
