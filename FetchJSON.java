package algos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.json.JSONObject; // Assure-toi d'avoir la bibliothèque JSON dans ton classpath


public class FetchJSON {
	
	public static int loginDecideur(String identifiant,String pass) {
		
		
		return 0;
	}
	
	public static JSONObject recupJSONVote(int idVote,int idVotant,int idGroupe) throws IOException {
		/*
		 * Arguments : 
		 * 		- idVote -> id du vote dont on veut les informations 
		 * 		- idVotant -> id de la personne dont on veut le vote, ne sevira pas ici mais est necessaire à l'api
		 * 		- idGroupe -> id du groupe dans lequel le vote est crée
		 * Sortie : 
		 * 		- un objet JSON qui contient toutes les informations liées au vote
		 * Contenu de la fonction : 
		 * 		- la fonction établit une connexion avec le serveur,envoie la requete Get, vérifie que le code de retour est le bon, puis convertit le contenu
		 * 			obtenu en JSON et renvoie cet objet
		 */
		JSONObject jsonVoulu = null;
        String baseUrl = "https://projets.iut-orsay.fr/saes3-vjacqu3/classePHP/rest/GET.php?classe=vote&id="+idVote+"&idVotant="+idVotant+"&idGroupe="+idGroupe;
        String urlString = baseUrl + idVote; 
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        //Vérifier le code de réponse HTTP pour du debug
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code for ID " + idVote + ": " + responseCode); 
        
        //lire la réponse en s'assurant que le code est le bon
        if (responseCode == HttpURLConnection.HTTP_OK) {  // on verifie que le code renvoie un ok
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Convertir la réponse en JSON
            String jsonResponse = response.toString();
            System.out.println(jsonResponse);

            // Analyser la réponse JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);
            jsonVoulu = jsonObject;
        }
        connection.disconnect();
		return jsonVoulu;
		
	}
	public static JSONObject recupJSONUtilisateur(int idUser) throws IOException {
		/*
		 * Arguments : 
		 * 		- idUser -> l'id de l'utilisateur dont on veut les informations
		 * Sortie : 
		 * 		- un objet JSON contenant les informations de l'utililsateur
		 * Contenu de la fonction : 
		 * 		- voir description de recupJSONVote
		 */
		JSONObject jsonVoulu = null;
        String baseUrl = "https://projets.iut-orsay.fr/saes3-vjacqu3/classePHP/rest/GET.php?classe=utilisateur&id=";
        String urlString = baseUrl + idUser; 
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        //Vérifier le code de réponse HTTP pour du debug
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code for ID " + idUser + ": " + responseCode); 
        
        //lire la réponse en s'assurant que le code est le bon
        if (responseCode == HttpURLConnection.HTTP_OK) {  // on verifie que le code renvoie un ok
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Convertir la réponse en JSON
            String jsonResponse = response.toString();
            System.out.println(jsonResponse);

            // Analyser la réponse JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);
            jsonVoulu = jsonObject;
        }
        connection.disconnect();
		return jsonVoulu;
		
	}
 
}

