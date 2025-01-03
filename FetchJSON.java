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
	
	
	/******* dans les methodes recup y'a du debug qui traine donc des prints random se balladent dans le code faites gaffe */
	
	
	public static JSONObject recupJSONVote(int idUser,int idVotant,int idGroupe) throws IOException {
		JSONObject jsonVoulu = null;
        String baseUrl = "https://projets.iut-orsay.fr/saes3-vjacqu3/classePHP/rest/GET.php?classe=vote&id="+idUser+"&idVotant="+idVotant+"&idGroupe="+idGroupe;
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
	public static JSONObject recupJSONUtilisateur(int idUser) throws IOException {
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
    public static void main(String[] args) {
//            // Configurer l'authentification par défaut
//            Authenticator.setDefault(new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("abaco", "Evedydruggi2leficane.".toCharArray());
//                }
//            });
            try {
            	JSONObject a = recupJSONUtilisateur(1);
            	JSONObject b = recupJSONVote(5,1,1);
                System.out.println(a);
                System.out.println("keyset : " + a.keySet());
                for (String key : a.keySet()) {
                	  Object value = a.get(key);
                	  System.out.println(key + ": " + value);
                }
                System.out.println("Vote !");
                System.out.println("");
                System.out.println("keyset : " + b.keySet());
                for (String key : b.keySet()) {
                	  Object value = b.get(key);
                	  System.out.println(key + ": " + value);
                }
                
            }catch(IOException e) {}

            

                    // Ouvrir la connexion HTTP
                   



                    // Lire la réponse JSON
                

                    // Fermer la connexion
                    

//                } catch (Exception e) {
//                    System.out.println("Erreur lors de la récupération des données pour l'ID " + i);
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            // Gestion d'exception générale si l'authentification échoue
//            System.out.println("Erreur de configuration de l'authentification");
//            e.printStackTrace();
//        }
    }
}

