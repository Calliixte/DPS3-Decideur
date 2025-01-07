package algos;

import java.util.ArrayList;
/*
 * Auteur de ce mode de décision : A.Baco
 */
public class Diversite {
	public static ArrayList<Vote> glouton(Groupe g) {
		/*
		 * Arguments : 
		 * 		- g -> un groupe sur lequel on veut réaliser ce traitement
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction : 
		 * 		- Algorithme glouton -> Cherche dans les votes du groupe un vote qui contient un sujet pas encore traité même si d'autres sujets sont déja priset le choisi si jamais il rentre dans le budget
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
	public static ArrayList<Vote> gloutonExclusif(Groupe g){
		/*
		 * Arguments : 
		 * 		- g -> un groupe sur lequel on veut réaliser ce traitement
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 * 		- Algorithme glouton qui cherche dans les votes un vote dont aucune des étiquettes n'a été utilisée auparavant
		 * 	et le rajoute à la liste des votes à mettre en place pour peu qu'il respecte le budget
		 */
		int budj = g.BudgetAlloue;
		ArrayList<String> etiquettesPrises = new ArrayList<>();
		ArrayList<Vote> suggeres = new ArrayList<>();
		for (Vote vote : g.votes) {
			if (budj-vote.estimBudj<0) { //si le vote coute trop cher par rapport a notre budget c'est tchao
				continue;
			}
			
			//on cherche si le vote a un theme qui n'a pas encore été abordé, si ce n'est pas le cas on le traite pas
			boolean etiquetteDejaUtilisee = false;
			for(int i=0;i<vote.etiquettes.size();i++) {
				if(etiquettesPrises.contains(vote.etiquettes.get(i))){
					etiquetteDejaUtilisee=true;
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
	public static ArrayList<Vote> bruteForceExclusif(Groupe g){
		/*
		 * Fonction d'overloarding de bruteForce exclusif pour toujours permettre de l'appeller avec simplement un groupe en parametres
		 */
		return bruteForceExclusif(g,new ArrayList<String>());
	}
	public static ArrayList<Vote> bruteForceExclusif(Groupe g,ArrayList<String> etiquettesPrises){
		/*
		 * Arguments : 
		 * 		- g -> un groupe sur lequel on veut réaliser ce traitement
		 * 		- etiquettesPrises -> une liste d'étiquettes déja traitées par l'algorithme
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 *  	- Brute force :
		 * Couvre le plus de sujets possibles avec le moins de votes possibles, permet de minimiser le budget alloué tout en traitant beaucoup de sujets
		 * Utilise une liste d'étiquettes mais grace a l'overloading il est possible de l'appeller avec uniqument un groupe,
		 * on peut par conséquent lui passer en second paramètre à l'appel de la fonction une liste de sujet qu'on ne souhaite pas traiter
		 */
		if(g.votes.isEmpty() || g.votes.size()<=0 /*Check la meme chose normalement mais c'est pour au cas ou*/|| g.BudgetAlloue<=0) {
			return new ArrayList<Vote>();
		}
		ArrayList<Vote> choixCas1 = new ArrayList<>();
		ArrayList<Vote> choixCas2 = new ArrayList<>();
		Groupe gCopie = new Groupe(g);
		//on prend le groupe de base et vraiment avec les pointeurs ça risque de faire de la merde donc faut en faire au moins une copie 
		
		Vote premierVote = gCopie.votes.remove(0);
		
		//----Cas 1
		//on pop le premier vote no matter what 
		//on relance l'algo avec ce truc
		choixCas1.addAll(bruteForceExclusif(new Groupe(gCopie),new ArrayList<>(etiquettesPrises)));
		
		//----Cas 2 
		//si le premier vote rentre dans le budget alors choixCas2.add(premier vote)
		//et choixCas2.add(maxNbSatisfaits(groupe - le premier vote))
		//s'il rentre pas on relance aussi sans le premier vote juste y'aura pas le premier vote avant
		
		boolean etiquetteDejaUtilisee = false;
		for(int i=0;i<premierVote.etiquettes.size();i++) {
			if(etiquettesPrises.contains(premierVote.etiquettes.get(i))){
				etiquetteDejaUtilisee=true;
			}
		}
		
		if(!etiquetteDejaUtilisee && premierVote.estimBudj<g.BudgetAlloue) {
			choixCas2.add(premierVote);
			Groupe gCopieCas2 = new Groupe(gCopie);
			gCopieCas2.BudgetAlloue -= premierVote.estimBudj;
			ArrayList<String> nouvellesEtiquettesPrises = new ArrayList<>(etiquettesPrises);
			for (String s : premierVote.etiquettes) {
			    nouvellesEtiquettesPrises.add(s);
			}
			choixCas2.addAll(bruteForceExclusif(gCopieCas2, nouvellesEtiquettesPrises));
		}else {
			choixCas2.addAll(bruteForceExclusif(new Groupe(gCopie),new ArrayList<>(etiquettesPrises)));
		}
		int nbEtiChx1=0;
		int nbEtiChx2=0;
		for(Vote v : choixCas1) {
			if(v.choixGagnant == null) {
				v.setChoixGagnant();
			}
			for(String eti : v.etiquettes) {
				nbEtiChx1++;
			}
		}
		for(Vote v : choixCas2) {
			if(v.choixGagnant == null) {
				v.setChoixGagnant();
			}
			for(String eti : v.etiquettes) {
				nbEtiChx2++;
			}
		}
		if(nbEtiChx1>nbEtiChx2) {
			return choixCas1;
		}else {
			return choixCas2;
		}
	}
}
