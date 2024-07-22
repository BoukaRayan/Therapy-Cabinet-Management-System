package Cabinet.controller;

import Cabinet.models.DossierPatient;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Cabinet.models.Adulte;
import Cabinet.models.Enfant;
import Cabinet.models.Patient;

public class ModifierPatientController {

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

    private DossierPatient dossier;

    private Patient patient;

    public void setDossier(DossierPatient dossier) {
        this.dossier = dossier;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

        nomArea.setText(patient.getNom());
        prenomArea.setText(patient.getPrenom());
        adresseArea.setText(patient.getAdresse());
        telephoneArea.setText(patient.getNumeroTel());
        dateNaisArea.setValue(patient.getDateNais());
        lieuNaisArea.setText(patient.getLieuNais());

        typeComboBox.getItems().addAll("Enfant", "Adulte");

        if (patient instanceof Enfant enfant) {
            typeComboBox.setValue("Enfant");
            classeEtudesArea.setText(enfant.getClasseEtudes());
            showEnfantFields();
        } else if (patient instanceof Adulte adulte) {
            typeComboBox.setValue("Adulte");
            diplomeArea.setText(adulte.getDiplome());
            professionArea.setText(adulte.getProfession());
            showAdulteFields();
        }

        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Enfant".equals(newValue)) {
                showEnfantFields();
            } else if ("Adulte".equals(newValue)) {
                showAdulteFields();
            }
        });
    }

    @FXML
    void initialize() {
        enfantFields.setVisible(false);
        enfantFields.setManaged(false);
        adulteFields.setVisible(false);
        adulteFields.setManaged(false);
    }

    @FXML
    private void handleSave() {

        patient.setNom(nomArea.getText());
        patient.setPrenom(prenomArea.getText());
        patient.setAdresse(adresseArea.getText());
        patient.setDateNais(dateNaisArea.getValue());
        patient.setLieuNais(lieuNaisArea.getText());
        patient.setNumeroTel(telephoneArea.getText());


        if ("Enfant".equals(typeComboBox.getValue())) {
            if (patient instanceof Enfant enfant) {
                enfant.setClasseEtudes(classeEtudesArea.getText());
            } else {
                dossier.setInfos(new Enfant(nomArea.getText(), prenomArea.getText(), dateNaisArea.getValue(), lieuNaisArea.getText(), adresseArea.getText(), telephoneArea.getText(), classeEtudesArea.getText()));
            }
        } else {
            if (patient instanceof Adulte adulte) {
                adulte.setDiplome(diplomeArea.getText());
                adulte.setProfession(professionArea.getText());
            } else {
                dossier.setInfos(new Adulte(nomArea.getText(), prenomArea.getText(), dateNaisArea.getValue(), lieuNaisArea.getText(), adresseArea.getText(), telephoneArea.getText(), diplomeArea.getText(), professionArea.getText()));
            }
        }

        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();

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
}