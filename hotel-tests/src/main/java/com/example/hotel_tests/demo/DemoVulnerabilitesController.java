package com.example.hotel_tests.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoVulnerabilitesController {

    @PersistenceContext
    private EntityManager em;

    // Faille 1 : injection SQL par concaténation
    @GetMapping("/search")
    public Object rechercher(@RequestParam String nom) {
        return em.createQuery("SELECT e FROM Employee e WHERE e.nom = :nom", Object.class)
                .setParameter("nom", nom)
                .getResultList();}

    // Faille 2 : secret en dur
    @Value("${api.secret}")
    private String apiSecret;}