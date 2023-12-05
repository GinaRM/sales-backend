package com.mitocode.mitosales.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private Integer idClient;
    @NotNull @NotEmpty
    @Size(min = 3, max = 10) //string only not numbers type
    private String firstName;
    @NotNull @NotEmpty
    @Size(min = 3, max = 100)
    private String lastName;
    @NotNull @NotEmpty
    @Size(min = 3, max = 100)
    private String country;
    @NotNull @NotEmpty
    @Size(min = 10, max = 10)
    private String cardId;
    @NotNull @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;
    @NotNull @NotEmpty
    @Email
    private String email;
    @Size(min = 3, max = 100)
    private String address;
}
