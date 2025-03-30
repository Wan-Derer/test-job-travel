package ru.demo.testjobtravel.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.demo.testjobtravel.model.Product;

public interface ProductRepository extends R2dbcRepository<Product, Long> {

}