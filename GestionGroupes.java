package algos;

import java.util.ArrayList;

public class GestionGroupes {

	/*
	 *	Hop tout le code a disparu dommage c'est le dieux du code qui sont passés (non je l'ai juste changé de fichier pour adapter au sujet)
	 * 
	 */


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
		a = MaxNbVotesSatisfaits.gloutonSeuil(g,80);
		//a = MaxNbVotesSatisfaits.bruteForce(g);
		ArrayList<String> t = new ArrayList<>();
		a= Diversite.bruteForceExclusif(g,t);
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
