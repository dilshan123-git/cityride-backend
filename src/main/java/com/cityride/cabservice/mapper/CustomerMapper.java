
package com.cityride.cabservice.mapper;

import com.cityride.cabservice.dto.CustomerDTO;
import com.cityride.cabservice.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO convertToDTO(Customer customer) {

        CustomerDTO dto = new CustomerDTO();

        dto.setCustomerId(customer.getUserId());
        dto.setFullName(customer.getFullName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());

        return dto;
    }
}
