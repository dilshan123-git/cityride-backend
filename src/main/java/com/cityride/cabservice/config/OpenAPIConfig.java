
package com.cityride.cabservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CityRide Cab Booking API",
                version = "1.0.0",
                description = "REST API documentation for the CityRide Taxi and Cab Booking Platform",
                contact = @Contact(
                        name = "CityRide Development Team"
                )
        )
)
public class OpenAPIConfig {
}
