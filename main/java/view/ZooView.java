package view;

import controller.DTO.AnimalDTO;
import controller.DTO.ExemplarDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Observable;
import model.Observer;
import model.viewmodel.ZooViewModel;


import java.io.File;

public class ZooView implements Observer {
    // Animal table and columns
    public final TableView<AnimalDTO> animalTable = new TableView<>();
    public final TableColumn<AnimalDTO, Integer> idColumn = new TableColumn<>("Id");
    public final TableColumn<AnimalDTO, String> specieColumn = new TableColumn<>("Specie");
    public final TableColumn<AnimalDTO, String> categorieColumn = new TableColumn<>("Categorie");
    public final TableColumn<AnimalDTO, String> alimentatieColumn = new TableColumn<>("Alimentatie");
    public final TableColumn<AnimalDTO, String> habitatColumn = new TableColumn<>("Habitat");
    public final TableColumn<AnimalDTO, Double> greutateMedieColumn = new TableColumn<>("Greutate");
    public final TableColumn<AnimalDTO, Integer> varstaMedieColumn = new TableColumn<>("Varsta");

    // Exemplar table and columns
    public final TableView<ExemplarDTO> exemplarTable = new TableView<>();
    public final TableColumn<ExemplarDTO, Integer> exemplarIdColumn = new TableColumn<>("Id Exemplar");
    public final TableColumn<ExemplarDTO, Integer> animalIdColumn = new TableColumn<>("Animal Id");
    public final TableColumn<ExemplarDTO, String> locatieColumn = new TableColumn<>("Locatie");
    public final TableColumn<ExemplarDTO, String> imagineColumn = new TableColumn<>("Imagine");

    // Search controls
    public final TextField exemplarSearchField = new TextField();
    public final Button exemplarSearchButton = new Button("Cauta Exemplare");

    // Filter + export controls
    public final ComboBox<String> specieFilterBox = new ComboBox<>();
    public final ComboBox<String> alimentatieFilterBox = new ComboBox<>();
    public final ComboBox<String> habitatFilterBox = new ComboBox<>();
    public final Button filterButton = new Button("Filtreaza Animale");
    public final Button exportButton = new Button("Export CSV");
    public final Button exportDocButton = new Button("Export DOC");

    // Language controls
    public final ToggleGroup languageToggleGroup = new ToggleGroup();
    public final RadioButton englishButton = new RadioButton("English");
    public final RadioButton frenchButton = new RadioButton("Francais");
    public final RadioButton romanianButton = new RadioButton("Romana");

    // Message
    public final Label messageLabel = new Label();

    // Root pane
    public final GridPane root = new GridPane();

    public ZooView() {
        setupLayout();
    }

    private void setupLayout() {
        englishButton.setUserData("en");
        frenchButton.setUserData("fr");
        romanianButton.setUserData("ro");
        englishButton.setToggleGroup(languageToggleGroup);
        frenchButton.setToggleGroup(languageToggleGroup);
        romanianButton.setToggleGroup(languageToggleGroup);
        englishButton.setSelected(true);

        HBox languageSelector = new HBox(10, new Label("Language:"), englishButton, frenchButton, romanianButton);
        languageSelector.setPadding(new Insets(5, 0, 5, 0));

        // Animal table setup
        animalTable.getColumns().addAll(idColumn, specieColumn, categorieColumn, alimentatieColumn, habitatColumn, greutateMedieColumn, varstaMedieColumn);
        animalTable.setPrefWidth(600);

        // Exemplar table setup
        exemplarTable.getColumns().addAll(exemplarIdColumn, animalIdColumn, locatieColumn, imagineColumn);
        exemplarTable.setPrefWidth(600);
        imagineColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);
                if (empty || imageUrl == null || imageUrl.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        File file = new File(imageUrl);
                        Image image = new Image(file.toURI().toString(), 100, 100, true, true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        // Search controls
        exemplarSearchField.setPromptText("Cauta dupa locatie...");
        HBox searchBox = new HBox(10, exemplarSearchField, exemplarSearchButton);

        VBox leftBox = new VBox(20, animalTable, exemplarTable, searchBox);

        VBox rightBox = new VBox(10, specieFilterBox, alimentatieFilterBox, habitatFilterBox, filterButton, exportButton, exportDocButton);
        rightBox.setPrefWidth(220);

        HBox mainBox = new HBox(10, leftBox, rightBox);
        GridPane.setColumnSpan(mainBox, 2);

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10));
        root.add(languageSelector, 0, 0);
        GridPane.setColumnSpan(languageSelector, 2);
        root.add(mainBox, 0, 1);
        root.add(messageLabel, 0, 2);
        GridPane.setColumnSpan(messageLabel, 2);
    }

    public GridPane getView() {
        return root;
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof ZooViewModel viewModel) {
            messageLabel.setText("Export Generated!");

            // Print output message when observer is triggered
            System.out.println("Observer triggered: Export Generated.");
        }
    }
}