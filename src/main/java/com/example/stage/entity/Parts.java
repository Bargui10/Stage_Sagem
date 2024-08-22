package com.example.stage.entity;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Parts {

    @Id
    @Column(name = "expected_pn")
    private String expectedPn;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prix")
    private Double prix;


    // Getters and setters
    public String getExpectedPn() {
        return expectedPn;
    }

    public void setExpectedPn(String expectedPn) {
        this.expectedPn = expectedPn;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

}