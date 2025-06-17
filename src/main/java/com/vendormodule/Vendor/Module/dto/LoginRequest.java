package com.ceil.vendor.vendorportal.dto;

import lombok.Data; // If you're using Lombok
import lombok.NoArgsConstructor; // If you're using Lombok
import lombok.AllArgsConstructor; // If you're using Lombok

// Lombok annotations (if you have Lombok in your pom.xml)
@Data // Automatically generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class LoginRequest {

    private String email;    // Corresponds to the 'name="email"' input in your HTML form
    private String password; // Corresponds to the 'name="password"' input in your HTML form

}
