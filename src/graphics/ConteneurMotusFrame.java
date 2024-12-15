package graphics;

import java.awt.Color;
import controllers.Score;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import controllers.Dictionnary;
import controllers.PropositionInvalideException;

@SuppressWarnings("serial")
public class ConteneurMotusFrame extends JPanel {

    private JLabel etiquette1;
    private JLabel etiquette2;
    private JLabel chronoLabel;
    public static Score score;

    private JButton bouton1;
    private JButton bouton2;

    private JTextField text;
    private JTextField[][] grilleCases;

    private Timer chronoTimer;
    private int tempsRestant = 180; // Temps restant initial en secondes

    private String motADeviner;
    private Dictionnary dictionnaire;
    public String pseudo;

    private int currentRow = 0;

    private String niveauDifficulte;
    
    private MotusFrame motusFrame; 

    Color couleur1 = new Color(0, 0, 0);
    

    public ConteneurMotusFrame(MotusFrame motusFrame, int tailleMot, String niveauDifficulte, String pseudo) {
        super();
        this.motusFrame = motusFrame;
        this.niveauDifficulte = niveauDifficulte;
        this.proprietesConteneur(tailleMot);
        this.pseudo = pseudo;
        this.score = new Score(pseudo);

        initChrono();
        
    }

    private void proprietesConteneur(int tailleMot) {
        try {
            dictionnaire = new Dictionnary("./Doc.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(null);
        this.proprietesEtiquette();
        this.proprietesBouton();
        this.propText();
        initGrilleCases(tailleMot);
    }

    private void proprietesEtiquette() {
        etiquette1 = new JLabel();
        this.etiquette1.setBounds(720, 30, 220, 20);
        this.etiquette1.setText("Votre proposition :");
        this.add(etiquette1);
        etiquette2 = new JLabel();
        this.etiquette2.setBounds(720, 120, 220, 20);
        this.etiquette2.setText("Temps restant :");
        this.add(etiquette2);

        chronoLabel = new JLabel();
        this.chronoLabel.setBounds(710, 165, 220, 40);
        this.chronoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        this.chronoLabel.setHorizontalAlignment(JLabel.CENTER);
        updateChronoLabel();
        this.add(chronoLabel);
    }

    private void updateChronoLabel() {
        int minutes = tempsRestant / 60;
        int seconds = tempsRestant % 60;
        chronoLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void proprietesBouton() {
        bouton1 = new JButton();
        this.bouton1.setText("Recommencer");
        this.bouton1.setBounds(720, 260, 190, 30);
        this.bouton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        this.add(bouton1);

        bouton2 = new JButton();
        this.bouton2.setText("Quitter");
        this.bouton2.setBounds(720, 300, 190, 30);
        this.bouton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(bouton2);
    }

    private void propText() {
        text = new JTextField();
        this.text.setBounds(720, 70, 190, 30);
        this.text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPlayerInput();
            }
        });
        this.add(text);
    }

    private void initGrilleCases(int longueurMot) {
        motADeviner = dictionnaire.getMotADeviner(longueurMot);
        System.out.println(motADeviner);

        int nbLignes;

        switch (niveauDifficulte) {
            case "Facile":
                nbLignes = 7;
                break;
            case "Moyen":
                nbLignes = 5;
                break;
            case "Difficile":
                nbLignes = 3;
                break;
            default:
                nbLignes = 7; 
                break;
        }

        grilleCases = new JTextField[nbLignes][longueurMot];
        int cellSize = 47;
        int cellSpacing = 2;

        for (int i = 0; i < grilleCases.length; i++) {
            for (int j = 0; j < grilleCases[i].length; j++) {
                grilleCases[i][j] = new JTextField();
                grilleCases[i][j].setBounds(10 + j * (cellSize + cellSpacing), 10 + i * (cellSize + cellSpacing),
                        cellSize, cellSize);
                grilleCases[i][j].setHorizontalAlignment(JTextField.CENTER);
                grilleCases[i][j].setEditable(false);
                grilleCases[i][j].setBackground(Color.WHITE);
                grilleCases[i][j].setFont(new Font("Arial", Font.BOLD, 12));
                this.add(grilleCases[i][j]);
            }
        }
        grilleCases[0][0].setText(String.valueOf(motADeviner.charAt(0)));
        this.add(grilleCases[0][0]);
        Random random = new Random();
        int indiceAleatoire = random.nextInt(1,motADeviner.length());
        grilleCases[0][indiceAleatoire].setText(String.valueOf(motADeviner.charAt(indiceAleatoire)));
        this.add(grilleCases[0][indiceAleatoire]);
  
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
    }

