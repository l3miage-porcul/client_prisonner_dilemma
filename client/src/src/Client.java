package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    int idJoueur;
    private final String baseUrl;
    private final String creerPartieUrl;
    private final String jouerCoupUrl;
    private final String historiqueUrl;
    private final String longueurUrl;

    public Client(int idJoueur) {
        this.idJoueur = idJoueur;
        baseUrl = "http://localhost:8080/api/dilemne-du-prisonnier/";
        creerPartieUrl = "creer-partie/";
        jouerCoupUrl = "%d/jouer/%d/%b";  // URL avec paramètres à formater plus tard
        historiqueUrl = "%d/historique";   // URL avec paramètre idPartie
        longueurUrl = "%d/longueur";   // URL avec paramètre idPartie
    }

    public int creerPartie(int nbTours) {
        try {
            String urlString = String.format("%s%s%d", baseUrl, creerPartieUrl, nbTours);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return Integer.parseInt(response.toString()); // Retourne l'ID de la partie créée
            } else {
                System.out.println("Erreur lors de la création de la partie : " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 en cas d'erreur
    }

    public void jouerCoup(int idPartie, int idJoueur, boolean coopere) {
        try {
            String urlString = String.format(baseUrl + jouerCoupUrl, idPartie, idJoueur, coopere);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Réponse : " + response.toString());
            } else {
                System.out.println("Erreur lors de l'envoi du coup : " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int obtenirNbToursPartie(int idPartie){

        try {
            String urlString = String.format(baseUrl + longueurUrl, idPartie);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Historique : " + response.toString());
                return response.length();

            } else {
                System.out.println("Erreur lors de la récupération de l'historique : " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
    public int getHistorique(int idPartie) {
        try {
            String urlString = String.format(baseUrl + historiqueUrl, idPartie);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Historique : " + response.toString());
                return response.length();

            } else {
                System.out.println("Erreur lors de la récupération de l'historique : " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    */
}
