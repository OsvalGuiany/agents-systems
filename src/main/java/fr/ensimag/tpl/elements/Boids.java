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
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     */
    public Boids(int maxX, int maxY, int nbBoids) {
        super(maxX, maxY, nbBoids);
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
        super(maxX, maxY, nbBoids, maxInterval, minInterval);
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
        HashMap<Integer, ArrayList<Boid>> neighborsToFlee, neighborsToSeek;
        // les boids dont le boid à l'indice i doit fuir
        neighborsToFlee = new HashMap<Integer, ArrayList<Boid>>();
        // les boids dont le boid à l'indice i doit se rapprocher
        neighborsToSeek = new HashMap<Integer, ArrayList<Boid>>();

        Boid boid;
        // on cherche les boids dont le boid i doit se rapprocher ou fuir
        for (int i = 0; i < getBoids().size(); i++) {
            boid = getBoid(i);

            for (Boid b : getBoids()) {
                if (!b.equals(boid)) {
                    PVector sub = PVector.sub(b.getLocation(), boid.getLocation());
                    if (sub.mag() <= getMinInterval()) {
                        if (!neighborsToFlee.containsKey(i)) {
                            neighborsToFlee.put(i, new ArrayList<Boid>());
                        }
                        neighborsToFlee.get(i).add(b);
                    } else if (sub.mag() <= getMaxInterval()) {
                        if (!neighborsToSeek.containsKey(i)) {
                            neighborsToSeek.put(i, new ArrayList<Boid>());
                        }
                        neighborsToSeek.get(i).add(b);
                    }
                }
            }
        }

        targets = this.computeTarget(neighborsToFlee, neighborsToSeek);
        // faire avancer chaque boid dans une direction donnée.
        for (int i = 0; i < getBoids().size(); ++i) {
            boid = getBoid(i);

            if (neighborsToFlee.get(i) != null && !neighborsToFlee.get(i).isEmpty()) {
                boid.nextState(targets.get(i), false);
            } else {
                boid.nextState(targets.get(i), true);
            }
        }
        
        this.returnOnScreen();
    }
}
