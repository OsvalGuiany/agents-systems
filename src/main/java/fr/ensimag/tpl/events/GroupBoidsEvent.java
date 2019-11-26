package fr.ensimag.tpl.events;

import fr.ensimag.tpl.elements.ProiePredateur;
import fr.ensimag.tpl.simulations.GroupBoidsSimulator;

/**
 * Événement sur des groupes de boids.
 */
public class GroupBoidsEvent extends SimulationEvent {
    /**
     * Constructeur d'un événement sur des groupes de boids.
     *
     * @param date         Date à laquelle doit s'exécuter l'événement.
     * @param groups       Modèle proie-prédateur à afficher dans le simulateur.
     * @param simulator    Simulateur courant.
     * @param eventManager Gestionnaire d'événements courant.
     */
    public GroupBoidsEvent(long date, ProiePredateur groups,
                           GroupBoidsSimulator simulator,
                           EventManager eventManager) {
        super(date, eventManager, groups, simulator);
    }

    /**
     * Fonction permettant d'exécuter l'affichage des groupes de boids et
     * d'enregistrer le prochain événement d'affichage à venir.
     */
    @Override
    public void execute() {
        executeAffichage();

        getEventManager().addEvent(
                new GroupBoidsEvent(
                        getDate() + 1,
                        (ProiePredateur) getElements(),
                        (GroupBoidsSimulator) getSimulator(),
                        getEventManager()
                )
        );
    }
}
