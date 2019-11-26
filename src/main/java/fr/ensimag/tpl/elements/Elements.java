package fr.ensimag.tpl.elements;

/**
 * Classe permettant de factoriser les propriétés des éléments à simuler.
 */
public abstract class Elements {
    /**
     * Abscisse maximale à laquelle placer les éléments.
     */
    private int xMax;
    /**
     * Ordonnée maximale à laquelle placer les éléments.
     */
    private int yMax;

    /**
     * Constructeur des éléments à simuler.
     *
     * @param xMax Abscisse maximale.
     * @param yMax Ordonnée maximale.
     * @throws IllegalArgumentException Renvoyée si l'abscisse ou l'ordonnée sont invalides (inférieur à 0).
     */
    public Elements(int xMax, int yMax) throws IllegalArgumentException {
        if (xMax <= 0 || yMax <= 0) {
            throw new IllegalArgumentException(
                    "Il faut une abscisse et/ou une ordonnée maximale(s) "
                            + "positive(s)."
            );
        }
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * Accesseur à l'abscisse maximale de la zone de simulation.
     *
     * @return Abscisse maximale de la zone de simulation.
     */
    public int getxMax() {
        return xMax;
    }

    /**
     * @param xMax attribuer une valeur à xMax
     */
    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    /**
     * @param yMax la valeur à attribuer à yMax
     */
    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    /**
     * Accesseur à l'ordonnée maximale de la zone de simulation.
     *
     * @return Ordonnée maximale de la zone de simulation.
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Fonction calculant l'état de l'automate cellulaire
     * au temps t+1.
     */
    public abstract void nextState();
}
