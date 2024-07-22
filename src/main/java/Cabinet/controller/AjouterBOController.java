package Cabinet.controller;

import Cabinet.models.DossierPatient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterBOController {

    private DossierPatient dossier;

    public void setDossier(DossierPatient selectedDossier) {
        this.dossier = selectedDossier;
    }

    @FXML
    private void handleCommencer(ActionEvent event) {
        if(dossier != null) {
            if(dossier.getBilansOrthophoniques().isEmpty()){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartieChoixAnamnese.fxml"));
                    Parent root = loader.load();
                    PartieChoixAnamneseController controller = loader.getController();
                    controller.setDossier(dossier);

                    Stage stage = new Stage();
                    stage.setTitle("Anamnese");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root, 1534, 800));
                    stage.showAndWait();
                    /*Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartieChoixEpreuvesCliniques.fxml"));
                    Parent root = loader.load();
                    PartieChoixEpreuvesCliniquesController controller = loader.getController();
                    controller.setDossier(dossier);
                    controller.setAnamnese(null);

                    Stage stage = new Stage();
                    stage.setTitle("Epreuves Cliniques");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root, 1534, 800));
                    stage.showAndWait();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
