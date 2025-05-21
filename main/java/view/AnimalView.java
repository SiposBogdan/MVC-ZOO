package view;

import controller.DTO.AnimalDTO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Observable;
import model.Observer;
import model.viewmodel.AnimalViewModel;

import java.util.List;
import java.util.Locale;

public class AnimalView implements Observer {

    public final TableView<AnimalDTO> animalTable = new TableView<>();
    public final TableColumn<AnimalDTO, Integer> idColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, String> specieColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, String> categorieColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, String> alimentatieColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, String> habitatColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, Double> greutateColumn = new TableColumn<>();
    public final TableColumn<AnimalDTO, Integer> varstaColumn = new TableColumn<>();

    public final TextField specieField = new TextField();
    public final TextField categorieField = new TextField();
    public final TextField alimentatieField = new TextField();
    public final TextField habitatField = new TextField();
    public final TextField greutateField = new TextField();
    public final TextField varstaField = new TextField();

    public final Button addButton = new Button();
    public final Button updateButton = new Button();
    public final Button deleteButton = new Button();
    public final Button clearButton = new Button();
    public final Button statsBySpeciesButton = new Button();
    public final Button statsByHabitatButton = new Button();

    public final Button exportCSVButton = new Button();
    public final Button exportDOCButton = new Button();

    public final Label messageLabel = new Label();
    public final Label specieLabel = new Label();
    public final Label categorieLabel = new Label();
    public final Label alimentatieLabel = new Label();
    public final Label habitatLabel = new Label();
    public final Label greutateLabel = new Label();
    public final Label varstaLabel = new Label();
    public final Label languageLabel = new Label();

    public final ToggleGroup languageToggleGroup = new ToggleGroup();
    public final RadioButton englishButton = new RadioButton();
    public final RadioButton frenchButton = new RadioButton();
    public final RadioButton romanianButton = new RadioButton();

    public final VBox root = new VBox();

    public final TextField filterSpecieField = new TextField();
    public final TextField filterAlimentatieField = new TextField();
    public final TextField filterHabitatField = new TextField();
    public final Button filterButton = new Button();

    public final PieChart statsChart = new PieChart();


    public AnimalView() {
        setupLayout();
    }

    private void setupLayout() {
        GridPane filterPane = new GridPane();
        filterPane.setHgap(10);
        filterPane.setVgap(10);
        filterPane.addRow(0, specieLabel, filterSpecieField);
        filterPane.addRow(1, alimentatieLabel, filterAlimentatieField);
        filterPane.addRow(2, habitatLabel, filterHabitatField);
        filterPane.add(filterButton, 0, 3, 2, 1);

        animalTable.getColumns().addAll(
                idColumn, specieColumn, categorieColumn,
                alimentatieColumn, habitatColumn,
                greutateColumn, varstaColumn
        );

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, specieLabel, specieField);
        form.addRow(1, categorieLabel, categorieField);
        form.addRow(2, alimentatieLabel, alimentatieField);
        form.addRow(3, habitatLabel, habitatField);
        form.addRow(4, greutateLabel, greutateField);
        form.addRow(5, varstaLabel, varstaField);

        HBox buttons = new HBox(10, addButton, updateButton, deleteButton, clearButton, statsByHabitatButton, statsBySpeciesButton);

        englishButton.setToggleGroup(languageToggleGroup);
        frenchButton.setToggleGroup(languageToggleGroup);
        romanianButton.setToggleGroup(languageToggleGroup);

        englishButton.setUserData(Locale.ENGLISH);
        frenchButton.setUserData(Locale.FRENCH);
        romanianButton.setUserData(Locale.forLanguageTag("ro"));
        englishButton.setSelected(true);

        HBox languageSelector = new HBox(10,
                languageLabel, englishButton, frenchButton, romanianButton
        );
        languageSelector.setPadding(new Insets(5, 0, 5, 0));

        HBox exportButtons = new HBox(10, exportCSVButton, exportDOCButton);

        root.getChildren().addAll(
                languageSelector,
                filterPane,
                animalTable,

                form,
                statsChart,
                exportButtons,
                buttons,
                messageLabel
        );

        root.setSpacing(10);
        root.setPadding(new Insets(10));
    }


    public VBox getView() {
        return root;
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof AnimalViewModel viewModel) {
            List<AnimalDTO> updated = viewModel.getCurrentAnimals();
            animalTable.setItems(FXCollections.observableArrayList(updated));
            messageLabel.setText("Refreshed!");
            System.out.println("Observer triggered: Animal table refreshed.");
        }
    }
}
