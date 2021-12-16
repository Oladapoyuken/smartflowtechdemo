package com.smartflowtech.demo.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long id;
    private String item;
    private double price;
    private String type;
    private double credit;
    private LocalDate dateOfTransact;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "customer_transaction_fk"
            )
    )
    private Customer customer;


    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Transaction(String item, Double price, String type, double credit, LocalDate dateOfTransact) {
        this.item = item;
        this.price = price;
        this.type = type;
        this.credit = credit;
        this.dateOfTransact = dateOfTransact;
    }

    public Transaction() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String status) {
        this.type = status;
    }

    public LocalDate getDateOfTransact() {
        return dateOfTransact;
    }

    public void setDateOfTransact(LocalDate dateOfTransact) {
        this.dateOfTransact = dateOfTransact;
    }


}

