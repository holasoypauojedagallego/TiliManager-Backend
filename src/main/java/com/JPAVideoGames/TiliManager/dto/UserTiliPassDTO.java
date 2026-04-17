package com.JPAVideoGames.TiliManager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserTiliPassDTO {
    private UUID id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 16, message = "Entre 3 y 16 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._+-]+$", message = "Ha de tener caracteres válidos (a-zA-Z0-9._+-)")
    private String name;

    @NotBlank
    @Email
    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
