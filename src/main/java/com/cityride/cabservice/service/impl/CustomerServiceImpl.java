
package com.cityride.cabservice.service.impl;

import com.cityride.cabservice.dto.CustomerDTO;
import com.cityride.cabservice.entity.Customer;
import com.cityride.cabservice.enums.Roles;
import com.cityride.cabservice.enums.UserStatus;
import com.cityride.cabservice.mapper.CustomerMapper;
import com.cityride.cabservice.repository.CustomerRepository;
import com.cityride.cabservice.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE CUSTOMER
    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password is required"
            );
        }

        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists"
            );
        }

        if (customerRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists"
            );
        }

        Customer customer = new Customer();

        customer.setFullName(dto.getFullName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setAddress(dto.getAddress());

        customer.setRole(Roles.CUSTOMER);
        customer.setUserStatus(UserStatus.ACTIVE);

        return CustomerMapper.convertToDTO(customerRepository.save(customer));
    }

    // GET ALL CUSTOMERS
    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerMapper::convertToDTO)
                .toList();
    }

    // GET CUSTOMER BY ID
    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Integer id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

        return CustomerMapper.convertToDTO(customer);
    }

    // UPDATE CUSTOMER
    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO dto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

        if (customerRepository.existsByEmailAndUserIdNot(
                dto.getEmail(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists"
            );
        }

        if (customerRepository.existsByPhoneAndUserIdNot(
                dto.getPhone(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already exists"
            );
        }

        customer.setFullName(dto.getFullName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            customer.setPassword(
                    passwordEncoder.encode(dto.getPassword())
            );
        }

        return CustomerMapper.convertToDTO(customerRepository.save(customer));
    }

    // DELETE CUSTOMER
    @Override
    public void deleteCustomer(Integer id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

        customerRepository.delete(customer);
    }
}
