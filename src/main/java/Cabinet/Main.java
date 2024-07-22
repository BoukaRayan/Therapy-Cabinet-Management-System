package Cabinet;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.holder.Chargeur;
import Cabinet.models.CompteOrthophoniste;
import Cabinet.models.QCU;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Charger les données au démarrage de l'application
        Chargeur.chargerDonnees();
        //Chargeur.start();78+9+

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1534, 800);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Authentification");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        Chargeur.sauvegarderDonnees();
    }




    public static void main(String[] args) {
        launch();
    }
}