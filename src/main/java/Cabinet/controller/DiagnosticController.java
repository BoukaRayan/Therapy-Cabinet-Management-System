package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.Anamnese;
import Cabinet.models.DossierPatient;
import Cabinet.models.EpreuveClinique;
import Cabinet.models.Trouble;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticController {

    @FXML
    private JFXTextArea numerosArea;

    private DossierPatient dossier;

    private Anamnese anamnese;

    private EpreuveClinique epreuveClinique;

    public void setEpreuveClinique(EpreuveClinique epreuveClinique) {
        this.epreuveClinique = epreuveClinique;
    }

    public void setDossier(DossierPatient selectedDossier) {
        this.dossier = selectedDossier;
    }

    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        if (dossier != null) {
            String numerosText = numerosArea.getText();
            String[] numerosArray = numerosText.split("#");
            List<Trouble> troublesList = new ArrayList<>();
            for (String numeroStr : numerosArray) {
                int numero = Integer.parseInt(numeroStr.trim());
                Trouble trouble = CabinetHolder.getInstance().getCabinetOrthophonique().getTroubleParNumero(numero);
                if (trouble != null) {
                    troublesList.add(trouble);
                }
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProjetTherapeutique.fxml"));
                Parent root = loader.load();
                ProjetTherapeutiqueController controller = loader.getController();
                controller.setDossier(dossier);
                controller.setAnamnese(anamnese);
                controller.setEpreuveClinique(epreuveClinique);
                controller.setDiagnostic(troublesList);

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
