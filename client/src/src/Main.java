package src;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le dilemme du prisonnier !");
        System.out.println("1. Créer une partie");
        System.out.println("2. Rejoindre une partie");

        int choix;
        final int idPartie;
        do {
            System.out.print("Entrez votre choix (1 pour créer, 2 pour rejoindre) : ");
            choix = scanner.nextInt();
        } while (choix != 1 && choix != 2);

        Client client = new Client(choix);

        if (choix == 1) {
            System.out.print("Combien de tours voulez-vous pour la partie ? ");
            int nbTours = scanner.nextInt();
            idPartie = client.creerPartie(nbTours);
            if (idPartie < 0) {
                System.out.println("Erreur lors de la création de la partie.");
                return;
            }
        }else{
            System.out.print("Quel est l'ID de la partie que vous voulez rejoindre ?");
            idPartie = scanner.nextInt();
        }

        demarrerPartie(client, idPartie);

        scanner.close();
    }

    private static void demarrerPartie(Client client, int idPartie) {

        int nbTours;
        int choix;
        Scanner scanner;

        System.out.println("La partie commence !");
        scanner = new Scanner(System.in);
        nbTours = client.obtenirNbToursPartie(idPartie);

        for(int i = 0; i < nbTours; i++){

            do {
                System.out.print("Entrez votre choix (1 pour coopérer, 2 pour trahir) : ");
                choix = scanner.nextInt();
            } while (choix != 1 && choix != 2);

            client.jouerCoup(idPartie, client.idJoueur, (choix == 1) ? true : false);

        }
    }
}
