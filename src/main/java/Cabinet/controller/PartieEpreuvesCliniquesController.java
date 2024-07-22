package Cabinet.controller;

import Cabinet.models.*;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartieEpreuvesCliniquesController {

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> columnEnonce;
    @FXML
    private TableColumn<Item, String> columnPropositions;
    @FXML
    private TableColumn<Item, Integer> columnScore;
    @FXML
    private JFXTextArea observationsArea;
    @FXML
    private JFXTextArea conclusionArea;

    private DossierPatient dossier;
    private Test currentTest;
    private Anamnese anamnese;

    public void setDossier(DossierPatient dossier) {
        this.dossier = dossier;
    }

    public void setTest(Test test) {
        this.currentTest = test;
        initializeTable();
    }

    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
    }

    private void initializeTable() {
        tableView.setEditable(true); // Enable editing

        columnEnonce.setCellValueFactory(new PropertyValueFactory<>("enonce"));
        columnPropositions.setCellValueFactory(new PropertyValueFactory<>("propositions"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        columnEnonce.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPropositions.setCellFactory(TextFieldTableCell.forTableColumn());
        columnScore.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        columnEnonce.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setEnonce(event.getNewValue());
        });

        columnPropositions.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setPropositions(event.getNewValue());
        });

        columnScore.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setScore(event.getNewValue());
        });

        ObservableList<Item> items = FXCollections.observableArrayList();
        if (currentTest instanceof TestExercices) {
            TestExercices testExercices = (TestExercices) currentTest;
            for (Exercice exercice : testExercices.getExercices()) {
                items.add(new Item(exercice.getConsigne(), null, exercice.getMateriel(), 0, "Exercice"));
            }
        } else if (currentTest instanceof TestQuestions) {
            TestQuestions testQuestions = (TestQuestions) currentTest;
            String propositions = "Pas de propositions";
            for (Question question : testQuestions.getQuestions()) {
                if (question instanceof QCM) {
                    propositions = String.join(", ", ((QCM) question).getPropositions());
                    items.add(new Item(question.getEnonce(), propositions, "", 0, "QCM"));
                } else if (question instanceof QCU) {
                    propositions = String.join(", ", ((QCU) question).getPropositions());
                    items.add(new Item(question.getEnonce(), propositions, "", 0, "QCU"));
                } else {
                    items.add(new Item(question.getEnonce(), propositions, "", 0, "QL"));
                }
            }
        }
        tableView.setItems(items);
    }

    @FXML
    private void handleSuivant(ActionEvent event) {
        List<Item> items = tableView.getItems();
        String conclusion = conclusionArea.getText();
        String[] observationsArray = observationsArea.getText().split("#");
        List<String> observations = new ArrayList<>(List.of(observationsArray));

        if (currentTest instanceof TestExercices) {
            Map<Exercice, Integer> scores = new HashMap<>();
            for (Item item : items) {
                scores.put(new Exercice(item.getEnonce(), item.getMateriel()), item.getScore());
            }
            CompteRenduExercices compteRendu = new CompteRenduExercices(currentTest, conclusion, scores);
            EpreuveClinique epreuveClinique = new EpreuveClinique(observations, Map.of(currentTest, compteRendu));
            diagnostic(event, epreuveClinique);
        } else if (currentTest instanceof TestQuestions) {
            Map<Question, Integer> scores = new HashMap<>();
            for (Item item : items) {
                if (item.type.equals("QCM")) {
                    scores.put(new QCM(item.getEnonce(), List.of(item.getPropositions().split(", "))), item.getScore());
                } else if (item.type.equals("QCU")) {
                    scores.put(new QCU(item.getEnonce(), List.of(item.getPropositions().split(", "))), item.getScore());
                } else {
                    scores.put(new QL(item.getEnonce()), item.getScore());
                }
            }
            CompteRenduQuestions compteRendu = new CompteRenduQuestions(currentTest, conclusion, scores);
            EpreuveClinique epreuveClinique = new EpreuveClinique(observations, Map.of(currentTest, compteRendu));
            diagnostic(event, epreuveClinique);
        }
    }

    private void diagnostic(ActionEvent event, EpreuveClinique epreuveClinique) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Diagnostic.fxml"));
            Parent root = loader.load();

            DiagnosticController controller = loader.getController();
            controller.setEpreuveClinique(epreuveClinique);
            controller.setDossier(dossier);
            controller.setAnamnese(anamnese);

            Stage stage = new Stage();
            stage.setTitle("Diagnostic");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root, 1534, 800));

            stage.showAndWait();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setReponses(List<String> reponses) {
        reponses = reponses;
    }

    public static class Item {
        private String enonce;
        private String propositions;
        private String materiel;
        private int score;
        private String type;

        public Item(String enonce, String propositions, String materiel, int score, String type) {
            this.enonce = enonce;
            this.propositions = propositions;
            this.materiel = materiel;
            this.score = score;
            this.type = type;
        }

        public String getEnonce() {
            return enonce;
        }

        public void setEnonce(String enonce) {
            this.enonce = enonce;
        }

        public String getPropositions() {
            return propositions;
        }

        public void setPropositions(String propositions) {
            this.propositions = propositions;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getMateriel() {
            return materiel;
        }

        public void setMateriel(String materiel) {
            this.materiel = materiel;
        }
    }
}
