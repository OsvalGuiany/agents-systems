package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.elements.Balls;
import fr.ensimag.tpl.events.BallsEvent;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;

/**
 * Classe permettant de simuler les balles à l'écran.
 */
public class BallsSimulator extends Simulator implements Simulable {
    /**
     * Ensemble des balles à afficher
     */
    private Balls balles;

    /**
     * Constructeur du simulateur de balles. Défini suivant le sujet pour tirer
     * 3 balles et les contenir dans une fenêtre 500×500.
     *
     * @param window La fenêtre graphique du simulateur.
     * @param balles Les balles à simuler.
     */
    public BallsSimulator(GUISimulator window, Balls balles) {
        super(window);
        this.balles = balles;
        getEventManager().addEvent(new BallsEvent(1, balles, this, getEventManager()));
    }

    /**
     * Appelée lors d'un clic sur le bouton Suivant, ou bien à intervalles
     * réguliers si la lecture a été démarrée.
     */
    @Override
    public void next() {
        // On appelle directement la méthode next() du manager d'événements
        getEventManager().next();
    }

    /**
     * Appelée lors d'un clic sur le bouton Début. La lecture est alors arrêtée
     * et le simulateur revient dans l'état initial (les balles sont replacées
     * comme au départ).
     */
    @Override
    public void restart() {
        balles.reInit();
        // Trace
        System.out.println(balles);

        afficherBalles(balles);
    }

    /**
     * Affiche les balles dans la fenêtre *window* après avoir effacé le reste de l'affichage.
     *
     * @param balles Les balles à afficher.
     */
    public void afficherBalles(Balls balles) {
        // Affichage des balles à l'écran
        getWindow().reset();

        for (int[] coords : balles) {
            getWindow().addGraphicalElement(
                    new Oval(
                            coords[0],
                            coords[1],
                            Color.decode("#8dc63f"),
                            Color.decode("#8dc63f"),
                            10
                    )
            );
        }
    }
}
