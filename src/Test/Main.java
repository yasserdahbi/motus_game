package Test;

import graphics.*;
import controllers.*;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        // Affiche la fenêtre de chargement
        displayLoadingWindow();

        // Lance la fenêtre de choix après un certain délai 
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    ChoixFenetre choixFenetre = new ChoixFenetre("Choix");
                    choixFenetre.setVisible(true);
                });
            }
        }, 1000); // Délai de 1000 millisecondes (1 secondes)
    }

    private static void displayLoadingWindow() {
        ImageIcon icon = new ImageIcon("./motus.jpg");

        JDialog loadingDialog = new JDialog(null, "Jeu Motus", Dialog.ModalityType.APPLICATION_MODAL);
        loadingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(icon);
        panel.add(label, BorderLayout.CENTER);

        // Add the loading bar to the bottom of the panel
        JProgressBar loadingBar = new JProgressBar();
        loadingBar.setPreferredSize(new Dimension(500, 30)); 
        panel.add(loadingBar, BorderLayout.SOUTH);

        loadingDialog.add(panel);

        // Schedule a timer to update the loading bar
        int totalDelay = 2000; // Total delay in milliseconds
        int updateInterval = 20; // Interval for updating the loading bar in milliseconds
        int steps = totalDelay / updateInterval;
        int progressStep = 100 / steps;

        Timer delayTimer = new Timer();
        delayTimer.scheduleAtFixedRate(new TimerTask() {
            int progress = 0;

            @Override
            public void run() {
                loadingBar.setValue(progress);
                progress += progressStep;

                if (progress >= 100) {
                    delayTimer.cancel();
                    loadingDialog.dispose(); 
                }
            }
        }, 0, updateInterval);

        // taille de la fenêtre
        loadingDialog.setSize(470, 470);
        loadingDialog.setLocationRelativeTo(null);
        loadingDialog.setVisible(true);
    }

}
