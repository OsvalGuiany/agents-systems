package fr.ensimag.tpl;

import fr.ensimag.tpl.elements.Cellules;
import fr.ensimag.tpl.elements.CellulesConway;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests unitaires pour les cellules de Conway.
 */
public class CellulesConwayTest {
    @Test
    public void testBasicConway() {
        CellulesConway cc = new CellulesConway(14, 15);

        assertTrue(cc instanceof CellulesConway);
        assertTrue(cc instanceof Cellules);
        assertTrue(cc.getNombreEtats() == 2);
        assertTrue(cc.getHauteur() == 15);
        assertTrue(cc.getLargeur() == 14);

        for (int i = 0; i < cc.getLargeur(); i++) {
            for (int j = 0; j < cc.getHauteur(); j++) {
                assertTrue(cc.getEtat(i, j) >= 0);
                assertTrue(cc.getEtat(i, j) < 2);
            }
        }
    }

    @Test
    public void testEtats() {
        CellulesConway cc = new CellulesConway(14, 15);

        assertTrue(cc.getNombreEtats() == 2);

        int [][] etats = new int[14][15];

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 15; j++) {
                etats[i][j] = cc.getEtat(i, j);
            }
        }

        cc.nextState();
        cc.nextState();

        cc.restart();

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 15; j++) {
                assertTrue(etats[i][j] == cc.getEtat(i, j));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConwayNegatif() {
        CellulesConway cc = new CellulesConway(-4, -7);
        
        assertTrue(cc instanceof CellulesConway);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConwayZero() {
        CellulesConway cc = new CellulesConway(0, 0);
        
        assertTrue(cc instanceof CellulesConway);
    }
}
