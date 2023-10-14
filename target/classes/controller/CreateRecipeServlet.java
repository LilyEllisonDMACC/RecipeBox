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

import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createRecipeServlet")
public class CreateRecipeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CreateRecipeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("RecipeBox");
			em = emf.createEntityManager();
			RecipeHelper rh = new RecipeHelper(em);
			CategoryHelper ch = new CategoryHelper(em);

			// Get form parameters
			String recipeName = request.getParameter("name");
			int servings = Integer.parseInt(request.getParameter("servings"));
			int prepTime = Integer.parseInt(request.getParameter("preparationTime"));

			String category = request.getParameter("category");
			Category categoryObj;
			if (category.equals("New")) {
				category = request.getParameter("newCategory");
				categoryObj = new Category(category);
			} else {
				categoryObj = ch.getCategoryById(Integer.parseInt(category));
			}

			String ingredientBlock = request.getParameter("ingredients");
			String instructionsBlock = request.getParameter("instructions");

			String[] ingredientsSeparate = ingredientBlock.split("[,]", 0);
			String[] instructionsSeparate = instructionsBlock.split("[,]", 0);

			// Convert ingredientItems to List<Ingredient>
			List<Ingredient> ingredientsList = new ArrayList<>();
			for (int i = 0; i < ingredientsSeparate.length; i++) {
				Ingredient j = new Ingredient(ingredientsSeparate[i]);
				ingredientsList.add(j);
			}

			// Convert instructions to single String
			String instructionText = String.join("\n", instructionsSeparate);

			// Create new Recipe object
			Recipe newRecipe = new Recipe(recipeName, servings, prepTime, categoryObj, instructionText);
			newRecipe.setIngredients(ingredientsList);

			// Insert new Recipe into database
			rh.insertRecipe(newRecipe);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}

		// Redirect to another page
		getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
