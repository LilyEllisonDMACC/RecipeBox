package controller;

import model.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/categoryServlet")
public class CreateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CreateCategoryServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		EntityManager em = emf.createEntityManager();
		RecipeHelper rh = new RecipeHelper();
		IngredientHelper ih = new IngredientHelper();
		CategoryHelper ch = new CategoryHelper();
		
		String name = request.getParameter("name");
		Category newCategory = new Category(name);
		
		ch.addCategory(newCategory);
		
		em.close();
		getServletContext().getRequestDispatcher("/listCategories.jsp").forward(request, response);
	}
		
		/**
		String action = request.getParameter("action");

		if (action.equals("add")) {
			String name = request.getParameter("name");
			Category newCategory = new Category(name);
			try {
				categoryHelper.addCategory(newCategory);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
			}
		} else if (action.equals("edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Category categoryToUpdate = new Category();
			categoryToUpdate.setId(id);
			try {
				categoryHelper.updateCategory(categoryToUpdate);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
			}
		} else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Category categoryToDelete = new Category();
			categoryToDelete.setId(id);
			try {
				categoryHelper.deleteCategory(categoryToDelete);
			} catch (DatabaseAccessException e) {
				e.printStackTrace();
			}
		}



	//protected void doGet(HttpServletRequest request, HttpServletResponse response)
		//	throws ServletException, IOException {
		List<Category> categories = null;
		try {
			categories = categoryHelper.getAllCategories();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("categories", categories);
		getServletContext().getRequestDispatcher("/listCategories.jsp").forward(request, response);
	//}
	 * 
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
