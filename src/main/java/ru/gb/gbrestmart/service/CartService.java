package ru.gb.gbrestmart.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbrestmart.dao.CartDao;
import ru.gb.gbrestmart.dao.ProductDao;
import ru.gb.gbrestmart.entity.Cart;
import ru.gb.gbrestmart.entity.Product;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class CartService {
//    @Autowired
    private final CartDao cartDao;
    private final ProductDao productDao;

    private Cart cart;
    private Set<Product> products;

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        return cartDao.getById(id);
    }

    @Transactional
    public Product addProduct(Product product) {
        cart = cartDao.findById(11L).get();
        cart.addProductToCart(product);
        cartDao.save(cart);
        return product;
    }

    @Transactional
    public void deleteProduct(Product product) {
        cart = cartDao.findById(11L).get();
        cart.deleteProduct(product);
        cartDao.save(cart);
    }

    @Transactional(readOnly = true)
    public Set<Product> getProducts() {
        cart = cartDao.findById(11L).get();
        return cart.getProducts();
    }
}
