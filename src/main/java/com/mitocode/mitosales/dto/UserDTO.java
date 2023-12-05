package com.mitocode.mitosales.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer idUser;
    @NotNull
    @JsonIncludeProperties(value = {"idRole", "nameRole"})
    private RoleDTO role;
    @NotNull @NotEmpty
    @Size(min = 3, max = 50)
    private String username;

    @NotNull @NotEmpty
    @Size(min = 3, max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //won't read password
    private String password;
    @NotNull
    private boolean enabled;
}
