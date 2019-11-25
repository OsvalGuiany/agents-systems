package fr.ensimag.tpl.elements;

/**
 * Classe modélisant des cellules de Conway.
 */
public class CellulesConway extends Cellules {
    /**
     * Constructeur de la grille de cellules de Conway.
     *
     * @param largeur Largeur de la grille de cellules.
     * @param hauteur Hauteur de la grille de cellules.
     * @throws IllegalArgumentException Exception renvoyée en cas de largeur ou hauteur négative ou nulle.
     */
    public CellulesConway(int largeur, int hauteur) throws IllegalArgumentException {
        super(largeur, hauteur, 2);
    }

    /**
     * Fonction calculant l'état de l'automate cellulaire
     * au temps t+1 selon la méthode de calcul du jeu de la vie de Conway.
     */
    public void nextState() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                int num = neighbourInState(i, j, 1);
                if (etatsPrec[i][j] == 0) {
                    if (num == 3) {
                        etats[i][j] = 1;
                    }
                } else {
                    if (num < 2 || num > 3) {
                        etats[i][j] = 0;
                    }
                }
            }
        }

        this.savePrevStates();
    }
}
