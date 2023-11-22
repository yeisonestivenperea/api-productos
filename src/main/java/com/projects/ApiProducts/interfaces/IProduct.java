package com.projects.ApiProducts.interfaces;

import com.projects.ApiProducts.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Yeison Perea
 */
public interface IProduct  extends JpaRepository<Product, Long> {

}
