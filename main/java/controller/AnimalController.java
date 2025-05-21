package controller;

import controller.DTO.AnimalDTO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import model.viewmodel.AnimalViewModel;
import view.AnimalView;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AnimalController {
    private final AnimalViewModel model;
    private final AnimalView view;
    private Locale currentLocale;
    private ResourceBundle bundle;

    public AnimalController(AnimalViewModel model, AnimalView view, Locale locale) {
        this.model = model;
        this.view = view;
        this.currentLocale = locale;
        this.bundle = ResourceBundle.getBundle("lang.messages", currentLocale);

        model.addObserver(view);
        setupBindings();
        setupEventHandlers();
        load();
    }

    private void setupBindings() {
        TableView<AnimalDTO> table = view.animalTable;

        view.idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idAnimal"));
        view.specieColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("specie"));
        view.categorieColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("categorie"));
        view.alimentatieColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("alimentatie"));
        view.habitatColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("habitat"));
        view.greutateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("greutateMedie"));
        view.varstaColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("varstaMedie"));


        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.specieField.setText(newVal.getSpecie());
                view.categorieField.setText(newVal.getCategorie());
                view.alimentatieField.setText(newVal.getAlimentatie());
                view.habitatField.setText(newVal.getHabitat());
                view.greutateField.setText(String.valueOf(newVal.getGreutateMedie()));
                view.varstaField.setText(String.valueOf(newVal.getVarstaMedie()));
            }
        });

        updateLanguage();
    }

    private void setupEventHandlers() {
        view.addButton.setOnAction(e -> {
            model.addAnimal(getDTOFromFields());
            clearFields();
        });

        view.updateButton.setOnAction(e -> {
            AnimalDTO dto = getDTOFromFieldsWithId();
            if (dto != null) {
                model.updateAnimal(dto);
                clearFields();
            }
        });

        view.deleteButton.setOnAction(e -> {
            AnimalDTO dto = getDTOFromFieldsWithId();
            if (dto != null) {
                model.deleteAnimal(dto);
                clearFields();
            }
        });

        view.clearButton.setOnAction(e -> clearFields());

        view.exportCSVButton.setOnAction(e -> {
            ZooExporter.exportToCSV(model.getAnimals(), "animals.csv");
        });

        view.exportDOCButton.setOnAction(e -> {
            ZooExporter.exportToDOC(model.getAnimals(), "animals.docx");
        });




        view.filterButton.setOnAction(e -> {
            List<AnimalDTO> filtered = model.filterAnimals(
                    view.filterSpecieField.getText(),
                    view.filterAlimentatieField.getText(),
                    view.filterHabitatField.getText()
            );
            view.animalTable.setItems(FXCollections.observableArrayList(filtered));
            view.messageLabel.setText("Filtered " + filtered.size() + " animals.");


        });

        view.statsBySpeciesButton.setOnAction(e -> {
            List<AnimalDTO> list = model.getCurrentAnimals();
            Map<String, Long> bySpecie = list.stream()
                    .collect(Collectors.groupingBy(AnimalDTO::getSpecie, Collectors.counting()));

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            bySpecie.forEach((specie, count) -> {
                pieData.add(new PieChart.Data(specie, count));
            });

            view.statsChart.setData(pieData);
        });

        view.statsByHabitatButton.setOnAction(e -> {
            var list = model.getCurrentAnimals();
            var byHabitat = list.stream()
                    .collect(Collectors.groupingBy(AnimalDTO::getHabitat, Collectors.counting()));

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            byHabitat.forEach((habitat, cnt) ->
                    pieData.add(new PieChart.Data(habitat, cnt))
            );

            view.statsChart.setData(pieData);
        });


        view.languageToggleGroup.selectedToggleProperty().addListener(languageToggleChangeListener());
    }


    private ChangeListener<javafx.scene.control.Toggle> languageToggleChangeListener() {
        return (obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                currentLocale = (Locale) newToggle.getUserData();
                System.out.println("🌍 LIMBA ACTUALIZATĂ: " + currentLocale);
                bundle = ResourceBundle.getBundle("lang.messages", currentLocale);
                updateLanguage();
            }
        };
    }


    public void load() {
        model.loadAnimals();
        updateLanguage();

    }

    private AnimalDTO getDTOFromFields() {
        return new AnimalDTO(0,
                view.specieField.getText(),
                view.categorieField.getText(),
                view.alimentatieField.getText(),
                view.habitatField.getText(),
                Double.parseDouble(view.greutateField.getText()),
                Integer.parseInt(view.varstaField.getText())
        ); // vitezaMedie can be extended if used
    }

    private AnimalDTO getDTOFromFieldsWithId() {
        AnimalDTO selected = view.animalTable.getSelectionModel().getSelectedItem();
        if (selected == null) return null;
        return new AnimalDTO(selected.getIdAnimal(),
                view.specieField.getText(),
                view.categorieField.getText(),
                view.alimentatieField.getText(),
                view.habitatField.getText(),
                Double.parseDouble(view.greutateField.getText()),
                Integer.parseInt(view.varstaField.getText())
        );
    }

    private void clearFields() {
        view.specieField.clear();
        view.categorieField.clear();
        view.alimentatieField.clear();
        view.habitatField.clear();
        view.greutateField.clear();
        view.varstaField.clear();
    }

    private void updateLanguage() {

        System.out.println("updateLanguage() called with locale: " + currentLocale);


        view.specieLabel.setText(bundle.getString("label.specie"));
        view.categorieLabel.setText(bundle.getString("label.categorie"));
        view.alimentatieLabel.setText(bundle.getString("label.alimentatie"));
        view.habitatLabel.setText(bundle.getString("label.habitat"));
        view.greutateLabel.setText(bundle.getString("label.greutate"));
        view.varstaLabel.setText(bundle.getString("label.varsta"));
        view.languageLabel.setText(bundle.getString("label.language"));



        view.idColumn.setText(bundle.getString("column.id"));
        view.specieColumn.setText(bundle.getString("column.specie"));
        view.categorieColumn.setText(bundle.getString("column.categorie"));
        view.alimentatieColumn.setText(bundle.getString("column.alimentatie"));
        view.habitatColumn.setText(bundle.getString("column.habitat"));
        view.greutateColumn.setText(bundle.getString("column.greutate"));
        view.varstaColumn.setText(bundle.getString("column.varsta"));

        view.addButton.setText(bundle.getString("button.add"));
        view.updateButton.setText(bundle.getString("button.update"));
        view.deleteButton.setText(bundle.getString("button.delete"));
        view.clearButton.setText(bundle.getString("button.clear"));
        view.exportCSVButton.setText(bundle.getString("button.exportCSV"));
        view.exportDOCButton.setText(bundle.getString("button.exportDOC"));
        view.statsBySpeciesButton.setText(bundle.getString("stats.species.button"));
        view.statsByHabitatButton.setText(bundle.getString("stats.habitat.button"));



        view.englishButton.setText(bundle.getString("language.english"));
        view.frenchButton.setText(bundle.getString("language.french"));
        view.romanianButton.setText(bundle.getString("language.romanian"));

        view.filterButton.setText(bundle.getString("filter.button"));
        view.filterAlimentatieField.setText(bundle.getString("filter.alimentatie"));
        view.filterHabitatField.setText(bundle.getString("filter.habitat"));
        view.filterSpecieField.setText(bundle.getString("filter.specie"));


    }
}

