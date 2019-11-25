package fr.ensimag.tpl.tests;

import fr.ensimag.tpl.events.EventManager;
import fr.ensimag.tpl.events.MessageEvent;

/**
 * Test donné dans le sujet du TPL : permet de vérifier notre implémentation des événements discrets.
 */
public class TestEventManager {
    /**
     * Fonction principale du test. Créé un simulateur de messages puis simule un échange de messages.
     *
     * @param args Arguments passés à la commande.
     * @throws InterruptedException Exception pouvant être renvoyée par Thread.sleep
     */
    public static void main(String[] args) throws InterruptedException {
        // On crée un simulateur
        EventManager manager = new EventManager();

        for (int i = 2; i <= 10; i += 2) {
            manager.addEvent(new MessageEvent(i, " [PING]"));
        }

        // On pose un événement [PONG] tous les trois pas de temps
        for (int i = 3; i <= 9; i += 3) {
            manager.addEvent(new MessageEvent(i, " [PONG]"));
        }

        while (!manager.isFinished()) {
            manager.next();
            Thread.sleep(1000);
        }
    }
}
