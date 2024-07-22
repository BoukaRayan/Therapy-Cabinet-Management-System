package Cabinet.controller;

import Cabinet.models.BilanOrthophonique;
import Cabinet.models.CompteRendu;
import Cabinet.models.PremierBilanOrthophonique;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ResultatBOController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label projetTherapeutiqueLabel;

    @FXML
    private Label premierLabel;

    private BilanOrthophonique bo;

    public void setBO(BilanOrthophonique bo) {
        this.bo = bo;
        updateLabels();
    }

    private void updateLabels() {
        if (bo != null) {
            premierLabel.setText("Premier bilan orthophonique: " + (bo instanceof PremierBilanOrthophonique ? "Oui" : "Non"));
            dateLabel.setText("Date: " + bo.getDate());
            double score = ((CompteRendu) new ArrayList<>(bo.getEpreuvesCliniques().getFirst().getTests().values()).getFirst()).calculerScore();
            scoreLabel.setText("Score: " + String.format("%.2f", score));
            projetTherapeutiqueLabel.setText("Projet thérapeutique: " + bo.getProjetTherapeutique());
        }
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
