package Cabinet.controller;

import Cabinet.models.Atelier;
import Cabinet.models.Consultation;
import Cabinet.models.RendezVous;
import Cabinet.models.Suivi;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class AjouterRendezVousController {
    @FXML
    private AnchorPane form2;

    @FXML
    private VBox consultationFields;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField ageField;

    @FXML
    private VBox suiviFields;

    @FXML
    private TextField numeroDossierField;

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private VBox atelierFields;

    @FXML
    private TextField numerosDossiersParticipantsField;

    @FXML
    private TextField thematiqueField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timePicker;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextArea observationField;

    private RendezVous rendezVous;
    private boolean isEditMode = false;

    @FXML
    private void initialize() {
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFieldsVisibility(newValue));
        observationField.setVisible(false);
        observationField.setManaged(false);
    }

    private void updateFieldsVisibility(String type) {
        consultationFields.setVisible(false);
        consultationFields.setManaged(false);
        suiviFields.setVisible(false);
        suiviFields.setManaged(false);
        atelierFields.setVisible(false);
        atelierFields.setManaged(false);

        if ("Consultation".equals(type)) {
            consultationFields.setVisible(true);
            consultationFields.setManaged(true);
        } else if ("Suivi".equals(type)) {
            suiviFields.setVisible(true);
            suiviFields.setManaged(true);
        } else if ("Atelier".equals(type)) {
            atelierFields.setVisible(true);
            atelierFields.setManaged(true);
        }
    }

    @FXML
    private void handleSave() {
        String type = typeComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String timeString = timePicker.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeString, timeFormatter);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        try {
            switch (type) {
                case "Consultation":
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    rendezVous = new Consultation(dateTime, nom, prenom, age);
                    break;
                case "Suivi":
                    int numeroDossier = Integer.parseInt(numeroDossierField.getText());
                    boolean enPresentiel = modeComboBox.getValue().equals("En Présentiel");
                    rendezVous = new Suivi(dateTime, numeroDossier, enPresentiel);
                    break;
                case "Atelier":
                    List<String> numerosDossiersParticipants = Arrays.asList(numerosDossiersParticipantsField.getText().split(","));
                    String thematique = thematiqueField.getText();
                    rendezVous = new Atelier(dateTime, thematique, numerosDossiersParticipants);
                    break;
                default:
                    showAlert("Erreur de validation", "Veuillez sélectionner un type de rendez-vous.");
                    break;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur de validation", "L'âge doit être un nombre entier.");
            return;
        }

        if (isEditMode) {
            rendezVous.setObservation(observationField.getText());
        }
        handleClose();
    }

    @FXML
    private void handleCancel() {
        rendezVous = null;
        handleClose();
    }

    private void handleClose() {
        Stage stage = (Stage) form2.getScene().getWindow();
        stage.close();
    }

    public RendezVous getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
        if (rendezVous != null) {
            isEditMode = true;
            fillForm(rendezVous);
        }
    }

    private void fillForm(RendezVous rendezVous) {
        LocalDateTime dateTime = rendezVous.getDate();
        datePicker.setValue(dateTime.toLocalDate());
        timePicker.setValue(dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));

        if (rendezVous instanceof Consultation) {
            typeComboBox.setValue("Consultation");
            Consultation consultation = (Consultation) rendezVous;
            nomField.setText(consultation.getNom());
            prenomField.setText(consultation.getPrenom());
            ageField.setText(String.valueOf(consultation.getAge()));
        } else if (rendezVous instanceof Suivi) {
            typeComboBox.setValue("Suivi");
            Suivi suivi = (Suivi) rendezVous;
            numeroDossierField.setText(String.valueOf(suivi.getNumeroDossier()));
            modeComboBox.setValue(suivi.isEnPresentiel() ? "En Présentiel" : "À Distance");
            numeroDossierField.setDisable(true);
        } else if (rendezVous instanceof Atelier) {
            typeComboBox.setValue("Atelier");
            Atelier atelier = (Atelier) rendezVous;
            numerosDossiersParticipantsField.setText(String.join(",", atelier.getParticipants()));
            thematiqueField.setText(atelier.getThematique());
            numerosDossiersParticipantsField.setDisable(true);
        }

        typeComboBox.setDisable(isEditMode); // Désactiver le ComboBox une fois qu'un rendez-vous est en mode édition

        if (isEditMode) {
            observationField.setVisible(true);
            observationField.setManaged(true);
            observationField.setText(rendezVous.getObservation());
        } else {
            observationField.setVisible(false);
            observationField.setManaged(false);
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
