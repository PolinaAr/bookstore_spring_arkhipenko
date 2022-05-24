package com.belhard.bookstore.service.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private Role role;
    private String email;
    private String password;
    private LocalDate birthday;

    public enum Role {
        ADMIN, MANAGER, CUSTOMER
    }

    public UserDto() {
    }

    public UserDto(String name, String lastName, Role role, String email, String password, LocalDate birthday) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(name, userDto.name)
                && Objects.equals(lastName, userDto.lastName)
                && role == userDto.role
                && Objects.equals(email, userDto.email)
                && Objects.equals(password, userDto.password)
                && Objects.equals(birthday, userDto.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, role, email, password, birthday);
    }

    @Override
    public String toString() {
        return "\nUserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
