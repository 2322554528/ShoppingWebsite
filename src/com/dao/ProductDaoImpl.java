package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Product;

public class ProductDaoImpl implements ProductDao {
	
	public Product find(String pid){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
			DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","scott","admin");
			String sql = "select * from goods where pid=?";//goods是商品表
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, pid);
			ResultSet rs=psmt.executeQuery();
			if(rs.next()){
				Product p=new Product();
			    p.setPid(rs.getString("pid"));//商品表第一列列名
				p.setPimage(rs.getString("pimage"));//商品表第二列列名
				p.setPname(rs.getString("pname"));
			    p.setShop_price(rs.getDouble("price"));//商品表第四列列名
			    
			    return p;
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; 
		
		
		
	}

}
