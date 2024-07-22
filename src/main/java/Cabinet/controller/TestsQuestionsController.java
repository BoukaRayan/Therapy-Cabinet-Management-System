package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TestsQuestionsController {

    @FXML
    private TableView<TestQuestions> testsQstsTable;

    @FXML
    private TableColumn<TestQuestions, String> numeroColumn;

    @FXML
    private TableColumn<TestQuestions, String> nomColumn;

    @FXML
    private TableColumn<TestQuestions, String> capaciteColumn;

    @FXML
    private TextField qstIdField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField capaciteField;

    @FXML
    private Button ajouterExoButton;

    @FXML
    private Button supprimerExoButton;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    private ObservableList<TestQuestions> testsQstsList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        capaciteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacite()))
        );

        testsQstsList = FXCollections.observableArrayList(cabinet.getTestsQuestions());
        testsQstsTable.setItems(testsQstsList);

        ajouterExoButton.setOnAction(e -> ajouterQuestion());
        supprimerExoButton.setOnAction(e -> supprimerQuestion());
        ajouterButton.setOnAction(e -> ajouter());
        modifierButton.setOnAction(e -> modifier());
        supprimerButton.setOnAction(e -> supprimer());
    }

    private void ajouterQuestion() {
        TestQuestions testQuestions = testsQstsTable.getSelectionModel().getSelectedItem();
        if (!qstIdField.getText().isEmpty() && testQuestions != null) {
            int qstId = Integer.parseInt(qstIdField.getText());
            Question qst = cabinet.getQuestionParNumero(qstId);
            if (qst != null){
                if(!testQuestions.getQuestions().contains(qst)) {
                    testQuestions.ajouterQuestion(qst);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Question ajoutée");
                    alert.setHeaderText(null);
                    alert.setContentText("La question a été ajouté au test " + testQuestions.getNom() + " avec succès.");
                    alert.showAndWait();
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur lors de l'ajout de la question");
                    alert.setHeaderText(null);
                    alert.setContentText("La question est déjà dans le test.");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de l'ajout de la question");
                alert.setHeaderText(null);
                alert.setContentText("Pas de question avec le numero " + qstId + " trouvée.");
                alert.showAndWait();
            }
            qstIdField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de l'ajout de la question");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero de question valide.");
            alert.showAndWait();
        }
    }

    private void supprimerQuestion(){
        TestQuestions testQuestions = testsQstsTable.getSelectionModel().getSelectedItem();
        if (!qstIdField.getText().isEmpty() && testQuestions != null) {
            int qstId = Integer.parseInt(qstIdField.getText());
            Question qst = cabinet.getQuestionParNumero(qstId);
            if (qst != null){
                testQuestions.supprimerQuestion(qst);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question supprimée");
                alert.setHeaderText(null);
                alert.setContentText("La question a été supprimé du test " + testQuestions.getNom() + " avec succès.");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de la suppression de la question");
                alert.setHeaderText(null);
                alert.setContentText("Pas de question avec le numero " + qstId + " trouvé.");
                alert.showAndWait();
            }
            qstIdField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de la suppression de la question");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero de question valide.");
            alert.showAndWait();
        }
    }

    private void modifier() {
        TestQuestions testQuestions = testsQstsTable.getSelectionModel().getSelectedItem();
        if (!nomField.getText().isEmpty() && !capaciteField.getText().isEmpty() && testQuestions != null) {
            testQuestions.setNom(nomField.getText());
            testQuestions.setCapacite(capaciteField.getText());
            testsQstsList = FXCollections.observableArrayList(cabinet.getTestsQuestions());
            testsQstsTable.setItems(testsQstsList);
            testsQstsTable.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test modifié");
            alert.setHeaderText(null);
            alert.setContentText("Le test a été modifié avec succès.");
            alert.showAndWait();
            nomField.clear();
            capaciteField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du test.");
            alert.showAndWait();
        }
    }

    private void ajouter() {
        if (!nomField.getText().isEmpty() && !capaciteField.getText().isEmpty()) {
            TestQuestions te = new TestQuestions(nomField.getText(), capaciteField.getText());
            cabinet.ajouterTest(te);
            testsQstsList = FXCollections.observableArrayList(cabinet.getTestsQuestions());
            testsQstsTable.setItems(testsQstsList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test ajouté");
            alert.setHeaderText(null);
            alert.setContentText("Le test a été ajouté avec succès.");
            alert.showAndWait();
            nomField.clear();
            capaciteField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du test.");
            alert.showAndWait();
        }
    }

    private void supprimer() {
        TestQuestions selectedTestQuestions = testsQstsTable.getSelectionModel().getSelectedItem();
        if (selectedTestQuestions != null) {
            testsQstsList.remove(selectedTestQuestions);
            testsQstsTable.refresh();
            cabinet.supprimerTest(selectedTestQuestions);
        }
    }
}