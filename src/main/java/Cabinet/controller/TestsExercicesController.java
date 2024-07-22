package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TestsExercicesController {

    @FXML
    private TableView<TestExercices> testsExosTable;

    @FXML
    private TableColumn<TestExercices, String> numeroColumn;

    @FXML
    private TableColumn<TestExercices, String> nomColumn;

    @FXML
    private TableColumn<TestExercices, String> capaciteColumn;

    @FXML
    private TextField exoIdField;

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

    private ObservableList<TestExercices> testsExosList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        capaciteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacite()))
        );

        testsExosList = FXCollections.observableArrayList(cabinet.getTestsExerices());
        testsExosTable.setItems(testsExosList);

        ajouterExoButton.setOnAction(e -> ajouterExercice());
        supprimerExoButton.setOnAction(e -> supprimerExercice());
        ajouterButton.setOnAction(e -> ajouter());
        modifierButton.setOnAction(e -> modifier());
        supprimerButton.setOnAction(e -> supprimer());
    }

    private void ajouterExercice() {
        TestExercices testExercices = testsExosTable.getSelectionModel().getSelectedItem();
        if (!exoIdField.getText().isEmpty() && testExercices != null) {
            int exoId = Integer.parseInt(exoIdField.getText());
            Exercice exo = cabinet.getExerciceParNumero(exoId);
            if (exo != null){
                testExercices.ajouterExercice(exo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exercice ajouté");
                alert.setHeaderText(null);
                alert.setContentText("L'exercice a été ajouté au test " + testExercices.getNom() + " avec succès.");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de l'ajout de l'exercice");
                alert.setHeaderText(null);
                alert.setContentText("Pas d'exercice avec le numero " + exoId + " trouvé.");
                alert.showAndWait();
            }
            exoIdField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de l'ajout de l'exercice");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero d'exercice valide.");
            alert.showAndWait();
        }
    }

    private void supprimerExercice(){
        TestExercices testExercices = testsExosTable.getSelectionModel().getSelectedItem();
        if (!exoIdField.getText().isEmpty() && testExercices != null) {
            int exoId = Integer.parseInt(exoIdField.getText());
            Exercice exo = cabinet.getExerciceParNumero(exoId);
            if (exo != null){
                testExercices.supprimerExercice(exo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exercice supprimé");
                alert.setHeaderText(null);
                alert.setContentText("L'exercice a été supprimé du test " + testExercices.getNom() + " avec succès.");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de la suppression de l'exercice");
                alert.setHeaderText(null);
                alert.setContentText("Pas d'exercice avec le numero " + exoId + " trouvé.");
                alert.showAndWait();
            }
            exoIdField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de la suppression de l'exercice");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero d'exercice valide.");
            alert.showAndWait();
        }
    }

    private void modifier() {
        TestExercices testExercices = testsExosTable.getSelectionModel().getSelectedItem();
        if (!nomField.getText().isEmpty() && !capaciteField.getText().isEmpty() && testExercices != null) {
            testExercices.setNom(nomField.getText());
            testExercices.setCapacite(capaciteField.getText());
            testsExosList = FXCollections.observableArrayList(cabinet.getTestsExerices());
            testsExosTable.setItems(testsExosList);
            testsExosTable.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exercice modifié");
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
            TestExercices te = new TestExercices(nomField.getText(), capaciteField.getText());
            cabinet.ajouterTest(te);
            testsExosList = FXCollections.observableArrayList(cabinet.getTestsExerices());
            testsExosTable.setItems(testsExosList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exercice ajouté");
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
        TestExercices selectedTestExercices = testsExosTable.getSelectionModel().getSelectedItem();
        if (selectedTestExercices != null) {
            testsExosList.remove(selectedTestExercices);
            testsExosTable.refresh();
            cabinet.supprimerTest(selectedTestExercices);
        }
    }
}