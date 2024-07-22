package Cabinet.controller;

import Cabinet.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BilansOrthophoniquesController {

    @FXML
    private TableView<BilanOrthophonique> tableView;
    @FXML
    private TableColumn<BilanOrthophonique, String> premierColumn;
    @FXML
    private TableColumn<BilanOrthophonique, String> dateColumn;
    @FXML
    private TableColumn<BilanOrthophonique, String> epreuvesColumn;
    @FXML
    private TableColumn<BilanOrthophonique, String> troublesColumn;
    @FXML
    private TableColumn<BilanOrthophonique, String> projetColumn;
    @FXML
    private Button supprimerButton;
    @FXML
    private Button voirBosButton;

    private DossierPatient dossier;

    private ObservableList<BilanOrthophonique> observableBosList;

    @FXML
    public void initializetable() {
        premierColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue() instanceof PremierBilanOrthophonique ? "Oui" : "Non");
        });

        dateColumn.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDate();
            return new SimpleStringProperty(date.toString());
        });
        epreuvesColumn.setCellValueFactory(cellData -> {
            int nbNbEreuves = cellData.getValue().getEpreuvesCliniques().size();
            String nbString = String.valueOf(nbNbEreuves);
            return new SimpleStringProperty(nbString);
        });
        troublesColumn.setCellValueFactory(cellData -> {
            int nbNbTroubles = cellData.getValue().getDiagnostic().size();
            String nbString = String.valueOf(nbNbTroubles);
            return new SimpleStringProperty(nbString);
        });
        projetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProjetTherapeutique()));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);

        List<BilanOrthophonique> BosList = dossier.getBilansOrthophoniques();

        observableBosList = FXCollections.observableArrayList(BosList);
        tableView.setItems(observableBosList);

        supprimerButton.setOnAction(event -> supprimerBO());

        /*tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FicheSuivi selectedFiche = tableView.getSelectionModel().getSelectedItem();
                if (selectedFiche != null) {
                    detailsFiche(selectedFiche);
                }
            }
        });*/
    }

    /*private void detailsFiche(FicheSuivi ficheSuivi) {
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
    }*/


    private void supprimerBO() {
        BilanOrthophonique selectedBO = tableView.getSelectionModel().getSelectedItem();
        if (selectedBO != null) {
            observableBosList.remove(selectedBO);
            dossier.supprimerBilanOrthophonique(selectedBO);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Pas de bilan choisi.");
            alert.showAndWait();
        }
    }

    public void setDossier(DossierPatient dossier) {
        this.dossier = dossier;
    }

}
