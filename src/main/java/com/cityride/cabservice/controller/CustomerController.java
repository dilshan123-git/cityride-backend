
package com.cityride.cabservice.controller;

import com.cityride.cabservice.dto.CommonResponseDto;
import com.cityride.cabservice.dto.CustomerDTO;
import com.cityride.cabservice.service.CustomerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Customer Management", description = "Customer CRUD operations")
public class CustomerController {

    private final CustomerService customerService;

    // CREATE CUSTOMER - PUBLIC (GUEST)
    @PostMapping
    public ResponseEntity<CommonResponseDto<CustomerDTO>> createCustomer(
            @RequestBody CustomerDTO customerDTO) {

        log.info("Customer registration request received");

        CustomerDTO customer = customerService.createCustomer(customerDTO);

        log.info("Customer registered successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.success(
                        "Customer account created successfully",
                        customer
                ));
    }

    // GET ALL CUSTOMERS - ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommonResponseDto<List<CustomerDTO>>> getAllCustomers() {

        log.info("Get all customers request received");

        List<CustomerDTO> customers = customerService.getAllCustomers();

        log.info("Retrieved {} customers", customers.size());

        return ResponseEntity.ok(
                CommonResponseDto.success(
                        "Customers retrieved successfully",
                        customers
                )
        );
    }

    // GET CUSTOMER BY ID - ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommonResponseDto<CustomerDTO>> getCustomerById(
            @PathVariable Integer id) {

        log.info("Get customer request received. ID: {}", id);

        CustomerDTO customer = customerService.getCustomerById(id);

        log.info("Customer retrieved successfully. ID: {}", id);

        return ResponseEntity.ok(
                CommonResponseDto.success(
                        "Customer retrieved successfully",
                        customer
                )
        );
    }

    // UPDATE CUSTOMER - ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommonResponseDto<CustomerDTO>> updateCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerDTO customerDTO) {

        log.info("Customer update request received. ID: {}", id);

        CustomerDTO updatedCustomer =
                customerService.updateCustomer(id, customerDTO);

        log.info("Customer updated successfully. ID: {}", id);

        return ResponseEntity.ok(
                CommonResponseDto.success(
                        "Customer updated successfully",
                        updatedCustomer
                )
        );
    }

    // DELETE CUSTOMER - ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CommonResponseDto<Void>> deleteCustomer(
            @PathVariable Integer id) {

        log.info("Customer deletion request received. ID: {}", id);

        customerService.deleteCustomer(id);

        log.info("Customer deleted successfully. ID: {}", id);

        return ResponseEntity.ok(
                CommonResponseDto.success(
                        "Customer deleted successfully",
                        null
                )
        );
    }
}
