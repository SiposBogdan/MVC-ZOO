package controller;



import controller.DTO.AnimalDTO;
import controller.DTO.ExemplarDTO;
import javafx.collections.FXCollections;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.viewmodel.ZooViewModel;
import view.ZooView;


import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ZooController {
    private final ZooViewModel viewModel;
    private final ZooView view;
    private Locale currentLocale;
    private ResourceBundle bundle;

    public ZooController(ZooViewModel viewModel, ZooView view, Locale locale) {
        this.viewModel = viewModel;
        this.view = view;
        this.currentLocale = locale;
        this.bundle = ResourceBundle.getBundle("lang.messages", currentLocale);

        setupBindings();
        setupEventHandlers();
        loadData();
        setupLanguageChangeListener();
        updateLanguage();
    }

    private void setupBindings() {
        view.idColumn.setCellValueFactory(new PropertyValueFactory<>("idAnimal"));
        view.specieColumn.setCellValueFactory(new PropertyValueFactory<>("specie"));
        view.categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        view.alimentatieColumn.setCellValueFactory(new PropertyValueFactory<>("alimentatie"));
        view.habitatColumn.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        view.greutateMedieColumn.setCellValueFactory(new PropertyValueFactory<>("greutateMedie"));
        view.varstaMedieColumn.setCellValueFactory(new PropertyValueFactory<>("varstaMedie"));

        view.exemplarIdColumn.setCellValueFactory(new PropertyValueFactory<>("idExemplar"));
        view.animalIdColumn.setCellValueFactory(new PropertyValueFactory<>("idAnimal"));
        view.locatieColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        view.imagineColumn.setCellValueFactory(new PropertyValueFactory<>("imagineUrl"));
        view.imagineColumn.setCellFactory(column -> new TableCell<>() {
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

        view.specieFilterBox.setItems(FXCollections.observableArrayList(viewModel.getAvailableSpecies()));
    }

    private void setupEventHandlers() {
        view.filterButton.setOnAction(e -> {
            String specie = view.specieFilterBox.getValue();
            String alimentatie = view.alimentatieFilterBox.getValue();
            String habitat = view.habitatFilterBox.getValue();
            List<AnimalDTO> filtered = viewModel.filterAnimals(specie, alimentatie, habitat);
            view.animalTable.setItems(FXCollections.observableArrayList(filtered));
            view.messageLabel.setText(MessageFormat.format(bundle.getString("filteredAnimals"), filtered.size()));
        });

        view.exemplarSearchButton.setOnAction(e -> {
            String query = view.exemplarSearchField.getText();
            List<ExemplarDTO> results = viewModel.filterExemplars(query);
            view.exemplarTable.setItems(FXCollections.observableArrayList(results));
            view.messageLabel.setText(MessageFormat.format(bundle.getString("foundExemplars"), results.size()));
        });

        view.exportButton.setOnAction(e -> {
            viewModel.exportToCSV();
            view.messageLabel.setText(bundle.getString("exportCSV"));
        });

        view.exportDocButton.setOnAction(e -> {
            viewModel.exportToDOC();
            view.messageLabel.setText(bundle.getString("exportDOC"));
        });
    }

    private void loadData() {
        viewModel.loadAllData();
        view.animalTable.setItems(viewModel.getAnimals());
        view.exemplarTable.setItems(viewModel.getExemplars());
        view.messageLabel.setText(bundle.getString("messageDataLoaded"));
    }

    private void setupLanguageChangeListener() {
        view.languageToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                Object langCode = selected.getUserData();
                if (langCode == null) return;

                switch (selected.getText()) {
                    case "English" -> currentLocale = Locale.ENGLISH;
                    case "Francais" -> currentLocale = Locale.FRENCH;
                    case "Romana" -> currentLocale = Locale.forLanguageTag("ro");
                }

                bundle = ResourceBundle.getBundle("lang.messages", currentLocale);
                updateLanguage();
            }
        });
    }

    private void updateLanguage() {
        view.filterButton.setText(bundle.getString("filter"));
        view.exportButton.setText(bundle.getString("export"));
        view.exportDocButton.setText(bundle.getString("exportDoc"));
        view.exemplarSearchButton.setText(bundle.getString("search"));

        view.specieColumn.setText(bundle.getString("species"));
        view.categorieColumn.setText(bundle.getString("category"));
        view.alimentatieColumn.setText(bundle.getString("alimentatie"));
        view.habitatColumn.setText(bundle.getString("habitat"));
        view.greutateMedieColumn.setText(bundle.getString("greutate"));
        view.varstaMedieColumn.setText(bundle.getString("varsta"));
        view.exemplarIdColumn.setText(bundle.getString("exemplarId"));
        view.animalIdColumn.setText(bundle.getString("animalId"));
        view.locatieColumn.setText(bundle.getString("location"));
        view.imagineColumn.setText(bundle.getString("image"));

        view.messageLabel.setText(bundle.getString("messageDataLoaded"));
    }
}
