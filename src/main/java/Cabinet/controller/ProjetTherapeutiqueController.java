package Cabinet.controller;

import Cabinet.models.*;
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
import java.util.List;

public class ProjetTherapeutiqueController {
    @FXML
    private JFXTextArea numerosArea;

    private DossierPatient dossier;

    private Anamnese anamnese;

    private EpreuveClinique epreuveClinique;

    private List<Trouble> diagnostic;

    private BilanOrthophonique bo;

    public void setDiagnostic(List<Trouble> diagnostic) {
        this.diagnostic = diagnostic;
    }

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
        if(dossier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResultatBO.fxml"));
                Parent root = loader.load();
                ResultatBOController controller = loader.getController();
                if(anamnese == null) {
                    bo = new BilanOrthophonique(diagnostic, numerosArea.getText());
                    bo.ajouterEpreuveClinique(epreuveClinique);
                }else {
                    bo = new PremierBilanOrthophonique(diagnostic, numerosArea.getText(), anamnese);
                    bo.ajouterEpreuveClinique(epreuveClinique);
                }
                controller.setBO(bo);
                dossier.ajouterBilanOrthophonique(bo);

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
