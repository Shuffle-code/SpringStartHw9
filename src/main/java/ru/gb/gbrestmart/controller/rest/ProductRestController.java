package ru.gb.gbrestmart.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbrestmart.controller.dto.ProductDto;
import ru.gb.gbrestmart.entity.Cart;
//import ru.gb.gbrestmart.entity.CartProduct;
import ru.gb.gbrestmart.entity.Product;
//import ru.gb.gbrestmart.service.CartProductService;
import ru.gb.gbrestmart.service.CartService;
import ru.gb.gbrestmart.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;
    private final CartService cartService;
    @GetMapping
    public List<Product> getProductList() {
        return productService.findAll();

    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long id) {
        Product product;
        if (id != null) {
            product = productService.findById(id);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.save(productDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto) {
        productDto.setId(id);
        ProductDto savedProductDto = productService.save(productDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }

    @PostMapping("/addToCart" + "/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("productId") Long id) {
        cartService.addProduct(productService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cart")
    public Set<Product> showCart() {
        return cartService.getProducts();
    }

    @DeleteMapping("/cartDelete" + "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdInCart(@PathVariable("productId") Long id) {
        cartService.deleteProduct(productService.findById(id));
    }

}
