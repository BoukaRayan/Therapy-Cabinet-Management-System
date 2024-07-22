package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AnamneseController {

    @FXML
    private TableView<Anamnese> anamneseTable;

    @FXML
    private TableColumn<Anamnese, String> numeroColumn;

    @FXML
    private TableColumn<Anamnese, String> nomColumn;

    @FXML
    private TextField QstIdField;

    @FXML
    private TextField nomField;

    @FXML
    private Button ajouterQstButton;

    @FXML
    private Button supprimerQstButton;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    private ObservableList<Anamnese> anamneseList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

        anamneseList = FXCollections.observableArrayList(cabinet.getAnamnese());
        anamneseTable.setItems(anamneseList);

        ajouterQstButton.setOnAction(e -> ajouterQuestionAnamnese());
        supprimerQstButton.setOnAction(e -> supprimmerQuestionAnamnese());
        ajouterButton.setOnAction(e -> ajouter());
        modifierButton.setOnAction(e -> modifier());
        supprimerButton.setOnAction(e -> supprimer());
    }

    private void ajouterQuestionAnamnese() {
        Anamnese anamnese = anamneseTable.getSelectionModel().getSelectedItem();
        if (!QstIdField.getText().isEmpty() && anamnese != null) {
            int QstId = Integer.parseInt(QstIdField.getText());
            QLAnamnese QuestionAnamnese = cabinet.getQuestionAnamneseParNumero(QstId);
            if (QuestionAnamnese != null) {
                if (anamnese.getQuestions().contains(QuestionAnamnese)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Erreur lors de l'ajout de la question anamnese");
                    alert.setHeaderText(null);
                    alert.setContentText("La question anamnese avec le numero " + QstId + " est déjà dans l'anamnese " + anamnese.getNom() + ".");
                    alert.showAndWait();
                    return;
                }
                anamnese.ajouterQuestion(QuestionAnamnese);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question anamnese ajouté");
                alert.setHeaderText(null);
                alert.setContentText("La question a été ajouté a l'anamnese " + anamnese.getNom() + " avec succès.");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de l'ajout de la question anamnese");
                alert.setHeaderText(null);
                alert.setContentText("Pas de question anamnese avec le numero " + QstId + " trouvé.");
                alert.showAndWait();
            }
            QstIdField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de l'ajout de l'exercice");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero d'exercice valide.");
            alert.showAndWait();
        }
    }

    private void supprimmerQuestionAnamnese(){
        Anamnese anamnese = anamneseTable.getSelectionModel().getSelectedItem();
        if (!QstIdField.getText().isEmpty() && anamnese != null) {
            int QstId = Integer.parseInt(QstIdField.getText());
            QLAnamnese question = cabinet.getQuestionAnamneseParNumero(QstId);
            if (question != null){
                anamnese.supprimerQuestion(question);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question anamnese supprimé");
                alert.setHeaderText(null);
                alert.setContentText("La question a été supprimé de l anamnese " + anamnese.getNom() + " avec succès.");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur lors de la suppression de la question anamnese");
                alert.setHeaderText(null);
                alert.setContentText("Pas de question avec le numero " + QstId + " trouvé.");
                alert.showAndWait();
            }
            QstIdField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur lors de la suppression de la question anamnese");
            alert.setHeaderText(null);
            alert.setContentText("Entrez un numero de question valide.");
            alert.showAndWait();
        }
    }

    private void modifier() {
        Anamnese anamnese = anamneseTable.getSelectionModel().getSelectedItem();
        if (!nomField.getText().isEmpty()  && anamnese != null) {
            anamnese.setNom(nomField.getText());
            anamneseList = FXCollections.observableArrayList(cabinet.getAnamnese());
            anamneseTable.setItems(anamneseList);
            anamneseTable.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Anamnese modifié");
            alert.setHeaderText(null);
            alert.setContentText("L' Anamnese a été modifié avec succès.");
            alert.showAndWait();
            nomField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la modification de 'Anamnese.");
            alert.showAndWait();
        }
    }

    private void ajouter() {
        if (!nomField.getText().isEmpty() ) {
            Anamnese anamnese = new Anamnese(nomField.getText() );
            cabinet.ajouterAnamnese(anamnese);
            anamneseList = FXCollections.observableArrayList(cabinet.getAnamnese());
            anamneseTable.setItems(anamneseList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Anamnese ajouté");
            alert.setHeaderText(null);
            alert.setContentText("L'anamnese a été ajouté avec succès.");
            alert.showAndWait();
            nomField.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout de l'anamnese.");
            alert.showAndWait();
        }
    }

    private void supprimer() {
        Anamnese selectedAnamnese = anamneseTable.getSelectionModel().getSelectedItem();
        if (selectedAnamnese != null) {
            anamneseList.remove(selectedAnamnese);
            anamneseTable.refresh();
            cabinet.supprimerAnamnese(selectedAnamnese);
        }
    }
}