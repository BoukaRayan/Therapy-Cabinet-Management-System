package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.CompteOrthophoniste;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private JFXTextArea emailField;

    @FXML
    private JFXTextArea passwordField;

    @FXML
    private JFXTextArea registerNomField;

    @FXML
    private JFXTextArea registerPrenomField;

    @FXML
    private JFXTextArea registerTelephoneField;

    @FXML
    private JFXTextArea registerAdresseField;

    @FXML
    private JFXTextArea registerEmailField;

    @FXML
    private JFXTextArea registerPasswordField;

    @FXML
    private Label titleLabel;

    @FXML
    private void handleSeConnecter(ActionEvent event) {
        String email = emailField.getText();
        String motDePasse = passwordField.getText();

        CompteOrthophoniste compte = CabinetHolder.getInstance().getCabinetOrthophonique().authentifier(email, motDePasse);
        if (compte != null) {
            // Connexion réussie
            CabinetHolder.getInstance().setCompteOrthophoniste(compte);
            try{
                // Charger la nouvelle fenêtre
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PagePrincipale.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1534, 800);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Cabinet Orthophonique");
                newStage.show();

                // Fermer la fenêtre actuelle
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Connexion échouée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Email ou mot de passe incorrect.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleShowRegister() {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    @FXML
    private void handleRetour(){
        loginPane.setVisible(true);
        registerPane.setVisible(false);
    }

    @FXML
    private void handleRegister() {
        String email = registerEmailField.getText();
        String motDePasse = registerPasswordField.getText();
        String nom = registerNomField.getText();
        String prenom = registerPrenomField.getText();
        String telephone = registerTelephoneField.getText();
        String adresse = registerAdresseField.getText();
        CabinetOrthophonique cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        // Si l'utilisateur existe déjà
        if(cabinet.compteExiste(email)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de la creation du compte");
            alert.setHeaderText(null);
            alert.setContentText("Un compte avec cette adresse email existe déja.");
            alert.showAndWait();
        }else {
            cabinet.ajouterCompte(new CompteOrthophoniste(nom, prenom, adresse, telephone, email, motDePasse));
        }

        // Réinitialiser les champs de texte
        registerEmailField.setText("Adresse email");
        registerPasswordField.setText("Mot de passe");

        // Afficher le formulaire de connexion
        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }


}
