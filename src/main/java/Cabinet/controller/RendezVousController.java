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

public class RendezVousController {

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

    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;

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

        List<RendezVous> rendezVousList = CabinetHolder.getInstance().getCabinetOrthophonique().getRdv();

        observableRendezVousList = FXCollections.observableArrayList(rendezVousList);
        tableView.setItems(observableRendezVousList);

        ajouterButton.setOnAction(event -> ajouterRendezVous());
        modifierButton.setOnAction(event -> modifierRendezVous());
        supprimerButton.setOnAction(event -> supprimerRendezVous());
    }

    private void ajouterRendezVous() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterRendezVous.fxml"));
            Parent root = loader.load();
            AjouterRendezVousController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Ajout d'un rendez-vous");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setScene(new Scene(root, 650, 700));
            stage.showAndWait();

            RendezVous newRendezVous = controller.getRendezVous();
            if (newRendezVous != null) {
                if (CabinetHolder.getInstance().getCabinetOrthophonique().isRendezVousExistant(newRendezVous)) {
                    afficherMessageErreur("Un rendez-vous existe déjà avec la même date et heure de début.");
                    return;
                }
                if (newRendezVous instanceof Atelier) {
                    List<String> numerosDossiersParticipants = ((Atelier) newRendezVous).getParticipants();
                    for (String numeroDossierStr : numerosDossiersParticipants) {
                        int numeroDossier;
                        numeroDossier = Integer.parseInt(numeroDossierStr);
                        if (!CabinetHolder.getInstance().getCabinetOrthophonique().dossierPatientExiste(numeroDossier)){
                            afficherMessageErreur("Le dossier patient avec le numéro " + numeroDossier + " n'existe pas.");
                            return;
                        }
                    }
                    CabinetHolder.getInstance().getCabinetOrthophonique().addRdv(newRendezVous);
                    observableRendezVousList.add(newRendezVous);
                    for(String numeroDossierStr : numerosDossiersParticipants){
                        int numeroDossier = Integer.parseInt(numeroDossierStr);
                        CabinetHolder.getInstance().getCabinetOrthophonique().ajouterRendezVous(newRendezVous, numeroDossier);
                    }
                    showAlert("Confirmation", "Rendez-vous enregistré avec succès !");

                } else
                if (newRendezVous instanceof Suivi) {
                    if (CabinetHolder.getInstance().getCabinetOrthophonique().dossierPatientExiste(((Suivi) newRendezVous).getNumeroDossier())) {
                        CabinetHolder.getInstance().getCabinetOrthophonique().addRdv(newRendezVous);
                        observableRendezVousList.add(newRendezVous);
                        CabinetHolder.getInstance().getCabinetOrthophonique().ajouterRendezVous(newRendezVous, ((Suivi) newRendezVous).getNumeroDossier());
                        showAlert("Confirmation", "Rendez-vous enregistré avec succès !");
                    } else {
                        afficherMessageErreur("Le dossier patient avec le numéro " + ((Suivi) newRendezVous).getNumeroDossier() + " n'existe pas.");
                        return;
                    }
                }
                else {
                    CabinetHolder.getInstance().getCabinetOrthophonique().addRdv(newRendezVous);
                    observableRendezVousList.add(newRendezVous);
                    showAlert("Confirmation", "Rendez-vous enregistré avec succès !");
                }

                tableView.refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierRendezVous() {
        RendezVous selectedRendezVous = tableView.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterRendezVous.fxml"));
                Parent root = loader.load();
                AjouterRendezVousController controller = loader.getController();
                controller.setRendezVous(selectedRendezVous);

                Stage stage = new Stage();
                stage.setTitle("Modification d'un rendez-vous");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root, 650, 700));
                stage.showAndWait();


                RendezVous updatedRendezVous = controller.getRendezVous();
                if (updatedRendezVous != null) {
                    boolean dateTimeChanged = !updatedRendezVous.getDate().equals(selectedRendezVous.getDate());
                    if (dateTimeChanged && CabinetHolder.getInstance().getCabinetOrthophonique().isRendezVousExistant(updatedRendezVous)) {
                        afficherMessageErreur("Un rendez-vous existe déjà avec la même date et heure de début.");
                        return;
                    }
                    if (updatedRendezVous instanceof Atelier || updatedRendezVous instanceof Suivi) {
                        CabinetHolder.getInstance().getCabinetOrthophonique().modifierRendezVous(selectedRendezVous, updatedRendezVous);
                        CabinetHolder.getInstance().getCabinetOrthophonique().updateRdv(selectedRendezVous, updatedRendezVous);
                        observableRendezVousList.set(observableRendezVousList.indexOf(selectedRendezVous), updatedRendezVous);
                        showAlert("Confirmation", "Rendez-vous modifié avec succès !");

                    }
                    else {
                        CabinetHolder.getInstance().getCabinetOrthophonique().updateRdv(selectedRendezVous, updatedRendezVous);
                        observableRendezVousList.set(observableRendezVousList.indexOf(selectedRendezVous), updatedRendezVous);
                        showAlert("Confirmation", "Rendez-vous modifié avec succès !");
                    }
                    tableView.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Aucun rendez-vous sélectionné", "Veuillez sélectionner un rendez-vous à modifier.");
        }
    }


    private void supprimerRendezVous() {
        RendezVous selectedRendezVous = tableView.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            observableRendezVousList.remove(selectedRendezVous);
            CabinetHolder.getInstance().getCabinetOrthophonique().removeRdv(selectedRendezVous);
            if (selectedRendezVous instanceof Suivi || selectedRendezVous instanceof Atelier) {
                CabinetHolder.getInstance().getCabinetOrthophonique().supprimerRendezVous(selectedRendezVous);
            }

        }
    }

    public void setRendezVous(List<RendezVous> rendezVousList) {

        masquerBoutons();
        observableRendezVousList = FXCollections.observableArrayList(rendezVousList);
        tableView.setItems(observableRendezVousList);
    }

    public void masquerBoutons() {
        ajouterButton.setVisible(false);
        modifierButton.setVisible(false);
        supprimerButton.setVisible(false);
    }



    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}