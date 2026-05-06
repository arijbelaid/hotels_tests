package com.example.hotel_tests.integration;

import com.example.hotel_tests.employee.Employee;
import com.example.hotel_tests.employee.EmployeeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeApiE2ETest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("E2E : créer un employé puis le récupérer par ID")
    void creerPuisRecuperer() {
        EmployeeDTO dto = new EmployeeDTO("Karim", 4000, "Finance");

        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(
                "/api/employees",  // chemin relatif grâce à RANDOM_PORT
                dto,
                Employee.class
        );

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Employee created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getNom()).isEqualTo("Karim");

        Long id = created.getId();

        ResponseEntity<Employee> getResponse = restTemplate.getForEntity(
                "/api/employees/" + id,
                Employee.class
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNom()).isEqualTo("Karim");
    }

    @Test
    @DisplayName("E2E : créer, supprimer puis GET → 404")
    void creerPuisSupprimerPuis404() {
        EmployeeDTO dto = new EmployeeDTO("Leila", 3500, "Marketing");
        ResponseEntity<Employee> post = restTemplate.postForEntity(
                "/api/employees",
                dto,
                Employee.class
        );
        Long id = post.getBody().getId();

        restTemplate.delete("/api/employees/" + id);

        ResponseEntity<Employee> getAfterDelete = restTemplate.getForEntity(
                "/api/employees/" + id,
                Employee.class
        );
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}