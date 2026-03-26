package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de QuestionnaireDTO.
 *
 * STRATÉGIE DE COUVERTURE :
 * - Instanciation et valeurs initiales
 * - Getter/Setter : libelleQuestionnaire, langueEnum, questions
 * - Manipulation de la liste (ajout, suppression, liste vide)
 * - Cas limites : null, liste vide, plusieurs questions
 */
@DisplayName("Tests de QuestionnaireDTO")
class QuestionnaireDTOTest {

        private QuestionnaireDTO questionnaire;
        private QuestionDTO questionA;
        private QuestionDTO questionB;

        // -------------------------------------------------------------------------
        // SETUP — données partagées propres à chaque test
        // -------------------------------------------------------------------------
        @BeforeEach
        void setUp() {
                questionnaire = questionnaire = new QuestionnaireDTO("", LangueEnum.fr);

                questionA = new QuestionDTO(1,"Combien font 2+2 ?","4","Arithmétique de base");

                questionB = new QuestionDTO(2,"Quelle est la dérivée de x² ?","2x","Analyse - Terminale");

        }

        // =========================================================================
        // BLOC 1 — Instanciation
        // =========================================================================

        @Test
        @DisplayName("L'instance ne doit pas être nulle après création")
        void testInstanciationNonNull() {
                assertNotNull(questionnaire);
        }

        // =========================================================================
        // BLOC 2 — libelleQuestionnaire
        // =========================================================================

        @Test
        @DisplayName("setLibelleQuestionnaire / getLibelleQuestionnaire — cas nominal")
        void testLibelleNominal() {
                questionnaire.setLibelleQuestionnaire("Mathématiques");
                assertEquals("Mathématiques", questionnaire.getLibelleQuestionnaire());
        }

        @Test
        @DisplayName("setLibelleQuestionnaire accepte null")
        void testLibelleNull() {
                questionnaire.setLibelleQuestionnaire(null);
                assertNull(questionnaire.getLibelleQuestionnaire());
        }

        @Test
        @DisplayName("setLibelleQuestionnaire accepte une chaîne vide")
        void testLibelleVide() {
                questionnaire.setLibelleQuestionnaire("");
                assertEquals("", questionnaire.getLibelleQuestionnaire());
        }

        @Test
        @DisplayName("setLibelleQuestionnaire écrase la valeur précédente")
        void testLibelleEcrasement() {
                questionnaire.setLibelleQuestionnaire("Sport");
                questionnaire.setLibelleQuestionnaire("SVT");
                assertEquals("SVT", questionnaire.getLibelleQuestionnaire());
        }

        // =========================================================================
        // BLOC 3 — langueEnum
        // =========================================================================

        @Test
        @DisplayName("setLangueEnum / getLangueEnum — fr")
        void testLangueFrancais() {
                questionnaire.setLangue(LangueEnum.fr);
                assertEquals(LangueEnum.fr, questionnaire.getLangue());
        }

        @Test
        @DisplayName("setLangueEnum / getLangueEnum — an")
        void testLangueAnglais() {
                questionnaire.setLangue(LangueEnum.an);
                assertEquals(LangueEnum.an, questionnaire.getLangue());
        }

        @Test
        @DisplayName("setLangueEnum / getLangueEnum — ru")
        void testLangueRusse() {
                questionnaire.setLangue(LangueEnum.ru);
                assertEquals(LangueEnum.ru, questionnaire.getLangue());
        }

        @Test
        @DisplayName("setLangueEnum / getLangueEnum — vt")
        void testLangueVietnamien() {
                questionnaire.setLangue(LangueEnum.vt);
                assertEquals(LangueEnum.vt, questionnaire.getLangue());
        }

        @Test
        @DisplayName("setLangueEnum accepte null")
        void testLangueNull() {
                questionnaire.setLangue(null);
                assertNull(questionnaire.getLangue());
        }

        // =========================================================================
        // BLOC 4 — Liste de QuestionDTO
        // =========================================================================

