package com.pragma.powerup.plazamicroservice.domain.dto;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String dni;
    private String phone;
    private String birthDate;
    private String email;
    private String password;
    private Long idRole;

    public User(Long id, String name, String surname, String dni, String phone, String birthDate, String email, String password, Long idRole) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.idRole = idRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long role) {
        this.idRole = role;
    }
}
