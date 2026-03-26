package universite_Paris8.iut.qdev.tp2026.gr31.commons.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de LangueEnum.
 *
 * STRATÉGIE DE COUVERTURE :
 * - Existence de chaque valeur attendue par le métier
 * - valueOf() : résolution par nom exact
 * - valueOf() : nom invalide → IllegalArgumentException (comportement Java natif documenté)
 * - Unicité des valeurs (pas de doublon)
 * - Comparaison d'égalité entre constantes
 */
@DisplayName("Tests de LangueEnum")
class LangueEnumTest {

        // =========================================================================
        // BLOC 1 — Existence des valeurs métier
        // Chaque valeur utilisée dans le projet doit exister dans l'enum.
        // =========================================================================

        @Test
        @DisplayName("La valeur FRANCAIS existe dans l'enum")
        void testFrancaisExiste() {
                assertNotNull(LangueEnum.fr);
        }

        @Test
        @DisplayName("La valeur ANGLAIS existe dans l'enum")
        void testAnglaisExiste() {
                assertNotNull(LangueEnum.an);
        }

        @Test
        @DisplayName("La valeur RUSSE existe dans l'enum")
        void testRusseExiste() {
                assertNotNull(LangueEnum.ru);
        }

        @Test
        @DisplayName("La valeur VIETNAMIEN existe dans l'enum")
        void testVietnamienExiste() {
                assertNotNull(LangueEnum.vt);
        }

        // =========================================================================
        // BLOC 2 — valueOf() : résolution par nom
        // =========================================================================

        @Test
        @DisplayName("valueOf(\"FRANCAIS\") retourne bien LangueEnum.fr")
        void testValueOfFrancais() {
                assertEquals(LangueEnum.fr, LangueEnum.valueOf("fr"));
        }

        @Test
        @DisplayName("valueOf(\"ANGLAIS\") retourne bien LangueEnum.an")
        void testValueOfAnglais() {
                assertEquals(LangueEnum.an, LangueEnum.valueOf("an"));
        }

        @Test
        @DisplayName("valueOf(\"RUSSE\") retourne bien LangueEnum.ru")
        void testValueOfRusse() {
                assertEquals(LangueEnum.ru, LangueEnum.valueOf("ru"));
        }

        @Test
        @DisplayName("valueOf(\"VIETNAMIEN\") retourne bien LangueEnum.vt")
        void testValueOfVietnamien() {
                assertEquals(LangueEnum.vt, LangueEnum.valueOf("vt"));
        }

        @Test
        @DisplayName("valueOf() avec nom inconnu lève IllegalArgumentException")
        void testValueOfInconnu() {
                assertThrows(IllegalArgumentException.class,
                        () -> LangueEnum.valueOf("INCONNU"),
                        "Un nom inexistant doit déclencher IllegalArgumentException.");
        }

        // =========================================================================
        // BLOC 3 — Unicité et nombre de valeurs
        // =========================================================================

        @Test
        @DisplayName("L'enum contient au moins 4 valeurs métier")
        void testNombreValeurs() {
                assertTrue(LangueEnum.values().length >= 4,
                        "L'enum doit contenir au minimum fr, an, ru, vt.");
        }

        @ParameterizedTest
        @EnumSource(LangueEnum.class)
        void testToutesValeursNonNulles(LangueEnum langue) {
                assertNotNull(langue);
        }

        // =========================================================================
        // BLOC 4 — Égalité des constantes (== sur les enums est correct en Java)
        // =========================================================================

        @Test
        @DisplayName("Deux références à FRANCAIS sont identiques (==)")
        void testEgaliteFrancais() {
                LangueEnum l1 = LangueEnum.fr;
                LangueEnum l2 = LangueEnum.fr;
                assertSame(l1, l2, "Les constantes d'enum sont des singletons.");
        }

        @Test
        @DisplayName("FRANCAIS et ANGLAIS sont des valeurs distinctes")
        void testDistinctionFrancaisAnglais() {
                assertNotSame(LangueEnum.fr, LangueEnum.an);
                assertNotEquals(LangueEnum.fr, LangueEnum.an);
        }

        // =========================================================================
        // BLOC 5 — name() : cohérence du nom de la constante
        // =========================================================================

        @Test
        @DisplayName("name() de FRANCAIS retourne la chaîne \"fr\"")
        void testNameFrancais() {
                assertEquals("fr", LangueEnum.fr.name());
        }

        @Test
        @DisplayName("name() de ANGLAIS retourne la chaîne \"an\"")
        void testNameAnglais() {
                assertEquals("an", LangueEnum.an.name());
        }
}