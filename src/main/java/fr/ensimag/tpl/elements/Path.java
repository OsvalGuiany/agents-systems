package fr.ensimag.tpl.elements;

import processing.core.PVector;

/**
 * Classe permettant de modéliser un chemin pour les simulations de boids.
 *
 * @author equipe78
 */
public class Path {
    /**
     * Rayon du chemin.
     */
    private static float radius;

    /**
     * Largeur de l'écran de simulation.
     */
    private static float screenWidth;

    /**
     * Hauteur de l'écran de simulation.
     */
    private static float screenHeight;

    /**
     * Permet de verifier si les paramètres screenHeight, screenWidth et radius ont été initialisés.
     */
    private static void areSet() {
        if (screenHeight == 0)
            throw new IllegalArgumentException("les paramètres statiques de la classe"
                    + "Path doivent être intialisée");
        if (screenWidth == 0)
            throw new IllegalArgumentException("les paramètres statiques de la classe"
                    + "Path doivent être intialisée");
        if (radius == 0)
            throw new IllegalArgumentException("les paramètres statiques de la classe"
                    + "Path doivent être intialisée");
    }

    /**
     * Permet de connaître le point de départ du chemin.
     *
     * @return Le point de départ du chemin.
     */
    public static PVector getStart() {
        areSet();
        return new PVector(0, (4 / 5) * screenHeight);
    }

    /**
     * Permet de connaître le point d'arrivée du chemin.
     *
     * @return Le point d'arrivée du chemin.
     */
    public static PVector getEnd() {
        areSet();
        return new PVector(screenWidth, screenHeight / 5);
    }

    /**
     * Accesseur du rayon du chemin.
     *
     * @return Rayon du chemin.
     */
    public static float getRadius() {
        areSet();
        return radius;
    }

    /**
     * Mutateur du rayon du chemin.
     *
     * @param rad Rayon du chemin.
     */
    public static void setRadius(float rad) {
        radius = rad;
    }

    /**
     * Mutateur de la largeur de l'écran.
     *
     * @param width Largeur courante de l'écran.
     */
    public static void setScreenWidth(float width) {
        screenWidth = width;
    }

    /**
     * Mutateur de la hauteur de l'écran.
     *
     * @param height Hauteur courante de l'écran.
     */
    public static void setScreenHeight(float height) {
        screenHeight = height;
    }
}
