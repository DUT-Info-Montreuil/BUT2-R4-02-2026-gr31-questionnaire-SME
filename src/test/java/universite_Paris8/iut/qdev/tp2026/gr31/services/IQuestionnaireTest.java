package universite_Paris8.iut.qdev.tp2026.gr31.services;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;
import universite_Paris8.iut.qdev.tp2026.gr31.services.interfaces.Iquestionnaire;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.AccesRefuseException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIncoherentException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIntrouvableException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de l'interface IQuestionnaire.
 *
 * =============================================================================
 * STRATÉGIE GÉNÉRALE — SANS MOCK
 * =============================================================================
 * Puisque aucun framework de mock n'est utilisé, on procède via une
 * IMPLÉMENTATION DE TEST (Test Double manuel) : QuestionnaireServiceTestImpl.
 *
 * Cette classe interne implémente IQuestionnaire et simule les comportements
 * attendus (chargement CSV, filtrage, exceptions) sans accès réel au disque
 * pour la majorité des cas. Quelques tests utilisent de vrais fichiers
 * temporaires (java.io.File) pour les cas d'exceptions filesystem.
 *
 * STRUCTURE DES TESTS :
 *   BLOC A — chargerFichier() : retour booléen + exceptions
 *   BLOC B — fournirListeQuestionnaire()
 *   BLOC C — fournirUnQuestionnaire() : filtrage par difficulté + thème + langue
 *   BLOC D — Cas limites et enchaînements
 *
 * =============================================================================
 * IMPLÉMENTATION DE TEST (inner class)
 * =============================================================================
 * QuestionnaireServiceTestImpl centralise la logique simulée.
 * Les drapeaux booléens permettent de contrôler le comportement pour chaque test.
 */
@DisplayName("Tests de IQuestionnaire (via implémentation de test manuelle)")
class IQuestionnaireTest {

        // -------------------------------------------------------------------------
        // Implémentation de test (Test Double manuel — pas de Mockito)
        // -------------------------------------------------------------------------
        static class QuestionnaireServiceTestImpl implements Iquestionnaire {


                // Drapeaux de comportement — à configurer avant chaque test
                boolean simulerFichierIntrouvable  = false;
                boolean simulerFichierIncoherent   = false;
                boolean simulerAccesRefuse         = false;
                boolean simulerRetourFalse         = false;   // échec sans exception

                // Données internes simulées
                ArrayList<QuestionnaireDTO> listeInterne = new ArrayList<>();


                @Override
                public boolean chargerFichier(File file) {

                        // 1. Fichier null → FichierIntrouvableException
                        if (file == null) {
                                throw new RuntimeException(new FichierIntrouvableException());
                        }

                        // 2. Fichier inexistant → FichierIntrouvableException
                        if (simulerFichierIntrouvable || !file.exists()) {
                                throw new RuntimeException(new FichierIntrouvableException());
                        }

                        // 3. Fichier illisible → AccesRefuseException
                        if (simulerAccesRefuse || !file.canRead()) {
                                throw new RuntimeException(new AccesRefuseException());
                        }

                        // 4. Contenu invalide → FichierIncoherentException
                        if (simulerFichierIncoherent) {
                                throw new RuntimeException(new FichierIncoherentException());
                        }

                        // 5. Échec sans exception → false
                        if (simulerRetourFalse) {
                                return false;
                        }

                        // 6. Succès → true
                        return true;
                }
                @Override
                public void majStatQuestions() {
                        // Méthode vide pour les tests — aucun comportement à simuler
                }

                @Override
                public QuestionDTO fournirStatQuestion() {

                        return null;
                }

                @Override
                public ArrayList<QuestionnaireDTO> fournirListeQuestionnaire() {
                        return listeInterne;
                }

                @Override
                public QuestionnaireDTO fournirUnQuestionnaire(
                        int difficulte, LangueEnum langueEnum, String libelle) {

                        ArrayList<QuestionDTO> questionsFiltrees = new ArrayList<>();

                        for (QuestionnaireDTO q : listeInterne) {
                                if (q.getLibelleQuestionnaire().equalsIgnoreCase(libelle)
                                        && q.getLangue() == langueEnum) {

                                        for (QuestionDTO question : q.getListeQuestion()) {
                                                if (question.getDifficulte() == difficulte) {
                                                        questionsFiltrees.add(question);
                                                }
                                        }
                                }
                        }

                        if (questionsFiltrees.isEmpty()) {
                                return new QuestionnaireDTO("", null); // Retour liste vide = QuestionnaireDTO vide
                        }

                        QuestionnaireDTO resultat = new QuestionnaireDTO("", null);
                        resultat.setLibelleQuestionnaire(libelle);
                        resultat.setLangue(langueEnum);
                        resultat.setQuestions(questionsFiltrees);
                        return resultat;
                }
        }


