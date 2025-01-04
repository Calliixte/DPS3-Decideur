package algos;

import java.util.ArrayList;

public class Diversite {
	public static ArrayList<Vote> glouton(Groupe g) {
		/* A.Baco
		 * Algorithme glouton : Cherche dans les votes du groupe un vote qui contient un sujet pas encore traité et le choisi si jamais il rentre dans le budget
		 * aucune considération d'optimisation de budget ou de satisfaction n'est faite ici
		 */
		int budj = g.BudgetAlloue;
		ArrayList<String> etiquettesPrises = new ArrayList<>();
		ArrayList<Vote> suggeres = new ArrayList<>();
		for (Vote vote : g.votes) {
			if (budj-vote.estimBudj<0) { //si le vote coute trop cher par rapport a notre budget c'est tchao
				continue;
			}
			
			//on cherche si le vote a un theme qui n'a pas encore été abordé, si ce n'est pas le cas on le traite pas
			boolean etiquetteDejaUtilisee = true;
			for(int i=0;i<vote.etiquettes.size();i++) {
				if(!etiquettesPrises.contains(vote.etiquettes.get(i))){
					etiquetteDejaUtilisee=false;
				}
			}
			
			if(etiquetteDejaUtilisee) continue;
			
			for(int i=0;i<vote.etiquettes.size();i++) {
				etiquettesPrises.add(vote.etiquettes.get(i));
			}
			budj-=vote.estimBudj;
			suggeres.add(vote);
		}
		return suggeres;
	}
}
