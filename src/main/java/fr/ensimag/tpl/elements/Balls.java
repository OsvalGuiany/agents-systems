package fr.ensimag.tpl.elements;

import java.awt.*;
import java.util.Iterator;

/**
 * Classe permettant de gérer des ensembles de balles.
 * Implémente un itérateur de coordonnées entières sur les points afin de
 * respecter le principe d'encapsulation (car on ne veut pas pouvoir modifier
 * les points des balles).
 */
public class Balls extends Elements implements Iterable<int[]> {
    /**
     * Tableau des balles à afficher
     */
    private Point[] tableauBalles;
    /**
     * Tableau de sauvegarde des positions de l'état initial de l'ensemble
     * de balles
     */
    private Point[] tableauBallesInit;

    /**
     * Constructeur de la classe Balls. Initialise un tableau de nbBalles balles à modéliser.
     *
     * @param nbBalles Nombre de balles à modéliser
     * @param xMax     Abscisse maximale où l'on peut placer les balles.
     * @param yMax     Ordonnée maximale où l'on peut placer les balles.
     * @throws IllegalArgumentException Renvoyée si l'on donne un nombre de balles ou des coordonnées maximales négatives.
     */
    public Balls(int nbBalles, int xMax, int yMax) throws IllegalArgumentException {
        super(xMax, yMax);

        // Vérification des paramètres
        if (nbBalles <= 0) {
            throw new IllegalArgumentException(
                    "Il faut un nombre positif de balles."
            );
        } else if (nbBalles > xMax * yMax) {
            throw new IllegalArgumentException(
                    "Trop de balles à afficher."
            );
        }

        // On commence l'initialisation des balles
        tableauBalles = new Point[nbBalles];
        tableauBallesInit = new Point[nbBalles];

        for (int i = 0; i < nbBalles; i++) {
            tableauBalles[i] = new Point(
                    (int) (Math.random() * xMax),
                    (int) (Math.random() * yMax)
            );
            /* On sauvegarde également les positions initiales pour pouvoir
                    réinitialiser les positions */
            tableauBallesInit[i] = new Point(tableauBalles[i].getLocation());
        }
    }

    /**
     * Permet de translater chacun des points de manière identique.
     * Lorsque les balles touchent les bordures, elles rebondissent.
     *
     * @param dx Déplacement sur l'axe des abscisses.
     * @param dy Déplacement sur l'axe des ordonnées.
     */
    public void translate(int dx, int dy) {
        for (Point balle : tableauBalles) {
            if (balle.getX() + dx >= 0 && balle.getX() + dx <= getxMax()
                    && balle.getY() + dy >= 0 && balle.getY() + dy <= getxMax()) {
                balle.translate(dx, dy);
                continue;
            }

            // Si on sort, on rebondit !
            if (balle.getX() + dx < 0 || balle.getX() + dx > getxMax()) {
                balle.translate(
                        -(2 * dx - (getxMax() - (int) balle.getX())), 2 * dy
                );
            }

            if (balle.getY() + dy < 0 || balle.getY() + dy > getyMax()) {
                balle.translate(
                        2 * dx, -(2 * dy - (getyMax() - (int) balle.getY()))
                );
            }
        }
    }

    /**
     * Fonction de transition des balles d'un état i à un état (i+1).
     */
    public void nextState() {
        translate(10, 10);
    }

    /**
     * Remet toutes les balles à leur position initiale.
     */
    public void reInit() {
        for (int i = 0; i < tableauBalles.length; i++) {
            tableauBalles[i].setLocation(tableauBallesInit[i].getLocation());
        }
    }

    /**
     * Itérateur sur les coordonnées des points des balles. Permet de respecter
     * le principe d'encapsulation : l'utilisateur ne peut pas utiliser les
     * setters des balles contenues dans java.awt.Point.
     *
     * @return Un itérateur sur les coordonnées des points de Balls.
     */
    public Iterator<int[]> iterator() {
        Iterator<int[]> it = new Iterator<int[]>() {
            private int currentIndex = 0;

            /**
             * Retourne s'il y a un élément suivant.
             * @return Vrai s'il y a un élément suivant, faux s'il n'y a pas d'élément suivant.
             */
            @Override
            public boolean hasNext() {
                return currentIndex < tableauBalles.length &&
                        tableauBalles[currentIndex] != null;
            }

            /**
             * Retourne l'élément suivant et itère.
             * @return L'élément suivant.
             */
            @Override
            public int[] next() {
                int[] next = {
                        (int) tableauBalles[currentIndex].getX(),
                        (int) tableauBalles[currentIndex].getY()
                };
                currentIndex++;
                return next;
            }

            /**
             * Itérateur de suppression (non implémenté ici).
             * @throws UnsupportedOperationException
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        return it;
    }

    /**
     * Méthode retournant une chaîne de caractères retranscrivant l'ensemble
     * des positions des balles.
     *
     * @return Chaîne de caractères décrivant l'ensemble de balles courant.
     */
    @Override
    public String toString() {
        String description = "{";

        if (tableauBalles.length > 0) {
            for (int i = 0; i < tableauBalles.length; i++) {
                description += "(" + tableauBalles[i].getX() + ", ";
                description += tableauBalles[i].getY() + "), ";
            }
            description = description.substring(0, description.length() - 2);
        }

        return description + "}";
    }
}
