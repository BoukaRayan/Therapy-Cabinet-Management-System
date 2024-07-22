package Cabinet.controller;

import Cabinet.models.*;
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
import java.util.List;

public class FichesSuiviController {

    @FXML
    private TableView<FicheSuivi> tableView;
    @FXML
    private TableColumn<FicheSuivi, String> nomColumn;
    @FXML
    private TableColumn<FicheSuivi, String> idColumn;

    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private Button voirFichesButton;

    private DossierPatient dossier;

    private ObservableList<FicheSuivi> observableFichesList;

    @FXML
    public void initializetable() {

        idColumn.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getNumero();
            String idString = String.valueOf(id);
            return new SimpleStringProperty(idString);
        });
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);

        List<FicheSuivi> fichesList = dossier.getFichesSuivi();
        System.out.println(fichesList.size());

        observableFichesList = FXCollections.observableArrayList(fichesList);
        tableView.setItems(observableFichesList);

        ajouterButton.setOnAction(event -> ajouterFiche());
        modifierButton.setOnAction(event -> detailsFiche(tableView.getSelectionModel().getSelectedItem()));
        supprimerButton.setOnAction(event -> supprimerPatient());

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FicheSuivi selectedFiche = tableView.getSelectionModel().getSelectedItem();
                if (selectedFiche != null) {
                    detailsFiche(selectedFiche);
                }
            }
        });
    }

    private void detailsFiche(FicheSuivi ficheSuivi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Objectifs.fxml"));
            Parent root = loader.load();
            ObjectifsController controller = loader.getController();
            controller.setFicheSuivi(ficheSuivi);

            Stage stage = new Stage();
            stage.setTitle("Gestion des Objectifs");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setScene(new Scene(root, 650, 563));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ajouterFiche() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterFicheSuivi.fxml"));
            Parent root = loader.load();
            AjouterFicheSuiviController controller = loader.getController();
            controller.setDossier(dossier);

            Stage stage = new Stage();
            stage.setTitle("Ajout d'une fiche de suivi");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setScene(new Scene(root, 650, 200));
            stage.showAndWait();
            observableFichesList.add(controller.getFiche());
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void supprimerPatient() {
        FicheSuivi selectedFiche = tableView.getSelectionModel().getSelectedItem();
        if (selectedFiche != null) {
            observableFichesList.remove(selectedFiche);
            dossier.supprimerFicheSuivi(selectedFiche);
        }
    }

    public void setDossier(DossierPatient dossier) {
        System.out.println("Setting Dossier: " + dossier); // Debug statement
        this.dossier = dossier;
    }

}
