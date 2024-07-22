package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.*;
import Cabinet.models.enums.CategorieTrouble;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class AjouterModifierTroublesController {


    @FXML
    private Node root;

    @FXML
    private Stage stage;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nomField;

    @FXML
    private ComboBox<Enum> categorieComboBox;

    @FXML
    private Button validerButton;

    private CabinetOrthophonique cabinet;

    private Trouble troubleToModify;
    private boolean saved = false;

    public void initData(Trouble trouble) {
        this.troubleToModify = trouble;

        if (titleLabel != null) {
            if (trouble != null) {
                titleLabel.setText("Modifier Question");
                nomField.setText( trouble.getNom());
            } else {
                titleLabel.setText("Ajouter Question");
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();
        if (categorieComboBox != null) {
            categorieComboBox.getItems().clear();
            categorieComboBox.getItems().addAll(CategorieTrouble.values());
        }
        validerButton.setOnAction(e -> valider());
    }

    private void valider() {
        String nom = nomField != null ? nomField.getText() : "";
        Enum categorie =  categorieComboBox.getValue();

        if (troubleToModify != null) {
            Trouble oldTrouble = troubleToModify;
            troubleToModify.setNom(nom);
            troubleToModify.setCategorie((CategorieTrouble) categorie);
            cabinet.modifierTrouble(oldTrouble,troubleToModify);

        } else {
            Trouble newTrouble = new Trouble(nom, (CategorieTrouble) categorie);
            cabinet.ajouterTrouble(newTrouble);
        }
        saved = true;
        // Fermer la fenetre
        Stage stage = (Stage) validerButton.getScene().getWindow();
        stage.close();


    }
    public boolean isSaved() {
        return saved;
    }
}