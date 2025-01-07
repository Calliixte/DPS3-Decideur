package algos;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;

public class GestionGroupes {
	
	public static int lecturSecur() {
		Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {  // Vérifie si l'entrée est un entier
            System.out.println("Entrez un nombre, pas autre chose : ");
            scanner.next();  // Ignore l'entrée incorrecte
        }

        return scanner.nextInt();  
	}
	
	public static void afficherResultat(List<Vote> a) {
		System.out.println("-----------------------------------");
		int totalSatis = 0;
		int totalVotes = 0;
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
			totalSatis+= a.get(i).nbVotantsGagnant();
			totalVotes+=a.get(i).getTotalVotant();
			System.out.println("-----------------------------------");
		}
		if (totalVotes<=0) {
			System.out.println("Aucun vote ne correspond au seuil que vous avez choisi");
		}
		System.out.println("Nombre de votes dans les votes choisis : " + totalVotes);
		System.out.println("Nombre total de votes satisfaits/insatisfaits " + totalSatis+"/"+(totalVotes-totalSatis));


		System.out.println("-----------------------------------");
	}
	public static void afficherResultat(ArrayList<Vote> a) {
		System.out.println("-----------------------------------");
		int totalSatis = 0;
		int totalVotes = 0;
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
			totalSatis+= a.get(i).nbVotantsGagnant();
			totalVotes+=a.get(i).getTotalVotant();
			System.out.println("-----------------------------------");
		}
		if (totalVotes<=0) {
			System.out.println("Aucun vote ne correspond au seuil que vous avez choisi");
		}else {
		System.out.println("Nombre de votes dans les votes choisis : " + totalVotes);
		System.out.println("Nombre total de votes satisfaits/insatisfaits " + totalSatis+"/"+(totalVotes-totalSatis));
		}
		System.out.println("-----------------------------------");
	}
	public static void QCMMaxNbVotesSatisfaits(Groupe g) {
		boolean applicationTourne = true;
		while(applicationTourne) {
			System.out.println("Quel algorithme voulez vous tester ?");
			System.out.println("1 : Selection de votes jusque à épuisement du budget");
			System.out.println("2 : Selection des votes ayant un pourcentage de votes pour la proposition gagnante supérieure à un certain seuil");
			System.out.println("3 : Choix le plus précis du maximum de votes qu'il est possible de satisfaire dans le groupe");
			System.out.println("4 : Version alternative de l'algorithme 3, qui réalise le meme traitement (List au lieu de ArrayList)");
			System.out.println("Entrez 5 pour revenir au menu précédent");
			
			int choix = lecturSecur();

            switch (choix) {
                case 1:
                	afficherResultat(MaxNbVotesSatisfaits.glouton(g));
                    break;

                case 2:
                	System.out.println("Quel pourcentage minimum de votes voulez-vous qu'une proposition choisie ait ?");
                	int seuil = lecturSecur();
                	afficherResultat(MaxNbVotesSatisfaits.gloutonSeuil(g, seuil));
                    break;
                
                case 3:
                	afficherResultat(MaxNbVotesSatisfaits.bruteForce(g));
                	break;
                    
                case 4:
                	afficherResultat(MaxNbVotesSatisfaits.bruteForceList(g));
                	break;
                    
                case 5:
                    System.out.println("Affichage du menu précédent : ");
                    applicationTourne = false;
                    break;
                	

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
		}
	}
	public static void QCMMinbudget(Groupe g) {
		boolean applicationTourne = true;
		while(applicationTourne) {
			System.out.println("Quel algorithme voulez vous tester ?");
			System.out.println("1 : Selection du maximum de proposition en gardant le budget en dessous d'un seuil donné");
			System.out.println("2 : Minimiser le budget tout en gardant une satisfaction moyenne au dessus d'un seuil en pourcentage donné en paramètre");
			System.out.println("3 : Choix le plus précis sélectionnant le maximum de proposition en gardant le budget en dessous d'un seuil donné");
			System.out.println("4 : Choix le plus précis minimisant le budget tout en gardant une satisfaction moyenne au dessus d'un seuil en pourcentage donné en paramètre");
			System.out.println("Entrez 5 pour revenir au menu précédent");
			
			int seuil;
			int choix = lecturSecur();

            switch (choix) {
                case 1:
                	System.out.println("Quel budget maximum voulez vous ne pas dépasser ?");
                	seuil = lecturSecur();
                	afficherResultat(MinBudget.minimiserBudgetGlouton(g,seuil));
                    break;

                case 2:
                	System.out.println("Quelle satisfaction moyenne souhaitez vous qu'un groupe ait ?");
                	seuil = lecturSecur();
                	afficherResultat(MinBudget.satisfactionMoyenneGlouton(g, seuil));
                    break;
                
                case 3:
                	System.out.println("Quel budget maximum voulez vous ne pas dépasser ?");
                	seuil = lecturSecur();
                	afficherResultat(MinBudget.minimiserBudgetBruteForce(g.votes, seuil));
                    break;
                    
                case 4:
                	System.out.println("Quelle satisfaction moyenne souhaitez vous qu'un groupe ait ?");
                	seuil = lecturSecur();
                	afficherResultat(MinBudget.satisfactionMoyenneBruteForce(g.votes, seuil));
                    break;
                    
                case 5:
                    System.out.println("Affichage du menu précédent : ");
                    applicationTourne = false;
                    break;
                	

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
		}
	}
	public static void QCMDiversite(Groupe g) {
		boolean applicationTourne = true;
		while(applicationTourne) {
			System.out.println("Quel algorithme voulez vous tester ?");
			System.out.println("1 : Recherche de vote abordant un sujet pas encore traité");
			System.out.println("2 : Recherche de vote abordant exclusivement des sujets pas encore traités");
			System.out.println("3 : Recherche de vote abordant exclusivement des sujets pas encore traités tout en s'assurant de couvrir le plus de sujets possibles");
			System.out.println("Entrez 4 pour revenir au menu précédent");
			
			int seuil;
			int choix = lecturSecur();

            switch (choix) {
                case 1:
                	afficherResultat(Diversite.glouton(g));
                    break;

                case 2:
                	afficherResultat(Diversite.gloutonExclusif(g));
                    break;
                
                case 3:
                	System.out.println("Souhaitez vous exclure certains sujets de la recherche ? 1 pour oui, n'importe quel nombre pour non");
                	if(lecturSecur()==1) {
                		ArrayList<String> etiquettesAExclure = new ArrayList<>();
                		/*
                		 * Cette maniere de saisir les etiquettes n'est évidemment pas parfaite :
                		 * 		-elle n'est adaptée qu'aux groupes de test
                		 * 		-on peut saisir plusieurs fois la meme etiquette, ce qui est inutile
                		 * elle sert néanmoins a démontrer ce qui est possible avec l'overloading de Diversite.bruteForceExclusif()
                		 * 
                		 */
                		System.out.println("Les sujets disponibles sont les suivants : 1) Urgent, 2)Information, 3)Changement, 4)Debat, 5)Culture");
                		System.out.println("Entrez 6 pour arreter de saisir des sujets");
                		int choixSujet = -1;
                		while(choixSujet !=6) {
                			choixSujet = lecturSecur();
                			switch(choixSujet) { 
                				case 1:
                					etiquettesAExclure.add("Urgent");
                					System.out.println("Urgent sera exclu");
                					break;
                				case 2:
                					etiquettesAExclure.add("Information");
                					System.out.println("Information sera exclu");
                					break;
                				case 3:
                					etiquettesAExclure.add("Changement");
                					System.out.println("Changement sera exclu");
                					break;
                				case 4:
                					etiquettesAExclure.add("Debat");
                					System.out.println("Debat sera exclu");
                					break;
                				case 5:
                					etiquettesAExclure.add("Culture");
                					System.out.println("Culture sera exclu");
                					break;
                				case 6:
                					break;
                			}
                		}
                		afficherResultat(Diversite.bruteForceExclusif(g,etiquettesAExclure));
                	}
                	else {
                    	afficherResultat(Diversite.bruteForceExclusif(g));
                	}
                    break;
                    
                case 4:
                    System.out.println("Affichage du menu précédent : ");
                    applicationTourne = false;
                    break;

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
		}
	}
	public static void QCMModeTraitement(Groupe g) {
		boolean applicationTourne = true;
		while(applicationTourne) {
			System.out.println("Quel mode de traitement voulez vous tester ?");
			System.out.println("1 : Maximiser le nombre de votes satisfaits");
			System.out.println("2 : Minimiser le budget");
			System.out.println("3 : Avoir des choix diversifiés");
			System.out.println("Entrez 4 pour revenir au menu précédent");
			
			int choix = lecturSecur();

            switch (choix) {
                case 1:
                	QCMMaxNbVotesSatisfaits(g);
                    break;

                case 2:
                	QCMMinbudget(g);
                    break;
                
                case 3:
                	QCMDiversite(g);
                	break;
                    
                case 4:
                    System.out.println("Affichage du menu précédent : ");
                    applicationTourne = false;
                    break;

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
		}
	}
	public static void main(String[] args) {
		/*
		 * J.Ava
		 */
		System.out.println("Bienvenue dans le debug menu de l'application de Gestion decideur DPS3");
		System.out.println("Votre groupe créé aléatoirement pour le debug est le suivant : ");
		Groupe g = new Groupe(); //crée un groupe aléatoire
		g.afficherGroupe();
		boolean applicationTourne = true;
		while(applicationTourne) {
			System.out.println("Quelle action voulez vous executer ?");
			System.out.println("1 : Réafficher le groupe de test");
			System.out.println("2 : Tester un algorithme de traitement sur le groupe de test");
			System.out.println("3 : Ajuster le budget du groupe de test");
			System.out.println("4 : Quitter l'application");
	        int choix = lecturSecur();

            switch (choix) {
                case 1:
                	g.afficherGroupe();
                    break;

                case 2:
                    QCMModeTraitement(g);
                    break;

                case 3:
                	System.out.println("Entrez la modification de budget que vous voulez faire (nombre négatif pour baisser le budget");
                	System.out.println("Budget actuel : "+g.BudgetAlloue);
                	int changementBudg = lecturSecur();
                	g.BudgetAlloue = g.BudgetAlloue + changementBudg;
                	System.out.println("Le nouveau budget est : " + g.BudgetAlloue);
                	break;
                
                case 4:
                	System.out.println("Voulez vous vraiment quitter l'application ? Cela supprimera votre groupe de test actuel. Entrez 1 pour valider");
                	if(lecturSecur()==1) {
	                    System.out.println("Au revoir !");
	                    applicationTourne = false;
	                    break;
                	}else {
                		System.out.println("Retour à l'application");
                		break;
                	}

                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
		}
		
		/*
		ArrayList<Vote> a = new ArrayList<>();
		a = MaxNbVotesSatisfaits.gloutonSeuil(g,80);
		//a = MaxNbVotesSatisfaits.bruteForce(g);
		ArrayList<String> t = new ArrayList<>();
		a= Diversite.bruteForceExclusif(g,t);
		int totalSatis = 0;
		int totalVotes = 0;
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
			totalSatis+= a.get(i).nbVotantsGagnant();
			totalVotes+=a.get(i).getTotalVotant();
		}
		System.out.println("total Votes satisfaits : " + totalSatis);
		System.out.println("total Votants votes choisis : " + totalVotes);
		
		System.out.println("-----------------------------------");
		
		a = MinBudget.satisfactionMoyenneBruteForce(g.getListeVote(),60);
		
		System.out.println("brute force");
		System.out.println(MinBudget.getSatisfactionMoyenne(a));
		System.out.println(MinBudget.getSumBudget(a));
		
		a = MinBudget.satisfactionMoyenneGlouton(g,60);
		
		System.out.println("glouton");
		System.out.println(MinBudget.getSatisfactionMoyenne(a));
		System.out.println(MinBudget.getSumBudget(a));
		
		a = MinBudget.minimiserBudgetBruteForce(g.getListeVote(), 15000);
		
		System.out.println("minimiser brute force");
		System.out.println(MinBudget.getSatisfactionMoyenne(a));
		System.out.println(MinBudget.getSumBudget(a));
		
		a = MinBudget.minimiserBudgetGlouton(g, 15000);
		
		System.out.println("minimiser glouton");
		System.out.println(MinBudget.getSatisfactionMoyenne(a));
		System.out.println(MinBudget.getSumBudget(a));
		
		for(int i =0;i<a.size();i++) {
			a.get(i).afficherVote();
		}
		*/
	}
}
