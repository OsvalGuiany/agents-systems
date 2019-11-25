/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.tests;

import fr.ensimag.tpl.elements.Balls;
import fr.ensimag.tpl.simulations.BallsSimulator;
import gui.GUISimulator;

import java.awt.*;

/**
 * Classe permettant de lancer le simulateur de balles pour le tester.
 */
public class TestBalls {
    /**
     * Test de simulation de balles.
     * Pas de simulateur : seulement affichage des traces à la génération puis après une translation.
     */
    public static void test() {
        Balls balles = new Balls(4, 200, 200);
        System.out.println(balles);

        balles.translate(4, 6);
        System.out.println(balles);

        Balls balles2 = new Balls(0, -200, 400);
        System.out.println(balles2);
    }

    /**
     * Test qui lance le simulateur de balles.
     * @param nbBalles Nombre de balles à simuler.
     */
    public static void testSimuBalls(int nbBalles) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        Balls balles = new Balls(nbBalles, gui.getPanelWidth(), gui.getPanelHeight());
        gui.setSimulable(new BallsSimulator(gui, balles));
    }
}
