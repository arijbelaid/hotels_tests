/*
 * SLOs (Service Level Objectives) pour le test de charge Gatling :
 * - Temps de réponse maximum acceptable pour GET /api/employees et GET /api/employees/1 :
 *     95e percentile < 300 ms
 * - Taux d'erreur maximum tolérable : < 1% (soit > 99% de requêtes réussies)
 * - Charge cible : 30 utilisateurs simultanés en montée progressive sur 10 secondes
 */
package com.example.hotel_tests.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EmployeeSimulation extends Simulation {

    // Configuration HTTP de base
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")   // URL de l'application démarrée
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Scénario type : un utilisateur consulte la liste, attend 1 seconde, puis consulte un employé précis
    ScenarioBuilder scenario = scenario("Consultation employés")
            .exec(http("GET /api/employees")          // nom de la requête pour le rapport
                    .get("/api/employees")
                    .check(status().is(200)))
            .pause(1)                                  // temps de réflexion d'1 seconde
            .exec(http("GET /api/employees/1")
                    .get("/api/employees/1")
                    .check(status().is(200)));

    {
        setUp(
                scenario.injectOpen(
                        rampUsers(30).during(10)       // charge normale : 30 utilisateurs en 10s
                ).protocols(httpProtocol)
        ).assertions(
                global().responseTime().percentile(95).lt(300),   // 95e percentile < 300ms
                global().successfulRequests().percent().gt(99.0)  // succès > 99% (erreur < 1%)
        );
    }
}