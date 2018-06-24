package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderItemListServlet extends HttpServlet
{
	public OrderItemListServlet()
	{
		super();
	}
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		arg0.getRequestDispatcher("listOrderItem.jsp").forward(arg0, arg1);
	}
}
