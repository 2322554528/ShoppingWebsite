package com.servlet;

import java.io.IOException;
import java.util.Map;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.service.ProductService;
import com.service.ProductServiceImpl;


/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/product")
public class CartServlet extends BaseServlet{
	public void addProductToCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		ProductService service=new ProductServiceImpl();
		//获取要放入购物车的商品的pid
		String pid=request.getParameter("pid");
		//获取该商品的购买数量
		int buyNum=Integer.parseInt(request.getParameter("buyNum"));
		//获取product 对象
		Product product=service.findProductByPid(pid);
		
		//计算小计
		double subtotal=product.getShop_price()*buyNum;
		
		//封装CartItem
		CartItem item=new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subtotal);
		
		//获得购物车
		Cart cart=(Cart)session.getAttribute("cart");
		if(cart==null){
			cart=new Cart();//判断Session中是否已经存在购物车，若不存在则创建一个
		}
		//将购物项放到购物车里面
		//先判断购物车中是否已经存在该购物项，即判断key是否已经存在
		//如果购物车里面已经存在该购物项--将现在买的数量与原来的数量相加
		
		Map<String, CartItem> cartItems=cart.getCartItems();
		//新买的商品小计
		double newsubtotal=0.0;
		
		if(cartItems.containsKey(pid)){
			CartItem cartItem=cartItems.get(pid);
			
			//获取原有商品的数量
			int oldBuyNum=cartItem.getBuyNum();
			oldBuyNum=oldBuyNum+buyNum;
		    //将总的商品购买数量放到购物项里面
			 cartItem.setBuyNum(oldBuyNum);
			//将购物项放到购物车里面
			 cart.setCartItems(cartItems);
			 
		   //修改小计
			 
			 //原来商品小计
			 double oldsubtotal = cartItem.getSubtotal();
			 //新买的商品小计
			 newsubtotal=buyNum*product.getShop_price();
			//新的商品小计
			 cartItem.setSubtotal(newsubtotal+oldsubtotal);
			
		}else{
			cartItems.put(product.getPid(), item);
			newsubtotal=buyNum*product.getShop_price();
			
		}
		
		
		//计算所有商品的总价格
		double total=cart.getTotal()+newsubtotal;
		cart.setTotal(total);
		
		//将车再次访问session
		session.setAttribute("cart", cart);
		
		//直接跳转到购物车页面
		response.sendRedirect("shopcart.jsp");
		
		
		
	}
	
}
