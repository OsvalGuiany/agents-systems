package fr.ensimag.tpl.elements;

import java.util.Random;

/**
 * Classe modélisant des boids qui doivent suivre un chemin précis.
 */
public class BoidsOnRoad extends AbstractBoids {
    /**
     * Constructeur de la classe BoidsOnRoad.
     *
     * @param maxX        Valeur maximale en abscisse.
     * @param maxY        Valeur maximale en ordonnée.
     * @param nbBoids     Nombre de boids à modéliser.
     * @param maxInterval TODO
     * @param minInterval TODO
     */
    public BoidsOnRoad(int maxX, int maxY, int nbBoids, double maxInterval, double minInterval) {
        super(maxX, maxY, nbBoids, maxInterval, minInterval);
    }

    /**
     * Constructeur de la classe BoidsOnRoad.
     *
     * @param maxX    Valeur maximale en abscisse.
     * @param maxY    Valeur maximale en ordonnée.
     * @param nbBoids Nombre de boids à modéliser.
     */
    public BoidsOnRoad(int maxX, int maxY, int nbBoids) {
        super(maxX, maxY, nbBoids);
    }

    /**
     * Permet de passer d'un état au suivant.
     * Principe général : on récupère les trois boids en tête de liste et
     * on les fait avancer sur le chemin qu'on a défini jusqu'à la fin, on les
     * enlève de la liste et on passe aux boids suivants jusqu'à la liste se vide
     * on reinitialise la liste et on continue.
     */
    @Override
    public void nextState() {
        // TODO code à refaire si possible avec les boucles for.
        Path path = new Path();
        BoidOnRoad boid1 = null, boid2 = null, boid3 = null;

        // s'il le tableau de boids est vide, on la remplit.
        if (this.getBoids().size() == 0) {
            this.initBoids((new Random()).nextInt(120));
        }

        if (this.getBoid(0) != null) {
            boid1 = (BoidOnRoad) this.getBoids().get(0);
        }
        if (this.getBoid(1) != null) {
            boid2 = (BoidOnRoad) this.getBoid(1);
        }
        if (this.getBoid(2) != null) {
            boid3 = (BoidOnRoad) this.getBoid(2);
        }

        if (boid1 != null) {
            boid1.followPath();

            if (boid1.getLocation().x > getxMax()) {
                this.getBoids().remove(boid1);
            }
        }
        if (boid2 != null) {
            boid2.followPath();

            if (boid2.getLocation().x > getxMax()) {
                this.getBoids().remove(boid2);
            }
        }
        if (boid3 != null) {
            boid3.followPath();

            if (boid3.getLocation().x > getxMax()) {
                this.getBoids().remove(boid3);
            }
        }
    }
}
