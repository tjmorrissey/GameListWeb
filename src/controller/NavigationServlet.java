package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListItem;

/**
 * Servlet implementation class NavigationServlet
 */
@WebServlet("/navigationServlet")
public class NavigationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavigationServlet() {
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

		ListItemHelper game = new ListItemHelper();
		String act = request.getParameter("doThisToItem");
		
		//Where to redirect to, will only change if wanting to add
		String path = "/viewAllItemsServlet";
		
		if(act.equals("Delete")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				ListItem itemToDelete = game.searchForItemById(tempId);
				game.deleteItem(itemToDelete);
			}
			catch (NumberFormatException e) {
				System.out.println("Forgot to select an item");
			}
		}
		else if(act.equals("Edit")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				ListItem itemToEdit = game.searchForItemById(tempId);
				request.setAttribute("itemToEdit", itemToEdit);
				path = "/edit-item.jsp";
			}
			catch (NumberFormatException e){
				System.out.println("Forgot to select an item");
			}
		}
		else if(act.equals("Add")) {
			path = "/index.html";
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
