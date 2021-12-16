package com.smartflowtech.demo.controller;

import com.smartflowtech.demo.dao.CustomerDao;
import com.smartflowtech.demo.dto.Request;
import com.smartflowtech.demo.dto.Response;
import com.smartflowtech.demo.models.Customer;
import com.smartflowtech.demo.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CustomerApi {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerApi(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer, Model model){
        Response response = customerDao.create(customer);
        model.addAttribute("customers", response.getCustomers());
        model.addAttribute("message", response.getStatus());
        model.addAttribute("customer", new Customer());
        return "dashboard";

    }

    @GetMapping
    public String dashboard(Model model){
        model.addAttribute("customers", customerDao.getAll().getCustomers());
        return "dashboard";
    }

    @PostMapping("/order")
    public String purchase(@ModelAttribute Request request, Model model){
        Response response = customerDao.order(request);
        model.addAttribute("customers", response.getCustomers());
        model.addAttribute("message", response.getStatus());
        model.addAttribute("request", new Request());
        return "dashboard";

    }

    @PostMapping("/limit")
    public String setLimit(@ModelAttribute Request request, Model model){
        Response response = customerDao.setLimit(request);
        model.addAttribute("customers", response.getCustomers());
        model.addAttribute("message", response.getStatus());
        model.addAttribute("request", new Request());
        return "dashboard";

    }

    @PostMapping("/reset")
    public String reset(@ModelAttribute Request request, Model model){
        Response response = customerDao.reset(request);
        model.addAttribute("customers", response.getCustomers());
        model.addAttribute("message", response.getStatus());
        model.addAttribute("request", new Request());
        return "dashboard";

    }

    @GetMapping("/transaction")
    public String transaction(@ModelAttribute Request request, Model model){
        Response response = customerDao.transaction(request);
        model.addAttribute("customers", response.getCustomers());
        model.addAttribute("transactions", response.getTransactions());
        model.addAttribute("message", response.getStatus());
        model.addAttribute("request", new Request());
        model.addAttribute("transaction", new Transaction());
        return "dashboard";

    }

}
