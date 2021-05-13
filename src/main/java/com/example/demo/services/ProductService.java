package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Product;

public interface ProductService {

	public List<Product> findAll();

	public void updateProduct(Product product, int pid);
	
}
