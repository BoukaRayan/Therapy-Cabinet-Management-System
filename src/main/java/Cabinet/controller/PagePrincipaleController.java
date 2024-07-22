package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CompteOrthophoniste;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PagePrincipaleController {

    @FXML
    private JFXButton btnForm1;

    @FXML
    private JFXButton btnForm2;

    @FXML
    private JFXButton btnForm3;

    @FXML
    private JFXButton btnForm4;

    @FXML
    private JFXButton btnForm5;


    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane form1;

    @FXML
    private AnchorPane form2;

    @FXML
    private AnchorPane form3;

    @FXML
    private AnchorPane form4;
    private AnchorPane form5;

    @FXML
    private Label utilisateurLabel;

    @FXML
    public void initialize() {
        try {
            form1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RendezVous.fxml")));
            form2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/DossiersPatients.fxml")));
            form3 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/TestsAnamneses.fxml")));
            form4 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Statistiques.fxml")));
            form5= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Comptes.fxml")));


            mainPane.setCenter(form1);  // Load the first form by default

            CompteOrthophoniste utilisateur = CabinetHolder.getInstance().getCompteOrthophoniste();
            // Le nom de l'utilisateur
            utilisateurLabel.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == btnForm1) {
            mainPane.setCenter(form1);
        } else if (event.getSource() == btnForm2) {
            mainPane.setCenter(form2);
        } else if (event.getSource() == btnForm3) {
            mainPane.setCenter(form3);
        }else if (event.getSource() == btnForm4) {
            mainPane.setCenter(form4);
        }else if (event.getSource() == btnForm5) {
            mainPane.setCenter(form5);
        }
    }

    public void handleDeconnexion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Connexion.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1534, 800);
            Stage newStage = new Stage();

            newStage.setScene(scene);
            newStage.setTitle("Authentification");
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}