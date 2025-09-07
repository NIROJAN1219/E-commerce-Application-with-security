package com.example.e_commerce.repository;

import com.example.shopbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUsernameOrderByPurchaseDateDesc(String username);
}
