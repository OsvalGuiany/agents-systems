package fr.ensimag.tpl.tests;

import fr.ensimag.tpl.elements.Boids;
import fr.ensimag.tpl.simulations.BoidsSimulator;
import gui.GUISimulator;

import java.awt.*;

/**
 * Classe permettant de lancer les simulateurs de boids.
 */
public class TestBoids {
    /**
     * Démarre le simulateur de boids "classiques".
     * @param nbBoids Nombre de boids à simuler.
     */
    public static void testSimuBoids(int nbBoids) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new BoidsSimulator(gui, new Boids(500, 500, nbBoids)));
    }
}
