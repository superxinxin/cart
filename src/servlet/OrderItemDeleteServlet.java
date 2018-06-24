package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrderItem;

public class OrderItemDeleteServlet extends HttpServlet {
	
    public OrderItemDeleteServlet() {
        super();
    }
    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
    {
    	int delpid = Integer.parseInt(arg0.getParameter("deloiProductId"));
    	int delnum = Integer.parseInt(arg0.getParameter("deloiNum"));
    	List<OrderItem> ois = (List<OrderItem>)arg0.getSession().getAttribute("ois");
    	if(null == ois)
		{
			ois = new ArrayList<OrderItem>();
			arg0.getSession().setAttribute("ois", ois);
		}
    	int temp = -1;
		for(OrderItem orderItem : ois)
		{
			if(orderItem.getProduct().getId()==delpid && orderItem.getNum()==delnum)
			{
				temp = ois.indexOf(orderItem);
				break;
			}
		}
		ois.remove(temp);
		arg1.sendRedirect("listOrderItem");
    }
}
