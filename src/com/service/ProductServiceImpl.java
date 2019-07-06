package com.service;

import com.dao.ProductDao;
import com.dao.ProductDaoImpl;
import com.model.Product;

public class ProductServiceImpl implements ProductService{
	ProductDao dao=new ProductDaoImpl();
	
	@Override
	public Product findProductByPid(String pid){
	
		return dao.find(pid);
				
	}
	

}
