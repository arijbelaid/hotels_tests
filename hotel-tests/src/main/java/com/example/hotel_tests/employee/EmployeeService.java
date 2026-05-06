package com.example.hotel_tests.employee;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructeur pour l'injection de dépendance
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> listerTous() {
        return employeeRepository.findAll();
    }

    public Employee trouverParId(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee creer(EmployeeDTO dto) {
        Employee emp = new Employee(dto.getNom(), dto.getDepartment(), dto.getSalaire());
        return employeeRepository.save(emp);
    }

    public void supprimer(Long id) {
        employeeRepository.deleteById(id);
    }
}