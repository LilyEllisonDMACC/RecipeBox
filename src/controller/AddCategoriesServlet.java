package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;

/**
 * Servlet implementation class AddCategoriesServlet
 */
@WebServlet("/AddCategoriesServlet")
public class AddCategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCategoriesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		
		Category newCategory = new Category(name);
		CategoryHelper ch = new CategoryHelper();
		ch.addCategory(newCategory);
		
		getServletContext().getRequestDispatcher("/viewAllCategoriesServlet").forward(request, response);
	}

}
