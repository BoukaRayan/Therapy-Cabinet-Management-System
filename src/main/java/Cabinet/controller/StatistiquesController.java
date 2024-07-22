package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Cabinet.models.enums.CategorieTrouble;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatistiquesController {

    @FXML
    private TableView<Trouble> troublesTable;

    @FXML
    private TableColumn<Trouble, String> numeroColumn;

    @FXML
    private TableColumn<Trouble, String> nomColumn;

    @FXML
    private TableColumn<Trouble, String> pourcentageColumn;

    private ObservableList<Trouble> troublesList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        pourcentageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", pourcentage(cellData.getValue())) + "%"));

        troublesList = FXCollections.observableArrayList(cabinet.getTroubles());
        troublesTable.setItems(troublesList);

        troublesTable.setRowFactory(tv -> {
            TableRow<Trouble> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Trouble rowData = row.getItem();
                    // Open an alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Patients souffrant du trouble");
                    alert.setHeaderText(null);
                    StringBuilder str = new StringBuilder();
                    for(String patient : patientsTrouble(rowData)){
                        str.append(patient).append("\n");
                    }
                    alert.setContentText(str.toString());
                    alert.showAndWait();
                }
            });
            return row;
        });
    }

    @FXML
    private void refreshTroublesList() {
        // Rafraîchir la liste des troubles affichée dans la table
        troublesList.setAll(cabinet.getTroubles());
    }

    public double pourcentage(Trouble trouble){
        int nbDossier = cabinet.getDossiersPatients().size();
        boolean stop = false;
        int nbDossierTrouble = 0;
        for(DossierPatient dossier : cabinet.getDossiersPatients()){
            for (BilanOrthophonique bo : dossier.getBilansOrthophoniques()){
                if(stop) {
                    stop = false;
                    break;
                }
                for (Trouble t : bo.getDiagnostic()){
                    if(t.equals(trouble)){
                        nbDossierTrouble++;
                        stop = true;
                        break;
                    }
                }

            }
        }
        return ((double) nbDossierTrouble /nbDossier)*100;
    }

    public List<String> patientsTrouble(Trouble trouble){
        List<String> patients = new ArrayList<>();
        boolean stop = false;
        for(DossierPatient dossier : cabinet.getDossiersPatients()){
            for (BilanOrthophonique bo : dossier.getBilansOrthophoniques()){
                if(stop) {
                    stop = false;
                    break;
                }
                for (Trouble t : bo.getDiagnostic()){
                    if(t.equals(trouble)){
                        patients.add(dossier.getInfos().getNom() + " " + dossier.getInfos().getPrenom());
                        stop = true;
                        break;
                    }
                }
            }
        }
        return patients;

    }
}