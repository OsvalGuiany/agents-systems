package fr.ensimag.tpl.elements;

import processing.core.PVector;

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
     * @param maxInterval Distance maximale entre deux boids pour qu'ils
     *                    soient considérés comme voisins.
     * @param minInterval Distance minimale entre deux boids pour qu'ils se
     *                    repoussent.
     */
    public BoidsOnRoad(int maxX, int maxY, int nbBoids, double maxInterval,
                       double minInterval) {
        super(maxX, maxY, nbBoids, maxInterval);
    }

    /**
     * Constructeur de la classe BoidsOnRoad.
     *
     * @param maxX    Valeur maximale en abscisse.
     * @param maxY    Valeur maximale en ordonnée.
     * @param nbBoids Nombre de boids à modéliser.
     * @param rayon   Rayon des boids
     */
    public BoidsOnRoad(int maxX, int maxY, int nbBoids, float rayon) {
        super(maxX, maxY, nbBoids, rayon);
    }

    /**
     * Projette un point sur une droite.
     *
     * @param loc   Endroit à partir duquel on veut calculer la normale.
     * @param start Point du début de la route.
     * @param end   Point de fin de la route à suivre
     * @return Le point correspondant à la projection du point loc sur la
     * droite déterminée par les points start et end.
     */
    public static PVector getNormalPoint(PVector loc, PVector start, PVector end) {
        PVector a = PVector.sub(loc, start);
        PVector b = PVector.sub(end, start);

        b.normalize();
        b.mult(a.dot(b));

        return PVector.add(start, b);
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
        for (Boid boid : getBoids()) {
            this.followPath(boid);

            boid.normaliseLocation(this.getxMax(), this.getyMax());
        }
    }

    /**
     * Permet à un boid de suivre un chemin statique Path.
     *
     * @param boid Boid à déplacer le long du chemin.
     */
    public void followPath(Boid boid) {
        PVector predict = boid.getVelocity();
        predict.normalize();
        predict.mult(25);
        PVector nextLoc = PVector.add(boid.getLocation(), predict);

        PVector a = Path.getStart();
        PVector b = Path.getEnd();
        PVector normalPoint = getNormalPoint(nextLoc, a, b);

        PVector dir = PVector.sub(b, a);
        dir.normalize();
        dir.mult(10);
        PVector target = PVector.add(normalPoint, dir);

        float distance = PVector.dist(normalPoint, nextLoc);

        if (distance > Path.getRadius()) {
            boid.nextState(target, true);
        } else {
            boid.nextState(Path.getEnd(), true);
        }
    }
}
