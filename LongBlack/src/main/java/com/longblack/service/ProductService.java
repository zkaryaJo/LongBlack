package com.longblack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.longblack.config.Role;
import com.longblack.domain.Product;
import com.longblack.domain.Member;
import com.longblack.domain.Store;
import com.longblack.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(Product product, Member member) {
        if (member.getRole() != Role.ROLE_OWNER) {
            throw new IllegalArgumentException("Only owners can create a product.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

	public List<Product> findByStore(Store store) {
		return productRepository.findByStore(store);
	}
}
