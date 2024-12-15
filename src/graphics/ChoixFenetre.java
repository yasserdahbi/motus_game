package graphics;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChoixFenetre extends JFrame{
	ConteneurChoixFenetre pan;
	public String title;
	
	public ChoixFenetre (String title) {
		super();
		this.title = title;
		proprietesChoixFenetre();
	}
	
	@SuppressWarnings("unused")
	private void proprietesChoixFenetre() {
		this.setTitle(title);
		this.setSize(550, 260);
		this.setResizable(false);
		this.setLocationRelativeTo(null); 
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		pan = new ConteneurChoixFenetre();
		this.setContentPane(pan);
	}
	
}