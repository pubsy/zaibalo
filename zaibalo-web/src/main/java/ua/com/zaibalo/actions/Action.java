package ua.com.zaibalo.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	
	public abstract void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception;
}
