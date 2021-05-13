package com.example.demo.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;

@RestController
@RequestMapping("api/v1/")
public class ProductController {

	@Autowired
	ProductRepository productrepository;
	
	@Autowired
	ProductService productservice;
	
	@GetMapping("/productList")
	public List<Product> getAllProduct()
	{
		return productrepository.findAll();
	}
	
	@GetMapping("/product/{id}")
	public  ResponseEntity getProductById(@PathVariable("id")String id)
	{
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/getProduct/{id}")
	public  Product getProductById1(@PathVariable("id")int id)
	{
		return productrepository.findById(id).get();
	}
	
	@PostMapping
	public Product addProduct(@RequestBody Product product)
	{
		return productrepository.save(product);
	
	}
	
	@DeleteMapping("/product/{id}")
	public String delete(@PathVariable int id)
	{
		Optional<Product> p=productrepository.findById(id);
		
		if(p.isPresent())
		{
			productrepository.delete(p.get());
			return "Product is deleted with id::"+id;
		}
		else
		{
			throw new RuntimeException("Product Not found for the id"+id);
		}
		
			}

	@PutMapping("/product")
	public Product update(@RequestBody Product product)
	{
		return productrepository.save(product);
	}
	
	@PutMapping("product/{pid}")
	public Product updateProduct(@RequestBody Product product,@PathVariable String pid)
	{
		this.productservice.updateProduct(product,Integer.parseInt(pid));
		return product;
	}
	
	@DeleteMapping("/deleteproduct/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id)
	{
		try {
			Optional<Product> p=Optional.of(productrepository.findById(Integer.parseInt(id)).get());
			productrepository.delete(p.get());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("{id}")
	public @ResponseBody void saveProduct(@PathVariable int id,@RequestBody Map<Object, Object> fields)
	{
		Product product=this.productrepository.findById(id).get();
		fields.forEach((k,v)->{
			Field field=ReflectionUtils.findRequiredField(Product.class,(String)k);
			field.setAccessible(true);
			ReflectionUtils.setField(field, product, v);
		});
		productservice.updateProduct(product, id);
	}
}
