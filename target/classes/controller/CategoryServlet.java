package controller;

import exceptions.DatabaseAccessException;
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

@WebServlet("/categoryServlet")
public class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private EntityManager em;

	@Override
	public void init() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		em = emf.createEntityManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		try {
			if ("view".equals(action)) {
				Category category = getCategoryById(Integer.parseInt(id));
				request.setAttribute("category", category);
				getServletContext().getRequestDispatcher("/viewCategory.jsp").forward(request, response);
			} else if ("delete".equals(action)) {
				Category categoryToDelete = getCategoryById(Integer.parseInt(id));
				deleteCategory(categoryToDelete);
				getServletContext().getRequestDispatcher("/manageCategories.jsp").forward(request, response);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryHelper ch = new CategoryHelper(em);

		String categoryId = request.getParameter("categoryId");
		String categoryName = request.getParameter("name");

		Category category = new Category(categoryName);

		try {
			if (categoryId == null || categoryId.isEmpty()) {
				ch.addCategory(category);
			} else {
				category.setId(Integer.parseInt(categoryId));
				ch.updateCategory(category);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		getServletContext().getRequestDispatcher("/manageCategories.jsp").forward(request, response);
	}

	private Category getCategoryById(int id) throws DatabaseAccessException {
		try {
            return em.find(Category.class, id);
		} catch (Exception e) {
			throw new DatabaseAccessException("Error getting category by ID: " + e.getMessage());
		}
	}

	private void deleteCategory(Category toDelete) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			Category result = em.find(Category.class, toDelete.getId());
			em.remove(result);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new DatabaseAccessException("Error deleting category: " + e.getMessage());
		}
	}
}
