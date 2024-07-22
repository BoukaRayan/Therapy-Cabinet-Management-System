package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.RendezVous;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import Cabinet.models.Adulte;
import Cabinet.models.Enfant;
import Cabinet.models.DossierPatient;

import java.io.IOException;
import java.util.List;

public class DossiersPatientsController {

    @FXML
    private TableView<DossierPatient> tableView;
    @FXML
    private TableColumn<DossierPatient, String> nomColumn;
    @FXML
    private TableColumn<DossierPatient, String> prenomColumn;
    @FXML
    private TableColumn<DossierPatient, String> dateNaissanceColumn;
    @FXML
    private TableColumn<DossierPatient, String> lieuNaissanceColumn;
    @FXML
    private TableColumn<DossierPatient, String> adresseColumn;
    @FXML
    private TableColumn<DossierPatient, String> classeEtudesColumn;
    @FXML
    private TableColumn<DossierPatient, String> telephoneColumn;
    @FXML
    private TableColumn<DossierPatient, String> diplomeColumn;
    @FXML
    private TableColumn<DossierPatient, String> professionColumn;
    @FXML
    private TableColumn<DossierPatient, String> idColumn;

    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private Button voirFichesButton;
    @FXML
    private Button voirRdvsButton;

    private ObservableList<DossierPatient> observablePatientsList;

    @FXML
    public void initialize() {
        // Initialiser les colonnes du TableView
        idColumn.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getNumeroDossier();
            String idString = String.valueOf(id);
            return new SimpleStringProperty(idString);
        });
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getPrenom()));
        dateNaissanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getDateNais() != null ? cellData.getValue().getInfos().getDateNais().toString() : "/"));
        lieuNaissanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getLieuNais()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getAdresse()));
        telephoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getNumeroTel()));
        classeEtudesColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getInfos() instanceof Enfant) {
                return new SimpleStringProperty(((Enfant) cellData.getValue().getInfos()).getClasseEtudes());
            } else {
                return new SimpleStringProperty("/");
            }
        });
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfos().getNumeroTel()));
        diplomeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getInfos() instanceof Adulte) {
                return new SimpleStringProperty(((Adulte) cellData.getValue().getInfos()).getDiplome());
            } else {
                return new SimpleStringProperty("/");
            }
        });
        professionColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getInfos() instanceof Adulte) {
                return new SimpleStringProperty(((Adulte) cellData.getValue().getInfos()).getProfession());
            } else {
                return new SimpleStringProperty("/");
            }
        });

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);

        List<DossierPatient> patientsList = CabinetHolder.getInstance().getCabinetOrthophonique().getDossiersPatients();

        observablePatientsList = FXCollections.observableArrayList(patientsList);
        tableView.setItems(observablePatientsList);

        ajouterButton.setOnAction(event -> ajouterPatient());
        modifierButton.setOnAction(event -> modifierPatient());
        supprimerButton.setOnAction(event -> supprimerPatient());
        voirFichesButton.setOnAction(event -> voirFichesSuivi());
        voirRdvsButton.setOnAction(event -> voirRendezVous());
    }

    private void voirFichesSuivi(){
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FichesSuivi.fxml"));
                Parent root = loader.load();
                FichesSuiviController controller = loader.getController();
                controller.setDossier(selectedDossier);
                controller.initializetable();

                Stage stage = new Stage();
                stage.setTitle("Fiches de suivi");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root, 1534, 800));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun dossier choisi.");
            alert.showAndWait();
        }
    }

    @FXML
    private void voirBos(){
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BilansOrthophoniques.fxml"));
                Parent root = loader.load();
                BilansOrthophoniquesController controller = loader.getController();
                controller.setDossier(selectedDossier);
                controller.initializetable();

                Stage stage = new Stage();
                stage.setTitle("Bilans Orthophoniques");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root, 1534, 800));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun dossier choisi.");
            alert.showAndWait();
        }
    }

    private void voirRendezVous() {
        // Récupérer le dossier du patient sélectionné dans la table
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();

        if (selectedDossier != null) {
            // Récupérer la liste des rendez-vous du patient
            List<RendezVous> rendezVousList = selectedDossier.getRendezVous();

            try {
                // Charger le fichier FXML de la vue des rendez-vous
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RendezVous.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur de la vue
                RendezVousController controller = loader.getController();

                // Passer la liste des rendez-vous au contrôleur
                controller.setRendezVous(rendezVousList);

                // Créer une nouvelle fenêtre
                Stage stage = new Stage();
                stage.setTitle("Rendez-vous de " + selectedDossier.getInfos().getNom() + " " + selectedDossier.getInfos().getPrenom());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root,1534, 800));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun patient sélectionné");
            alert.setContentText("Veuillez sélectionner un patient pour afficher les rendez-vous.");
            alert.showAndWait();
        }
    }


    private void ajouterPatient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterPatient.fxml"));
            Parent root = loader.load();
            AjouterPatientController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Ajout d'un patient");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableView.getScene().getWindow());
            stage.setScene(new Scene(root, 650, 700));
            stage.showAndWait();
            controller.initialize();
            observablePatientsList.add(controller.getPatient());
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierPatient() {
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();
        if (selectedDossier.getInfos() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifierPatient.fxml"));
                Parent root = loader.load();
                ModifierPatientController controller = loader.getController();
                controller.setDossier(selectedDossier);
                controller.setPatient(selectedDossier.getInfos());

                Stage stage = new Stage();
                stage.setTitle("Modification des informations du patient");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root, 650, 700));
                stage.showAndWait();
                controller.initialize();
                tableView.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void supprimerPatient() {
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();
        if (selectedDossier.getInfos() != null) {
            observablePatientsList.remove(selectedDossier);
            CabinetHolder.getInstance().getCabinetOrthophonique().supprimerDossierPatient(selectedDossier);
        }
    }

    public void ajouterBo() {
        DossierPatient selectedDossier = tableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterBO.fxml"));
                Parent root = loader.load();
                AjouterBOController controller = loader.getController();
                controller.setDossier(selectedDossier);

                Stage stage = new Stage();
                stage.setTitle("Bilan Orthophonique");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(tableView.getScene().getWindow());
                stage.setScene(new Scene(root, 1534, 800));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun dossier choisi.");
            alert.showAndWait();
        }
    }
}
