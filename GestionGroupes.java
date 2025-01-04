package algos;

import java.util.ArrayList;

public class GestionGroupes {

	/*
	 *ATTENTION : De ce que j'ai lu dans le sujet : chacun doit être responsable d'un mode de decision (j'en ai codé deux j'ai lu trop tard)
	 *ce qui signifie : par exemple j'ai fait un truc pour maximiser le nombre de satisfaits : il faut que je fasse plusieurs algos gloutons avec differents criteres glouton + un force brute 
	 *et ça sera le cas peut importe le mode de decision que vous prendrez, on peut en faire plus donc par exemple vous pouvez laisser mon truc de diversité et faire chacun un mode de decision 
	 *et ça nous fera du bonus je pense. En tout cas important :  ! faites plusieurs algos pour le meme mode de decision !
	 * 
	 */
	public static ArrayList<Vote> maxSatisfaits(Groupe g){
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
				
				System.out.println("final : " + getSatisfactionMoyenne(votesChoisis)); //Debug
				System.out.println("final : " + getSumBudget(votesChoisis));
				
				return votesChoisis;
	}
	public static ArrayList<Vote> maxNbSatisfaitsSeuil(Groupe g, double seuil){
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
				
				System.out.println("final : " + getSatisfactionMoyenne(votesSatisfaisants)); //Debug
				System.out.println("final : " + getSumBudget(votesSatisfaisants));
				
				return votesSatisfaisants;
	}
	public static ArrayList<Vote> maxNbSatisfaits(Groupe g) {
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
		choixCas1.addAll(maxNbSatisfaits(gCopie));
		
		//----Cas 2 
		//si le premier vote rentre dans le budget alors choixCas2.add(premier vote)
		//et choixCas2.add(maxNbSatisfaits(groupe - le premier vote))
		//s'il rentre pas on relance aussi sans le premier vote juste y'aura pas le premier vote avant
		if(premierVote.estimBudj<g.BudgetAlloue) {
			choixCas2.add(premierVote);
			gCopie.BudgetAlloue-=premierVote.estimBudj;
			choixCas2.addAll(maxNbSatisfaits(gCopie));
		}else {
			choixCas2.addAll(maxNbSatisfaits(gCopie));
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
	
	public static ArrayList<Vote> minimiserBudget(Groupe g, double seuil){
		/*
		 * A.Sandoz
		 * Algo glouton minimisant le budget tout en gardant une satisfaction moyenne 
		 * au dessus d'un seuil en pourcentage donné en paramètre
		 *  
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 * 
		 * la moyenne de satisfaction est le critère prioriataire : si un vote a le budget minimum mais n'est pas au dessus du seuil il sera ignoré
		 */
		
		ArrayList<Vote> selecGlouton = new ArrayList<Vote>();
		ArrayList<Vote> listeVote = new ArrayList<Vote>(g.getListeVote());
		
		while(listeVote.size() > 0){	//tant que la liste n'est pas vide
			int idMinVote = idMinBudget(listeVote); //On récupère le vote au budget minimum
			
			if(testSatisfactionMoyenne(selecGlouton,listeVote.get(idMinVote), seuil)) //On verifie que la moyenne reste au dessus du seuil (critère prioritaire)
			{
				selecGlouton.add(listeVote.get(idMinVote)); //Si oui, on ajoute le vote à la liste
			}
			
			listeVote.remove(idMinVote); //On retire le vote testé
		}
		
		System.out.println("final : " + getSatisfactionMoyenne(selecGlouton)); //Debug
		System.out.println("final : " + getSumBudget(selecGlouton));
		
		return selecGlouton;
	}
	
	public static int idMinBudget(ArrayList<Vote> listeVote) {
		/*
		 * A.Sandoz
		 */
		int idMin = 0;
		
		for(int i=0; i < listeVote.size(); i++) {
			if(listeVote.get(i).estimBudj < listeVote.get(idMin).estimBudj) //On prend la proposition au budget minimum
			{ 
				idMin = i;
			}
		}
		
		return idMin;
	}
	
	public static int idMaxSatisfaction(ArrayList<Vote> listeVote) {
		/*
		 * A.Sandoz
		 */
		int max = 0;
		for(int i=0; i < listeVote.size(); i++){
			if(listeVote.get(i).choixGagnant.getPourcentage() > listeVote.get(max).choixGagnant.getPourcentage()) //On prend la proposition à la satisfaction maximum (celle avec le plus grand pourcentage de vote gagnant)
			{
				max = i;
			}
		}
		
		return max;
	}
	
	public static boolean testSatisfactionMoyenne(ArrayList<Vote> listeVote, Vote test, double seuil) {
		/*
		 * A.Sandoz
		 */
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		moyenne += test.choixGagnant.getPourcentage();
		return	(moyenne/(listeVote.size() + 1)) >= seuil;
	}
	
	public static double getSatisfactionMoyenne(ArrayList<Vote> listeVote) {
		/*
		 * A.Sandoz
		 */
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		
		return	moyenne/listeVote.size();
	}
	
	public static int getSumBudget(ArrayList<Vote> listeVote) {
		/*
		 * A.Sandoz
		 */
		int sum = 0;
		for(Vote vote : listeVote){
			sum += vote.estimBudj;
		}
		
		return	sum;
	}
	
	public static ArrayList<Vote> choisirDiversite(Groupe g) {
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

	public static void main(String[] args) {
		/*
		 * J.Ava
		 */
		Groupe g = new Groupe();
		for (int i=0; i <g.votes.size();i++) {
			System.out.println(i);
			g.votes.get(i).afficherVote();
		}
		System.out.println("Budget : " + g.BudgetAlloue + " €");
		
		System.out.println("-----------------------------------");
		
		ArrayList<Vote> a = new ArrayList<>();
		a = maxNbSatisfaitsSeuil(g,80);
		//a = maxNbSatisfaits(g);
		int totalSatis = 0;
		int totalVotes = 0;
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
			totalSatis+= a.get(i).nbVotantsGagnant();
			totalVotes+=a.get(i).totalVotant;
		}
		System.out.println("total Votes satisfaits : " + totalSatis);
		System.out.println("total Votants votes choisis : " + totalVotes);
	}
}
