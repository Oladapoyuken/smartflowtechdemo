package com.smartflowtech.demo.dao;

import com.smartflowtech.demo.dto.Request;
import com.smartflowtech.demo.dto.Response;
import com.smartflowtech.demo.models.Customer;

public interface CustomerDao {
    Response create(Customer customer);

    Response getAll();

    Response order(Request request);

    Response transaction(Request request);

    Response setLimit(Request request);

    Response reset(Request request);







}
