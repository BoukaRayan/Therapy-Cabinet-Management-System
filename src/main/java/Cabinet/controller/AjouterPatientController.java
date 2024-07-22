package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.DossierPatient;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Cabinet.models.Adulte;
import Cabinet.models.Enfant;
import Cabinet.models.RendezVous;
import java.util.ArrayList;
import java.util.List;

public class AjouterPatientController {

    @FXML
    private JFXComboBox<String> typeComboBox;
    @FXML
    private JFXTextArea nomArea;
    @FXML
    private JFXTextArea prenomArea;
    @FXML
    private JFXTextArea adresseArea;
    @FXML
    private JFXTextArea lieuNaisArea;
    @FXML
    private DatePicker dateNaisArea;
    @FXML
    private JFXTextArea telephoneArea;


    @FXML
    private VBox enfantFields;
    @FXML
    private JFXTextArea classeEtudesArea;


    @FXML
    private VBox adulteFields;
    @FXML
    private JFXTextArea diplomeArea;
    @FXML
    private JFXTextArea professionArea;

    private DossierPatient patient;

    @FXML
    void initialize() {
        typeComboBox.getItems().addAll("Enfant", "Adulte");

        enfantFields.setVisible(false);
        enfantFields.setManaged(false);
        adulteFields.setVisible(false);
        adulteFields.setManaged(false);

        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Enfant".equals(newValue)) {
                showEnfantFields();
            } else if ("Adulte".equals(newValue)) {
                showAdulteFields();
            }
        });
    }

    @FXML
    private void handleSave() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        if ("Enfant".equals(typeComboBox.getValue())) {
            patient = new DossierPatient(new Enfant(nomArea.getText(), prenomArea.getText(), dateNaisArea.getValue(), lieuNaisArea.getText(), adresseArea.getText(), telephoneArea.getText(), classeEtudesArea.getText()), rendezVousList);
            CabinetHolder.getInstance().getCabinetOrthophonique().ajouterDossierPatient(patient);
        } else {
            patient = new DossierPatient(new Adulte(nomArea.getText(), prenomArea.getText(), dateNaisArea.getValue(), lieuNaisArea.getText(), adresseArea.getText(), telephoneArea.getText(), diplomeArea.getText(), professionArea.getText()), rendezVousList);
            CabinetHolder.getInstance().getCabinetOrthophonique().ajouterDossierPatient(patient);
        }
        System.out.println(CabinetHolder.getInstance().getCabinetOrthophonique().getDossiersPatients());
        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }

    private void setPatient(DossierPatient patient) {
        this.patient = patient;
    }


    @FXML
    private void handleCancel() {
        // Fermer la fenêtre sans enregistrer les modifications
        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }

    private void showEnfantFields() {
        enfantFields.setVisible(true);
        enfantFields.setManaged(true);
        adulteFields.setVisible(false);
        adulteFields.setManaged(false);
    }

    private void showAdulteFields() {
        adulteFields.setVisible(true);
        adulteFields.setManaged(true);
        enfantFields.setVisible(false);
        enfantFields.setManaged(false);
    }

    public DossierPatient getPatient() {
        return patient;
    }
}