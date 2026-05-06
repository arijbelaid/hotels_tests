package com.example.hotel_tests.reservation;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.*;

class ReservationServiceTest {

    private ReservationService service;

    @BeforeEach
    void setUp() {
        service = new ReservationService();
    }

    // ---- Étape 1 : Tests pour calculerPrixTotal ----
    @Test
    @DisplayName("Séjour de 3 nuits à 100€/nuit → 300€ (pas de remise)")
    void calculerPrixTotal_sejourCourt_sansRemise() {
        double prix = service.calculerPrixTotal(100.0, 3);
        assertThat(prix).isEqualTo(300.0);
    }

    @Test
    @DisplayName("Séjour de 7 nuits à 100€/nuit → 630€ (remise 10%)")
    void calculerPrixTotal_sejourLong_avecRemise() {
        double prix = service.calculerPrixTotal(100.0, 7);
        assertThat(prix).isEqualTo(630.0);
    }

    @Test
    @DisplayName("Prix par nuit négatif → IllegalArgumentException")
    void calculerPrixTotal_prixNegatif_throwsException() {
        assertThatThrownBy(() -> service.calculerPrixTotal(-50.0, 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ---- Étape 2 : Tests pour verifierDisponibilite ----
    @Test
    @DisplayName("Vérification disponibilité avec dates valides")
    void verifierDisponibilite_datesValides_retourneTrue() {
        // Pour l'instant on teste juste que ça ne lance pas d'exception
        assertThatCode(() -> service.verifierDisponibilite(LocalDate.now(), LocalDate.now().plusDays(3)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Vérification avec date début nulle → IllegalArgumentException")
    void verifierDisponibilite_debutNull_throwsException() {
        assertThatThrownBy(() -> service.verifierDisponibilite(null, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Vérification avec date fin nulle → IllegalArgumentException")
    void verifierDisponibilite_finNull_throwsException() {
        assertThatThrownBy(() -> service.verifierDisponibilite(LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ---- Étape 3 : Test du format du code de confirmation ----
    @Test
    @DisplayName("Code de confirmation commence par HOTEL- et fait 12 caractères")
    void genererCodeConfirmation_formatValide() {
        String code = service.genererCodeConfirmation();
        assertThat(code)
                .startsWith("HOTEL-")
                .hasSize(12);
    }
}