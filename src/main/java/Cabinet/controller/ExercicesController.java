package Cabinet.controller;

import Cabinet.holder.CabinetHolder;
import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.Exercice;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ExercicesController {

    @FXML
    private TableView<Exercice> tableView;

    @FXML
    private TableColumn<Exercice, String> consigneColumn;

    @FXML
    private TableColumn<Exercice, String> materielColumn;

    @FXML
    private TableColumn<Exercice, String> numeroColumn;

    @FXML
    private TextField consigneField;

    @FXML
    private TextField materielField;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    private ObservableList<Exercice> exercicesList;

    private CabinetOrthophonique cabinet;

    @FXML
    public void initialize() {
        cabinet = CabinetHolder.getInstance().getCabinetOrthophonique();

        numeroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumero())));
        consigneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getConsigne()));
        materielColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMateriel()));

        exercicesList = FXCollections.observableArrayList(cabinet.getExercices());
        tableView.setItems(exercicesList);

        ajouterButton.setOnAction(e -> ajouterExercice());
        modifierButton.setOnAction(e -> modifierExercice());
        supprimerButton.setOnAction(e -> supprimerExercice());
    }

    private void ajouterExercice() {
        String consigne = consigneField.getText();
        String materiel = materielField.getText();

        Exercice exercice = new Exercice(consigne, materiel);
        exercicesList.add(exercice);
        cabinet.ajouterExercice(exercice);

        consigneField.clear();
        materielField.clear();
    }

    private void modifierExercice() {
        Exercice selectedExercice = tableView.getSelectionModel().getSelectedItem();
        if (selectedExercice != null) {
            selectedExercice.setConsigne(consigneField.getText());
            selectedExercice.setMateriel(materielField.getText());
            tableView.refresh();
        }
    }

    private void supprimerExercice() {
        Exercice selectedExercice = tableView.getSelectionModel().getSelectedItem();
        if (selectedExercice != null) {
            exercicesList.remove(selectedExercice);
            cabinet.supprimerExercice(selectedExercice);
        }
    }
}
