package algos;

import java.util.ArrayList;

public class MaxNbVotesSatisfaits {
	/*
	 * author : A.Baco
	 */
	public static ArrayList<Vote> glouton(Groupe g){
		/*
		 * A.Baco
		 * Glouton :
		 * Algorithme extremement primaire : prends des votes jusque à ne plus pouvoir vis à vis du budget 
		 */
		ArrayList<Vote> votesChoisis = new ArrayList<>();
		ArrayList<Vote> listeVote = new ArrayList<Vote>(g.getListeVote());
		int budgetRestant = g.BudgetAlloue; //copie pour pouvoire suivre l'evolution du budget
				while(listeVote.size() > 0){	
					Vote v = listeVote.remove(0);
					if(budgetRestant-v.estimBudj>=0) { //si la voix gagnante est au dessus du seuil et que le vote rentre dans le budj
						votesChoisis.add(v);//On l'ajoute au result
						budgetRestant-=v.estimBudj; //on update le budj restant
					}
				}
				
				System.out.println("final : " + MinBudget.getSatisfactionMoyenne(votesChoisis)); //Debug
				System.out.println("final : " + MinBudget.getSumBudget(votesChoisis));
				
				return votesChoisis;
	}
	public static ArrayList<Vote> gloutonSeuil(Groupe g, double seuil){
		/*
		 * A.Baco
		 * Glouton :
		 * Selectionne des votes sans regard vis à vis du budget en vérifiant simplement que la proposition gagnante du vote
		 * dépasse le seuil donné dans la fonction
		 */
		ArrayList<Vote> votesSatisfaisants = new ArrayList<>();
		ArrayList<Vote> listeVote = new ArrayList<Vote>(g.getListeVote());
		int budgetRestant = g.BudgetAlloue; //copie pour pouvoire suivre l'evolution du budget
				while(listeVote.size() > 0){	
					Vote v = listeVote.remove(0);
					if(v.choixGagnant.getPourcentage()>=seuil && budgetRestant-v.estimBudj>=0) { //si la voix gagnante est au dessus du seuil et que le vote rentre dans le budj
						votesSatisfaisants.add(v);//On l'ajoute au result
						budgetRestant-=v.estimBudj; //on update le budj restant
					}
				}
				
				System.out.println("final : " + MinBudget.getSatisfactionMoyenne(votesSatisfaisants)); //Debug
				System.out.println("final : " + MinBudget.getSumBudget(votesSatisfaisants));
				
				return votesSatisfaisants;
	}
	public static ArrayList<Vote> bruteForce(Groupe g) {
		/*
		 * A.Baco
		 * Force Brute : 
		 * Maximise le nombre de votes satisfaits tout en respectant la contrainte de budget
		 */
		
	    // Condition d'arrêt : groupe vide ou budget épuisé
		if(g.votes.isEmpty() || g.votes.size()<=0 || g.BudgetAlloue<=0) {
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
		choixCas1.addAll(bruteForce(gCopie));
		
		//----Cas 2 
		//si le premier vote rentre dans le budget alors choixCas2.add(premier vote)
		//et choixCas2.add(maxNbSatisfaits(groupe - le premier vote))
		//s'il rentre pas on relance aussi sans le premier vote juste y'aura pas le premier vote avant
		if(premierVote.estimBudj<g.BudgetAlloue) {
			choixCas2.add(premierVote);
			gCopie.BudgetAlloue-=premierVote.estimBudj;
			choixCas2.addAll(bruteForce(gCopie));
		}else {
			choixCas2.addAll(bruteForce(gCopie));
		}
		
		//----Comparaison des deux 
		//on a deux groupe : choixCas1 et choixCas2
		//on return le cas1 ou 2 en fonction de celui qui a le plus grand nombre de satisfaits
		int totalSatis1=0;
		int totalSatis2=0;
		for(Vote v : choixCas1) {
			if(v.choixGagnant == null) {
				v.setChoixGagnant();
			}
			totalSatis1+=v.nbVotantsGagnant();
		}
		for(Vote v : choixCas2) {
			if(v.choixGagnant == null) {
				v.setChoixGagnant();
			}
			totalSatis2+=v.nbVotantsGagnant();
		

		}
		if(totalSatis1>totalSatis2) {
			return choixCas1;
		}else {
			return choixCas2;
		}
		
	}
}
