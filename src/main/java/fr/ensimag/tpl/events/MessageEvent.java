package fr.ensimag.tpl.events;

/**
 * Événement discret associé aux messages (test donné dans le sujet)
 */
public class MessageEvent extends Event {
    /**
     * Le message à afficher lors de l'exécution de l'événement.
     */
    private String message;

    /**
     * Constructeur d'un événement associé à un message.
     *
     * @param date    Date d'exécution de l'événement.
     * @param message Le message à afficher.
     */
    public MessageEvent(int date, String message) {
        super(date);
        this.message = message;
    }

    /**
     * Exécute l'action associée à l'événement (ici, afficher un message).
     */
    public void execute() {
        System.out.println(this.getDate() + this.message);
    }
}
