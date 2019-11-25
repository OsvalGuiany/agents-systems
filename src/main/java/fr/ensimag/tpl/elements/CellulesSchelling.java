package fr.ensimag.tpl.elements;

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
     * Contient la chaine désignant les maisons vacantes
     */
    private ArrayList<String> vacants = new ArrayList<String>();

    /**
     * Contient les maisons vacantes à l'état initial.
     */
    private ArrayList<String> vacantsPrev = new ArrayList<String>();

    /**
     * Constructeur de cellules de Schelling. Prend en paramètre les différents paramètres d'un automate de cellules de
     * Schelling.
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
     * Remet les cellules à leur état initial.
     */
    @Override
    public void restart() {
        for (int i = 0; i < largeur; i++) {
            System.arraycopy(this.etatsInit[i], 0, this.etats[i], 0, hauteur);
        }

        for (int k = 0; k < this.vacants.size(); k++) {
            this.vacantsPrev.add(k, this.vacantsPrev.get(k));
        }
    }

    /**
     * Permet de selectionner au hasard un ensemble de maisons qui seront
     * vacantes
     */
    private void initVacants() {
        Random r = new Random();

        while (vacants.size() < 0.25 * largeur * hauteur) {
            int i = r.nextInt(largeur);
            int j = r.nextInt(hauteur);

            if (!vacants.contains(i + " " + j))
                setVacant(i, j);
        }

        for (int k = 0; k < this.vacants.size(); k++) {
            this.vacantsPrev.add(k, this.vacants.get(k));
        }
    }

    /**
     * permet de passer à l'état suivant de nos cellules pour qu'éventuellement
     * certaines familles puissent emménager dans d'autres maisons
     */
    @Override
    public void nextState() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                int[] values = neighboursValues(i, j);
                int[] racetab = reduce(values, etats[i][j]);
                int couleurDifferent = 0;

                for (int num : racetab) {
                    if (num != 0) {
                        couleurDifferent += num;
                    }
                }

                // s'il a plus de K voisins de couleurs différente à la sienne,
                // alors, il doit démanger sinon, il reste
                if (couleurDifferent > K) {
                    int couleur = etats[i][j];
                    setVacant(i, j);
                    setHouse(couleur);
                }
            }
        }

        this.savePrevStates();
    }

    /**
     * @param tab tableau contenant les valeurs à réduire.
     * @return un tableau contenant le nombre de voisins par couleur
     */
    private int[] reduce(int[] tab, int c) {
        int[] val = new int[nombreEtats];

        for (int i = 0; i < tab.length; i++) {
            // chaque val[i] contient le nombre de voisins ayant la couleur i.
            if (tab[i] > 0 && tab[i] != c)
                val[tab[i]] += 1;
        }

        return val;
    }


    /**
     * Permet de savoir si la cellule est vacante ou non.
     *
     * @param i le rang de la cellule
     * @param j la colonne de la cellule
     * @return Retourne vrai si la cellule est vacante et faux sinon
     */
    public boolean isVacant(int i, int j) {
        return vacants.contains(i + " " + j);
    }

    /**
     * @param i le rang de la cellule
     * @param j la colonne de la cellule
     *          permet d'occuper une cellule
     */
    private void isOccupied(int i, int j) {
        if (!isVacant(i, j)) {
            throw new RuntimeException(
                    "the house at the position " + i + " " + j
                            + "is already occupied"
            );
        }

        vacants.remove(i + " " + j);
    }

    /**
     * @param c couleur de la famille qui occupera une maison vacante
     *          Permet d'attribuer un domicile vacant à une famille.
     */
    private void setHouse(int c) {
        if (!this.vacants.isEmpty()) {
            int index = (new Random()).nextInt(this.vacants.size());
            String str = vacants.get(index);
            int sp = str.indexOf(" ");
            int i = Integer.parseInt(str.substring(0, sp));
            int j = Integer.parseInt(str.substring(sp + 1));

            isOccupied(i, j);
            etats[i][j] = c;
        } else {
            throw new RuntimeException("Il n'y a plus de maison libre");
        }
    }

    /**
     * @param i le rang de la cellule (maison)
     * @param j la colonne de la maison (cellule) qu'on veut mettre vacant
     *          pPrmet de mettre une maison vacante au cas ou une famille l'a quitté
     */
    private void setVacant(int i, int j) {
        if (!isVacant(i, j)) {
            etats[i][j] = 0;
            vacants.add(i + " " + j);
        }
    }
}