package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ConteneurChoixFenetre extends JPanel {

    private JLabel etiquette;
    private JLabel etiquette2;
    private JLabel etiquettePseudo;
    private JTextField champPseudo;

    private JComboBox<Integer> choixTaille;
    private JComboBox<String> choixDifficulte;
    private static final String FACILE = "Facile";
    private static final String MOYEN = "Moyen";
    private static final String DIFFICILE = "Difficile";

    private JButton boutonCancel;
    private JButton boutonOK;


    public ConteneurChoixFenetre() {
        super();
        this.proprietesConteneurChoixFenetre();
    }

    private void proprietesConteneurChoixFenetre() {
        this.setLayout(null);
        this.proprietesEtiquette();
        this.proprietesBouton();
        
    }

    private void proprietesEtiquette() {

        etiquettePseudo = new JLabel();
        this.etiquettePseudo.setBounds(35, 10, 500, 20);
        this.etiquettePseudo.setText("Entrez votre pseudo :");
        this.add(etiquettePseudo);

        etiquette = new JLabel();
        this.etiquette.setBounds(35, 65, 500, 20);
        this.etiquette.setText("Choisir le nombre de caractères pour le mot à deviner :");
        this.add(etiquette);

        etiquette2 = new JLabel();
        this.etiquette2.setBounds(35, 120, 500, 20);
        this.etiquette2.setText("Choisir le niveau de difficulté :");
        this.add(etiquette2);
    }

    private void proprietesBouton() {

        champPseudo = new JTextField();
        this.champPseudo.setBounds(70, 35, 400, 20);
        this.add(champPseudo);

        choixTaille = new JComboBox<>(new Integer[]{7, 8, 9, 10, 11, 12, 13, 14, 15});
        choixTaille.setBounds(70, 90, 400, 20);
        this.add(choixTaille);

        choixDifficulte = new JComboBox<>(new String[]{FACILE, MOYEN, DIFFICILE});
        choixDifficulte.setBounds(70, 145, 400, 20);
        this.add(choixDifficulte);

        boutonOK = new JButton();
        this.boutonOK.setText("OK");
        this.boutonOK.setBounds(180, 180, 80, 30);
        this.boutonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playername = (String) champPseudo.getText();
                int tailleMot = (Integer) choixTaille.getSelectedItem();
                String niveauSelectionne = (String) choixDifficulte.getSelectedItem();

                enregistrerPseudo(playername);

                MotusFrame fenetrePrincipale;
                fenetrePrincipale = new MotusFrame("Jeu Motus", tailleMot, niveauSelectionne,playername);
                fenetrePrincipale.setVisible(true);
                ConteneurChoixFenetre.this.setVisible(false);
            }
        });
        this.add(boutonOK);

        boutonCancel = new JButton();
        this.boutonCancel.setText("Cancel");
        this.boutonCancel.setBounds(280, 180, 100, 30);
        this.boutonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(boutonCancel);
    }

    private void enregistrerPseudo(String pseudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("jeu.txt", true))) {
            // If the file doesn't exist, create it
            if (!Files.exists(Paths.get("jeu.txt"))) {
                Files.createFile(Paths.get("jeu.txt"));
            }

            writer.write(pseudo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
