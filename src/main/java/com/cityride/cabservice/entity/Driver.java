
package com.cityride.cabservice.entity;

import com.cityride.cabservice.enums.Availability;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "drivers")
@PrimaryKeyJoinColumn(
        name = "driver_id",
        referencedColumnName = "user_id"
)
@Getter
@Setter
@ToString(callSuper = true)
public class Driver extends AuthUser {

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private Availability availability = Availability.Available;
}
