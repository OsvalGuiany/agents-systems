package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.elements.ProiePredateur;
import fr.ensimag.tpl.events.GroupBoidsEvent;
import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;

/**
 * Simulateur de 2 groupes de boids.
 */
public class GroupBoidsSimulator extends AbstractBoidsSimulator implements Simulable {
    /**
     * Modèle proie-prédateur à simuler.
     */
    private ProiePredateur groups;

    /**
     * Constructeur du simulateur de boids.
     *
     * @param window La fenêtre graphique du simulateur.
     * @param groups Le modèle proie-prédateur à simuler.
     */
    public GroupBoidsSimulator(GUISimulator window, ProiePredateur groups) {
        super(window);
        this.groups = groups;

        getEventManager().addEvent(new GroupBoidsEvent(1, groups, this, getEventManager()));
    }

    /**
     * Appelée lors d'un clic sur le bouton Début. La lecture est alors arrêtée
     * et le simulateur revient dans l'état initial (l'affichage des boids est réinitialisé).
     */
    @Override
    public void restart() {
        groups.restart();
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
    @Override
    public void afficher() {
        getWindow().reset();

        afficher(groups.getProies(), Color.GREEN);
        afficher(groups.getPredateurs(), Color.RED);
    }
}
