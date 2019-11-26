package fr.ensimag.tpl.events;

import fr.ensimag.tpl.elements.Balls;
import fr.ensimag.tpl.simulations.BallsSimulator;

/**
 * Événement de changement d'état d'un groupe de balles.
 */
public class BallsEvent extends SimulationEvent {
    /**
     * Constructeur d'événement propre à un événement sur les balles.
     *
     * @param date      Date à laquelle exécuter l'événement.
     * @param balls     Les balls modélisées.
     * @param simulator Le simulateur de balls.
     * @param em        Le gestionnaire d'événements courant.
     */
    public BallsEvent(long date, Balls balls, BallsSimulator simulator, EventManager em) {
        super(date, em, balls, simulator);
    }

    /**
     * Fonction d'exécution d'actions associée à l'événement courant. Translate les balles, les affiche dans le
     * simulateur et créé l'événement associé à l'action qui sera effectuée pour passer de l'état suivant à son
     * suivant.
     */
    @Override
    public void execute() {
        executeAffichage();

        // Création du nouvel événement (pour animation perpétuelle)
        getEventManager().addEvent(
                new BallsEvent(
                        getDate() + 1,
                        (Balls) getElements(),
                        (BallsSimulator) getSimulator(),
                        getEventManager()
                )
        );
    }
}
