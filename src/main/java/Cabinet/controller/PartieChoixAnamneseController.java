package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.Anamnese;
import Cabinet.models.DossierPatient;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PartieChoixAnamneseController {

    @FXML
    private JFXTextArea numerosArea;

    private Anamnese anamnese;

    private DossierPatient dossier;

    public void setDossier(DossierPatient selectedDossier) {
        this.dossier = selectedDossier;
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        if(dossier != null) {
            anamnese = CabinetHolder.getInstance().getCabinetOrthophonique().getAnamneseParNumero(Integer.parseInt(numerosArea.getText()));
            if (anamnese != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartieAnamnese.fxml"));
                    Parent root = loader.load();
                    PartieAnamneseController controller = loader.getController();
                    controller.setDossier(dossier);
                    controller.setAnamnese(anamnese);
                    controller.initializeTable();


                    Stage stage = new Stage();
                    stage.setTitle("Anamnese");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setScene(new Scene(root, 1534, 800));
                    stage.showAndWait();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Entrez un numéro d'anamnese valide.");
                alert.showAndWait();
            }
        }
    }
}
