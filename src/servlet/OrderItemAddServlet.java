package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrderItem;
import bean.Product;
import dao.ProductDao;

public class OrderItemAddServlet extends HttpServlet
{
	public OrderItemAddServlet()
	{
		super();
	}
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		int num = Integer.parseInt(arg0.getParameter("num"));
		int pid = Integer.parseInt(arg0.getParameter("pid"));
		Product p = new ProductDao().getProduct(pid);
		OrderItem oi = new OrderItem();
		oi.setNum(num);
		oi.setProduct(p);
		List<OrderItem> ois = (List<OrderItem>)arg0.getSession().getAttribute("ois");
		if(null == ois)
		{
			ois = new ArrayList<OrderItem>();
			arg0.getSession().setAttribute("ois", ois);
		}
		
		boolean found = false;
		for(OrderItem orderItem : ois)
		{
			if(orderItem.getProduct().getId() == oi.getProduct().getId())
			{
				orderItem.setNum(orderItem.getNum()+oi.getNum());
				found = true;
				break;
			}
		}
		if(!found)
		{
			ois.add(oi);
		}
		arg1.sendRedirect("listOrderItem");
	}
}
