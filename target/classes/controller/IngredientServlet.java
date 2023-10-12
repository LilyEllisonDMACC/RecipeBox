package controller;

import exceptions.DatabaseAccessException;
import model.Ingredient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ingredientServlet")
public class IngredientServlet extends HttpServlet {
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
				Ingredient ingredient = getIngredientById(Integer.parseInt(id));
				request.setAttribute("ingredient", ingredient);
				getServletContext().getRequestDispatcher("/viewIngredient.jsp").forward(request, response);
			} else if ("delete".equals(action)) {
				Ingredient ingredientToDelete = getIngredientById(Integer.parseInt(id));
				deleteIngredient(ingredientToDelete);
				getServletContext().getRequestDispatcher("/manageIngredients.jsp").forward(request, response);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IngredientHelper ih = new IngredientHelper(em);

		String ingredientId = request.getParameter("ingredientId");
		String ingredientName = request.getParameter("name");

		Ingredient ingredient = new Ingredient(ingredientName);

		try {
			if (ingredientId == null || ingredientId.isEmpty()) {
				ih.insertIngredient(ingredient);
			} else {
				ingredient.setId(Integer.parseInt(ingredientId));
				ih.updateIngredient(ingredient);
			}
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		getServletContext().getRequestDispatcher("/manageIngredients.jsp").forward(request, response);
	}

	private Ingredient getIngredientById(int id) throws DatabaseAccessException {
		try {
            return em.find(Ingredient.class, id);
		} catch (Exception e) {
			throw new DatabaseAccessException("Error getting ingredient by ID: " + e.getMessage());
		}
	}

	private void deleteIngredient(Ingredient toDelete) throws DatabaseAccessException {
		try {
			em.getTransaction().begin();
			Ingredient result = em.find(Ingredient.class, toDelete.getId());
			em.remove(result);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new DatabaseAccessException("Error deleting ingredient: " + e.getMessage());
		}
	}
}
