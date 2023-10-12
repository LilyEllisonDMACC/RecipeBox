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

@WebServlet(name = "SearchServlet", value = "/searchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecipeHelper recipeHelper;

	public void init() {
		recipeHelper = new RecipeHelper(null);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("searchQuery");
		String searchType = request.getParameter("searchType");
		List<Recipe> allRecipes = null;
		try {
			allRecipes = recipeHelper.showAllRecipes();
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}

		List<Recipe> searchResults = allRecipes.stream().filter(r -> {
			switch (searchType) {
			case "name":
				return r.getName().toLowerCase().contains(query.toLowerCase());
			case "category":
				return r.getCategory().getName().toLowerCase().contains(query.toLowerCase());
			case "ingredient":
				return r.getIngredients().stream()
						.anyMatch(i -> i.getName().toLowerCase().contains(query.toLowerCase()));
			case "servingSize":
				return String.valueOf(r.getServings()).equals(query);
			default:
				return false;
			}
		}).collect(Collectors.toList());

		request.setAttribute("searchResults", searchResults);
		getServletContext().getRequestDispatcher("/searchResults.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
