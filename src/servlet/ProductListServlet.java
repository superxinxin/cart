package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Product;
import dao.ProductDao;

public class ProductListServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ProductListServlet()
	{
	}
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		List<Product> products = new ProductDao().listProduct();
		arg0.setAttribute("products", products);
		arg0.getRequestDispatcher("listProduct.jsp").forward(arg0, arg1);
	}
}