        @Test
        @DisplayName("La liste de questions initialisée doit être vide ou non nulle")
        void testListeInitialeNonNulleOuVide() {
                // Selon l'implémentation : soit null, soit ArrayList vide.
                // Les deux sont acceptables au niveau DTO.
                ArrayList<QuestionDTO> liste = questionnaire.getListeQuestion();
                assertTrue(liste == null || liste.isEmpty(),
                        "La liste initiale doit être null ou vide, jamais pré-remplie.");
        }

        @Test
        @DisplayName("setQuestions / getQuestions — affectation d'une liste non vide")
        void testSetListeNonVide() {
                ArrayList<QuestionDTO> liste = new ArrayList<>();
                liste.add(questionA);
                liste.add(questionB);

                questionnaire.setQuestions(liste);

                assertEquals(2, questionnaire.getListeQuestion().size());
                assertTrue(questionnaire.getListeQuestion().contains(questionA));
                assertTrue(questionnaire.getListeQuestion().contains(questionB));
        }

        @Test
        @DisplayName("setQuestions avec liste vide → getQuestions retourne liste vide")
        void testSetListeVide() {
                questionnaire.setQuestions(new ArrayList<>());
                assertNotNull(questionnaire.getListeQuestion());
                assertTrue(questionnaire.getListeQuestion().isEmpty());
        }

        @Test
        @DisplayName("setQuestions accepte null")
        void testSetListeNull() {
                questionnaire.setQuestions(null);
                assertNull(questionnaire.getListeQuestion());
        }

        @Test
        @DisplayName("Ajout manuel d'une question dans la liste récupérée")
        void testAjoutManuelQuestion() {
                ArrayList<QuestionDTO> liste = new ArrayList<>();
                questionnaire.setQuestions(liste);

                questionnaire.getListeQuestion().add(questionA);

                assertEquals(1, questionnaire.getListeQuestion().size());
                assertSame(questionA, questionnaire.getListeQuestion().get(0));
        }

        @Test
        @DisplayName("Suppression d'une question dans la liste")
        void testSuppressionQuestion() {
                ArrayList<QuestionDTO> liste = new ArrayList<>();
                liste.add(questionA);
                liste.add(questionB);
                questionnaire.setQuestions(liste);

                questionnaire.getListeQuestion().remove(questionA);

                assertEquals(1, questionnaire.getListeQuestion().size());
                assertFalse(questionnaire.getListeQuestion().contains(questionA));
                assertTrue(questionnaire.getListeQuestion().contains(questionB));
        }

        @Test
        @DisplayName("Remplacement complet de la liste par une nouvelle")
        void testRemplacementListe() {
                ArrayList<QuestionDTO> ancienne = new ArrayList<>();
                ancienne.add(questionA);
                questionnaire.setQuestions(ancienne);

                ArrayList<QuestionDTO> nouvelle = new ArrayList<>();
                nouvelle.add(questionB);
                questionnaire.setQuestions(nouvelle);

                assertEquals(1, questionnaire.getListeQuestion().size());
                assertSame(questionB, questionnaire.getListeQuestion().get(0));
        }

        // =========================================================================
        // BLOC 5 — Cohérence globale
        // =========================================================================

        @Test
        @DisplayName("QuestionnaireDTO entièrement rempli conserve toutes ses valeurs")
        void testRemplissageComplet() {
                ArrayList<QuestionDTO> liste = new ArrayList<>();
                liste.add(questionA);

                questionnaire.setLibelleQuestionnaire("Mathématiques");
                questionnaire.setLangue(LangueEnum.fr);
                questionnaire.setQuestions(liste);

                assertAll("Vérification globale de QuestionnaireDTO",
                        () -> assertEquals("Mathématiques", questionnaire.getLibelleQuestionnaire()),
                        () -> assertEquals(LangueEnum.fr, questionnaire.getLangue()),
                        () -> assertEquals(1, questionnaire.getListeQuestion().size())
                );
        }
}