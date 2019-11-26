package fr.ensimag.tpl.simulations;

import fr.ensimag.tpl.elements.Cellules;
import fr.ensimag.tpl.events.CellulesEvent;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

/**
 * Classe générant les simulateurs de cellules.
 */
public class CellulesSimulator extends Simulator implements Simulable {
    /**
     * Ensemble des cellules à afficher.
     */
    private Cellules cellules;

    /**
     * Constructeur du simulateur de cellules de Conway.
     * Place sur une grille de taille 5×5.
     *
     * @param window   La fenêtre graphique du simulateur.
     * @param cellules Les cellules à simuler.
     */
    public CellulesSimulator(GUISimulator window, Cellules cellules) {
        super(window);
        this.cellules = cellules;

        getEventManager().addEvent(new CellulesEvent(1, cellules, this, getEventManager()));
    }

    /**
     * Méthode appelée lors d'un clic sur le bouton Suivant, ou bien à intervalles
     * réguliers si le mode "lecture" a été démarré. Passe l'ensemble des cellules d'un état i à un état (i+1).
     */
    @Override
    public void next() {
        // On appelle directement la méthode next() du manager d'événements
        getEventManager().next();
    }

    /**
     * Méthode appelée lors d'un clic sur le bouton Début. La lecture est alors arrêtée
     * et le simulateur revient dans l'état initial (la grille est redessinée
     * comme au départ).
     */
    @Override
    public void restart() {
        // On revient à l'état initial
        cellules.restart();

        afficher();
    }

    /**
     * Affiche la grille complète avec le quadrillage et les cellules.
     */
    @Override
    public void afficher() {
        getWindow().reset();

        // On récupère la taille du carré dans lequel on va tracer notre grille.
        int hauteurWindow = getWindow().getPanelHeight();
        int largeurWindow = getWindow().getPanelWidth();

        // On trace la grille et les cellules en même temps
        // Quitte à boucler, on fait les 2 à la fois
        int pasVertical = hauteurWindow / getCellules().getHauteur();
        int pasHorizontal = largeurWindow / getCellules().getLargeur();

        for (int i = 0; i < getCellules().getLargeur(); i++) {
            for (int j = 0; j < getCellules().getHauteur(); j++) {
                // Affichage du quadrillage
                getWindow().addGraphicalElement(
                        new Rectangle(
                                i * pasHorizontal + pasHorizontal / 2,
                                j * pasVertical + pasVertical / 2,
                                Color.decode("#bfbfbf"),
                                Color.decode("#FFFFFF"),
                                pasHorizontal,
                                pasVertical
                        )
                );

                afficherCellule(i, j, pasHorizontal, pasVertical);
            }
        }
    }

    /**
     * Fonction permettant d'afficher chaque cellule dans la grille de la fenêtre du simulateur.
     *
     * @param i             Abscisse de la cellule à afficher.
     * @param j             Ordonnée de la cellule à afficher.
     * @param pasHorizontal Largeur de la cellule.
     * @param pasVertical   Hauteur de la cellule.
     */
    private void afficherCellule(int i, int j, int pasHorizontal, int pasVertical) {
        float proportion = (float) getCellules().getEtat(i, j) / (float) getCellules().getNombreEtats();

        if (!getCellules().isEtat(i, j, 0)) {
            Color c = new Color(1f - proportion, 1f - proportion, 1f - proportion);

            System.out.println("Couleur de (" + i + ", " + j + ") : " + c +
                    " calculée avec la proportion :" + proportion);

            getWindow().addGraphicalElement(
                    new Rectangle(
                            i * pasHorizontal + pasHorizontal / 2 + 1,
                            j * pasVertical + pasVertical / 2 + 1,
                            c,
                            c,
                            pasHorizontal - 2,
                            pasVertical - 2
                    )
            );
        }
    }

    /**
     * Accesseur sur les cellules simulées.
     *
     * @return Les cellules simulées.
     */
    private Cellules getCellules() {
        return cellules;
    }
}
