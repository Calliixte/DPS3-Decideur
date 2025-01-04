package algos;

public class Choix {
	private String nom;
	private double pourcentage;
	
	public Choix(String nom, double pourcentage) {
		this.nom = nom;
		this.pourcentage = pourcentage;
	}
	
	public String getNom(){
		return nom;
	}
	
	public double getPourcentage() {
		return pourcentage;
	}
	
	public String toString() {
		return nom + " : " + pourcentage + "%";
	}
}
