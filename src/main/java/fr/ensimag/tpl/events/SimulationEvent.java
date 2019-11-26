package fr.ensimag.tpl.events;

import fr.ensimag.tpl.elements.Elements;
import fr.ensimag.tpl.simulations.Simulator;

/**
 * Représente un événement d'un fait simulable.
 */
public abstract class SimulationEvent extends Event {
    /**
     * Le gestionnaire d'événements courant (utile pour créer de nouveaux événements pour les états suivants).
     */
    private EventManager eventManager;

    /**
     * Les éléments simulés.
     */
    private Elements elements;

    /**
     * Le simulateur courant.
     */
    private Simulator simulator;

    /**
     * Constructeur d'événement pour une simulation.
     *
     * @param date      Date à laquelle exécuter l'événement.
     * @param em        Le gestionnaire d'événements courant.
     * @param elements  Les éléments en cours de simulation.
     * @param simulator Le simulateur courant.
     */
    public SimulationEvent(long date, EventManager em, Elements elements, Simulator simulator) {
        super(date);

        this.eventManager = em;
        this.elements = elements;
        this.simulator = simulator;
    }

    /**
     * Accesseur au gestionnaire d'événements.
     *
     * @return Gestionnaire d'événements courant.
     */
    protected EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Accesseur sur les éléments simulés.
     * @return Les éléments simulés.
     */
    protected Elements getElements() {
        return elements;
    }

    /**
     * Accesseur sur le simulateur courant.
     * @return Le simulateur courant.
     */
    protected Simulator getSimulator() {
        return simulator;
    }

    /**
     * Passe à l'état suivant et effectue l'affichage.
     */
    protected void executeAffichage() {
        elements.nextState();
        simulator.afficher();
    }
}
