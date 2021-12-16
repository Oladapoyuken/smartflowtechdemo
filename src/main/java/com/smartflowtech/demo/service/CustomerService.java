package com.smartflowtech.demo.service;

import com.smartflowtech.demo.dao.CustomerDao;
import com.smartflowtech.demo.dto.Request;
import com.smartflowtech.demo.dto.Response;
import com.smartflowtech.demo.models.Customer;
import com.smartflowtech.demo.models.Transaction;
import com.smartflowtech.demo.repository.CustomerRepo;
import com.smartflowtech.demo.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService implements CustomerDao {

    private final CustomerRepo customerRepo;
    private final TransactionRepo transactionRepo;


    @Autowired
    public CustomerService(CustomerRepo customerRepo, TransactionRepo transactionRepo) {
        this.customerRepo = customerRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public Response create(Customer customer) {
        Optional<Customer> getCustomer = customerRepo.findByUsername(customer.getUsername());
        Response response = new Response();
        if(getCustomer.isPresent()) {
            response.setStatus("Username already taken");
        }
        else {
            customer.setDateCreated(LocalDate.now());
            customer.setTransaction(new ArrayList<>());
            customerRepo.save(customer);
            response.setStatus("User added successfully");
            response.setCustomers(getAllCustomers());
        }
        return response;
    }

    @Override
    public Response getAll() {
        return new Response(null, getAllCustomers(), null);
    }


    @Override
    public Response order(Request request) {
        Optional<Customer> customer = customerRepo.findByUsername(request.getUsername());
        if(customer.isEmpty())
            return new Response("User not found", getAllCustomers(), null);

        double remainder = customer.get().getBalance() - request.getMoney();
        double newCredit = Math.abs(remainder) + customer.get().getCredit();
        if(customer.get().getCredit() > customer.get().getCreditLimit() ||
                (remainder < 0 & newCredit > customer.get().getCreditLimit())
        )
            return new Response("Credit limit exceeded", getAllCustomers(), null);

        Transaction transaction = new Transaction(request.getItem(), request.getMoney(), "", 0, LocalDate.parse(request.getDate()));
        if(remainder <= 0){
            customer.get().setBalance(0);
            customer.get().setCredit(newCredit);
            transaction.setType("CREDIT");
            transaction.setCredit(Math.abs(remainder));
        }
        else{
            customer.get().setBalance(remainder);
            transaction.setType("Full Payment");
        }
        customer.get().addTransaction(transaction);

        double credit = remainder < 0 ? Math.abs(remainder):0;

        return new Response("Purchase was successful with N" + credit + " credit", getAllCustomers(), null);
    }

    @Override
    public Response transaction(Request request) {
        Optional<Customer> customer = customerRepo.findByUsername(request.getUsername());
        Response response = new Response("No transaction Found", getAllCustomers(), new ArrayList<>());
        if(customer.isPresent()){
            response.setStatus("Operation successful");
            response.setTransactions(getTransaction(customer.get().getTransaction(),
                    LocalDate.parse(request.getStart()), LocalDate.parse(request.getEnd())));

        }
        return response;
    }

    @Override
    public Response setLimit(Request request) {
        Optional<Customer> customer = customerRepo.findByUsername(request.getUsername());
        Response response = new Response();
        if(customer.isEmpty()) {
            response.setStatus("User not found");
        }
        else {
            customer.get().setCreditLimit(request.getMoney());
            response.setStatus("Credit limit set successfully");
        }
        response.setCustomers(getAllCustomers());
        return response;
    }

    @Override
    public Response reset(Request request) {
        LocalDate date = LocalDate.parse(request.getDate());
        if(List.of(29, 30, 31).contains(date.getDayOfMonth())){
            Optional<Customer> customer = customerRepo.findByUsername(request.getUsername());
            if(customer.isEmpty())
                return new Response("Cannot find customer", getAllCustomers(), null);

            customer.get().setCredit(0);
            return new Response("Credit set back to zero after reconsiltion", getAllCustomers(), null);
        }
        return new Response("Sorry, you cannot reset credit until ending of month", getAllCustomers(), null);
    }

    private List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    private List<Transaction> getTransaction(List<Transaction> list , LocalDate startDate, LocalDate endDate){
        return list.stream()
                .filter(t -> {
                    if(t.getDateOfTransact().isBefore(endDate) & t.getDateOfTransact().isAfter(startDate))
                        return true;
                    return false;
                })
                .collect(Collectors.toList());
    }
}
