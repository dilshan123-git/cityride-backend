
package com.cityride.cabservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private Integer customerId;
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private String address;
}
