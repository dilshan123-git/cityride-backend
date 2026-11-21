
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CustomerDTO;
import com.cityride.cabservice.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @RequestBody CustomerDTO customerDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerDTO));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerDTO customerDTO) {

        return ResponseEntity.ok(
                customerService.updateCustomer(id, customerDTO)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}
