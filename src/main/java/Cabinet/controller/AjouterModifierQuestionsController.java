package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.QCM;
import Cabinet.models.QCU;
import Cabinet.models.QL;
import Cabinet.models.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class AjouterModifierQuestionsController {

    @FXML
    private TextField enonceField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextArea propositionsArea;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Stage stage;
    private Question question;
    private boolean saved = false;
    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        typeChoiceBox.getItems().addAll("QCM", "QCU", "QL");
        typeChoiceBox.setOnAction(e -> togglePropositionsArea());

        saveButton.setOnAction(e -> saveQuestion());
        cancelButton.setOnAction(e -> stage.close());
    }

    private void togglePropositionsArea() {
        String selectedType = typeChoiceBox.getValue();
        propositionsArea.setDisable("QL".equals(selectedType));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setQuestion(Question question) {
        this.question = question;
        if (question != null) {
            enonceField.setText(question.getEnonce());
            if (question instanceof QCM) {
                typeChoiceBox.setValue("QCM");
                propositionsArea.setText(String.join("#", ((QCM) question).getPropositions()));
            } else if (question instanceof QCU) {
                typeChoiceBox.setValue("QCU");
                propositionsArea.setText(String.join("#", ((QCU) question).getPropositions()));
            } else {
                typeChoiceBox.setValue("QL");
                propositionsArea.setText("");
            }
            togglePropositionsArea();
        }
    }

    private void saveQuestion() {
        String enonce = enonceField.getText();
        String type = typeChoiceBox.getValue();
        String[] propositions = propositionsArea.getText().split("#");

        if (question == null) {
            if ("QCM".equals(type)) {
                question = new QCM(enonce, List.of(propositions));
            } else if ("QCU".equals(type)) {
                question = new QCU(enonce, List.of(propositions));
            } else {
                question = new QL(enonce);
            }
            cabinet.ajouterQuestion(question);
        } else {
            question.setEnonce(enonce);
            if (question instanceof QCM) {
                ((QCM) question).setPropositions(List.of(propositions));
            } else if (question instanceof QCU) {
                ((QCU) question).setPropositions(List.of(propositions));
            }
        }

        saved = true;
        stage.close();
    }

    public boolean isSaved() {
        return saved;
    }
}
