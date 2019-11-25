package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.events.EventManager;
import gui.GUISimulator;
import gui.Simulable;

/**
 * Classe abstraite modélisant un simulateur. Les comportements sont à implémenter dans des classes concrètes.
 */
public abstract class Simulator {
    /**
     * La fenêtre de simulateur dans lequel on affiche les balles.
     */
    private final GUISimulator window;
    /**
     * Manager d'événements pour avoir accès à la méthode next()
     */
    private final EventManager eventManager = new EventManager();

    /**
     * Constructeur du simulateur abstrait.
     * @param window La fenêtre de simulation du simulateur.
     */
    public Simulator(GUISimulator window) {
        this.window = window;
    }

    /**
     * Accesseur au gestionnaire d'événements courant.
     * @return Gestionnaire d'événements.
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Accesseur à la fenêtre du simulateur.
     * @return Objet représentant la fenêtre graphique du simulateur.
     */
    public GUISimulator getWindow() {
        return window;
    }
}
