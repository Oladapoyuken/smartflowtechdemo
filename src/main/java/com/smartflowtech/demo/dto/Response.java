package com.smartflowtech.demo.dto;

import com.smartflowtech.demo.models.Customer;
import com.smartflowtech.demo.models.Transaction;

import java.util.*;

public class Response {
    private String status;
    private List<Customer> customers;
    private List<Transaction> transactions;

    public Response(String status, List<Customer> customers, List<Transaction> transactions) {
        this.status = status;
        this.customers = customers;
        this.transactions = transactions;
    }

    public Response(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
