package fr.ensimag.tpl.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Gestionnaire d'événements discrets.
 */
public class EventManager {
    /**
     * Date courante de simulation.
     */
    private long currentDate = 0;

    /**
     * File d'attente contenant les événements à traiter.
     */
    private CopyOnWriteArrayList<Event> events = new CopyOnWriteArrayList<Event>();

    /**
     * Sauvegarde des événements pour pouvoir repartir du début
     */
    private LinkedList<Event> eventsSave = new LinkedList<Event>();

    /**
     * Méthode permettant d'ajouter un événement dans la file d'attente de notre manager d'événements.
     *
     * @param e Événement à ajouter à la flile d'attente du manager d'événements.
     */
    public void addEvent(Event e) {
        events.add(e);
        eventsSave.add(e);
    }

    /**
     * Méthode permettant d'exécuter les événements de la date suivante.
     * Incrémente la date du manager d'événements puis exécute les événements de la file d'attente dont la date est
     * inférieure ou égale à la date du manager.
     */
    public void next() {
        // On incrémente la date
        currentDate++;
        System.out.println("Next... Current date : " + currentDate);

        // On exécute les événements non traités jusque-là
        Iterator<Event> iter = events.iterator();

        for (Event e : events) {
            if (e.getDate() <= currentDate) {
                e.execute();
                events.remove(e);
            }
        }
    }

    /**
     * Retourne s'il y a encore des événements dans la file d'attente.
     *
     * @return boolean Booléen qui indique s'il reste des événements dans la file d'attente.
     */
    public boolean isFinished() {
        return events.isEmpty();
    }

    /**
     * Redémarre le gestionnaire d'événements au début (remet tous les événements en file d'attente).
     */
    public void restart() {
        currentDate = 0;

        for (Event e : eventsSave) {
            events.add(e);
        }
    }
}
