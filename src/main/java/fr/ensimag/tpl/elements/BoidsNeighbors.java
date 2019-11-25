package fr.ensimag.tpl.elements;


public class BoidsNeighbors extends AbstractBoids {
    public BoidsNeighbors(int maxX, int maxY, int nbBoids, double maxInterval, double minInterval) {
        super(maxX, maxY, nbBoids, maxInterval, minInterval);
    }

    public BoidsNeighbors(int maxX, int maxY, int nbBoids) {
        super(maxX, maxY, nbBoids);
    }

    public void nextState() {

    }
}
