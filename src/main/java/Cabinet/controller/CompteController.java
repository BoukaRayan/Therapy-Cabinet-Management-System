package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.CompteOrthophoniste;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CompteController {

    @FXML
    private TableView<CompteOrthophoniste> tableView;

    @FXML
    private TableColumn<CompteOrthophoniste, String> nomColumn;

    @FXML
    private TableColumn<CompteOrthophoniste, String> prenomColumn;

    @FXML
    private TableColumn<CompteOrthophoniste, String> adresseColumn;

    @FXML
    private TableColumn<CompteOrthophoniste, String> telephoneColumn;

    @FXML
    private TableColumn<CompteOrthophoniste, String> emailColumn;

    @FXML
    private Button deleteButton;

    private CabinetOrthophonique cabinet;

    private ObservableList<CompteOrthophoniste> comptesList;

    @FXML
    public void initialize() {
        // Définir la hauteur des lignes
        tableView.setRowFactory(tv -> {
            TableRow row = new TableRow();
            row.setPrefHeight(50); // Ajustez cette valeur selon vos besoins
            return row;
        });

        // Configurer les colonnes pour utiliser des cellules textuelles avec la hauteur préférée
        nomColumn.setCellFactory(column -> new TextFieldTableCell() {{
            setStyle("-fx-pref-height: 50px;");
        }});
        prenomColumn.setCellFactory(column -> new TextFieldTableCell() {{
            setStyle("-fx-pref-height: 50px;");
        }});
        adresseColumn.setCellFactory(column -> new TextFieldTableCell() {{
            setStyle("-fx-pref-height: 50px;");
        }});
        telephoneColumn.setCellFactory(column -> new TextFieldTableCell() {{
            setStyle("-fx-pref-height: 50px;");
        }});
        emailColumn.setCellFactory(column -> new TextFieldTableCell() {{
            setStyle("-fx-pref-height: 50px;");
        }});
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();
        comptesList = FXCollections.observableArrayList(cabinet.getComptesOrthophonistes());

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.setItems(comptesList);

    }


    @FXML
    public void handleDelete() {
        CompteOrthophoniste selectedCompte = tableView.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            cabinet.supprimerCompte(selectedCompte);
            comptesList.remove(selectedCompte);
        }
        else{
            showAlert("Erreur", "Veuillez sélectionner un compte à supprimer");
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}