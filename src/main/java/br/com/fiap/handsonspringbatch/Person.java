package br.com.fiap.handsonspringbatch;

import java.time.LocalDateTime;

public class Person {

    private String name;

    private String streetName;

    private String number;

    private String city;

    private String country;

    private String email;

    private String phoneNumber;

    private LocalDateTime createdDateTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
