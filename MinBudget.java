package algos;

import java.util.ArrayList;

public class MinBudget {
	/*
	 * author : A.Sandoz
	 */
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
}
