package fr.ensimag.tpl.events;

/**
 * Classe modélisant des événements
 */
public abstract class Event {
    /**
     * Date d'exécution de l'événement.
     */
    private long date;

    /**
     * Constructeur de la classe Event.
     *
     * @param date
     * @throws IllegalArgumentException Exception renvoyée cas de date négative ou nulle.
     */
    public Event(long date) {
        // Vérification des paramètres
        if (date <= 0) {
            throw new IllegalArgumentException("Date fournie invalide.");
        }

        this.date = date;
    }

    /**
     * Récupère la date à laquelle doit être exécutée l'événement
     *
     * @return long La date d'exécution de l'événement.
     */
    public long getDate() {
        return date;
    }

    /**
     * Méthode abstraite qui permet d'implémenter le comportement de l'exécution de l'événement.
     */
    public abstract void execute();
}
