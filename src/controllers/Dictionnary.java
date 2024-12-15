package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionnary {

    private List<String> mots;

    public Dictionnary(String cheminFichier) throws IOException {
        mots = chargerMotsDepuisFichier(cheminFichier);
    }

    private List<String> chargerMotsDepuisFichier(String cheminFichier) throws IOException {
        List<String> listeMots = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(cheminFichier)))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                listeMots.add(ligne.trim());
            }
        } catch (IOException e) {
            throw e;
        }

        return listeMots;
    }

    private List<String> filtrerMotsParLongueur(int longueurMot) {
        List<String> motsFiltres = new ArrayList<>();
        for (String mot : mots) {
            if (mot.length() == longueurMot) {
                motsFiltres.add(mot);
            }
        }
        return motsFiltres;
    }

    public String selectionnerMotAleatoire(int longueurMot) {
        List<String> motsFiltres = filtrerMotsParLongueur(longueurMot);

        if (motsFiltres.isEmpty()) {
            throw new IllegalStateException("Aucun mot de la longueur spécifiée n'a été trouvé.");
        }
        Random random = new Random();
        int indiceAleatoire = random.nextInt(motsFiltres.size());
        return motsFiltres.get(indiceAleatoire);
    }
    public String getMotADeviner(int longueurMot) {
    	return selectionnerMotAleatoire(longueurMot);
    }

}



