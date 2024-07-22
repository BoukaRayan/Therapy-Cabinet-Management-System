package Cabinet.controller;

import Cabinet.models.FicheSuivi;
import Cabinet.models.Objectif;
import Cabinet.models.enums.TypeObjectif;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AjouterModifierObjectifController {

    @FXML
    private JFXTextArea nomArea;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private JFXTextArea scoreArea;

    private FicheSuivi ficheSuivi;
    private Objectif objectif;
    private boolean modeModifier;

    @FXML
    private void initialize() {
        typeComboBox.getItems().setAll("Long terme", "Moyen terme", "Court terme");
    }

    @FXML
    private void handleSave() {
        if(Integer.parseInt(scoreArea.getText()) <= 5 && Integer.parseInt(scoreArea.getText()) >= 1) {
            String nom = nomArea.getText();
            TypeObjectif type;
            if (typeComboBox.getSelectionModel().getSelectedItem() == "Moyen terme") {
                type = TypeObjectif.MOYENTERME;
            } else if (typeComboBox.getSelectionModel().getSelectedItem() == "Court terme") {
                type = TypeObjectif.COURTTERME;
            } else {
                type = TypeObjectif.LONGTERME;
            }
            int score = Integer.parseInt(scoreArea.getText());

            if (modeModifier) {
                ficheSuivi.supprimerObjectif(objectif);
            }

            objectif = new Objectif(nom, type);
            ficheSuivi.ajouterObjectif(objectif, score);

            Stage stage = (Stage) nomArea.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de score");
            alert.setHeaderText(null);
            alert.setContentText("La note doit être comprise entre 1 et 5.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nomArea.getScene().getWindow();
        stage.close();
    }

    public void setFicheSuivi(FicheSuivi ficheSuivi) {
        this.ficheSuivi = ficheSuivi;
    }

    public void setObjectif(Objectif objectif, int score) {
        this.objectif = objectif;
        this.modeModifier = true;
        nomArea.setText(objectif.getNom());
        typeComboBox.setValue(objectif.getType());
        scoreArea.setText(String.valueOf(score));
    }
}
