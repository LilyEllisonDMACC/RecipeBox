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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryHelper ch = new CategoryHelper(null);
		String action = request.getParameter("action");
		Integer id = Integer.parseInt(request.getParameter("id"));

		if ("delete".equals(action)) {
			if (ch.isCategoryUsed(id)) {
				// Category is in use, set an error message
				request.setAttribute("errorMessage",
						"This category is in use by one or more recipes and cannot be deleted.");
			} else {
				// Category is not in use, proceed to delete
				Category categoryToDelete = ch.getCategoryById(id);
				ch.deleteCategory(categoryToDelete);
			}
		} else if ("edit".equals(action)) {
			Category categoryToEdit = ch.getCategoryById(id);
			request.setAttribute("categoryToEdit", categoryToEdit);
			getServletContext().getRequestDispatcher("/editCategory.jsp").forward(request, response);
			return;
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
