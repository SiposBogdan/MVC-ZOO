package view;

import controller.DTO.ExemplarDTO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Observable;
import model.Observer;
import model.viewmodel.ExemplarViewModel;

import java.util.List;

public class ExemplarView implements Observer {
    public final TableView<ExemplarDTO> exemplarTable = new TableView<>();
    public final TableColumn<ExemplarDTO, Integer> idColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, String> locatieColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, String> imagineUrlColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, Integer> animalIdColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, Integer> varstaColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, Double> greutateColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, String> specieColumn = new TableColumn<>();
    public final TableColumn<ExemplarDTO, String> numeColumn = new TableColumn<>();

    public final TextField locatieField = new TextField();
    public final TextField imagineUrlField = new TextField();
    public final TextField numeField = new TextField();

    public final TextField animalIdField = new TextField();
    public final TextField greutateField = new TextField();
    public final TextField varstaField = new TextField();
    public final TextField searchSpecieField = new TextField();
    public final Button searchButton = new Button();

    public final Button addButton = new Button();
    public final Button updateButton = new Button();
    public final Button deleteButton = new Button();
    public final Button clearButton = new Button();

    public final Label messageLabel = new Label();
    public final Label locatieLabel = new Label();
    public final Label imagineUrlLabel = new Label();
    public final Label greutateLabel = new Label();
    public final Label varstaLabel = new Label();
    public final Label numeLabel = new Label();
    public final Label languageLabel = new Label();
    public final Label specieLabel = new Label();
    public final Label animalIdLabel = new Label();

    public final ToggleGroup languageToggleGroup = new ToggleGroup();
    public final RadioButton englishButton = new RadioButton();
    public final RadioButton frenchButton = new RadioButton();
    public final RadioButton romanianButton = new RadioButton();

    public final VBox root = new VBox();

    public ExemplarView() {
        setupLayout();
    }

    private void setupLayout() {
        exemplarTable.getColumns().addAll(idColumn, numeColumn, specieColumn, locatieColumn, imagineUrlColumn, animalIdColumn, greutateColumn, varstaColumn);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.addRow(1, numeLabel, numeField);
        form.addRow(2, locatieLabel, locatieField);
        form.addRow(3, imagineUrlLabel, imagineUrlField);
        form.addRow(4, animalIdLabel, animalIdField);
        form.addRow(5, greutateLabel, greutateField);
        form.addRow(6, varstaLabel, varstaField);

        HBox buttons = new HBox(10, addButton, updateButton, deleteButton, clearButton);

        englishButton.setToggleGroup(languageToggleGroup);
        frenchButton.setToggleGroup(languageToggleGroup);
        romanianButton.setToggleGroup(languageToggleGroup);

        englishButton.setUserData(java.util.Locale.ENGLISH);
        frenchButton.setUserData(java.util.Locale.FRENCH);
        romanianButton.setUserData(java.util.Locale.forLanguageTag("ro"));

        englishButton.setSelected(true);

        HBox languageSelector = new HBox(10, languageLabel, englishButton, frenchButton, romanianButton);
        languageSelector.setPadding(new Insets(5, 0, 5, 0));

        HBox searchBox = new HBox(10, specieLabel, searchSpecieField, searchButton);
        root.getChildren().add(searchBox);

        root.getChildren().addAll(languageSelector, exemplarTable, form, buttons, messageLabel);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
    }

    public VBox getView() {
        return root;
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof ExemplarViewModel viewModel) {
            List<ExemplarDTO> updated = viewModel.getCurrentExemplars();
            exemplarTable.setItems(FXCollections.observableArrayList(updated));
            messageLabel.setText("Refreshed!");
            System.out.println("Observer triggered: Exemplar table refreshed.");
        }
    }
}
