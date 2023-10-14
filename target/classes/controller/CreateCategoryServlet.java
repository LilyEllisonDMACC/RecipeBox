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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;

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
		new RecipeHelper(em);
		new IngredientHelper(em);
		CategoryHelper ch = new CategoryHelper(em);

		String name = request.getParameter("name");
		Category newCategory = new Category(name);

		ch.addCategory(newCategory);

		em.close();
		getServletContext().getRequestDispatcher("/listCategories.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
