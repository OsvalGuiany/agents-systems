package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.elements.AbstractBoids;
import fr.ensimag.tpl.elements.Boid;
import fr.ensimag.tpl.events.BoidsEvent;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;

/**
 * Simulateur des boids.
 */
public class BoidsSimulator extends Simulator implements Simulable {
    /**
     * Les boids à simuler.
     */
    private AbstractBoids boids;

    /**
     * Constructeur du simulateur de boids.
     *
     * @param window La fenêtre graphique du simulateur.
     * @param boids  Les boids à simuler.
     */
    public BoidsSimulator(GUISimulator window, AbstractBoids boids) {
        super(window);
        this.boids = boids;

        getEventManager().addEvent(new BoidsEvent(1, boids, this, getEventManager()));
    }

    /**
     * Appelée lors d'un clic sur le bouton Début. La lecture est alors arrêtée
     * et le simulateur revient dans l'état initial (l'affichage des boids est réinitialisé).
     */
    @Override
    public void restart() {
        boids.restart();
        afficher();
    }

    /**
     * Méthode appelée lors d'un clic sur le bouton Suivant, ou bien à intervalles
     * réguliers si le mode "lecture" a été démarré. Passe l'ensemble des boids d'un état i à un état (i+1).
     */
    @Override
    public void next() {
        getEventManager().next();
    }

    /**
     * Sous-méthode gérant l'affichage des boids dans le simulateur.
     */
    public void afficher() {
        getWindow().reset();

        for (Boid boid : boids.getBoids()) {
            getWindow().addGraphicalElement(
                    new Oval(
                            (int) boid.getLocation().x,
                            (int) boid.getLocation().y,
                            Color.GREEN,
                            Color.WHITE,
                            5
                    )
            );
        }
    }
}
