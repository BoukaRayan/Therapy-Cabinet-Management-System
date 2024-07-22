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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Cabinet.models.enums.CategorieTrouble;

import java.io.IOException;

public class TroublesController {

    @FXML
    private TableView<Trouble> troublesTable;

    @FXML
    private TableColumn<Trouble, String> numeroColumn;

    @FXML
    private TableColumn<Trouble, String> nomColumn;

    @FXML
    private TableColumn<Trouble, String> categorieColumn;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    private ObservableList<Trouble> troublesList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        categorieColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCategorie() == CategorieTrouble.DEGLUTITION) {
                return new SimpleStringProperty("Deglutition");
            } else if (cellData.getValue().getCategorie() == CategorieTrouble.NEURO_DEVLOPPEMENTAL) {
                return new SimpleStringProperty("Neuro-Devloppemental");
            } else {
                return new SimpleStringProperty("Cognitif");
            }
        });

        troublesList = FXCollections.observableArrayList(cabinet.getTroubles());
        troublesTable.setItems(troublesList);

        ajouterButton.setOnAction(e -> openAjouterModifierPage(null));
        modifierButton.setOnAction(e -> openAjouterModifierPage(troublesTable.getSelectionModel().getSelectedItem()));
        supprimerButton.setOnAction(e -> supprimerTrouble());
    }


    private void supprimerTrouble() {
        Trouble selectedTrouble = troublesTable.getSelectionModel().getSelectedItem();
        if (selectedTrouble != null) {
            troublesList.remove(selectedTrouble);
            cabinet.supprimerTrouble(selectedTrouble);
        }
    }

    private void openAjouterModifierPage(Trouble trouble) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterModifierTroubles.fxml"));
            Parent root = loader.load();

            AjouterModifierTroublesController controller = loader.getController();

            controller.initData(trouble);

            Stage stage = new Stage();
            stage.setTitle("Ajouter/Modifier Question");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.showAndWait();

            if (controller.isSaved()) {
                refreshTroublesList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void refreshTroublesList() {
        // Rafraîchir la liste des troubles affichée dans la table
        troublesList.setAll(cabinet.getTroubles());
    }

}