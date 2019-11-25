package fr.ensimag.tpl.elements;

public class BoidsFollower extends AbstractBoids {

    public BoidsFollower(int maxX, int maxY, int nbBoids, double maxInterval, double minInterval) {
        super(maxX, maxY, nbBoids, maxInterval, minInterval);
    }

    public BoidsFollower(int maxX, int maxY, int nbBoids) {
        super(maxX, maxY, nbBoids);
    }

    public void nextState() {

    }
}
