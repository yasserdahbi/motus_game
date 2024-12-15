package graphics;

import javax.swing.JFrame;

import controllers.Score;

@SuppressWarnings("serial")
public class MotusFrame extends JFrame{
	
	ConteneurMotusFrame pan;
	public String title;
	public int tailleMot;
	public String niveauDifficulte;
	public String pseudo;
	public static Score score;
	public String motADeviner;
	
	public MotusFrame (String title, int tailleMot, String niveauDifficulte,String pseudo){
		super();
		this.title = title;
		this.tailleMot = tailleMot;
		this.niveauDifficulte = niveauDifficulte;
		this.pseudo = pseudo;
		proprietesFenetre();
	}
	
	@SuppressWarnings("unused")
	private void proprietesFenetre(){
		this.setTitle(title);
		this.setSize(940, 400); 
		this.setResizable(false);
		this.setLocationRelativeTo(null); //localiser la fenetre au milieu de l'ecran
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pan = new ConteneurMotusFrame(this,tailleMot, niveauDifficulte,pseudo);
		this.setContentPane(pan);
	}
	
}