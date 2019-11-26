package fr.ensimag.tpl.elements;

import java.util.Random;

/**
 * Classe générique dont héritent tous les types de cellules utilisés.
 */
public abstract class Cellules extends Elements {
    /**
     * Hauteur de la grille.
     */
    protected final int hauteur;

    /**
     * Largeur de la grille.
     */
    protected final int largeur;

    /**
     * Nombre d'etats possible.
     */
    protected final int nombreEtats;

    /**
     * Tableau d'états correspondant aux cellules (correspondance sur les indices)
     */
    protected int[][] etats;

    /**
     * Tableau d'états à l'instant t quand on passe à t+1.
     * Utile pour lors du passage des cellules du temps t au temps t+1.
     */
    protected int[][] etatsPrec;

    /**
     * Tableau des états au démarrage.
     */
    protected int[][] etatsInit;

    /**
     * Constructeur de la grille de cellules de Conway.
     *
     * @param largeur     Largeur de la grille de cellules.
     * @param hauteur     Hauteur de la grille de cellules.
     * @param nombreEtats Nombre d'états possibles d'une cellule.
     * @throws IllegalArgumentException Exception renvoyée en cas de largeur ou hauteur négative ou nulle.
     */
    public Cellules(int largeur, int hauteur, int nombreEtats) {
        super(500, 500);

        // Vérification des paramètres
        if (largeur <= 0 || hauteur <= 0) {
            throw new IllegalArgumentException("Taille invalide.");
        }
        if (nombreEtats <= 0 | nombreEtats > largeur * hauteur) {
            throw new IllegalArgumentException("Nombre de balles invalide.");
        }

        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nombreEtats = nombreEtats;
        this.etats = new int[largeur][hauteur];
        this.etatsPrec = new int[largeur][hauteur];
        this.etatsInit = new int[largeur][hauteur];

        //Initialisation de la grille.
        Random n = new Random();
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                this.etats[i][j] = n.nextInt(this.nombreEtats);
                this.etatsPrec[i][j] = this.etats[i][j];
                this.etatsInit[i][j] = this.etats[i][j];
            }
        }
    }

    /**
     * Remet les cellules à leur état initial.
     */
    public void restart() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                this.etats[i][j] = this.etatsInit[i][j];
                this.etatsPrec[i][j] = this.etatsInit[i][j];
            }
        }
    }

    /**
     * Fonction de sauvegarde de l'état à t avant de calculer l'état global à t+1.
     */
    public void savePrevStates() {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                etatsPrec[i][j] = etats[i][j];
            }
        }
    }

    /**
     * Calcule l'état des 8 voisins de la cellule (i, j)
     *
     * @param i abscisse de la cellule.
     * @param j ordonne de la cellule.
     * @return liste de 8 entiers decrivant les etats des voisins.
     */
    public int[] neighboursValues(int i, int j) {
        int[] values = new int[8];
        //On ajoute largeur ou hauteur pour éviter d'avoir des indices negatifs.
        values[0] = etatsPrec[i][(j + 1) % hauteur];
        values[1] = etatsPrec[i][(j - 1 + hauteur) % hauteur];
        values[2] = etatsPrec[(i + 1) % largeur][(j + 1) % hauteur];
        values[3] = etatsPrec[(i + 1) % largeur][(j - 1 + hauteur) % hauteur];
        values[4] = etatsPrec[(i + 1) % largeur][j];
        values[5] = etatsPrec[(i - 1 + largeur) % largeur][(j + 1) % hauteur];
        values[6] = etatsPrec[(i - 1 + largeur) % largeur][(j - 1 + hauteur) % hauteur];
        values[7] = etatsPrec[(i - 1 + largeur) % largeur][j];
        return values;
    }

    /**
     * Calcule le nombre de voisins de la cellule (i, j) dans l'etat state.
     *
     * @param i     abscisse de la cellule.
     * @param j     ordonne de la cellule.
     * @param state etat qu'on cherche.
     * @return nombre de voisins dans l'etat state.
     */
    public int neighbourInState(int i, int j, int state) {
        //il possible de fusionner cette fonction avec neighboursValues. 
        int[] values = neighboursValues(i, j);
        int count = 0;
        for (int v = 0; v < 8; v++) {
            if (state == values[v]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Accesseur public pour savoir si une cellule est dans un état particulier (renvoie un booléen).
     *
     * @param i    L'abscisse de la cellule.
     * @param j    L'ordonnée de la cellule.
     * @param etat L'état dans lequel devrait être la cellule.
     * @return Retourne un booléen indiquant si il est vrai ou non que la cellule (i, j) est vivante.
     * @throws IllegalArgumentException Renvoyée si les coordonnées ne sont pas valides.
     */
    public boolean isEtat(int i, int j, int etat) {
        // je ne vois pas pourquoi passer par countIsAlive() alors  qu'on
        // pouvait se contenter de faire le test et renvoyer etats[i][j]
        //return this.etats[i][j];
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur) {
            throw new IllegalArgumentException("argument non valide");
        }

        return etats[i][j] == etat;
    }

    /**
     * Retourne le nombre d'états possibles des cellules.
     *
     * @return Le nombre d'états possibles pour ces cellules.
     */
    public int getNombreEtats() {
        return nombreEtats;
    }

    /**
     * Retourne l'état de la cellule (i, j).
     *
     * @param i Abscisse de la cellule.
     * @param j Ordonnée de la cellule.
     * @return L'état de la cellule (i, j).
     */
    public int getEtat(int i, int j) {
        return etats[i][j];
    }

    /**
     * Retourne la hauteur de la grille en nombre de cellules.
     *
     * @return La hauteur de la grille en nombre de cellules.
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la largeur de la grille en nombre de cellules.
     *
     * @return La largeur de la grille en nombre de cellules.
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Renvoie une chaîne de caractères correspondant à l'objet.
     *
     * @return Une chaîne de caractères décrivant l'objet courant.
     */
    @Override
    public String toString() {
        String description = "";
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                description += (etats[i][j] + "\t");
            }
            description += "\n";
        }

        return description;
    }
}
