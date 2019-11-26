package fr.ensimag.tpl.elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe permettant de modéliser des cellules de Shelling.
 */
public class CellulesSchelling extends Cellules {
    /**
     * Seuil du nombre de voisins
     */
    private final int K;

    /**
     * Contient les coordonnées des maisons vacantes.
     */
    private ArrayList<Point> vacants = new ArrayList<Point>();

    /**
     * Contient les coordonnées des maisons vacantes à l'état initial.
     */
    private ArrayList<Point> vacantsInit = new ArrayList<Point>();

    /**
     * Constructeur de cellules de Schelling. Ici on initialise les cellules et les maisons vacantes.
     *
     * @param largeur      le nombre d'habitations par ligne
     * @param hauteur      le nombre d'habitations par colonne
     * @param K            le seuil de voisins pour qu'une personne quitte son domicile
     * @param nbreCouleurs le nombre de couleur couleurs dans la zone.
     */
    public CellulesSchelling(int largeur, int hauteur, int K, int nbreCouleurs) {
        super(largeur, hauteur, nbreCouleurs + 1);

        if (K > 8) {
            throw new IllegalArgumentException("La valeur de K ne doit pas"
                    + "dépasser 8");
        }

        this.K = K;
        this.initVacants();
    }

    /**
     * Remet les cellules et les maisons vacantes à leur état initial.
     */
    @Override
    public void restart() {
        for (int i = 0; i < getLargeur(); i++) {
            for (int j = 0; j < getHauteur(); j++) {
                this.etats[i][j] = this.etatsInit[i][j];
                this.etatsPrec[i][j] = this.etatsInit[i][j];
            }
        }

        this.vacants = new ArrayList<Point>(this.vacantsInit);
    }

    /**
     * Pour initialiser les cellules vacantes.
     * Et stocker leurs coordonées dans les tableaux vacants et vacantsInit.
     */
    private void initVacants() {
        //On parcours les cellules et on stocke celles à l"état 0(vacantes) dans vacants.
        for (int i = 0; i < getLargeur(); i++) {
            for (int j = 0; j < getHauteur(); j++) {
                if (isEtat(i, j, 0)) {
                    setVacant(i, j);
                }
            }
        }

        //si il n y a pas assez de cellules vacantes on en crée d'autres.
        Random r = new Random();
        while (vacants.size() < 0.25 * getLargeur() * getHauteur()) {
            int i = r.nextInt(getLargeur());
            int j = r.nextInt(getHauteur());

            if (!isEtat(i, j, 0)) {
                setVacant(i, j);
                etatsInit[i][j] = 0;
            }
        }

        //On rempli le tableau vacantsInit pour mémoriser l'état initial.
        for (int k = 0; k < this.vacants.size(); k++) {
            this.vacantsInit.add(k, this.vacants.get(k));
        }
    }

    /**
     * Passage à l'état suivant des cellules selon les couleurs de leurs voisins.
     */
    @Override
    public void nextState() {
        for (int i = 0; i < getLargeur(); i++) {
            for (int j = 0; j < getHauteur(); j++) {
                // On traite que les cases occupées par des cellules.
                if (!isEtat(i, j, 0)) {
                    // Calcul de nombres de cellules de couleur differente de cellule courante.
                    int nDiffCells = 0;
                    int[] values = neighboursValues(i, j);
                    for (int value : values) {
                        if (value != 0) {
                            nDiffCells++;
                        }
                    }

                    // Si on trouve plus de K cellules de couleurs differentes
                    // La cellule courant déménage.
                    if (nDiffCells > K) {
                        int couleur = etats[i][j];
                        setVacant(i, j);
                        getNewHouse(couleur);
                    }
                }
            }
        }

        this.savePrevStates();
    }

    /**
     * Donne la couleur c à la case vide (i, j) et enleve cettes case de tableau vacants.
     *
     * @param i Abscisse de la case.
     * @param j Ordonnée de la case.
     * @param c Couleur à donner à la case (i, j).
     */
    private void setOccupied(int i, int j, int c) {
        if (!isEtat(i, j, 0)) {
            throw new RuntimeException("the house at the position " + i + " " + j
                    + "is already occupied");
        }
        etats[i][j] = c;
        vacants.remove(new Point(i, j));
    }


    /**
     * Place une cellule de couleur c dans une case vide.
     *
     * @param c couleur de la cellule qui cherche une maison vacante.
     */
    private void getNewHouse(int c) {
        if (!this.vacants.isEmpty()) {
            // On choisit une maison vacante aléatoirement.
            int index = (new Random()).nextInt(this.vacants.size());
            Point vacantCell = vacants.get(index);

            // On place la cellule dans la maison choisie.
            setOccupied((int) vacantCell.getX(), (int) vacantCell.getY(), c);
        } else {
            throw new RuntimeException("Il n'y a plus de maison libre");
        }
    }

    /**
     * Transforme la case (i, j) en une maison vacante et l'ajoute au teableau des maison vacantes.
     *
     * @param i Abscisse de la case.
     * @param j Ordonnée de la case.
     */
    private void setVacant(int i, int j) {
        if (!isEtat(i, j, 0)) {
            etats[i][j] = 0;
            vacants.add(new Point(i, j));
        }
    }
}
