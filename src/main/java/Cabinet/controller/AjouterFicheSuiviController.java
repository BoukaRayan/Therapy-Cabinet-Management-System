package Cabinet.controller;

import Cabinet.models.DossierPatient;
import Cabinet.models.FicheSuivi;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AjouterFicheSuiviController {

    @FXML
    private JFXTextArea nomArea;

    private FicheSuivi fiche;

    private DossierPatient dossier;

    @FXML
    private void handleSave() {
        fiche = new FicheSuivi(nomArea.getText());
        dossier.ajouterFicheSuivi(fiche);
        Stage stage = (Stage) nomArea.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Fermer la fenêtre sans enregistrer les modifications
        Stage stage = (Stage) nomArea.getScene().getWindow();
        stage.close();
    }

    public void setDossier(DossierPatient dossier) {
        this.dossier = dossier;
    }

    public FicheSuivi getFiche() {
        return fiche;
    }
}