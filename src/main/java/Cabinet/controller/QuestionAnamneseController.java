package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.QLAnamnese;
import Cabinet.models.QLAnamneseEnfant;
import Cabinet.models.enums.CategorieQLAnamneseEnfant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionAnamneseController {

    @FXML
    private TableView<QLAnamnese> questionsTable;

    @FXML
    private TableColumn<QLAnamnese, String> NumeroColumn;
    @FXML
    private TableColumn<QLAnamnese, String> categorieColumn;
    @FXML
    private TableColumn<QLAnamnese, String> enonceColumn;
    @FXML
    private TableColumn<QLAnamnese, String> typeColumn;
    @FXML
    private VBox rootVBox;

    private ObservableList<QLAnamnese> questions;
    private CabinetOrthophonique cabinet;

    public void initialize() {
        rootVBox.setPrefHeight(800);
        rootVBox.setPrefWidth(1534);
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        enonceColumn.setCellValueFactory(new PropertyValueFactory<>("enonce"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();
        questions = FXCollections.observableArrayList(cabinet.getAnamneses());
        questionsTable.setItems(questions);

        loadQuestions();
    }

    private void loadQuestions() {
        if (questions.isEmpty()) {
            QLAnamneseEnfant exampleQuestion = new QLAnamneseEnfant("Question enfant exemple", CategorieQLAnamneseEnfant.STRUCTURE_FAMILIALE);
            cabinet.ajouterQlAnamnese(exampleQuestion);
            questions.add(exampleQuestion);
        }
    }

    @FXML
    private void handleAjouterQuestion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterAnamnese.fxml"));
            VBox page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter Question");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootVBox.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AjouterAnamneseController controller = loader.getController();
            controller.setAnamneseController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(QLAnamnese question) {
        cabinet.ajouterQlAnamnese(question);
        questions.add(question);
    }


    @FXML
    private void handleModifierQuestion() {
        QLAnamnese selectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterAnamnese.fxml"));
                VBox page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Modifier Question");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(rootVBox.getScene().getWindow());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                AjouterAnamneseController controller = loader.getController();
                controller.setAnamneseController(this);
                controller.setQuestionToModify(selectedQuestion);
                dialogStage.showAndWait();
                QLAnamnese updatedQlAnamnese = controller.getQlAnamnese();

                if (updatedQlAnamnese != null){
                    cabinet.modifierQlAnamnese(selectedQuestion, updatedQlAnamnese);
                    System.out.println(cabinet.getAnamneses().get(0).getEnonce());
                    questions.set(questions.indexOf(selectedQuestion), updatedQlAnamnese);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Sélectionnez une question", "Veuillez sélectionner une question à modifier.");
        }
    }


    @FXML
    private void handleSupprimerQuestion() {
        QLAnamnese selectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questions.remove(selectedQuestion);
            cabinet.supprimerQlAnamnese(selectedQuestion);
        } else {
            showAlert("Sélectionnez une question", "Veuillez sélectionner une question à supprimer.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshTable() {
        questionsTable.refresh();
    }
}