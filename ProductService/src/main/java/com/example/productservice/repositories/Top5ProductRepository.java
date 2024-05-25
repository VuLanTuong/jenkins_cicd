package com.example.productservice.repositories;

import com.example.productservice.models.Top5Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Top5ProductRepository extends CrudRepository<Top5Products, String > {


}
