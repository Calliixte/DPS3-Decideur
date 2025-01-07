package algos;

import java.util.ArrayList;
/*
 * Auteur de ce mode de décision : A.Sandoz
 */
public class MinBudget {
	
	public static ArrayList<Vote> minimiserBudgetBruteForce(ArrayList<Vote> listeVote, int seuil) {
		/*
		 * Arguments : 
		 * 		- listeVote -> la liste des votes d'un groupe sur lequel on veut réaliser ce traitement
		 * 		- seuil -> un entier qui definit le seuil de budget a ne pas depasser
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 * 		- Algo force brute sélectionnant le maximum de proposition 
		 * en gardant le budget en dessous d'un seuil donné en paramètre
		 */
		
		if(listeVote.size() <= 0) { //Cas de base : liste vide, on a testé toute la liste
			return new ArrayList<Vote>();
		}
		
		int idMin = idMinBudget(listeVote); //On récupère le vote au budget minimum
		
		ArrayList<Vote> copy = new ArrayList<Vote>(listeVote); //On copie la liste
		copy.remove(idMin); //On retire le vote testé
		
		ArrayList<Vote> choix1 = minimiserBudgetBruteForce(copy, seuil); //Choix 1 : on ne garde pas le vote
		ArrayList<Vote> choix2 = new ArrayList<Vote>(); //Choix 2 : on garde le vote
		
		
		// le Choix 2 étant le choix 1 avec le vote testé en plus, on peut utiliser choix 1 pour tester la condition
		// plutôt que de refaire un appel récursif
		if(testSumBudget(choix1,listeVote.get(idMin),seuil)) { //On vérifie si le deuxième choix respecte la condition
			choix2.add(listeVote.get(idMin)); //Si oui on ajoute le vote
			choix2.addAll(choix1); //Puis on ajoute le reste
		} else {
			return choix1; //Sinon on ignore ce vote
		}

		if(getSumBudget(choix1) > getSumBudget(choix2)) { //On sélectionne le meilleur des deux choix
			return choix1;
		} else {
			return choix2;
		}
	}
	
	public static ArrayList<Vote> minimiserBudgetGlouton(Groupe g, int seuil){
		/*
		 * Arguments : 
		 * 		- g -> un groupe sur lequel on veut réaliser ce traitement
		 * 		- seuil -> un entier qui definit le seuil de budget a ne pas depasser
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 * 		- Algo glouton sélectionnant le maximum de proposition 
		 * en gardant le budget en dessous d'un seuil donné en paramètre
		 */
		
		ArrayList<Vote> selecGlouton = new ArrayList<Vote>();
		ArrayList<Vote> listeVote = new ArrayList<Vote>(g.getListeVote());
		
		while(listeVote.size() > 0){	//tant que la liste n'est pas vide
			int idMinVote = idMinBudget(listeVote); //On récupère le vote au budget minimum
			
			if(testSumBudget(selecGlouton,listeVote.get(idMinVote),seuil)) //On verifie que le budget final reste en dessous du seuil
			{
				selecGlouton.add(listeVote.get(idMinVote)); //Si oui, on ajoute le vote à la liste
			}
			
			listeVote.remove(idMinVote); //On retire le vote testé
		}
		
		return selecGlouton;
	}
	
