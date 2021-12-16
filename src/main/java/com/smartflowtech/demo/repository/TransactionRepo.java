package com.smartflowtech.demo.repository;

import com.smartflowtech.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
