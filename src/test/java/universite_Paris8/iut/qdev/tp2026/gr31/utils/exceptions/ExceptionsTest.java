package universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.AccesRefuseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires des trois exceptions personnalisées :
 *   - AccesRefuseException
 *   - FichierIncoherentException
 *   - FichierIntrouvableException
 *
 * STRATÉGIE DE COUVERTURE :
 * - Héritage correct (extends Exception ou RuntimeException)
 * - Constructeur sans argument → getMessage() retourne null
 * - Levée et capture correcte via assertThrows
 * - Les trois exceptions sont bien DISTINCTES (pas de confusion de type)
 */
@DisplayName("Tests des exceptions personnalisées")
class ExceptionsTest {

        // =========================================================================
        // AccesRefuseException
        // Cas d'usage : tentative de lecture d'un fichier sans les droits suffisants
        // =========================================================================

        @Test
        @DisplayName("AccesRefuseException — héritage de Exception")
        void testAccesRefuseEstUneException() {
                assertTrue(new AccesRefuseException() instanceof Exception);
        }

        @Test
        @DisplayName("AccesRefuseException — getMessage() retourne null (pas de message)")
        void testAccesRefuseMessage() {
                AccesRefuseException ex = new AccesRefuseException();
                assertNull(ex.getMessage());
        }

        @Test
        @DisplayName("AccesRefuseException — getCause() retourne null (pas de cause)")
        void testAccesRefuseAvecCause() {
                AccesRefuseException ex = new AccesRefuseException();
                assertNull(ex.getCause());
        }

        @Test
        @DisplayName("AccesRefuseException — peut être levée et attrapée")
        void testAccesRefuseEstLevee() {
                assertThrows(AccesRefuseException.class, () -> {
                        throw new AccesRefuseException();
                });
        }

        @Test
        @DisplayName("AccesRefuseException — message null acceptable si constructeur sans arg existe")
        void testAccesRefuseSansMessage() {
                try {
                        AccesRefuseException ex = new AccesRefuseException();
                        assertNull(ex.getMessage());
                } catch (NoSuchMethodError | InstantiationError ignored) {
                        // Constructeur sans argument absent : comportement acceptable
                }
        }

        // =========================================================================
        // FichierIntrouvableException
        // Cas d'usage : le fichier passé est null ou n'existe pas sur le disque
        // =========================================================================

        @Test
        @DisplayName("FichierIntrouvableException — héritage de Exception")
        void testFichierIntrouvableEstUneException() {
                assertTrue(new FichierIntrouvableException() instanceof Exception);
        }

        @Test
        @DisplayName("FichierIntrouvableException — getMessage() retourne null (pas de message)")
        void testFichierIntrouvableMessage() {
                FichierIntrouvableException ex = new FichierIntrouvableException();
                assertNull(ex.getMessage());
        }

        @Test
        @DisplayName("FichierIntrouvableException — getCause() retourne null (pas de cause)")
        void testFichierIntrouvableAvecCause() {
                FichierIntrouvableException ex = new FichierIntrouvableException();
                assertNull(ex.getCause());
        }

        @Test
        @DisplayName("FichierIntrouvableException — peut être levée et attrapée")
        void testFichierIntrouvableEstLevee() {
                assertThrows(FichierIntrouvableException.class, () -> {
                        throw new FichierIntrouvableException();
                });
        }

        @Test
        @DisplayName("FichierIntrouvableException — n'est PAS une AccesRefuseException")
        void testFichierIntrouvableNEstPasAccesRefuse() {
                Exception ex = new FichierIntrouvableException();
                assertFalse(ex instanceof AccesRefuseException,
                        "FichierIntrouvableException et AccesRefuseException sont des types distincts.");
        }

        // =========================================================================
        // FichierIncoherentException
        // Cas d'usage : le fichier CSV est malformé, colonnes manquantes, etc.
        // =========================================================================

        @Test
        @DisplayName("FichierIncoherentException — héritage de Exception")
        void testFichierIncoherentEstUneException() {
                assertTrue(new FichierIncoherentException() instanceof Exception);
        }

        @Test
        @DisplayName("FichierIncoherentException — getMessage() retourne null (pas de message)")
        void testFichierIncoherentMessage() {
                FichierIncoherentException ex = new FichierIncoherentException();
                assertNull(ex.getMessage());
        }

        @Test
        @DisplayName("FichierIncoherentException — getCause() retourne null (pas de cause)")
        void testFichierIncoherentAvecCause() {
                FichierIncoherentException ex = new FichierIncoherentException();
                assertNull(ex.getCause());
        }

        @Test
        @DisplayName("FichierIncoherentException — peut être levée et attrapée")
        void testFichierIncoherentEstLevee() {
                assertThrows(FichierIncoherentException.class, () -> {
                        throw new FichierIncoherentException();
                });
        }

        @Test
        @DisplayName("FichierIncoherentException — n'est PAS une FichierIntrouvableException")
        void testFichierIncoherentNEstPasFichierIntrouvable() {
                Exception ex = new FichierIncoherentException();
                assertFalse(ex instanceof FichierIntrouvableException);
        }

        // =========================================================================
        // BLOC TRANSVERSAL — Distinction complète entre les 3 types
        // Ce test garantit qu'aucune exception n'hérite d'une autre par erreur.
        // =========================================================================

        @Test
        @DisplayName("Les trois exceptions sont bien des types totalement distincts")
        void testDistinctionCompleteDesExceptions() {
                Exception accesRefuse = new AccesRefuseException();
                Exception introuvable = new FichierIntrouvableException();
                Exception incoherent  = new FichierIncoherentException();

                // AccesRefuseException ne doit pas être les deux autres
                assertFalse(accesRefuse instanceof FichierIntrouvableException);
                assertFalse(accesRefuse instanceof FichierIncoherentException);

                // FichierIntrouvableException ne doit pas être les deux autres
                assertFalse(introuvable instanceof AccesRefuseException);
                assertFalse(introuvable instanceof FichierIncoherentException);

                // FichierIncoherentException ne doit pas être les deux autres
                assertFalse(incoherent instanceof AccesRefuseException);
                assertFalse(incoherent instanceof FichierIntrouvableException);
        }
}