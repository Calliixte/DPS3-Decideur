package algos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import java.util.Random;

public class Vote {
	ArrayList<Choix> lesChoix;
	ArrayList<String> etiquettes;
	int idVote;
	int totalVotant;
	int estimBudj;
	Choix choixGagnant;
	
	public Vote() {
		lesChoix = new ArrayList<Choix>();
		etiquettes = new ArrayList<String>();
		totalVotant=0;
	}
    public Vote(Vote v) {
        this.lesChoix = new ArrayList<>(v.lesChoix);
        this.etiquettes = new ArrayList<>(v.etiquettes);
        this.totalVotant = v.totalVotant;
        this.estimBudj = v.estimBudj;
    }
    
    public void setChoixGagnant() {
    	Choix maxChoix = lesChoix.get(0);
    	for(Choix ch : lesChoix) {
    		if(ch.getPourcentage() > maxChoix.getPourcentage()) {
    			maxChoix = ch;
    		}
    		
    		choixGagnant = maxChoix;
    	}
    }

	public Vote(int idVote,int idVotant,int idGroupe) { //constructeur qui va recupere depuis l'API, ne fonctionne pas pr l'instant (pas parfaitement)
		try {
		JSONObject j = FetchJSON.recupJSONVote(idVote, idVotant,idGroupe);
        System.out.println("keyset : " + j.keySet());
        System.out.println(j.toString());
//        for (String key : j.keySet()) {
//        	  Object value = j.get(key);
//        	  System.out.println(key.substring(6));
//        }
        
//		ArrayList<JSONObject>listeBizarre = (ArrayList<JSONObject>) j.get(" Vote choixVote");
//		for(JSONObject a : listeBizarre) {
//            for (String key : a.keySet()) {
//          	  Object value = a.get(key);
//          	  System.out.println(key + ": " + value);
//          }
//		}
		
		
		
		}catch(IOException e) {}
	} 
	
	public int nbVotantsGagnant() //renvoie le nombre de votants (approximativement) que réprésente la proposition majoritaire
	{
		return (int) ((totalVotant / 100.0) * Math.round(choixGagnant.getPourcentage()));
	}
	
    public static Vote creerRandom() {
        Vote vote = new Vote();
        Random random = new Random();
        ArrayList<String> etiquettesPossibles = new ArrayList<>(Arrays.asList("Urgent","Information","Changement","Debat","Culture"));
        
        // Générer un nombre aléatoire de choix (entre 2 et 5 par exemple)
        int nombreDeChoix = 2 + random.nextInt(4);
        int nbEtiquettes = 1 + random.nextInt(3); // entre 1 et 3 etiquettes
        
        double totalPourcentage = 100.0;
        // Remplir lesChoix avec "a", "b", "c", ...
        for (int i = 0; i < nombreDeChoix; i++) {
        	double pourcentage;
            if (i == nombreDeChoix - 1) {
                // Assigner le reste au dernier choix pour garantir un total de 100%
                pourcentage = totalPourcentage;
            } else {
                pourcentage = Math.round(random.nextDouble() * totalPourcentage * 100.0) / 100.0;
                totalPourcentage -= pourcentage;
            }
            
            String nom = Character.toString((char) ('a' + i));
        	Choix c = new Choix(nom, pourcentage);
        	
            vote.lesChoix.add(c);
        }
    	int etiquetteChoisie = -1;
    	ArrayList<Integer> dejaChoisi = new ArrayList<>(Arrays.asList(-1)); //on met -1 dedans pour declencher le while directement
        for(int i =0;i<nbEtiquettes;i++) {
        	while(dejaChoisi.contains(etiquetteChoisie)) {
        		etiquetteChoisie = random.nextInt(5); //nombre entre 0 et 4 pour couvrir toutes les etiquettes de etiquettesPossibles
        	}
        	vote.etiquettes.add(etiquettesPossibles.get(etiquetteChoisie));
        	dejaChoisi.add(etiquetteChoisie);
        }

        // Générer des pourcentages aléatoires pour les choix

        vote.totalVotant = 10 + random.nextInt(491); //total de votant entre 10 et 500

        vote.estimBudj = 1000 + random.nextInt(9001); //budget entre 1000 et 10000
        
        vote.setChoixGagnant();
        
        vote.idVote=-1;
        return vote;
    }
    
    public void afficherVote() {
        System.out.println("Vote Details:");
        System.out.println("Total Votants: " + totalVotant);
        System.out.println("Estimation Budgetaire: " + estimBudj + " €");
        System.out.println("Etiquettes : ");
        for(int i =0;i<etiquettes.size();i++) {
        	System.out.println(etiquettes.get(i) + " ");
        }
        System.out.println("Choix et Pourcentages:");
        for (int i = 0; i < lesChoix.size(); i++) {
            System.out.println(lesChoix.get(i));
        }
    }
    
    public void updateBudget(int nvBudj) throws IOException {
    	if(idVote==-1) {
    		throw new IOException("Ce vote est généré aléatoirement, vous ne pouvez pas le modifier dans la BD");
    	}
        String baseUrl = "https://projets.iut-orsay.fr/saes3-vjacqu3/classePHP/rest/PUT.php";
        String urlString = baseUrl + "?table=Vote&idVote="+ idVote +"&evalBudget=" + nvBudj;
        /*idTemporaire apres on mettra -> */
        /*this.idVote à implémenter dans la classe mais pour l'instant l'api marche pas donc autant ne pas se faire chier*/; 
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("PUT");  // Utilisation correcte de la méthode PUT
        
        // Vérification du code de réponse HTTP pour du debug
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {  // Vérification du code 200 OK
            System.out.println("Budget modifié !");
        } else {
            System.out.println("Échec de la modification du budget, code : " + responseCode);
        }

        connection.disconnect();
    }

    
	public static void main(String[] args) {
		Vote v = creerRandom();
		v.afficherVote();
		System.out.println(v.nbVotantsGagnant());

	}
}
