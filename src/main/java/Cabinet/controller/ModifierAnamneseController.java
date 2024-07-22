package Cabinet.controller;

import Cabinet.models.QLAnamnese;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ModifierAnamneseController {

    @FXML
    private TextArea enonceField;

    private QLAnamnese questionToModify;
    private QuestionAnamneseController anamneseController;

    public void setQuestion(QLAnamnese question, QuestionAnamneseController anamneseController) {
        this.questionToModify = question;
        this.anamneseController = anamneseController;
        enonceField.setText(question.getEnonce());
    }

    @FXML
    private void handleEnregistrer() {
        questionToModify.setEnonce(enonceField.getText());
        //anamneseController.refreshTable();
        Stage stage = (Stage) enonceField.getScene().getWindow();
        stage.close();
    }
}