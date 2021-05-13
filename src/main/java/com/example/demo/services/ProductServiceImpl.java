package com.example.demo.services;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productrepository;
	
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productrepository.findAll();
	}

	@Override
	public void updateProduct(Product product, int pid) {
		// TODO Auto-generated method stub
		
		this.productrepository.findAll().stream().map(p->{
			
			if(p.getId()==pid)
			{
			  p.setName(product.getName());
			  p.setPrice(product.getPrice());
			  p.setQuantity(product.getQuantity());
			  p.setStatus(product.getStatus());
			}
			return productrepository.save(p);
		}).collect(Collectors.toList());
	}

}
