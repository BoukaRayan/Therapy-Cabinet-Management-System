package Cabinet.controller;

import Cabinet.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartieAnamneseController {

    @FXML
    private TableView<QLAnamnese> tableView;
    @FXML
    private TableColumn<QLAnamnese, String> columnEnonce;
    @FXML
    private TableColumn<QLAnamnese, String> columnCategorie;
    @FXML
    private TextArea reponseInput;

    private DossierPatient dossier;
    private Anamnese anamnese;
    private Map<QLAnamnese, String> reponses = new HashMap<>();

    public void setDossier(DossierPatient dossier) {
        this.dossier = dossier;
    }

    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
        initializeTable(); // Ensure table is initialized when anamnese is set
    }

    @FXML
    void initializeTable() {
        if (anamnese == null) {
            return;
        }

        tableView.setEditable(false);

        columnEnonce.setCellValueFactory(new PropertyValueFactory<>("enonce"));
        columnCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        for (QLAnamnese q : anamnese.getQuestions()) {
            reponses.putIfAbsent(q, ""); // Ensure all questions have a corresponding response entry
        }

        ObservableList<QLAnamnese> items = FXCollections.observableArrayList(anamnese.getQuestions());
        tableView.setItems(items);
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        String inputText = reponseInput.getText();
        if (inputText != null && !inputText.trim().isEmpty()) {
            String[] responsesArray = inputText.split("#");
            List<String> reponsesList = new ArrayList<>();
            for (String response : responsesArray) {
                reponsesList.add(response.trim());
            }
            epreuvescliniques(event, reponsesList);
        } else {
            epreuvescliniques(event, new ArrayList<>()); // Handle the case where no responses are provided
        }
    }

    private void epreuvescliniques(ActionEvent event, List<String> reponsesList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartieChoixEpreuvesCliniques.fxml"));
            Parent root = loader.load();

            PartieChoixEpreuvesCliniquesController controller = loader.getController();
            controller.setDossier(dossier);
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
