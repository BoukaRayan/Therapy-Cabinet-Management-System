package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.QCM;
import Cabinet.models.QCU;
import Cabinet.models.Question;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionsController {

    @FXML
    private TableView<Question> questionsTable;

    @FXML
    private TableColumn<Question, String> numeroColumn;

    @FXML
    private TableColumn<Question, String> enonceColumn;

    @FXML
    private TableColumn<Question, String> typeColumn;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    private ObservableList<Question> questionsList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        enonceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnonce()));
        typeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof QCM) {
                return new SimpleStringProperty("QCM");
            } else if (cellData.getValue() instanceof QCU) {
                return new SimpleStringProperty("QCU");
            } else {
                return new SimpleStringProperty("QL");
            }
        });

        questionsList = FXCollections.observableArrayList(cabinet.getQuestions());
        questionsTable.setItems(questionsList);

        ajouterButton.setOnAction(e -> openAjouterModifierPage(null));
        modifierButton.setOnAction(e -> openAjouterModifierPage(questionsTable.getSelectionModel().getSelectedItem()));
        supprimerButton.setOnAction(e -> supprimerQuestion());
    }


    private void supprimerQuestion() {
        Question selectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questionsList.remove(selectedQuestion);
            cabinet.supprimerQuestion(selectedQuestion);
        }
    }

    private void openAjouterModifierPage(Question question) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterModifierQuestions.fxml"));
            Parent root = loader.load();

            AjouterModifierQuestionsController controller = loader.getController();
            controller.setQuestion(question);

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                questionsList.setAll(cabinet.getQuestions());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
