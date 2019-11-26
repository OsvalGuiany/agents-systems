/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.events;

import fr.ensimag.tpl.elements.Cellules;
import fr.ensimag.tpl.simulations.CellulesSimulator;

/**
 * Événement de changement d'état d'un groupe de cellules
 */
public class CellulesEvent extends SimulationEvent {
    /**
     * Constructeur d'événément propre à un changement d'état d'une cellule.
     *
     * @param date      Date à laquelle exécuter l'événement.
     * @param cellules  Les cellules modélisées.
     * @param simulator Le simulateur des cellules modélisées.
     * @param em        Le gestionnaire d'événements courant.
     */
    public CellulesEvent(long date, Cellules cellules, CellulesSimulator simulator, EventManager em) {
        super(date, em, cellules, simulator);
    }

    /**
     * Passe les cellules puis l'affichage du simulateur à l'état suivant.
     */
    @Override
    public void execute() {
        executeAffichage();

        // Création du nouvel événement (pour animation perpétuelle)
        getEventManager().addEvent(
                new CellulesEvent(
                        getDate() + 1,
                        (Cellules) getElements(),
                        (CellulesSimulator) getSimulator(),
                        getEventManager()
                )
        );
    }
}
