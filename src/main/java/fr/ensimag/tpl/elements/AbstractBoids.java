/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.elements;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Classe abstraite permettant de modéliser un ensemble de boids.
 * Le comportement de déplacement de ces boids reste à spécifier suivant le type "final" de boids.
 */
public abstract class AbstractBoids extends Elements {
    /**
     * Distance maximale entre 2 boids voisins. Par défaut : 30.
     */
    private double maxInterval = 30;
    /**
     * Distance minimale entre 2 boids pour qu'ils se repoussent. Par défaut : 4.
     */
    private double minInterval = 4;
    /**
     * Tableau contenant les différents boids qui seront dans le tableau.
     */
    private ArrayList<Boid> boids = new ArrayList<Boid>();

    /**
     * Tableau contenant une sauvegarde des boids afin de pouvoir revenir à l'état initial avec la méthode restart.
     */
    private ArrayList<Boid> prevBoids = new ArrayList<Boid>();

    /**
     * Constructeur de la classe conservant les valeurs par défaut de minInterval et maxInterval.
     *
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     */
    public AbstractBoids(int maxX, int maxY, int nbBoids) {
        super(maxX, maxY);
        initBoids(nbBoids);
    }

    /**
     * Constructeur d'un ensemble de boids permettant de spécifier également minInterval et maxInterval.
     *
     * @param maxInterval Distance maximale entre deux boids voisins.
     * @param minInterval Distance minimale entre 2 boids pour qu'ils se repoussent.
     * @param maxX        Abscisse maximale d'un boid.
     * @param maxY        Ordonnée maximale d'un boid.
     * @param nbBoids     Nombre de boids à simuler.
     */
    public AbstractBoids(int maxX, int maxY, int nbBoids, double maxInterval, double minInterval) {
        super(maxX, maxY);
        this.maxInterval = maxInterval;
        this.minInterval = minInterval;
        initBoids(nbBoids);
    }

    /**
     * Initialise les boids à simuler. Ceux-ci sont générés avec des positions et des vitesses aléatoires.
     *
     * @param nbBoids Nombre de boids à simuler.
     */
    protected void initBoids(int nbBoids) {
        // Génération aléatoire des caractéristiques de chacun des boids.
        Random r = new Random();
        for (int i = 0; i < nbBoids; i++) {
            float x = r.nextFloat() * getxMax();
            float y = r.nextFloat() * getyMax();
            PVector loc = new PVector(x, y);
            Boid boid = new Boid(loc);
            Boid boidCopy = new Boid(loc);
            boids.add(boid);
            prevBoids.add(boidCopy);
        }
    }

    /**
     * Permet de redémarrer la simulation avec les boids à leur état initial.
     */
    public void restart() {
        this.boids = new ArrayList<Boid>(prevBoids);
    }

    /**
     * Comportement des boids lors du passage d'un état i à un état (i+1). Dépend du type final de boids.
     */
    public abstract void nextState();

    /**
     * TODO
     *
     * @param neighborsToFlee
     * @param neighborsToSeek
     * @return
     */
    public ArrayList<PVector> computeTarget(HashMap<Integer, ArrayList<Boid>> neighborsToFlee,
                                            HashMap<Integer, ArrayList<Boid>> neighborsToSeek) {

        ArrayList<PVector> targets = new ArrayList<PVector>();
        PVector target;
        // calculer la cible de chaque boid en fonction de l'action 
        // qu'il va effectuer (fuir et aller vers la cible)
        for (int i = 0; i < this.boids.size(); i++) {
            ArrayList<Boid> boidsToFlee = neighborsToFlee.get(i);
            ArrayList<Boid> boidsToSeek = neighborsToSeek.get(i);
            // le cas où le boid i a des boids à fuir
            if (boidsToFlee != null && !boidsToFlee.isEmpty()) {
                target = boidsToFlee.get(0).getLocation().copy();
                for (int j = 1; j < boidsToFlee.size(); ++j) {
                    target.add(boidsToFlee.get(j).getLocation());
                }

                targets.add(i, target);
            }

            // cas où le boid i doit se rapprocher de certains boids
            else if (boidsToSeek != null && !boidsToSeek.isEmpty()) {
                target = boidsToSeek.get(0).getLocation().copy();

                for (int j = 1; j < boidsToSeek.size(); ++j) {
                    target.add(boidsToSeek.get(j).getLocation());
                }

                targets.add(i, target);
            }
            // cas où le boid n'a pas de voisin, il se dirige 
            // vers une position aléatoire.
            else {
                float x = new Random().nextFloat() * getxMax();
                float y = new Random().nextFloat() * getyMax();
                target = new PVector(x, y);
                targets.add(i, target);
            }
        }

        return targets;
    }

    /**
     * Accesseur de l'ensemble des boids.
     *
     * @return Un tableau contenant l'ensemble des boids simulés.
     */
    public ArrayList<Boid> getBoids() {
        return boids;
    }

    /**
     * Accesseur de l'ensemble des boids à l'état initial.
     *
     * @return Un tableau contenant l'ensemble des boids à l'état initial.
     */
    protected ArrayList<Boid> getPrevBoids() {
        return this.prevBoids;

    }

    /**
     * Accesseur sur l'attribut minInterval.
     *
     * @return Distance minimale entre deux boids pour qu'ils se repoussent.
     */
    protected double getMinInterval() {
        return minInterval;
    }

    /**
     * Accesseur sur l'attribut maxInterval.
     *
     * @return Distance maximale pour que deux boids soient considérés comme voisins.
     */
    protected double getMaxInterval() {
        return maxInterval;
    }

    /**
     * Accesseur au ième boid modélisé.
     *
     * @param i Indice du boid à récupérer.
     * @return Le boid correspondant à l'indice i.
     */
    public Boid getBoid(int i) {
        if (this.boids.size() <= i) {
            return null;
        }

        return this.boids.get(i);
    }

    /**
     * cette fonction permet de faire apparaître un boid à l'écran au cas où ses
     * coordonnées dépassent celles de l'écran.
     */
    public void returnOnScreen() {
        for (Boid boid : this.boids) {
            if (boid.getLocation().x > getxMax())
                boid.getLocation().x = 0;
            else if (boid.getLocation().x < 0)
                boid.getLocation().x = getxMax();

            if (boid.getLocation().y > getyMax())
                boid.getLocation().y = 0;
            else if (boid.getLocation().y < 0)
                boid.getLocation().y = getyMax();
        }
    }

    /**
     * Méthode permettant d'afficher des boids comme des chaînes de caractères (utile pour les traces de débug).
     *
     * @return Affichage textuel décrivant l'ensemble de boids simulé.
     */
    @Override
    public String toString() {
        String str = "État des boids \n";

        for (Boid boid : this.boids) {
            str += " position " + this.boids.indexOf(boid) + " " + boid.toString() + " \n";
        }

        return str;
    }
}
