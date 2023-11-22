package com.projects.ApiProducts.services;

import com.projects.ApiProducts.interfaces.IProduct;
import com.projects.ApiProducts.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yeison Perea
 */
@Service
public class ProductServices {

    @Autowired
    private IProduct iProduct;

    public void saveProduct(Product product) {
        iProduct.save(product);
    }

    public List<Product> listProducts() {
        return (List<Product>) iProduct.findAll();
    }

    public Optional<Product> listProductById(Long idProduct) {
        return iProduct.findById(idProduct);
      
    }

    public boolean deleteProductById(Long id) {
        try {
            if (iProduct.existsById(id)) {
                iProduct.deleteById(id);
                return true; // Indica que se eliminó el producto exitosamente
            } else {
                return false; // Indica que no se encontró el producto con ese ID
            }
        } catch (Exception e) {
            // Puedes manejar excepciones aquí si es necesario
            return false; // Indica que ocurrió un error durante la eliminación
        }
    }

    public boolean updateProductById(Long productId, Product updatedProduct) {
        Optional<Product> optionalProduct = iProduct.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            // Aplica los cambios del updatedProduct al existingProduct
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setImg(updatedProduct.getImg());

            iProduct.save(existingProduct); // Guarda los cambios

            return true; // Actualización exitosa
        } else {
            return false; // Producto no encontrado
        }
    }

}
