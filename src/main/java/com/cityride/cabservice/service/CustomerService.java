
package com.cityride.cabservice.service;

import com.cityride.cabservice.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Integer id);

    CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO);

    void deleteCustomer(Integer id);
}
