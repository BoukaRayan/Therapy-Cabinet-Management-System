package Cabinet.holder;

import Cabinet.models.*;
import Cabinet.models.enums.CategorieQLAnamneseEnfant;
import Cabinet.models.enums.CategorieTrouble;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chargeur {
    public static void sauvegarder(CabinetOrthophonique cabinet, String cheminFichier) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            outputStream.writeObject(cabinet);
            System.out.println("Les données ont été sauvegardées avec succès dans le fichier : " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CabinetOrthophonique charger(String cheminFichier) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            CabinetOrthophonique cabinet = (CabinetOrthophonique) inputStream.readObject();
            System.out.println("Les données ont été chargées avec succès à partir du fichier : " + cheminFichier);
            return cabinet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sauvegarderDonnees() {
        CabinetOrthophonique cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();
        // Appel à une méthode de sauvegarde dans la classe CabinetOrthophonique ou un autre gestionnaire de données
        sauvegarder(cabinet,"donnees.ser");
    }

    public static void chargerDonnees() {
        CabinetOrthophonique cabinet;
        // Appel à une méthode de chargement dans la classe CabinetOrthophonique ou un autre gestionnaire de données
        cabinet = charger("donnees.ser");
        if (cabinet != null) {
            CabinetHolder.getInstance().setCabinetOrthophonique(cabinet);
        } else {
            System.out.println("Erreur lors du chargement des données à partir du fichier : donnees.ser");
        }
    }
}