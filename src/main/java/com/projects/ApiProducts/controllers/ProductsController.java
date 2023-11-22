    package com.projects.ApiProducts.controllers;

import com.projects.ApiProducts.models.Product;
import com.projects.ApiProducts.services.ProductServices;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yeison Perea
 */
@RestController
@RequestMapping("/api") // Define el path padre "/api" para todas las rutas en este controlador
public class ProductsController {

    @Autowired
    private ProductServices productServices;

    @GetMapping("/index")
    @ResponseBody
    public String welcome() {
        return "Welcome to api products!";
    }

    @PostMapping("/save-product")
    @ResponseBody
    public HashMap<String, String> saveProduct(@RequestBody Product product) {
        HashMap<String, String> map = new HashMap<>();

        productServices.saveProduct(product);
        map.put("message", "Product save");

        return map;
    }

    @GetMapping("/list-products")
    public List<Product> listproducts() {

        return productServices.listProducts();

    }

    @GetMapping("/list-product-by-id/{idProduct}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> listProductById(@PathVariable Long idProduct) {
        Optional<Product> search = productServices.listProductById(idProduct);

        //Map<String, String> response = new HashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();
        
        HttpStatus httpStatus;

        if (search.isPresent()) {

            String price = Float.toString(search.get().getPrice());
            String id = Long.toString(search.get().getId());

            response.put("id", id);
            response.put("name", search.get().getName());
            response.put("img", search.get().getImg());
            response.put("price", price);
            response.put("createdAt", search.get().getCreatedAt());
            
            httpStatus = HttpStatus.OK;

        } else {
            System.out.println("Product no found");
            response.put("message", "Product not found");
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, httpStatus);

    }

    @DeleteMapping("/delete-product-by-id/{idProduct}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteProductById(@PathVariable Long idProduct) {

        Map<String, String> response = new HashMap<>();
        HttpStatus httpStatus;

        try {
            boolean deleted = productServices.deleteProductById(idProduct);

            if (deleted) {
                response.put("message", "Product deleted successfully");
                httpStatus = HttpStatus.OK;
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                response.put("message", "Product not found");
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.put("message", "An error occurred while deleting the product");
        }

        return new ResponseEntity<>(response, httpStatus);

    }

    @PutMapping("/update-product/{idProduct}")
    public ResponseEntity<Map<String, String>> updateProductById(@PathVariable Long idProduct, @RequestBody Product updatedProduct) {

        Map<String, String> response = new HashMap<>();
        HttpStatus httpStatus;

        try {
            boolean update = productServices.updateProductById(idProduct, updatedProduct);

            if (update) {
                response.put("message", "Product update successfully");
                httpStatus = HttpStatus.OK;
            } else {
                httpStatus = HttpStatus.NOT_FOUND;
                response.put("message", "Product not found");
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.put("message", "An error occurred while deleting the product");
        }

        return new ResponseEntity<>(response, httpStatus);

    }

}
