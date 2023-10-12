package controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DatabaseAccessException;
import model.Recipe;
import model.Ingredient;
import model.Category;

@WebServlet(name = "SearchServlet", value = "/searchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecipeHelper recipeHelper;
	private IngredientHelper ingredientHelper;
	private CategoryHelper categoryHelper;

	public void init() {
		recipeHelper = new RecipeHelper(null);
		ingredientHelper = new IngredientHelper(null);
		categoryHelper = new CategoryHelper(null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("searchQuery");

		List<Recipe> recipeResults = null;
		List<Ingredient> ingredientResults = null;
		List<Category> categoryResults = null;

		try {
			recipeResults = recipeHelper.showAllRecipes().stream()
					.filter(r -> r.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

			ingredientResults = ingredientHelper.showAllIngredients().stream()
					.filter(i -> i.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

			categoryResults = categoryHelper.getAllCategories().stream()
					.filter(c -> c.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}

		request.setAttribute("recipeResults", recipeResults);
		request.setAttribute("ingredientResults", ingredientResults);
		request.setAttribute("categoryResults", categoryResults);
		getServletContext().getRequestDispatcher("/searchResults.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
