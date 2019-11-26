package fr.ensimag.tpl.elements;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe concrète permettant de modéliser un ensemble de boids à simuler.
 */
public class Boids extends AbstractBoids {
    /**
     * Constructeur de boids conservant les valeurs par défaut de minInterval et maxInterval.
     *
     * @param minX    Abscisse minimale d'un boid.
     * @param minY    Ordonnée minimale d'un boid.
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     * @param rayon   Rayon du boid.
     */
    public Boids(int minX, int minY, int maxX, int maxY, int nbBoids, int rayon) {
        super(minX, minY, maxX, maxY, nbBoids, rayon);
    }

    /**
     * Constructeur de boids conservant les valeurs dans un espace restreint.
     *
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     * @param rayon   Rayon des boids
     */
    public Boids(int maxX, int maxY, int nbBoids, int rayon) {
        super(maxX, maxY, nbBoids, rayon);
    }

    /**
     * Constructeur de boids permettant de spécifier des valeurs de minInterval et maxInterval
     *
     * @param maxX        Abscisse maximale d'un boid.
     * @param maxY        Ordonnée maximale d'un boid.
     * @param nbBoids     Nombre de boids à simuler.
     * @param maxInterval Distance maximale entre deux boids considérés comme voisins.
     * @param minInterval Distance minimale entre deux boids qui se repoussent.
     */
    public Boids(int maxX, int maxY, int nbBoids, double maxInterval, double minInterval) {
        super(maxX, maxY, nbBoids, maxInterval);
    }

    /**
     * Comportement des boids lors du passage d'un état i à un état (i+1).
     */
    public void nextState() {
        // les cibles que chaque devra fuir ou se rapprocher;
        ArrayList<PVector> targets;
        //les voisins des boids coorrespondant à une caractéristique
        // un boid doit prioritairement fuir les plus proches mais
        // s'il y a pas de boids à fuir, il se rapproche des autres
        HashMap<Integer, ArrayList<Boid>> neighborsToSeek;

        // les boids dont le boid à l'indice i doit se rapprocher
        neighborsToSeek = new HashMap<Integer, ArrayList<Boid>>();

        Boid boid;
        // on cherche les boids dont le boid i doit se rapprocher ou fuir
        for (int i = 0; i < getBoids().size(); i++) {
            boid = getBoid(i);

            for (Boid b : getBoids()) {
                if (!b.equals(boid)) {
                    PVector sub = PVector.sub(b.getLocation(), boid.getLocation());

                    if (sub.mag() <= getMaxInterval()) {
                        if (!neighborsToSeek.containsKey(i)) {
                            neighborsToSeek.put(i, new ArrayList<Boid>());
                        }
                        neighborsToSeek.get(i).add(b);
                    }
                }
            }

            boid.normaliseLocation(getxMax(), getyMax());
        }

        targets = this.computeTarget(neighborsToSeek);
        // faire avancer chaque boid dans une direction donnée.
        for (int i = 0; i < getBoids().size(); ++i) {
            boid = getBoid(i);

            boid.nextState(targets.get(i), true);
        }
        this.fleeBoids(0);
        this.returnOnScreen();
    }
}
