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
		//��ȡҪ���빺�ﳵ����Ʒ��pid
		String pid=request.getParameter("pid");
		//��ȡ����Ʒ�Ĺ�������
		int buyNum=Integer.parseInt(request.getParameter("buyNum"));
		//��ȡproduct ����
		Product product=service.findProductByPid(pid);
		
		//����С��
		double subtotal=product.getShop_price()*buyNum;
		
		//��װCartItem
		CartItem item=new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subtotal);
		
		//��ù��ﳵ
		Cart cart=(Cart)session.getAttribute("cart");
		if(cart==null){
			cart=new Cart();//�ж�Session���Ƿ��Ѿ����ڹ��ﳵ�����������򴴽�һ��
		}
		//��������ŵ����ﳵ����
		//���жϹ��ﳵ���Ƿ��Ѿ����ڸù�������ж�key�Ƿ��Ѿ�����
		//������ﳵ�����Ѿ����ڸù�����--���������������ԭ�����������
		
		Map<String, CartItem> cartItems=cart.getCartItems();
		//�������ƷС��
		double newsubtotal=0.0;
		
		if(cartItems.containsKey(pid)){
			CartItem cartItem=cartItems.get(pid);
			
			//��ȡԭ����Ʒ������
			int oldBuyNum=cartItem.getBuyNum();
			oldBuyNum=oldBuyNum+buyNum;
		    //���ܵ���Ʒ���������ŵ�����������
			 cartItem.setBuyNum(oldBuyNum);
			//��������ŵ����ﳵ����
			 cart.setCartItems(cartItems);
			 
		   //�޸�С��
			 
			 //ԭ����ƷС��
			 double oldsubtotal = cartItem.getSubtotal();
			 //�������ƷС��
			 newsubtotal=buyNum*product.getShop_price();
			//�µ���ƷС��
			 cartItem.setSubtotal(newsubtotal+oldsubtotal);
			
		}else{
			cartItems.put(product.getPid(), item);
			newsubtotal=buyNum*product.getShop_price();
			
		}
		
		
		//����������Ʒ���ܼ۸�
		double total=cart.getTotal()+newsubtotal;
		cart.setTotal(total);
		
		//�����ٴη���session
		session.setAttribute("cart", cart);
		
		//ֱ����ת�����ﳵҳ��
		response.sendRedirect("shopcart.jsp");
		
		
		
	}
	
}
