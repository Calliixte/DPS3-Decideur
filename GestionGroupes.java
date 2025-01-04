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
	
	
	public static ArrayList<Vote> maxNbSatisfaits(Groupe g) {
		//du coup la flm mais je décris le fonctionnement avec des commentaires
		
	    // Condition d'arrêt : groupe vide ou budget épuisé
	    // si (groupe.isEmpty() || budget <= 0)
	    //    return new ArrayList<>();
		ArrayList<Vote> choixCas1 = new ArrayList<>();
		ArrayList<Vote> choixCas2 = new ArrayList<>();
		//on prend le groupe de base et vraiment avec les pointeurs ça risque de faire de la merde donc faut en faire au moins une copie 
		
		//----Cas 1 
		//on pop le premier vote no matter what 
		//on relance l'algo avec ce truc 
		//et choixCas1.add(maxNbSatisfaits(groupe - le premier vote))
		
		//----Cas 2 
		//si le premier vote rentre dans le budget alors choixCas2.add(premier vote)
		//et choixCas2.add(maxNbSatisfaits(groupe - le premier vote))
		//s'il rentre pas on relance aussi sans le premier vote juste y'aura pas le premier vote avant
		
		//----Comparaison des deux 
		//on a deux arrayList : choixCas1 et choixCas2
		//on aura fait une fonction : ArrayList<Vote>.totalNbSatisfaitsMajoritaire()
		//on return le cas1 ou 2 en fonction de celui qui a le plus grand nombre de satisfaits
		
		
		return null;
	}
	/*
	 * Potentiellement faire une version glouton de maxNbSatisfaits
	 */

	
	public static ArrayList<Vote> satisfactionParSeuil(Groupe g, double seuil){
		/*
		 * Algo glouton gardant une satisfaction moyenne au dessus d'un seuil en pourcentage donné en paramètre
		 * tout en minimisant le budget
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 * 
		 * la moyenne de satisfaction est le critère prioriataire : si un vote a le budget minimum mais n'est pas au dessus du seuil il sera ignoré
		 */
		
		ArrayList<Vote> selecGlouton = new ArrayList<Vote>();
		ArrayList<Vote> listeVote = g.getListeVote();
		while(listeVote.size() > 0){
			int idMinVote = idMinBudget(listeVote);
			
			if(testSatisfactionMoyenne(selecGlouton,listeVote.get(idMinVote), seuil)) //On verifie que la moyenne reste au dessus du seuil (critère prioritaire)
			{
				System.out.println("accepté");
				selecGlouton.add(listeVote.get(idMinVote));
			}
			
			listeVote.remove(idMinVote);
		}
		
		System.out.println("final : " + getSatisfactionMoyenne(selecGlouton));
		System.out.println("final : " + getSumBudget(selecGlouton));
		
		return selecGlouton;
	}
	
	public static ArrayList<Vote> minimiserBudget(Groupe g, double seuil){
		/*
		 * Algo glouton minimisant le budget tout en gardant une satisfaction moyenne 
		 * au dessus d'un seuil en pourcentage donné en paramètre
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 * 
		 * le budget est le critère prioriataire : si un vote est au dessus du seuil mais n'a pas le budget minimum il sera ignoré
		 */
		
		ArrayList<Vote> selecGlouton = new ArrayList<Vote>();
		ArrayList<Vote> listeVote = g.getListeVote();
		
		while(listeVote.size() > 0){
			int idMaxSat = -1;
			
			for(int i=0; i < listeVote.size(); i++) {
				if(testSatisfactionMoyenne(selecGlouton,listeVote.get(i), seuil)) //On prend la première proposition qui reste au dessus du seuil
				{ 
					idMaxSat = i;
					break;
				}
			}
			
			if(idMaxSat == -1) {
				idMaxSat = idMaxSatisfaction(listeVote);
			}
			
			double minBudget = listeVote.get(idMinBudget(listeVote)).estimBudj;
			
			if(listeVote.get(idMaxSat).estimBudj == minBudget) //On vérifie que c'est bien le budget minimum (critère prioritaire)
			{
				System.out.println("accepté");
				selecGlouton.add(listeVote.get(idMaxSat));
			}
			
			listeVote.remove(idMaxSat);
		}
		
		System.out.println("final : " + getSatisfactionMoyenne(selecGlouton));
		System.out.println("final : " + getSumBudget(selecGlouton));
		
		return selecGlouton;
	}
	
	public static int idMinBudget(ArrayList<Vote> listeVote) {
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
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		moyenne += test.choixGagnant.getPourcentage();
		return	(moyenne/(listeVote.size() + 1)) >= seuil;
	}
	
	public static double getSatisfactionMoyenne(ArrayList<Vote> listeVote) {
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		
		return	moyenne/listeVote.size();
	}
	
	public static int getSumBudget(ArrayList<Vote> listeVote) {
		int sum = 0;
		for(Vote vote : listeVote){
			sum += vote.estimBudj;
		}
		
		return	sum;
	}
	
	public static ArrayList<Vote> choisirDiversite(Groupe g) {
		/* Alice
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
		System.out.println("testAppliGit");
		Groupe g = new Groupe();
		for (int i=0; i <g.votes.size();i++) {
			System.out.println(i);
			g.votes.get(i).afficherVote();
		}
		System.out.println("Budget : " + g.BudgetAlloue + " €");
		
		System.out.println("-----------------------------------");
		
		ArrayList<Vote> a = new ArrayList<>();
		a = minimiserBudget(g,80);
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
		}

	}

}
