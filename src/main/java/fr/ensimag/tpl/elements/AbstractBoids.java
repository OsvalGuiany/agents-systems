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
     * Distance maximale entre 2 boids voisins. Par défaut : 40.
     */
    private double maxInterval = 40;

    /**
     * Tableau contenant les différents boids qui seront dans le tableau.
     */
    private ArrayList<Boid> boids = new ArrayList<Boid>();

    /**
     * Tableau contenant une sauvegarde des boids afin de pouvoir revenir à l'état initial avec la méthode restart.
     */
    private ArrayList<Boid> initBoids = new ArrayList<Boid>();

    /**
     * Rayon du boid.
     */
    private int rayon;

    /**
     * Constructeur de la classe conservant les valeurs par défaut de minInterval et maxInterval.
     *
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     * @param rayon   c'est le rayon des différents boids
     */
    public AbstractBoids(int maxX, int maxY, int nbBoids, int rayon) {
        super(maxX, maxY);
        this.rayon = rayon;
        initialiseBoids(0, 0, nbBoids);
    }

    /**
     * Constructeur de la classe conservant les valeurs par défaut de minInterval et maxInterval.
     *
     * @param minX    Abscisse minimale d'un boid.
     * @param minY    Ordonnée minimale d'un boid.
     * @param maxX    Abscisse maximale d'un boid.
     * @param maxY    Ordonnée maximale d'un boid.
     * @param nbBoids Nombre de boids à simuler.
     * @param rayon   c'est le rayon des différents boids
     */
    public AbstractBoids(int minX, int minY, int maxX, int maxY, int nbBoids,
                         int rayon) {
        super(maxX, maxY);
        this.rayon = rayon;
        initialiseBoids(minX, minY, nbBoids);
    }

    /**
     * Constructeur d'un ensemble de boids permettant de spécifier également minInterval et maxInterval.
     *
     * @param maxInterval Distance maximale entre deux boids voisins.
     * @param maxX        Abscisse maximale d'un boid.
     * @param maxY        Ordonnée maximale d'un boid.
     * @param nbBoids     Nombre de boids à simuler.
     */
    public AbstractBoids(int maxX, int maxY, int nbBoids, double maxInterval) {
        super(maxX, maxY);
        this.maxInterval = maxInterval;
        initialiseBoids(0, 0, nbBoids);
    }

    /**
     * Initialise les boids à simuler. Ceux-ci sont générés avec des positions et des vitesses aléatoires.
     *
     * @param xMin    Abscisse minimale.
     * @param yMin    Ordonnée minimale.
     * @param nbBoids Nombre de boids à simuler.
     */
    protected void initialiseBoids(int xMin, int yMin, int nbBoids) {
        // Génération aléatoire des caractéristiques de chacun des boids.
        Random r = new Random();
        for (int i = 0; i < nbBoids; i++) {
            float x = r.nextFloat() * (getxMax() - xMin) + xMin;
            float y = r.nextFloat() * (getyMax() - yMin) + yMin;

            PVector loc = new PVector(x, y);
            Boid boid = new Boid(loc);
            PVector locCopy = new PVector(x, y);
            Boid boidCopy = new Boid(locCopy);

            boids.add(boid);
            initBoids.add(boidCopy);
        }
    }

    /**
     * Permet de redémarrer la simulation avec les boids à leur état initial.
     */
    public void restart() {

        this.boids = new ArrayList<Boid>();

        for (Boid boid : this.initBoids) {
            this.boids.add(new Boid(boid.getLocation().copy()));
        }

    }

    /**
     * Comportement des boids lors du passage d'un état i à un état (i+1). Dépend du type final de boids.
     */
    public abstract void nextState();

    /**
     * Permet de calculer les centres de gravité d'un ensemble de boids.
     *
     * @param neighbors L'ensemble des boids dont on veut calculer le centre de gravité
     * @return Les coordonnées des centres de gravité des boids.
     */
    public ArrayList<PVector> computeTarget(HashMap<Integer,
            ArrayList<Boid>> neighbors) {
        PVector target;
        ArrayList<PVector> targets = new ArrayList<PVector>();
        // calculer la cible de chaque boid en prenant un ensemble de boids voisins.
        // il s'agit de calculer le centre de gravité de l'ensemble des boids neigbors
        for (int i = 0; i < this.boids.size(); i++) {
            ArrayList<Boid> boids_list = neighbors.get(i);
            // le cas où le boid i a des boids à fuir
            if (boids_list != null && !boids_list.isEmpty()) {
                target = boids_list.get(0).getLocation().copy();
                for (int j = 1; j < boids_list.size(); ++j) {
                    target.add(boids_list.get(j).getLocation());
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

    public void fleeBoids(float minInterval) {
        // les cibles que chaque devra fuir ou se rapprocher;
        ArrayList<PVector> targets;

        HashMap<Integer, ArrayList<Boid>> neighborsToFlee;
        // les boids dont le boid à l'indice i doit fuir
        neighborsToFlee = new HashMap<Integer, ArrayList<Boid>>();
        Boid boid;

        for (int i = 0; i < getBoids().size(); i++) {
            boid = getBoid(i);

            for (Boid b : getBoids()) {
                if (!b.equals(boid)) {
                    PVector sub = PVector.sub(b.getLocation(), boid.getLocation());

                    if (sub.mag() <= minInterval + 2 * this.rayon) {
                        if (!neighborsToFlee.containsKey(i)) {
                            neighborsToFlee.put(i, new ArrayList<Boid>());
                        }
                        neighborsToFlee.get(i).add(b);
                    }
                }
            }

            boid.normaliseLocation(getxMax(), getyMax());
        }

        targets = this.computeTarget(neighborsToFlee);
        // faire avancer chaque boid dans une direction donnée.
        for (int i = 0; i < getBoids().size(); ++i) {
            boid = getBoid(i);

            if (neighborsToFlee.get(i) != null && !neighborsToFlee.get(i).isEmpty()) {
                boid.nextState(targets.get(i), false);
            }
        }

        this.returnOnScreen();
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
     * Accesseur sur l'attribut maxInterval.
     *
     * @return Distance maximale pour que deux boids soient considérés comme voisins.
     */
    protected double getMaxInterval() {
        return maxInterval;
    }

    /**
     * Accesseur sur le rayon d'un boid.
     *
     * @return Rayon du boid.
     */
    public int getRayon() {
        return rayon;
    }

    /**
     * Accesseur au ième boid modélisé.
     *
     * @param i Indice du boid à récupérer.
     * @return Le boid correspondant à l'indice i.
     */
    public Boid getBoid(int i) {
        if (boids.size() <= i) {
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
            str += " position " + this.boids.indexOf(boid) + " " +
                    boid.toString() + " \n";
        }

        return str;
    }
}
