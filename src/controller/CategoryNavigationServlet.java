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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Category;

/**
 * Servlet implementation class CategoryNavigationServlet
 */
@WebServlet("/categoryNavigationServlet")
public class CategoryNavigationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryNavigationServlet() {
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
		CategoryHelper ch = new CategoryHelper(null);
		String act = request.getParameter("doThisToCategory");
		
		String path = "/viewAllCategoriesServlet";
		
		if(act.equals("Delete")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Category categoryToDelete = ch.getCategoryById(tempId);
				ch.deleteCategory(categoryToDelete);				
			} catch(Exception e) {
				System.out.println("Forgot to select a category or category is in use.");
			} 
		} else if(act.equals("Edit")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Category categoryToEdit = ch.getCategoryById(tempId);
				request.setAttribute("categoryToEdit", categoryToEdit);
				path = "/editCategory.jsp";				
			} catch(NumberFormatException e) {
				System.out.println("Forgot to select a category.");
			}
		}
			getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
