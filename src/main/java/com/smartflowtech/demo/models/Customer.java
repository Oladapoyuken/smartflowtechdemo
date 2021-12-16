package com.smartflowtech.demo.models;

import javax.persistence.*;
import java.util.*;
import java.time.LocalDate;

@Entity
public class Customer {

    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private double balance;
    private double credit;
    private double creditLimit;
    private LocalDate dateCreated;


    @OneToMany(
            mappedBy = "customer",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Transaction> transaction = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }


    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void addTransaction(Transaction transaction) {
        this.transaction.add(transaction);
        transaction.setCustomer(this);
    }
}
