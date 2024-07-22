package Cabinet.controller;

import Cabinet.models.QLAnamnese;
import Cabinet.models.QLAnamneseAdulte;
import Cabinet.models.QLAnamneseEnfant;
import Cabinet.models.enums.CategorieQLAnamneseAdulte;
import Cabinet.models.enums.CategorieQLAnamneseEnfant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AjouterAnamneseController {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<Enum> categorieComboBox;
    @FXML
    private TextArea enonceTextArea;

    private QLAnamnese selectedQuestion;

    private QuestionAnamneseController anamneseController;

    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("Enfant", "Adulte"));
        typeComboBox.setOnAction(event -> handleTypeSelection());
    }

    private void handleTypeSelection() {
        String selectedType = typeComboBox.getValue();
        if (selectedType != null) {
            if (selectedType.equals("Enfant")) {
                categorieComboBox.setItems(FXCollections.observableArrayList(CategorieQLAnamneseEnfant.values()));
            } else if (selectedType.equals("Adulte")) {
                categorieComboBox.setItems(FXCollections.observableArrayList(CategorieQLAnamneseAdulte.values()));
            }
        }
    }

    @FXML
    private void handleAjouter() {
        String selectedType = typeComboBox.getValue();
        Enum selectedCategorie = categorieComboBox.getValue();
        String enonce = enonceTextArea.getText();

        if (selectedType != null && selectedCategorie != null && enonce != null && !enonce.trim().isEmpty()) {
            QLAnamnese newQuestion;
            if (selectedType.equals("Enfant")) {
                newQuestion = new QLAnamneseEnfant(enonce, (CategorieQLAnamneseEnfant) selectedCategorie);
            } else {
                newQuestion = new QLAnamneseAdulte(enonce, (CategorieQLAnamneseAdulte) selectedCategorie);
            }
            anamneseController.addQuestion(newQuestion);
            Stage stage = (Stage) typeComboBox.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
        }
    }

    public void setAnamneseController(QuestionAnamneseController anamneseController) {
        this.anamneseController = anamneseController;
    }

    public void setQuestionToModify(QLAnamnese question) {
        selectedQuestion = question;
        typeComboBox.setValue(question instanceof QLAnamneseEnfant ? "Enfant" : "Adulte");
        typeComboBox.setDisable(true);
        if (question instanceof QLAnamneseEnfant) {
            categorieComboBox.setItems(FXCollections.observableArrayList(CategorieQLAnamneseEnfant.values()));
        } else {
            categorieComboBox.setItems(FXCollections.observableArrayList(CategorieQLAnamneseAdulte.values()));
        }
        enonceTextArea.setText(question.getEnonce());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleModifier() {
        Enum selectedCategorie = categorieComboBox.getValue();
        String enonce = enonceTextArea.getText();

        if (selectedCategorie != null && enonce != null && !enonce.trim().isEmpty()) {
            selectedQuestion.setCategorie(selectedCategorie);
            selectedQuestion.setEnonce(enonce);
            anamneseController.refreshTable();
            Stage stage = (Stage) typeComboBox.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
        }
    }

    public QLAnamnese getQlAnamnese() {
        return selectedQuestion;
    }
}