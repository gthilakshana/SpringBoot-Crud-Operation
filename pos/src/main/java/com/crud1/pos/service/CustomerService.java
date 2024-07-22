package com.crud1.pos.service;

import com.crud1.pos.dto.CustomerDTO;
import com.crud1.pos.dto.request.CustomerUpdateDTO;

import java.util.List;

public interface CustomerService {

public String saveCustomer(CustomerDTO customerDTO);

    String updateCustomer(CustomerUpdateDTO customerUpdateDTO);

    CustomerDTO getCustomerById(int customerId);

    List<CustomerDTO> getAllCustomers();

    String deleteCustomer(int customerId);

    List<CustomerDTO> getAllCustomersByActiveState(boolean activestate);
}
