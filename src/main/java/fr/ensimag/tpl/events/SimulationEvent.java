package fr.ensimag.tpl.events;

/**
 * Représente un événement d'un fait simulable.
 */
public abstract class SimulationEvent extends Event {
    /**
     * Le gestionnaire d'événements courant (utile pour créer de nouveaux événements pour les états suivants).
     */
    private EventManager eventManager;

    /**
     * Constructeur d'événement pour une simulation.
     *
     * @param date Date à laquelle exécuter l'événement.
     * @param em   Le gestionnaire d'événements courant.
     */
    public SimulationEvent(long date, EventManager em) {
        super(date);

        this.eventManager = em;
    }

    /**
     * Accesseur au gestionnaire d'événements.
     *
     * @return Gestionnaire d'événements courant.
     */
    protected EventManager getEventManager() {
        return eventManager;
    }
}