	public static ArrayList<Vote> satisfactionMoyenneGlouton(Groupe g, double seuil){
		/*
		 * Arguments : 
		 * 		- g -> un groupe sur lequel on veut réaliser ce traitement
		 * 		- seuil -> un pourcentage qui definit le seuil de satisfaction minimum voulu
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 * 		- Algo glouton minimisant le budget tout en gardant une satisfaction moyenne 
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
		
		return selecGlouton;
	}
	
	public static ArrayList<Vote> satisfactionMoyenneBruteForce(ArrayList<Vote> listeVote, double seuil) {
		/*
		 * Arguments : 
		 * 		- listeVote -> la liste des votes d'un groupe sur lequel on veut réaliser ce traitement
		 * 		- seuil -> un pourcentage qui definit le seuil de satisfaction minimum voulu
		 * Sortie : 
		 * 		- liste de Votes représentant les votes à choisir d'apres l'algorithme
		 * Contenu de la fonction :
		 * 		- Algo force brute minimisant le budget tout en gardant une satisfaction moyenne 
		 * au dessus d'un seuil en pourcentage donné en paramètre
		 *  
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 * 
		 * la moyenne de satisfaction est le critère prioriataire : si un vote a le budget minimum mais n'est pas au dessus du seuil il sera ignoré
		 */
		
		if(listeVote.size() <= 0) { //Cas de Base : Liste vide = on a parcouru toute la liste
			return new ArrayList<Vote>();
		}
		
		int idMin = idMinBudget(listeVote); //On récupère le vote au budget minimum
		
		ArrayList<Vote> copy = new ArrayList<Vote>(listeVote); //On fait une copie de la liste (méthode non destructive)
		copy.remove(idMin); //On retire le vote testé
		
		ArrayList<Vote> choix1 = satisfactionMoyenneBruteForce(copy, seuil); //Choix 1 : On ne garde pas le vote
		ArrayList<Vote> choix2 = new ArrayList<Vote>(); //Choix 2 : On garde le vote
		
		if(testSatisfactionMoyenne(choix1, listeVote.get(idMin), seuil)) { //On vérifie que la satisfaction moyenne du choix 2 est au dessus du seuil
			choix2.add(listeVote.get(idMin)); //Si oui on ajoute le vote
			choix2.addAll(choix1); //On ajoute le reste
		}else {
			return choix1; //Sinon on ignore ce vote
		}

		if(getSatisfactionMoyenne(choix1) > getSatisfactionMoyenne(choix2)) { //On regarde la meilleure moyenne de satisfaction entre les deux et on retourne la meilleure
			return choix1;
		} else {
			return choix2;
		}
	}
	
	
	public static int idMaxSatisfaction(ArrayList<Vote> listeVote) { //Jamais utilisé
		/*
		 * Arguments :
		 * 		-listeVote -> la liste des votes dont on veut recuperer l'emplacement de la satisfaction maximum 
		 * Sortie : 
		 * 		- un entier -> l'emplacement dans la liste de la satisfaction maximum
		 * Contenu de la fonction :
		 * 		- parcourt la liste passée en paramètre à la recherche de la proposition à la satisfaction maximum,
		 * renvoie sa position dans la liste lorsque elle est trouvée
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
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
	
	public static int idMinBudget(ArrayList<Vote> listeVote) {
		/*
		 * Arguments :
		 * 		-listeVote -> la liste des votes dont on veut recuperer l'emplacement du budget minimum
		 * Sortie : 
		 * 		- un entier -> l'emplacement dans la liste du budget minimum
		 * Contenu de la fonction :
		 * 		- parcourt la liste passée en paramètre à la recherche de la proposition au budget le plus bas,
		 * renvoie sa position dans la liste lorsque elle est trouvée
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
	
	public static boolean testSatisfactionMoyenne(ArrayList<Vote> listeVote, Vote test, double seuil) {
		/*
		 * Arguments : 
		 * 		- listeVote -> la liste actuelle des votes sélectionnés par un traitement
		 * 		- seuil -> un pourcentage qui definit le seuil de satisfaction minimum voulu
		 * 		- test -> un vote que l'on veut ajouter à la liste
		 * Sortie : 
		 * 		- boolean -> true sur listeVote + test restent sous le seuil, false sinon
		 * Contenu de la fonction :
		 * 		- Vérifie si la satisfaction moyenne de la liste restera sous le seuil donné en paramètre 
		 * si on y ajoute la proposition donnée en paramètre.
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 */
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		moyenne += test.choixGagnant.getPourcentage();
		return	(moyenne/(listeVote.size() + 1)) >= seuil; //On ajoute 1 car on ajoute une proposition à la liste, permet aussi d'éviter la division par zéros en cas de liste vide
	}
	
	public static double getSatisfactionMoyenne(ArrayList<Vote> listeVote) {
		/*
		 * Arguments : 
		 * 		- listeVote -> la liste dont on veut extraire la satisfaction moyenne
		 * Sortie : 
		 * 		- double -> la satisfaction moyenne
		 * Contenu de la fonction :
		 * 		- Renvoie la satisfaction moyenne d'une liste de votes
		 * 
		 * On définit la satisfaction comme le pourcentage de gens ayant voté pour le choix gagnant.
		 * On considère qu'en acceptant le vote dont le choix gagnant a été choisi par le plus de monde, 
		 * on maximisera le nombre de gens satisfait.
		 */
		double moyenne = 0;
		for(Vote vote : listeVote){
			moyenne += vote.choixGagnant.getPourcentage();
		}
		
		return	moyenne/listeVote.size();
	}
	
	public static boolean testSumBudget(ArrayList<Vote> listeVote, Vote test, int seuil) {
		/*
		 * Arguments : 
		 * 		- listeVote -> la liste actuelle des votes sélectionnés par un traitement
		 * 		- seuil -> un entier qui definit le seuil budgétaire a ne pas depasser
		 * 		- test -> un vote que l'on veut ajouter à la liste
		 * Sortie : 
		 * 		- boolean -> true sur le budget total de listeVote + le budget de test restent sous le seuil , false sinon
		 * Contenu de la fonction :
		 * 		- Vérifie si le budget total de la liste restera sous le seuil donné en paramètre 
		 * si on y ajoute la proposition donnée en paramètre.
		 */
		
		return	(getSumBudget(listeVote) + test.estimBudj) <= seuil;
	}
	
	public static int getSumBudget(ArrayList<Vote> listeVote) {
		/*
		 * Arguments : 
		 * 		- listeVote -> une liste d'objets vote dont on veut la somme de budgets
		 * Sortie : 
		 * 		- entier -> la somme du budget de la liste
		 * Contenu de la fonction :
		 * 		- parcourt la liste et ajoute à une somme totale le budget de chaque vote
		 */
		int sum = 0;
		for(Vote vote : listeVote){
			sum += vote.estimBudj;
		}
		
		return sum;
	}
}