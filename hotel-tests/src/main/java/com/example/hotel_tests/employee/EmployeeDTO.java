package com.example.hotel_tests.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class EmployeeDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @Positive(message = "Le salaire doit être > 0")
    private double salaire;
    private String department;

    public EmployeeDTO() {}
    public EmployeeDTO(String nom, double salaire, String department) {
        this.nom = nom;
        this.salaire = salaire;
        this.department = department;
    }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}