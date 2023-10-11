package controller;

import model.Category;
import javax.servlet.*;
import javax.servlet.http.*;

import exceptions.DatabaseAccessException;

import javax.servlet.annotation.*;
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

		if (action.equals("add")) {
			String name = request.getParameter("name");
			Category newCategory = new Category(name);
			try {
				categoryHelper.addCategory(newCategory);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Category categoryToUpdate = new Category(); // Replace with actual method to get Category by ID
			categoryToUpdate.setId(id);
			try {
				categoryHelper.updateCategory(categoryToUpdate);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Category categoryToDelete = new Category(); // Replace with actual method to get Category by ID
			categoryToDelete.setId(id);
			try {
				categoryHelper.deleteCategory(categoryToDelete);
			} catch (DatabaseAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categories = null;
		try {
			categories = categoryHelper.getAllCategories();
		} catch (DatabaseAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("allCategories", categories);
		getServletContext().getRequestDispatcher("/listCategories.jsp").forward(request, response);
	}
}