    private void initChrono() {
        chronoTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempsRestant--;

                if (tempsRestant <= 0) {
                    chronoTimer.stop();
                    showGameOverDialog();
                } else {
                    updateChronoLabel();
                }
            }
        });

        chronoTimer.start();
    }

    private void showGameOverDialog() {
        JOptionPane.showMessageDialog(this, "Le temps est écoulé. La partie est terminée.", "Fin du jeu",
                JOptionPane.INFORMATION_MESSAGE);
        displayScore(pseudo);

        int choice = JOptionPane.showOptionDialog(this, "Voulez-vous recommencer une nouvelle partie ?", "Nouveau tour",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }
    private static void displayScore(String pseudo) {
    	
    	JOptionPane.showMessageDialog(null, pseudo + " votre score est : " + score.getScoreFromPlayerName(pseudo) + ".", "Score",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String getMotADeviner() {
        return motADeviner;
    }

    private void startNewGame() {
        tempsRestant = 180;
        updateChronoLabel();
        chronoTimer.start();

        String nouveauMot = dictionnaire.getMotADeviner(grilleCases[0].length);

        for (JTextField[] row : grilleCases) {
            for (JTextField cell : row) {
                this.remove(cell);
            }
        }
        currentRow = 0;
        initGrilleCases(nouveauMot.length());
        
        revalidate();
        repaint();
        processPlayerInput();
    }

    private void processPlayerInput() {
        String input = text.getText().toLowerCase();
        text.setText("");

        try {
        	validerProposition(input);
            updateGrilleCases(input);   
        } catch (PropositionInvalideException e) {
            afficherMessageErreur(e.getMessage());
        }
        currentRow++;

        if (currentRow == grilleCases.length) {
            JOptionPane.showMessageDialog(this,
                    "Vous avez épuisé toutes les tentatives. Le mot à deviner était : " + motADeviner,
                    "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
            displayScore(pseudo);
            int choice = JOptionPane.showOptionDialog(this, "Voulez-vous recommencer une nouvelle partie ?", "Nouveau tour",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (choice == JOptionPane.YES_OPTION) {
            	int choice2 = JOptionPane.showOptionDialog(this, "Voulez-vous garder la même taille du mot et le même niveau de difficulté ?", "Nouveau tour",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            	if (choice2 == JOptionPane.YES_OPTION) {
            		startNewGame();
            	}
            	else {
            		motusFrame.dispose();
            		ChoixFenetre choixFenetre = new ChoixFenetre("Choix");
            		choixFenetre.setVisible(true);
            	}
            } else {
                System.exit(0);
            }
        }
        if (input.equals(motADeviner)) {
        	score.incrementScore();
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez deviné le mot.", "Victoire",
                    JOptionPane.INFORMATION_MESSAGE);
            displayScore(pseudo);
            int choice = JOptionPane.showOptionDialog(this, "Voulez-vous recommencer une nouvelle partie ?", "Nouveau tour",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (choice == JOptionPane.YES_OPTION) {
            	motusFrame.dispose();
            	ChoixFenetre choixFenetre = new ChoixFenetre("Choix");
            	choixFenetre.setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }

    public void validerProposition(String proposition) throws PropositionInvalideException {
        if (currentRow == 0 && proposition.isEmpty()) {
            return;
        }
        if (proposition.length() != grilleCases[0].length) {
            throw new PropositionInvalideException("Veuillez donner une proposition qui contient " + grilleCases[0].length + " caractéres.");
        }

        if (!proposition.matches("[a-zA-Z]+")) {
            throw new PropositionInvalideException("La proposition ne doit contenir que des lettres.");
        }
        

        if (grilleCases[0][0].getText().charAt(0) != proposition.charAt(0)) {
            throw new PropositionInvalideException("La proposition doit commencer avec la lettre : " + grilleCases[0][0].getText().charAt(0));
        }
    }

    private void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur de proposition", JOptionPane.ERROR_MESSAGE);
    }
    
    private void updateGrilleCases(String input) {
        if (currentRow < grilleCases.length) {
            String mot = getMotADeviner();
            Map<Character, Integer> lettreOccurrenceMot = new HashMap<>();
            Map<Character, Integer> lettreOccurrenceProposition = new HashMap<>();

            // les occurrences des lettres dans le mot à deviner
            for (char lettre : mot.toCharArray()) {
                lettreOccurrenceMot.put(lettre, lettreOccurrenceMot.getOrDefault(lettre, 0) + 1);
            }
            
            for (int j = 0; j < grilleCases[currentRow].length && j < input.length(); j++) {
                char lettreProposition = input.charAt(j);
                char lettreMot = mot.charAt(j);

                grilleCases[currentRow][j].setText(String.valueOf(lettreProposition));
                grilleCases[currentRow][j].setFont(new Font("Arial", Font.BOLD, 20));

                if (lettreProposition == lettreMot) {
                	//score.incrementScore();
                    grilleCases[currentRow][j].setBackground(Color.GREEN);
                    lettreOccurrenceMot.put(lettreMot, lettreOccurrenceMot.get(lettreMot) - 1);
                    //gestionScore.enregistrerScore();
                } else if (lettreOccurrenceMot.containsKey(lettreProposition) && lettreOccurrenceMot.get(lettreProposition) > 0) {
                    grilleCases[currentRow][j].setBackground(Color.YELLOW);
                    lettreOccurrenceMot.put(lettreProposition, lettreOccurrenceMot.get(lettreProposition) - 1);
                } else {
                    grilleCases[currentRow][j].setBackground(Color.RED);
                }

                // Mettre à jour les occurrences pour la proposition
                lettreOccurrenceProposition.put(lettreProposition, lettreOccurrenceProposition.getOrDefault(lettreProposition, 0) + 1);

                // Gérer les lettres supplémentaires directement après avoir traité la lettre
                int occurrenceProposition = lettreOccurrenceProposition.get(lettreProposition);
                int occurrenceMot = lettreOccurrenceMot.getOrDefault(lettreProposition, 0);

                for (int i = 0; i < occurrenceProposition - occurrenceMot; i++) {
                    int k = 0;
                    while (k < grilleCases[currentRow].length && grilleCases[currentRow][k].getText().length() > 0) {
                        k++;
                    }

                    if (k < grilleCases[currentRow].length) {
                        grilleCases[currentRow][k].setText(String.valueOf(lettreProposition));
                        grilleCases[currentRow][k].setFont(new Font("Arial", Font.BOLD, 20));
                        grilleCases[currentRow][k].setBackground(Color.RED);
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(couleur1);
        g.drawRect(705, 10, 220, 340);
    }
}