        @Test
        @DisplayName("chargerFichier(null) lève FichierIntrouvableException")
        void testChargerFichierNull() {
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(null));
                assertInstanceOf(FichierIntrouvableException.class, ex.getCause());
        }

        @Test
        @DisplayName("chargerFichier() lève FichierIntrouvableException si fichier inexistant")
        void testChargerFichierInexistant() {
                File inexistant = new File("/tmp/fichier_qui_nexiste_pas_xxxxxxxxx.csv");
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(inexistant));
                assertInstanceOf(FichierIntrouvableException.class, ex.getCause());
        }

        @Test
        @DisplayName("chargerFichier() lève FichierIntrouvableException quand le drapeau est activé")
        void testChargerFichierDrapeauIntrouvable() {
                service.simulerFichierIntrouvable = true;
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(fichierTempValide));
                assertInstanceOf(FichierIntrouvableException.class, ex.getCause());
        }
        // -------------------------------------------------------------------------
        // Fixtures de test
        // -------------------------------------------------------------------------
        private QuestionnaireServiceTestImpl service;
        private File fichierTempValide;

        @BeforeEach
        void setUp() throws IOException {
                service = new QuestionnaireServiceTestImpl();

                // Fichier CSV temporaire valide (existe + lisible)
                fichierTempValide = File.createTempFile("questionnaire_test", ".csv");
                fichierTempValide.deleteOnExit();
                try (FileWriter fw = new FileWriter(fichierTempValide)) {
                        fw.write("difficulte;libelle;reponse;reference\n");
                        fw.write("1;2+2?;4;Arithmétique\n");
                }
        }

        @AfterEach
        void tearDown() {
                if (fichierTempValide != null && fichierTempValide.exists()) {
                        fichierTempValide.delete();
                }
        }

        // =========================================================================
        // BLOC A — chargerFichier()
        // =========================================================================

        @Test
        @DisplayName("chargerFichier() retourne true pour un fichier valide existant")
        void testChargerFichierValide()
                throws FichierIntrouvableException, FichierIncoherentException, AccesRefuseException {
                assertTrue(service.chargerFichier(fichierTempValide));
        }


        @Test
        @DisplayName("chargerFichier() lève AccesRefuseException quand le drapeau est activé")
        void testChargerFichierAccesRefuse() {
                service.simulerAccesRefuse = true;
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(fichierTempValide));
                assertInstanceOf(AccesRefuseException.class, ex.getCause());
        }

        @Test
        @DisplayName("chargerFichier() lève FichierIncoherentException quand le drapeau est activé")
        void testChargerFichierIncoherent() {
                service.simulerFichierIncoherent = true;
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(fichierTempValide));
                assertInstanceOf(FichierIncoherentException.class, ex.getCause());
        }

        @Test
        @DisplayName("chargerFichier() retourne false en cas d'échec sans exception")
        void testChargerFichierRetourneFalse() {
                service.simulerRetourFalse = true;
                assertFalse(service.chargerFichier(fichierTempValide));
        }

        @Test
        @DisplayName("chargerFichier() — AccesRefuseException est une Exception (héritage)")
        void testChargerFichierAccesRefuseEstException() {
                service.simulerAccesRefuse = true;
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(fichierTempValide));
                assertInstanceOf(AccesRefuseException.class, ex.getCause());
                assertNotNull(ex.getCause().getMessage());
        }

        @Test
        @DisplayName("chargerFichier() — FichierIncoherentException contient un message explicite")
        void testChargerFichierIncoherentMessage() {
                service.simulerFichierIncoherent = true;
                RuntimeException ex = assertThrows(RuntimeException.class,
                        () -> service.chargerFichier(fichierTempValide));
                assertInstanceOf(FichierIncoherentException.class, ex.getCause());
                assertNotNull(ex.getCause().getMessage());
                assertFalse(ex.getCause().getMessage().isBlank());
        }

        // =========================================================================
        // BLOC B — fournirListeQuestionnaire()
        // =========================================================================

        @Test
        @DisplayName("fournirListeQuestionnaire() retourne liste vide si aucun fichier chargé")
        void testFournirListeVideSansChargement() {
                ArrayList<QuestionnaireDTO> liste = service.fournirListeQuestionnaire();
                assertNotNull(liste, "La liste ne doit jamais être null.");
                assertTrue(liste.isEmpty());
        }

        @Test
        @DisplayName("fournirListeQuestionnaire() retourne la liste après ajout manuel")
        void testFournirListeApresAjout() {
                QuestionnaireDTO q = creerQuestionnaire("Mathématiques", LangueEnum.fr, 1);
                service.listeInterne.add(q);

                ArrayList<QuestionnaireDTO> liste = service.fournirListeQuestionnaire();
                assertEquals(1, liste.size());
                assertSame(q, liste.get(0));
        }

        @Test
        @DisplayName("fournirListeQuestionnaire() retourne tous les questionnaires chargés")
        void testFournirListePlusieursQuestionnaires() {
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.fr, 1));
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.an, 2));
                service.listeInterne.add(creerQuestionnaire("SVT", LangueEnum.ru, 3));

                assertEquals(3, service.fournirListeQuestionnaire().size());
        }

        @Test
        @DisplayName("fournirListeQuestionnaire() retourne bien la même référence de liste")
        void testFournirListeMemereference() {
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.fr, 1));
                assertSame(service.listeInterne, service.fournirListeQuestionnaire());
        }

        // =========================================================================
        // BLOC C — fournirUnQuestionnaire()
        // =========================================================================

        @Test
        @DisplayName("fournirUnQuestionnaire() retourne questionnaire vide si liste vide")
        void testFournirUnQuestionnaireSansData() {
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(1, LangueEnum.fr, "Mathématiques");
                assertNotNull(resultat);
                // liste vide = questions nulles ou vides
                assertTrue(resultat.getListeQuestion() == null || resultat.getListeQuestion().isEmpty());
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() retourne questionnaire vide si thème inexistant")
        void testFournirUnQuestionnaireThemeInexistant() {
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.fr, 1));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(1, LangueEnum.fr, "Physique");
                assertTrue(resultat.getListeQuestion() == null || resultat.getListeQuestion().isEmpty());
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() retourne questionnaire vide si langue ne correspond pas")
        void testFournirUnQuestionnaireLangueIncompatible() {
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.fr, 2));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(2, LangueEnum.an, "Mathématiques");
                assertTrue(resultat.getListeQuestion() == null || resultat.getListeQuestion().isEmpty());
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() retourne questionnaire vide si difficulté ne correspond pas")
        void testFournirUnQuestionnaireDifficulteIncompatible() {
                service.listeInterne.add(creerQuestionnaire("SVT", LangueEnum.fr, 1));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(3, LangueEnum.fr, "SVT");
                assertTrue(resultat.getListeQuestion() == null || resultat.getListeQuestion().isEmpty());
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() filtre correctement par difficulté 1")
        void testFournirUnQuestionnaireDifficulte1() {
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.fr, 1));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(1, LangueEnum.fr, "Mathématiques");
                assertNotNull(resultat.getListeQuestion());
                assertFalse(resultat.getListeQuestion().isEmpty());
                resultat.getListeQuestion().forEach(q ->
                        assertEquals(1, q.getDifficulte(), "Toutes les questions doivent être de difficulté 1."));
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() filtre correctement par difficulté 2")
        void testFournirUnQuestionnaireDifficulte2() {
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.an, 2));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(2, LangueEnum.an, "Sport");
                assertFalse(resultat.getListeQuestion().isEmpty());
                resultat.getListeQuestion().forEach(q -> assertEquals(2, q.getDifficulte()));
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() filtre correctement par difficulté 3")
        void testFournirUnQuestionnaireDifficulte3() {
                service.listeInterne.add(creerQuestionnaire("SVT", LangueEnum.ru, 3));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(3, LangueEnum.ru, "SVT");
                assertFalse(resultat.getListeQuestion().isEmpty());
                resultat.getListeQuestion().forEach(q -> assertEquals(3, q.getDifficulte()));
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() retourne le bon libellé et la bonne langue")
        void testFournirUnQuestionnaireMetadonnees() {
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.vt, 2));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(2, LangueEnum.vt, "Mathématiques");

                assertAll(
                        () -> assertEquals("Mathématiques", resultat.getLibelleQuestionnaire()),
                        () -> assertEquals(LangueEnum.vt, resultat.getLangue())
                );
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() gère plusieurs questionnaires chargés — ne retourne que le bon")
        void testFournirUnQuestionnairePlusieursDisponibles() {
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.fr, 1));
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.fr, 1));
                service.listeInterne.add(creerQuestionnaire("SVT", LangueEnum.fr, 1));

                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(1, LangueEnum.fr, "Sport");
                assertNotNull(resultat);
                assertEquals("Sport", resultat.getLibelleQuestionnaire());
        }

        // =========================================================================
        // BLOC D — Cas limites et enchaînements
        // =========================================================================

        @Test
        @DisplayName("Enchaînement : chargerFichier réussi puis liste non vide")
        void testEnchaînementChargementEtListe()
                throws FichierIntrouvableException, FichierIncoherentException, AccesRefuseException {
                // On simule le chargement (true) et on ajoute manuellement un questionnaire
                assertTrue(service.chargerFichier(fichierTempValide));
                service.listeInterne.add(creerQuestionnaire("Mathématiques", LangueEnum.fr, 1));
                assertFalse(service.fournirListeQuestionnaire().isEmpty());
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() avec libelle null → questionnaire vide (pas de crash)")
        void testFournirUnQuestionnaireLibelleNull() {
                service.listeInterne.add(creerQuestionnaire("Sport", LangueEnum.fr, 1));
                // Doit retourner un questionnaire vide, pas lever de NullPointerException
                assertDoesNotThrow(() -> {
                        QuestionnaireDTO r = service.fournirUnQuestionnaire(1, LangueEnum.fr, null);
                        assertTrue(r.getListeQuestion() == null || r.getListeQuestion().isEmpty());
                });
        }

        @Test
        @DisplayName("fournirUnQuestionnaire() avec langueEnum null → questionnaire vide (pas de crash)")
        void testFournirUnQuestionnaireLangueNull() {
                service.listeInterne.add(creerQuestionnaire("SVT", LangueEnum.fr, 2));
                assertDoesNotThrow(() -> {
                        QuestionnaireDTO r = service.fournirUnQuestionnaire(2, null, "SVT");
                        assertTrue(r.getListeQuestion() == null || r.getListeQuestion().isEmpty());
                });
        }

        @ParameterizedTest(name = "Thème : {0}")
        @ValueSource(strings = {"Mathématiques", "Sport", "SVT"})
        @DisplayName("fournirUnQuestionnaire() fonctionne pour chaque thème métier")
        void testFournirUnQuestionnairePourChaqueTheme(String theme) {
                service.listeInterne.add(creerQuestionnaire(theme, LangueEnum.fr, 1));
                QuestionnaireDTO resultat = service.fournirUnQuestionnaire(1, LangueEnum.fr, theme);
                assertFalse(resultat.getListeQuestion().isEmpty(),
                        "Un questionnaire doit être retourné pour le thème : " + theme);
        }

        // =========================================================================
        // MÉTHODE UTILITAIRE — Création d'un QuestionnaireDTO complet pour les tests
        // =========================================================================

        /**
         * Crée un QuestionnaireDTO de test avec une seule question de la difficulté donnée.
         */
        private QuestionnaireDTO creerQuestionnaire(String libelle, LangueEnum langue, int difficulte) {
                QuestionDTO q = new QuestionDTO(0, null,"","");
                q.setDifficulte(difficulte);
                q.setLibelle("Question test " + libelle + " diff=" + difficulte);
                q.setReponse("Réponse test");
                q.setReference("Ref test");

                ArrayList<QuestionDTO> questions = new ArrayList<>();
                questions.add(q);

                QuestionnaireDTO qDTO = new QuestionnaireDTO("", null);
                qDTO.setLibelleQuestionnaire(libelle);
                qDTO.setLangue(langue);
                qDTO.setQuestions(questions);
                return qDTO;
        }
}