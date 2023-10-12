package controller;

import exceptions.DatabaseAccessException;
import model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "SearchServlet", value = "/searchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RecipeHelper recipeHelper;

    public void init() {
        recipeHelper = new RecipeHelper(null);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Recipe> allRecipes = null;
        try {
            allRecipes = recipeHelper.showAllRecipes();
        } catch (DatabaseAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<Recipe> searchResults = allRecipes.stream()
                .filter(r -> r.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        request.setAttribute("searchResults", searchResults);
        getServletContext().getRequestDispatcher("/searchResults.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
