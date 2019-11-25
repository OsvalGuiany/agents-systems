package fr.ensimag.tpl;

import fr.ensimag.tpl.elements.Cellules;
import fr.ensimag.tpl.elements.CellulesImmigration;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Tests unitaires pour les cellules d'immigration.
 */
public class CellulesImmigrationTest {
    @Test
    public void testBasicImmigration() {
        CellulesImmigration ci = new CellulesImmigration(14, 15, 4);

        assertTrue(ci instanceof CellulesImmigration);
        assertTrue(ci instanceof Cellules);
        assertTrue(ci.getNombreEtats() == 4);
        assertTrue(ci.getHauteur() == 15);
        assertTrue(ci.getLargeur() == 14);

        for (int i = 0; i < ci.getLargeur(); i++) {
            for (int j = 0; j < ci.getHauteur(); j++) {
                assertTrue(ci.getEtat(i, j) >= 0);
                assertTrue(ci.getEtat(i, j) < 4);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNbNegeEtats() {
        CellulesImmigration ci = new CellulesImmigration(13, 43, -4);

        assertTrue(ci instanceof CellulesImmigration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroEtats() {
        CellulesImmigration ci = new CellulesImmigration(13, 43, 0);

        assertTrue(ci instanceof CellulesImmigration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImmigrationNegatif() {
        CellulesImmigration ci = new CellulesImmigration(-4, -7, 4);

        assertTrue(ci instanceof CellulesImmigration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImmigrationZero() {
        CellulesImmigration ci = new CellulesImmigration(0, 0, 45);

        assertTrue(ci instanceof CellulesImmigration);
    }
}
