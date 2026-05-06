package com.example.hotel_tests.reservation;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class ReservationService {

    public double calculerPrixTotal(double prixParNuit, int nombreNuits) {
        if (prixParNuit < 0) {
            throw new IllegalArgumentException("Le prix par nuit ne peut être négatif");
        }
        double total = prixParNuit * nombreNuits;
        if (nombreNuits >= 7) {
            total = total * 0.9; // -10%
        }
        return total;
    }

    public boolean verifierDisponibilite(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException("Les dates ne peuvent être nulles");
        }
        // Pour l'exercice, on suppose toujours disponible
        return true;
    }

    public String genererCodeConfirmation() {
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000); // 6 chiffres
        return "HOTEL-" + randomNum;
    }
}