package controller;

import exceptions.DatabaseAccessException;
import model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/categoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryHelper categoryHelper;

	public void init() {
		categoryHelper = new CategoryHelper(null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		Category category = new Category(name);
		category.setId(id);

		try {
			if (action.equals("add")) {
				categoryHelper.addCategory(category);
			} else if (action.equals("edit")) {
				categoryHelper.updateCategory(category);
			} else if (action.equals("delete")) {
				categoryHelper.deleteCategory(category);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categories = null;
		try {
			categories = categoryHelper.getAllCategories();
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
		request.setAttribute("categories", categories);
		getServletContext().getRequestDispatcher("/listCategories.jsp").forward(request, response);
	}
}
