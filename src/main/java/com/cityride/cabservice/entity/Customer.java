package com.cityride.cabservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(
        name = "customer_id",
        referencedColumnName = "user_id"
)
@Getter
@Setter
@ToString(callSuper = true)
public class Customer extends AuthUser {

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;
}