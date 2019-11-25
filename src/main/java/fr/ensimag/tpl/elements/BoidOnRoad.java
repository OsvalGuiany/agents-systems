/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.elements;

import processing.core.PVector;

/**
 * cette classe implemente les boids qui se déplace sur un chemin défini par
 * la classe Path. L'élément supplémentaire par rapport à la classe boid de base
 * est la méthode follow.
 */
public class BoidOnRoad extends Boid {
    
    public BoidOnRoad(PVector location) {
        super(location);
    }
    
    /**
     * 
     * elle permet à un boid de suivre un chemin préciser par la classe
     * presque statique (contenant que les méthodes statiques) Path
     */
    public void followPath(){
        PVector predict = this.getVelocity();
        predict.normalize();
        predict.mult(25);
        PVector nextLoc = PVector.add(this.getLocation(), predict);

        PVector a = Path.getStart();
        PVector b = Path.getEnd();
        PVector normalPoint = getNormalPoint(nextLoc, a, b);

        PVector dir = PVector.sub(b, a);
        dir.normalize();
        dir.mult(10);
        PVector target = PVector.add(normalPoint, dir);

        float distance = PVector.dist(normalPoint, nextLoc);
        
        if (distance > Path.getRadius()) {
            nextState(target, true);
        }
        else{
            nextState(Path.getEnd(), true);
        }
    }
    
    public static PVector getNormalPoint(PVector loc, PVector start, PVector end) {
        PVector a = PVector.sub(loc, start);
        PVector b = PVector.sub(end, start);
        
        b.normalize();
        b.mult(a.dot(b));
        
        return PVector.add(start, b);
    }
    
}
