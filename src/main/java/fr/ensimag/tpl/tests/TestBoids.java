package fr.ensimag.tpl.tests;

import fr.ensimag.tpl.elements.Boids;
import fr.ensimag.tpl.elements.BoidsFollower;
import fr.ensimag.tpl.elements.BoidsOnRoad;
import fr.ensimag.tpl.elements.Path;
import fr.ensimag.tpl.elements.ProiePredateur;
import fr.ensimag.tpl.simulations.BoidsSimulator;
import fr.ensimag.tpl.simulations.GroupBoidsSimulator;
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
        gui.setSimulable(new BoidsSimulator(gui, new Boids(500, 500, nbBoids, 8)));
    }

    public static void testBoidsFollower(int nbBoids) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new BoidsSimulator(gui, new BoidsFollower(500, 500, nbBoids, 8)));
    }

    public static void testProiesPredateurs(int nbBoids) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new GroupBoidsSimulator(gui, new ProiePredateur(500, 500, nbBoids)));
    }
    
    public static void testBoidsOnRoad(int nbBoids) {
        Path.setRadius(10);
        Path.setScreenHeight(500);
        Path.setScreenWidth(500);
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new BoidsSimulator(gui, new BoidsOnRoad(500, 500, nbBoids, 8)));
    }
}
