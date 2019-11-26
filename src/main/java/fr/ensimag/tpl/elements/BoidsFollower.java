package fr.ensimag.tpl.elements;

import processing.core.PVector;

/**
 * Modélise des boids qui suivent une certaine direction (une cible mouvante).
 */
public class BoidsFollower extends Boids {
    /**
     * Rayon de séparation des boids
     */
    float r = 0;

    /**
     * La cible à suivre.
     */
    PVector seek;

    /**
     * Constructeur d'un ensemble de boids suiveurs.
     *
     * @param maxX    Abscisse maximale.
     * @param maxY    Ordonnée maximale.
     * @param nbBoids Nombre de boids à modéliser.
     * @param r       Rayon d'un boid.
     */
    public BoidsFollower(int maxX, int maxY, int nbBoids, int r) {
        super(maxX, maxY, nbBoids, r);
        this.r = r;

        seek = new PVector(
                (int) (Math.random() * getxMax()),
                (int) (Math.random() * getyMax())
        );
    }

    /**
     * Fonction de séparation des boids.
     */
    private void separate() {
        float desiredseparation = r * 2;
        PVector sum = new PVector();


        for (Boid boid : getBoids()) {
            int count = 0;

            for (Boid other : getBoids()) {
                float d = PVector.dist(boid.getLocation(), other.getLocation());

                if ((d > 0) && (d < desiredseparation)) {
                    PVector diff = PVector.sub(boid.getLocation(), other.getLocation());
                    diff.normalize();
                    diff.div(d);
                    sum.add(diff);
                    count++;
                }
            }

            if (count > 0) {
                sum.div(count);
                sum.normalize();
                sum.mult(boid.getMaxSpeed());
                System.out.println("sum : " + sum);
                PVector steer = PVector.sub(sum, boid.getVelocity());
                steer.limit(boid.getMaxForce());
                boid.getAcceleration().add(steer.x, steer.y);
            }
        }
    }

    /**
     * Fonction de transition des boids d'un état i à un état (i+1).
     */
    @Override
    public void nextState() {
        separate();
        System.out.println(getBoids());

        for (Boid boid : getBoids()) {
            boid.getAcceleration().add(seek.x, seek.y);
            boid.update();
            boid.normaliseLocation(getxMax(), getyMax());
        }
    }
}
