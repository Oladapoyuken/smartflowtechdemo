package com.smartflowtech.demo.repository;

import com.smartflowtech.demo.models.Customer;
import com.smartflowtech.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
