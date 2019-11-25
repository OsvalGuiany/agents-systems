package fr.ensimag.tpl;

import fr.ensimag.tpl.tests.TestEventManager;
import fr.ensimag.tpl.tests.TestGUI;

import java.io.Console;

import static fr.ensimag.tpl.tests.TestBalls.testSimuBalls;
import static fr.ensimag.tpl.tests.TestBoids.testSimuBoids;
import static fr.ensimag.tpl.tests.TestCells.*;
import java.util.Scanner;

/**
 * Classe principale de l'application de simulation. Lance un prompt pour démarrer les simulations et tests.
 */
public class App {
    /**
     * Programme principal de notre projet : lance un mini-shell permettant de démarrer des simulations avec des
     * paramètres personnalisés.
     * @param args Arguments à passer en ligne de commande (ignorés pour ce programme).
     */
    public static void main(String[] args) {
        String input = "";

        System.out.println("Bienvenue sur notre simulation orientée objet de systèmes multiagents !");
        System.out.println("=======================================================================");
        System.out.println("Nous vous proposons les simulations suivantes. Entrez le numéro correspondant pour lancer " +
                "la simulation :");
        System.out.println("1 : Simulation de balles (\"balls\").");
        System.out.println("2 : Jeu de la vie de Conway.");
        System.out.println("3 : Jeu de l'immigration.");
        System.out.println("4 : Cellules de Schelling.");
        System.out.println("5 : Simulation de \"boids\".");
        System.out.println("6 : Test du sujet permettant de vérifier l'implémentation des événements discrets.");
        System.out.println("7 : Test de la librairie GUI (TestGUI.java donné avec le sujet).");

        Scanner sc = new Scanner(System.in);

        while (!input.equals("exit")) {
            System.out.println("Note : Pour éteindre le programme, entrez \"exit\" dans le prompt.");
            System.out.print("Que souhaitez-vous faire ? ");
            input = sc.nextLine();

            if (input.equals("1")) {
                int nombreBalles = 0;

                while (nombreBalles <= 0) {
                    System.out.print("Nombre de balles (3 par défaut) ?  ");
                    String strNbrBalles = sc.nextLine();

                    if (strNbrBalles.equals("")) {
                        nombreBalles = 3;
                    } else {
                        nombreBalles = Integer.parseInt(strNbrBalles);
                    }
                }

                testSimuBalls(nombreBalles);
            } else if (input.equals("2") || input.equals("3") || input.equals("4")) {
                // Sélection de la taille de l'affichage
                int largeur = 0;
                int hauteur = 0;

                while (largeur <= 0) {
                    System.out.print("Largeur de l'automate (5 par défaut) ?  ");
                    String strLargeur = sc.nextLine();

                    if (strLargeur.equals("")) {
                        largeur = 5;
                    } else {
                        largeur = Integer.parseInt(strLargeur);
                    }
                }

                while (hauteur <= 0) {
                    System.out.print("Hauteur de l'automate (5 par défaut) ?  ");
                    String strHauteur = sc.nextLine();

                    if (strHauteur.equals("")) {
                        hauteur = 5;
                    } else {
                        hauteur = Integer.parseInt(strHauteur);
                    }
                }


                if (input.equals("2"))
                    testSimuConway(largeur, hauteur);
                else if (input.equals("3")) {
                    int nombreEtats = 0;

                    while (nombreEtats <= 0) {
                        System.out.print("Nombre d'états (4 par défaut) ?  ");
                        String strNbrEtats = sc.nextLine();

                        // L'état 0 compte déjà comme un état
                        if (strNbrEtats.equals("")) {
                            nombreEtats = 3;
                        } else {
                            nombreEtats = Integer.parseInt(strNbrEtats) - 1;
                        }
                    }

                    testSimuImmigration(largeur, hauteur, nombreEtats);
                } else {
                    // input.equals("4") Schelling
                    int seuil = 0;
                    int nombreEtats = 0;

                    while (seuil <= 0) {
                        System.out.print("Seuil K (4 par défaut) ?  ");
                        String strSeuil = sc.nextLine();

                        if (strSeuil.equals("")) {
                            seuil = 4;
                        } else {
                            seuil = Integer.parseInt(strSeuil);
                        }
                    }

                    while (nombreEtats <= 0) {
                        System.out.print("Nombre d'états (4 par défaut) ?  ");
                        String strNombreEtats = sc.nextLine();

                        if (strNombreEtats.equals("")) {
                            nombreEtats = 4;
                        } else {
                            nombreEtats = Integer.parseInt(strNombreEtats);
                        }
                    }

                    testSimuShelling(largeur, hauteur, seuil, nombreEtats);
                }
            } else if (input.equals("5")) {
                int nombreBoids = 0;

                while (nombreBoids <= 0) {
                    System.out.print("Nombre de boids (20 par défaut) ?  ");
                    String strNbrBoids = sc.nextLine();

                    if (strNbrBoids.equals("")) {
                        nombreBoids = 20;
                    } else {
                        nombreBoids = Integer.parseInt(strNbrBoids);
                    }
                }

                testSimuBoids(nombreBoids);
            } else if (input.equals("6")) {
                // Test événements discrets
                try {
                    TestEventManager.main(args);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Il y a eu un problème lors de l'exécution du test.");
                }
            } else if (input.equals("7")) {
                TestGUI.test(args);
            }

            System.out.println("Vous avez choisi la simulation : " + input + ".");
        }
    }
}
