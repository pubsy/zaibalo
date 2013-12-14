package ua.com.zaibalo.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.helper.ServletHelperService;

public abstract class GenericActionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static final String ACTION_PARAMETER = "action"; 
	
	protected abstract Action getActionByName(String actionName);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String actionName = request.getParameter(ACTION_PARAMETER);
		
		if(actionName == null || "".equals(actionName)){
			System.out.println("ERROR: 'action' parameter wasn't found.");
		
		}
		
		Action action = getActionByName(actionName);
		
        PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			request.setAttribute("errorText", e1.getMessage());
			response.sendError(500);
		}
		
		try {
			action.run(request, response, out);
		} catch (Exception e) {
			ServletHelperService.logException(e, request);

			out.write("{\"status\":\"fail\", \"message\":\"" + e.getMessage() + "\"}");
			out.close();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doPost(request, response);
	}
	
}
