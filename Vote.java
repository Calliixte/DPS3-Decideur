package algos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import java.util.Random;

public class Vote {
	ArrayList<Choix> lesChoix;
	ArrayList<String> etiquettes;
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
        for (String key : j.keySet()) {
        	  Object value = j.get(key);
        	  System.out.println(key.substring(6));
        	  if(key.substring(6)=="listeReactions") {
        		  break;
        	  }
        }
        
//		ArrayList<JSONObject>listeBizarre = (ArrayList<JSONObject>) j.get(" Vote choixVote");
//		for(JSONObject a : listeBizarre) {
//            for (String key : a.keySet()) {
//          	  Object value = a.get(key);
//          	  System.out.println(key + ": " + value);
//          }
//		}
		
		
		
		}catch(IOException e) {}
	}
	
	public int nbVotantsMajoritaire() //renvoie le nombre de votants (approximativement) que réprésente la proposition majoritaire
	{
		double pourcentageMajoritaire = 0.0;
		for (Choix ch : lesChoix) {
			if(ch.getPourcentage() > pourcentageMajoritaire) {
				pourcentageMajoritaire=ch.getPourcentage();
			}
		}
		
		return (int) ((totalVotant / 100.0) * Math.round(pourcentageMajoritaire));
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
    
	public static void main(String[] args) {
		Vote v = creerRandom();
		v.afficherVote();
		System.out.println(v.nbVotantsMajoritaire());

	}
}
