/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.events;

import fr.ensimag.tpl.elements.AbstractBoids;
import fr.ensimag.tpl.simulations.BoidsSimulator;


/**
 * Événement sur des boids.
 */
public class BoidsEvent extends SimulationEvent {
    /**
     * Constructeur d'un événement sur les boids.
     *
     * @param date      Date d'exécution de l'événement.
     * @param boids     Les boids simulés.
     * @param simulator Le simulateur associé à ces boids.
     * @param em        Le gestionnaire d'événements courant.
     */
    public BoidsEvent(long date, AbstractBoids boids, BoidsSimulator simulator, EventManager em) {
        super(date, em, boids, simulator);
    }

    /**
     * Éxécute l'événement courant, c'est-à-dire passe les boids à leur état suivant et les affiche à l'écran.
     */
    @Override
    public void execute() {
        executeAffichage();

        getEventManager().addEvent(
                new BoidsEvent(
                        getDate() + 1,
                        (AbstractBoids) getElements(),
                        (BoidsSimulator) getSimulator(),
                        getEventManager()
                )
        );
    }
}
