package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.Atelier;
import Cabinet.models.DossierPatient;
import Cabinet.models.Suivi;
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
import Cabinet.models.RendezVous;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RendezVousPatientController {

    @FXML
    private TableView<RendezVous> tableView;
    @FXML
    private TableColumn<RendezVous, String> idColumn;
    @FXML
    private TableColumn<RendezVous, String> dateColumn;
    @FXML
    private TableColumn<RendezVous, String> heureColumn;
    @FXML
    private TableColumn<RendezVous, String> typeColumn;
    @FXML
    private TableColumn<RendezVous, String> observationColumn;

    private DossierPatient dossier;

    public void setDossier(DossierPatient selectedDossier) {
        this.dossier = selectedDossier;
        System.out.println("Dossier: " + dossier);
    }

    private ObservableList<RendezVous> observableRendezVousList;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du TableView
        idColumn.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getId();
            String idString = String.valueOf(id);
            return new SimpleStringProperty(idString);
        });
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getDate();
            LocalDate date = dateTime.toLocalDate(); // Extraire la date sans l'heure
            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Formatage de la date (ex: 2024-05-26)
            return new SimpleStringProperty(formattedDate);
        });
        heureColumn.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getDate();
            String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm")); // Formatage de l'heure (ex: 14:30)
            return new SimpleStringProperty(formattedTime);
        });

        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        observationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservation()));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);

        List<RendezVous> rendezVousList = CabinetHolder.getInstance().getCabinetOrthophonique().getRdvParDossier(dossier);

        observableRendezVousList = FXCollections.observableArrayList(rendezVousList);
        tableView.setItems(observableRendezVousList);
    }
}
