package fr.ensimag.tpl.tests;

import fr.ensimag.tpl.elements.CellulesConway;
import fr.ensimag.tpl.elements.CellulesImmigration;
import fr.ensimag.tpl.elements.CellulesSchelling;
import fr.ensimag.tpl.simulations.CellulesSimulator;
import gui.GUISimulator;

import java.awt.*;

/**
 * Classe permettant de lancer le simulateur de cellules pour chacun des types de cellules.
 */
public class TestCells {
    /**
     * Teste le simulateur de cellules de Conway sur une grille 14×15.
     */
    public static void test() {
        CellulesConway cc = new CellulesConway(14, 15);
    }

    /**
     * Lance un simulateur de cellules de Conway de taille spécifique.
     *
     * @param largeur Nombre de cases en largeur.
     * @param hauteur Nombre de cases en hauteur.
     */
    public static void testSimuConway(int largeur, int hauteur) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new CellulesSimulator(gui, new CellulesConway(largeur, hauteur)));
    }

    /**
     * Lance un simulateur de cellules d'immigration avec des paramètres personnalisés.
     *
     * @param largeur     Nombre de cases en largeur.
     * @param hauteur     Nombre de cases en hauteur.
     * @param nombreEtats Nombre d'états possibles.
     */
    public static void testSimuImmigration(int largeur, int hauteur, int nombreEtats) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(new CellulesSimulator(gui, new CellulesImmigration(largeur, hauteur, nombreEtats)));
    }

    /**
     * Lance un simulateur de cellules de Schelling avec des paramètres personnalisés.
     *
     * @param largeur        Nombre de cases en largeur.
     * @param hauteur        Nombre de cases en hauteur.
     * @param seuil          Seuil du nombre de voisins.
     * @param nombreCouleurs Nombre de couleurs possibles.
     */
    public static void testSimuShelling(int largeur, int hauteur, int seuil, int nombreCouleurs) {
        GUISimulator gui = new GUISimulator(500, 500, Color.WHITE);
        gui.setSimulable(
                new CellulesSimulator(
                        gui,
                        new CellulesSchelling(largeur, hauteur, seuil, nombreCouleurs)
                )
        );
    }

    /**
     * Test permettant d'étudier les transitions de cellules de Conway sur une grille 4×4 à l'aide d'une trace
     * textuelle.
     */
    public static void testConwayTrace() {
        // cette partie du code permet de tester notre
        // jeu de la vie de Conway
        CellulesConway cels = new CellulesConway(4, 4);
        System.out.println(cels);
        cels.nextState();
        System.out.println(cels);
        cels.nextState();
        System.out.println(cels);
    }
}
