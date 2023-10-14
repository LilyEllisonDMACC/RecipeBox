package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;

/**
 * Servlet implementation class EditCategoryServlet
 */
@WebServlet("/editCategoryServlet")
public class EditCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCategoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryHelper ch = new CategoryHelper(null); // You may need to initialize this properly
		String action = request.getParameter("action");
		Integer id = Integer.parseInt(request.getParameter("id"));

		if ("delete".equals(action)) {
			Category categoryToDelete = ch.getCategoryById(id);
			ch.deleteCategory(categoryToDelete);
		}
		// Redirect to the list of categories
		getServletContext().getRequestDispatcher("/viewAllCategoriesServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryHelper ch = new CategoryHelper(null);

		String name = request.getParameter("name");
		Integer tempId = Integer.parseInt(request.getParameter("id"));

		Category categoryToUpdate = ch.getCategoryById(tempId);
		categoryToUpdate.setName(name);

		ch.updateCategory(categoryToUpdate);

		getServletContext().getRequestDispatcher("/viewAllCategoriesServlet").forward(request, response);
	}

}
