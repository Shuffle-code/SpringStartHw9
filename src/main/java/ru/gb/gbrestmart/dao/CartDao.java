package ru.gb.gbrestmart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbrestmart.entity.Cart;


public interface CartDao extends JpaRepository<Cart, Long> {
}
