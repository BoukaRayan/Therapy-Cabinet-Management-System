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
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PartieChoixEpreuvesCliniquesController {

    @FXML
    private JFXTextArea numerosArea;

    private DossierPatient dossier;

    private Anamnese anamnese;

    public void setDossier(DossierPatient selectedDossier) {
        this.dossier = selectedDossier;
    }

    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        if(dossier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartieEpreuvesCliniques.fxml"));
                Parent root = loader.load();
                PartieEpreuvesCliniquesController controller = loader.getController();
                controller.setDossier(dossier);
                controller.setTest(CabinetHolder.getInstance().getCabinetOrthophonique().getTestParNumero(Integer.parseInt(numerosArea.getText().split("#")[0])));
                controller.setAnamnese(anamnese);


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
