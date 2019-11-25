package fr.ensimag.tpl;

import fr.ensimag.tpl.elements.Balls;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests unitaires de la classe fr.ensimag.tpl.balles.Balls
 */
public class BallsTest {
    @Test
    public void testReInit() {
        Balls balles = new Balls(4, 200, 200);
        String config1 = balles.toString();
        /* Booléen qui permet de savoir quand on a réussi une translation correcte
           La translation peut aboutir (car le point est dans le cadre et on
           translate de manière à ne pas sortir systématiquement du cadre. */
        boolean ok = false;
        while (!ok) {
            balles = new Balls(4, 200, 200);
            config1 = balles.toString();

            try {
                balles.translate(1, 1);
            } catch (IllegalArgumentException e) {
                // Si on n'a pas pu translater, on ne fait rien
                //e.printStackTrace();
            }
            ok = true;
        }

        String config2 = balles.toString();
        balles.reInit();
        String config3 = balles.toString();

        assertTrue(config1.equals(config3));
        assertFalse(config1.equals(config2));
        assertFalse(config2.equals(config3));
    }

    @Test
    public void testBalls1() {
        Balls balles = new Balls(4, 200, 200);

        assertTrue(balles instanceof Balls);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoBalls() {
        Balls balles = new Balls(0, 100, 140);

        assertTrue(balles instanceof Balls);
        assertTrue(balles.toString().equals("{}"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegBalls1() {
        Balls balles = new Balls(-1, 10, 100);
        assertTrue(balles instanceof Balls);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegBalls2() {
        Balls balles = new Balls(1, -10, 100);
        assertTrue(balles instanceof Balls);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegBalls3() {
        Balls balles = new Balls(1, 10, -37);
        assertTrue(balles instanceof Balls);
    }
}
