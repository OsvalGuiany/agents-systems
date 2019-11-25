/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.tpl.elements;

/**
 * Classe modélisant des cellules d'immigration.
 */
public class CellulesImmigration extends Cellules {
    /**
     * Constructeur d'un semble de cellules d'immigration à simuler.
     *
     * @param largeur     Largeur de la grille.
     * @param hauteur     Hauteur de la grille.
     * @param nombreEtats nombre d'états possibles d'une cellule.
     */
    public CellulesImmigration(int largeur, int hauteur, int nombreEtats) {
        super(largeur, hauteur, nombreEtats);
    }

    /**
     * Fonction calculant l'état de l'automate cellulaire
     * au temps t+1 selon la méthode de calcul du jeu de l'immigration.
     */
    public void nextState() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                int nextState = (etatsPrec[i][j] + 1) % nombreEtats;
                int num = neighbourInState(i, j, nextState);

                if (num >= 3) {
                    this.etats[i][j] = (this.etats[i][j] + 1) % nombreEtats;
                }

            }
        }
        savePrevStates();
    }
}
