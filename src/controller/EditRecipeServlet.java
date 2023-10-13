package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model.Ingredient;
import model.Recipe;

/**
 * Servlet implementation class EditRecipeServlet
 */
@WebServlet("/editRecipeServlet")
public class EditRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRecipeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RecipeHelper rh = new RecipeHelper();
		CategoryHelper ch = new CategoryHelper();
		
		// Get form parameters
				String recipeName = request.getParameter("name");
				int servings = Integer.parseInt(request.getParameter("servings"));
				int prepTime = Integer.parseInt(request.getParameter("preparationTime"));
				String category = request.getParameter("category");
				Category categoryObj;
				if(category.equals("New")) {
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
				
				
				for(int i=0; i<ingredientsSeparate.length; i++) {
					Ingredient j = new Ingredient(ingredientsSeparate[i]);
					ingredientsList.add(j);
				}
				// Convert instructions to single String
				String instructionText = String.join("\n", instructionsSeparate);
				
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				
				Recipe recipeToUpdate = rh.getRecipeById(tempId);
				recipeToUpdate.setName(recipeName);
				recipeToUpdate.setServings(servings);
				recipeToUpdate.setPreparationTime(prepTime);
				recipeToUpdate.setCategory(categoryObj);
				recipeToUpdate.setIngredients(ingredientsList);
				recipeToUpdate.setInstructions(instructionText);
				rh.updateRecipe(recipeToUpdate);
				
				getServletContext().getRequestDispatcher("/viewAllRecipesServlet").forward(request, response);

	}

}
