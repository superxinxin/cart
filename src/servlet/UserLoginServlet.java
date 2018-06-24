package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDao;

public class UserLoginServlet extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		String name = arg0.getParameter("name");
		String password = arg0.getParameter("password");
		User user = new UserDao().getUser(name, password);
		if(null == user)
		{
			arg1.sendRedirect("login.jsp");
		}
		else
		{
			arg0.getSession().setAttribute("user", user);
			arg1.sendRedirect("listProduct");
		}
	}
}
