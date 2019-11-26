package fr.ensimag.tpl.elements;


import processing.core.PVector;

import static java.lang.Math.sqrt;

/**
 * Classe modélisant un modèle de type proie-prédateur.
 */
public class ProiePredateur extends Elements {
    /**
     * Les boids des proies.
     */
    private Boids proies;

    /**
     * Les boids des prédateurs.
     */
    private Boids predateurs;

    /**
     * Constructeur du modèle
     *
     * @param xMax    Abscisse maximale.
     * @param yMax    Ordonnée maximale.
     * @param nbBoids Nombre de boids par groupe.
     * @throws IllegalArgumentException Renvoyée si l'abscisse ou l'ordonnée sont invalides (inférieur à 0).
     */
    public ProiePredateur(int xMax, int yMax, int nbBoids) throws IllegalArgumentException {
        super(xMax, yMax);

        predateurs = new Boids(0, 0, xMax / 3, yMax / 3, nbBoids, 8);
        predateurs.setYMax(yMax);
        predateurs.setxMax(xMax);
        proies = new Boids(2 * xMax / 3, 2 * yMax / 3, xMax, yMax, nbBoids, 8);
        proies.setxMax(xMax);
        proies.setYMax(yMax);
    }

    /**
     * Transition permettant de passer de l'état i à l'état (i+1).
     */
    public void nextState() {
        followGroup(proies, predateurs, false);
        followGroup(predateurs, proies, true);
        proies.fleeBoids(0);
        predateurs.fleeBoids(0);
    }

    /**
     * Permet de redémarrer la simulation.
     */
    public void restart() {
        proies.restart();
        predateurs.restart();
    }

    /**
     * Fonction permettant de calculer l'évolution des positions
     * des boids d'un groupe qui suivent les boids de l'autre groupe.
     *
     * @param group1 Groupe de boids qui suivent.
     * @param group2 Groupe de boids à suivre.
     * @param follow Sens de déplacement.
     */
    private void followGroup(Boids group1, Boids group2, boolean follow) {
        for (Boid boid : group1.getBoids()) {
            // On recherche le prédateur le plus proche et on le fuit
            float distMin = (float) sqrt(getxMax() * getxMax() + getyMax() * getyMax());
            Boid predateurPrincipal = null;

            for (Boid predateur : group2.getBoids()) {
                float dist = PVector.dist(boid.getLocation(), predateur.getLocation());

                if (dist < distMin) {
                    distMin = dist;
                    predateurPrincipal = predateur;
                }
            }

            PVector diff;
            if (follow)
                diff = PVector.sub(predateurPrincipal.getLocation(), boid.getLocation());
            else
                diff = PVector.sub(boid.getLocation(), predateurPrincipal.getLocation());

            diff.normalize();
            diff.mult(boid.getMaxSpeed());

            if (predateurPrincipal != null) {
                boid.getAcceleration().add(diff.x, diff.y);
            }
            boid.update();
            boid.normaliseLocation(getxMax(), getyMax());
        }
    }

    /**
     * Accesseur des proies.
     *
     * @return L'ensemble des boids des proies.
     */
    public Boids getProies() {
        return proies;
    }

    /**
     * Accesseur des prédateurs.
     *
     * @return L'ensemble des boids des prédateurs.
     */
    public Boids getPredateurs() {
        return predateurs;
    }
}
