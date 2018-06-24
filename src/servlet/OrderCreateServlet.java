package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Order;
import bean.OrderItem;
import bean.User;
import dao.OrderDao;
import dao.OrderItemDao;
public class OrderCreateServlet extends HttpServlet {
    public OrderCreateServlet() {
        super();
    }
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		User user = (User)arg0.getSession().getAttribute("user");
		if(null == user)
		{
			arg1.sendRedirect("login.jsp");
			return;
		}
		Order order = new Order();
		order.setUser(user);
		new OrderDao().insert(order);
		List<OrderItem> ois = (List<OrderItem>)arg0.getSession().getAttribute("ois");
		for(OrderItem orderItem : ois)
		{
			orderItem.setOrder(order);
			new OrderItemDao().insert(orderItem);
		}
		ois.clear();
		arg1.setContentType("text/html; charset=UTF-8");
	    arg1.getWriter().println("订单创建成功");
	}
}
