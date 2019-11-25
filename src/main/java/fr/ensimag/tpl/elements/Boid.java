/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.elements;

import processing.core.PVector;

/**
 * Classe permettant de modéliser un Boid.
 */
public class Boid {
    /**
     * La vitesse maximale du boid.
     */
    private final float maxSpeed;
    /**
     * La force maximale appliquée au boid (en norme).
     */
    private final float maxForce;
    /**
     * Vecteur position du boid.
     */
    private PVector location;
    /**
     * Vecteur vitesse du boid.
     */
    private PVector velocity;
    /**
     * Vecteur accélération du boid.
     */
    private PVector acceleration;

    /**
     * Constructeur d'un boid.
     * @param location Vecteur position du boid au départ.
     */
    public Boid(PVector location) {
        this.location = location;
        this.velocity = PVector.random2D();
        this.acceleration = PVector.random2D();
        this.maxSpeed = 4;
        this.maxForce = 6;
        this.velocity.limit(maxSpeed);
    }

    /**
     * Calcule les nouvelles vitesse, position et accélération du boid.
     */
    public void update() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        acceleration.mult(0);
    }

    /**
     * Calcule l'évolution du boid entre l'état i et l'état (i+1).
     * @param neighborsCenter TODO
     * @param toSeek TODO
     */
    public void nextState(PVector neighborsCenter, boolean toSeek) {
        PVector desired, force;
        desired = PVector.sub(neighborsCenter, location);
        if (!toSeek) {
            desired.mult(-1);
        }

        desired.normalize();
        desired.mult(maxSpeed);
        force = PVector.sub(desired, velocity);
        force.limit(maxForce);
        acceleration.add(force);
        this.update();
    }

    /**
     * Accesseur du vecteur position du boid.
     * @return Vecteur position courant du boid.
     */
    public PVector getLocation() {
        return this.location;
    }

    /**
     * Accesseur du vecteur vitesse du boid.
     * @return Vecteur vitesse courant du boid.
     */
    public PVector getVelocity() {
        return this.velocity;
    }

    /**
     * Donne une description textuelle du boid.
     * @return Description textuelle du boid.
     */
    @Override
    public String toString() {
        return "Boid position : (" + location.x + ", " + location.y +
                ") vitesse : (" + velocity.x + ", " + velocity.y + " )";
    }
}
