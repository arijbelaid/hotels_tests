package com.example.hotel_tests.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        // Insertion de 3 employés en base H2
        repository.save(new Employee("Salah", "Informatique", 3500));
        repository.save(new Employee("Eya", "RH", 2800));
        repository.save(new Employee("Amar", "Informatique", 4200));
    }

    @Test
    @DisplayName("findByDepartment retourne les employés du département Informatique")
    void findByDepartment_informatique_retourneSalahEtAmar() {
        var employes = repository.findByDepartment("Informatique");
        assertThat(employes).hasSize(2);
        assertThat(employes)
                .extracting(Employee::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");
    }

    @Test
    @DisplayName("findBySalaireSuperieurA avec seuil 3000 retourne Salah et Amar (pas Eya)")
    void findBySalaireSuperieurA_seuil3000_retourneSalahEtAmar() {
        var employes = repository.findBySalaireSuperieurA(3000.0);
        assertThat(employes)
                .extracting(Employee::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");
    }

    @Test
    @DisplayName("findByDepartment avec département inexistant retourne liste vide")
    void findByDepartment_inexistant_retourneVide() {
        var employes = repository.findByDepartment("Comptabilité");
        assertThat(employes).isEmpty();
    }
}