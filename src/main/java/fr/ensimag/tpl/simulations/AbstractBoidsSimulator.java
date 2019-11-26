package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.elements.AbstractBoids;
import fr.ensimag.tpl.elements.Boid;
import gui.GUISimulator;
import gui.Oval;

import java.awt.*;

/**
 * Classe abstraite pour les simulateurs de boids.
 */
public abstract class AbstractBoidsSimulator extends Simulator {
    /**
     * Constructeur du simulateur abstrait de boids.
     *
     * @param window La fenêtre de simulation du simulateur.
     */
    public AbstractBoidsSimulator(GUISimulator window) {
        super(window);
    }

    /**
     * Sous-méthode gérant l'affichage d'un groupe de boids.
     *
     * @param boidsAAfficher Le groupe de boids à afficher.
     * @param couleur        La couleur avec laquelle afficher ces boids.
     */
    public void afficher(AbstractBoids boidsAAfficher, Color couleur) {
        for (Boid boid : boidsAAfficher.getBoids()) {
            getWindow().addGraphicalElement(
                    new Oval(
                            (int) boid.getLocation().x,
                            (int) boid.getLocation().y,
                            couleur,
                            Color.WHITE,
                            boidsAAfficher.getRayon()
                    )
            );
        }
    }
}
