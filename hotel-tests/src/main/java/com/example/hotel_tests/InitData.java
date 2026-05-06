package com.example.hotel_tests;

import com.example.hotel_tests.employee.Employee;
import com.example.hotel_tests.employee.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public InitData(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            Employee emp = new Employee("Jean", "IT", 3000);
            employeeRepository.save(emp);
            System.out.println("Employé de démonstration ajouté avec ID : " + emp.getId());
        }
    }
}