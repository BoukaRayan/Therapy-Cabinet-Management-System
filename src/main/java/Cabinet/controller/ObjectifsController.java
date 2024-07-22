package Cabinet.controller;

import Cabinet.models.FicheSuivi;
import Cabinet.models.Objectif;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ObjectifsController {

    @FXML
    private TableView<Map.Entry<Objectif, Integer>> tableViewObjectifs;
    @FXML
    private TableColumn<Map.Entry<Objectif, Integer>, String> nomColumn;
    @FXML
    private TableColumn<Map.Entry<Objectif, Integer>, String> typeColumn;
    @FXML
    private TableColumn<Map.Entry<Objectif, Integer>, String> scoreColumn;

    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;

    private FicheSuivi ficheSuivi;

    private ObservableList<Map.Entry<Objectif, Integer>> observableObjectifsList;

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNom()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getType().toString()));
        scoreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue())));

        ajouterButton.setOnAction(event -> ajouterObjectif());
        modifierButton.setOnAction(event -> modifierObjectif());
        supprimerButton.setOnAction(event -> supprimerObjectif());
    }

    public void setFicheSuivi(FicheSuivi ficheSuivi) {
        this.ficheSuivi = ficheSuivi;
        observableObjectifsList = FXCollections.observableArrayList(ficheSuivi.getObjectifs());
        tableViewObjectifs.setItems(observableObjectifsList);
    }

    private void ajouterObjectif() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterModifierObjectif.fxml"));
            System.out.println("TEST OBJ 1");
            Parent root = loader.load();
            System.out.println("TEST OBJ 2");
            AjouterModifierObjectifController controller = loader.getController();
            controller.setFicheSuivi(ficheSuivi);

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Objectif");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableViewObjectifs.getScene().getWindow());
            stage.setScene(new Scene(root, 400, 300));
            stage.showAndWait();
            observableObjectifsList.setAll(ficheSuivi.getObjectifs());
            tableViewObjectifs.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifierObjectif() {
        Map.Entry<Objectif, Integer> selectedObjectif = tableViewObjectifs.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterModifierObjectif.fxml"));
                Parent root = loader.load();
                AjouterModifierObjectifController controller = loader.getController();
                controller.setFicheSuivi(ficheSuivi);
                controller.setObjectif(selectedObjectif.getKey(), selectedObjectif.getValue());

                Stage stage = new Stage();
                stage.setTitle("Modifier un Objectif");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableViewObjectifs.getScene().getWindow());
                stage.setScene(new Scene(root, 400, 300));
                stage.showAndWait();
                observableObjectifsList.setAll(ficheSuivi.getObjectifs());
                tableViewObjectifs.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void supprimerObjectif() {
        Map.Entry<Objectif, Integer> selectedObjectif = tableViewObjectifs.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            ficheSuivi.supprimerObjectif(selectedObjectif.getKey());
            observableObjectifsList.remove(selectedObjectif);
            tableViewObjectifs.refresh();
        }
    }
}
