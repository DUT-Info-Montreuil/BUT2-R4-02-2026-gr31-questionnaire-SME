package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de QuestionDTO.
 *
 * STRATÉGIE DE COUVERTURE :
 * - Constructeur par défaut et constructeur paramétré (si existant)
 * - Chaque getter/setter individuellement
 * - Valeurs limites du champ difficulte (1, 2, 3 = valides ; 0 et 4 = hors domaine)
 * - Valeurs null sur les champs String
 * - Égalité de valeurs après set
 */
@DisplayName("Tests de QuestionDTO")
class QuestionDTOTest {

        private QuestionDTO question;

        // -------------------------------------------------------------------------
        // SETUP — exécuté avant chaque test
        // On crée une instance propre pour garantir l'isolation entre les tests.
        // -------------------------------------------------------------------------
        @BeforeEach
        void setUp() {
                question = new QuestionDTO(0, "", "", "");
        }

        // =========================================================================
        // BLOC 1 — Instanciation
        // =========================================================================

        @Test
        @DisplayName("L'instance ne doit pas être nulle après création")
        void testInstanciationNonNull() {
                assertNotNull(question,
                        "Une nouvelle instance de QuestionDTO ne doit pas être null.");
        }

        // =========================================================================
        // BLOC 2 — difficulte (int, domaine [1-3])
        // =========================================================================

        @ParameterizedTest(name = "difficulte = {0} → valeur valide acceptée")
        @ValueSource(ints = {1, 2, 3})
        @DisplayName("setDifficulte accepte les valeurs valides 1, 2, 3")
        void testSetDifficulteValide(int valeur) {
                question.setDifficulte(valeur);
                assertEquals(valeur, question.getDifficulte(),
                        "getDifficulte() doit retourner la valeur définie par setDifficulte().");
        }

        @Test
        @DisplayName("getDifficulte retourne bien la valeur après set à 1 (borne basse)")
        void testDifficulteMin() {
                question.setDifficulte(1);
                assertEquals(1, question.getDifficulte());
        }

        @Test
        @DisplayName("getDifficulte retourne bien la valeur après set à 3 (borne haute)")
        void testDifficulteMax() {
                question.setDifficulte(3);
                assertEquals(3, question.getDifficulte());
        }

        /**
         * Les valeurs 0 et 4 sont HORS domaine métier.
         * Ce test documente le comportement actuel du DTO (pas de validation interne).
         * Si une validation est ajoutée, ce test devra être adapté.
         */
        @ParameterizedTest(name = "difficulte = {0} → hors domaine, stocké tel quel (pas de validation DTO)")
        @ValueSource(ints = {0, 4, -1, 99})
        @DisplayName("setDifficulte stocke les valeurs hors domaine sans lever d'exception (validation métier externe)")
        void testDifficulteHorsDomaine(int valeur) {
                // Le DTO est un simple conteneur de données : il ne valide pas.
                // La validation métier appartient à la couche service.
                assertDoesNotThrow(() -> question.setDifficulte(valeur));
        }

        // =========================================================================
        // BLOC 3 — libelle (String)
        // =========================================================================

        @Test
        @DisplayName("setLibelle / getLibelle — cas nominal")
        void testLibelleNominal() {
                question.setLibelle("Quelle est la formule de l'eau ?");
                assertEquals("Quelle est la formule de l'eau ?", question.getLibelle());
        }

        @Test
        @DisplayName("setLibelle accepte une chaîne vide")
        void testLibelleVide() {
                question.setLibelle("");
                assertEquals("", question.getLibelle());
        }

        @Test
        @DisplayName("setLibelle accepte null (pas de validation dans le DTO)")
        void testLibelleNull() {
                question.setLibelle(null);
                assertNull(question.getLibelle());
        }

        @Test
        @DisplayName("setLibelle écrase correctement une valeur précédente")
        void testLibelleEcrasement() {
                question.setLibelle("Ancienne question");
                question.setLibelle("Nouvelle question");
                assertEquals("Nouvelle question", question.getLibelle());
        }

        // =========================================================================
        // BLOC 4 — reponse (String)
        // =========================================================================

        @Test
        @DisplayName("setReponse / getReponse — cas nominal")
        void testReponseNominal() {
                question.setReponse("H2O");
                assertEquals("H2O", question.getReponse());
        }

        @Test
        @DisplayName("setReponse accepte une chaîne vide")
        void testReponseVide() {
                question.setReponse("");
                assertEquals("", question.getReponse());
        }

        @Test
        @DisplayName("setReponse accepte null")
        void testReponseNull() {
                question.setReponse(null);
                assertNull(question.getReponse());
        }

        // =========================================================================
        // BLOC 5 — reference (String)
        // =========================================================================

        @Test
        @DisplayName("setReference / getReference — cas nominal")
        void testReferenceNominal() {
                question.setReference("Manuel SVT 3ème, p.42");
                assertEquals("Manuel SVT 3ème, p.42", question.getReference());
        }

        @Test
        @DisplayName("setReference accepte null")
        void testReferenceNull() {
                question.setReference(null);
                assertNull(question.getReference());
        }

        @Test
        @DisplayName("setReference accepte une chaîne vide")
        void testReferenceVide() {
                question.setReference("");
                assertEquals("", question.getReference());
        }

        // =========================================================================
        // BLOC 6 — Cohérence globale : remplissage complet d'un objet
        // =========================================================================

        @Test
        @DisplayName("Un QuestionDTO entièrement rempli conserve bien toutes ses valeurs")
        void testRemplissageComplet() {
                question = new QuestionDTO(2, "Quel est le symbole chimique de l'or ?", "Au", "Tableau périodique");

                assertAll("Vérification globale de QuestionDTO",
                        () -> assertEquals(2, question.getDifficulte()),
                        () -> assertEquals("Quel est le symbole chimique de l'or ?", question.getLibelle()),
                        () -> assertEquals("Au", question.getReponse()),
                        () -> assertEquals("Tableau périodique", question.getReference())
                );
        }
}