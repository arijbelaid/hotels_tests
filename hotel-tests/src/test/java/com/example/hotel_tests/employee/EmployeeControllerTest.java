package com.example.hotel_tests.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/employees → 200 avec liste JSON")
    void listerTous_retourneListe() throws Exception {
        Employee emp = new Employee("Alice", "IT", 5000);
        when(employeeService.listerTous()).thenReturn(List.of(emp));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/employees/99 → ressource introuvable → 404")
    void trouverParId_introuvable_404() throws Exception {
        when(employeeService.trouverParId(99L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/employees avec nom vide et salaire négatif → 400, service jamais appelé")
    void creer_invalide_400_et_serviceNonAppele() throws Exception {
        EmployeeDTO invalid = new EmployeeDTO("", -100, "IT");
        String json = objectMapper.writeValueAsString(invalid);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).creer(any());
    }
}