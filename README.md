# TPL POO Systèmes multiagents

TP en temps libre de programmation orientée objet. L'objectif est de simuler graphiquement des sytèmes multiagents.
Pour plus d'informations, notamment sur les choix d'implémentation, veuillez consulter le rapport au format PDF.

## Compilation et exécution

Notre TPL utilise *Maven*, un outil de gestion et d'automatisation de production de logiciels Java. Comparable à `make` pour gérer la production d'un programme `C`, il permet de gérer les dépendances, compiler en une fois, exécuter le programme, les tests et générer la documentation. Voici les commandes dont vous aurez besoin (elles sont à exécuter depuis la racine du projet) :

* `mvn initialize` : À exécuter une fois au départ, avant toute autre commande avec *Maven*. Permet à *Maven* d'enregistrer la librairie `gui` pour la lier au reste du code.
* `mvn compile` : Pour compiler.
* `mvn clean` : Permet de supprimer l'ensemble des fichiers générés lors de la compilation, des tests, etc.
* `mvn test` : Lancer les tests unitaires construits avec `JUnit`.
* `mvn exec:java -D exec.mainClass=fr.ensimag.tpl.App` : Pour lancer l'application (n'effectue pas les tests avant le démarrage).
* `mvn javadoc:javadoc` pour générer la *Javadoc* associée au projet (la page d'accueil de la documentation est alors `target/site/apidocs/index.html`).

Une autre manière de démarrer l'application est de créer une archive au format `jar` :
* Créer le package avec `mvn package`.
* Exécuter le package avec la commande `java -jar target/tpl-poo-1.0-SNAPSHOT.jar`.

Le paquet `jar` créé contient toutes les classes dont dépendent le projet et peut donc être directement exécuté avec `java`.

## Architecture du projet

On trouve à la racine les répertoires suivants :
* `bin/` : Contient la librairie pour faire les affichages graphiques (fournie).
* `doc_gui/` : Documentation à propos de la librairie permettant de faire des affichages.
* `src/` : Nos sources.
* `target/` : Les fichiers générés (classes compilées, documentation, …).
