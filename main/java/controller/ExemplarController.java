package controller;

import controller.DTO.ExemplarDTO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import model.viewmodel.ExemplarViewModel;
import view.ExemplarView;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExemplarController {
    private final ExemplarViewModel model;
    private final ExemplarView view;
    private Locale currentLocale;
    private ResourceBundle bundle;

    public ExemplarController(ExemplarViewModel model, ExemplarView view, Locale locale) {
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
        TableView<ExemplarDTO> table = view.exemplarTable;

        view.idColumn.setCellValueFactory(new PropertyValueFactory<>("exemplarId"));
        view.locatieColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        view.imagineUrlColumn.setCellValueFactory(new PropertyValueFactory<>("imagineUrl"));
        view.animalIdColumn.setCellValueFactory(new PropertyValueFactory<>("animalId"));
        view.varstaColumn.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        view.greutateColumn.setCellValueFactory(new PropertyValueFactory<>("greutate"));
        view.specieColumn.setCellValueFactory(new PropertyValueFactory<>("specie"));
        view.numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));

        view.imagineUrlColumn.setCellFactory(column -> new TableCell<ExemplarDTO, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null || url.isBlank()) {
                    setGraphic(null);
                } else {
                    try {
                        File file = new File(url);
                        Image image = new Image(file.toURI().toString(), 80, 80, true, true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                view.numeField.setText(newVal.getNume());
                view.locatieField.setText(newVal.getLocatie());
                view.imagineUrlField.setText(newVal.getImagineUrl());
                view.animalIdField.setText(String.valueOf(newVal.getAnimalId()));
                view.varstaField.setText(String.valueOf(newVal.getVarsta()));
                view.greutateField.setText(String.valueOf(newVal.getGreutate()));
            }
        });

        view.searchButton.setOnAction(e -> {
            String specie = view.searchSpecieField.getText();
            List<ExemplarDTO> filtered = model.searchBySpecie(specie);
            view.exemplarTable.setItems(FXCollections.observableArrayList(filtered));
            view.messageLabel.setText(bundle.getString("foundExemplars").replace("{0}", String.valueOf(filtered.size())));
        });

        updateLanguage();
    }

    private void setupEventHandlers() {
        view.addButton.setOnAction(e -> {
            ExemplarDTO dto = getDTOFromFields();
            if (dto != null) {
                model.addExemplar(dto);
                clearFields();
            }
        });

        view.updateButton.setOnAction(e -> {
            ExemplarDTO dto = getDTOFromFieldsWithId();
            if (dto != null) {
                model.updateExemplar(dto);
                clearFields();
            }
        });

        view.deleteButton.setOnAction(e -> {
            ExemplarDTO dto = getDTOFromFieldsWithId();
            if (dto != null) {
                model.deleteExemplar(dto);
                clearFields();
            }
        });

        view.clearButton.setOnAction(e -> clearFields());

        view.languageToggleGroup.selectedToggleProperty().addListener(languageToggleChangeListener());
    }

    private ChangeListener<javafx.scene.control.Toggle> languageToggleChangeListener() {
        return (obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                currentLocale = (Locale) selected.getUserData();
                bundle = ResourceBundle.getBundle("lang.messages", currentLocale);
                updateLanguage();
            }
        };
    }

    public void load() {
        model.loadExemplars();
    }

    private ExemplarDTO getDTOFromFields() {
        try {
            return new ExemplarDTO(
                    0,
                    Integer.parseInt(view.animalIdField.getText()),
                    view.numeField.getText(),
                    view.locatieField.getText(),
                    view.imagineUrlField.getText(),
                    Integer.parseInt(view.varstaField.getText()),
                    Double.parseDouble(view.greutateField.getText())
            );
        } catch (NumberFormatException e) {
            showAlert(bundle.getString("messageDataLoaded"), "Please enter valid numeric values.");
            return null;
        }
    }

    private ExemplarDTO getDTOFromFieldsWithId() {
        ExemplarDTO selected = view.exemplarTable.getSelectionModel().getSelectedItem();
        if (selected == null) return null;

        try {
            return new ExemplarDTO(
                    selected.getExemplarId(),
                    Integer.parseInt(view.animalIdField.getText()),
                    view.numeField.getText(),
                    view.locatieField.getText(),
                    view.imagineUrlField.getText(),
                    Integer.parseInt(view.varstaField.getText()),
                    Double.parseDouble(view.greutateField.getText())
            );
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values.");
            return null;
        }
    }

    private void clearFields() {
        view.numeField.clear();
        view.locatieField.clear();
        view.imagineUrlField.clear();
        view.animalIdField.clear();
        view.varstaField.clear();
        view.greutateField.clear();
    }

    private void updateLanguage() {
        view.languageLabel.setText(bundle.getString("label.language"));
        view.specieLabel.setText(bundle.getString("column.specie"));
        view.numeLabel.setText(bundle.getString("label.name"));
        view.locatieLabel.setText(bundle.getString("label.location"));
        view.imagineUrlLabel.setText(bundle.getString("label.imageUrl"));
        view.animalIdLabel.setText(bundle.getString("label.animalId"));
        view.varstaLabel.setText(bundle.getString("label.age"));
        view.greutateLabel.setText(bundle.getString("label.weight"));

        view.idColumn.setText(bundle.getString("column.exemplarId"));
        view.animalIdColumn.setText(bundle.getString("column.animalId"));
        view.numeColumn.setText(bundle.getString("column.name"));
        view.specieColumn.setText(bundle.getString("column.specie"));
        view.locatieColumn.setText(bundle.getString("column.location"));
        view.imagineUrlColumn.setText(bundle.getString("column.imageUrl"));
        view.varstaColumn.setText(bundle.getString("column.age"));
        view.greutateColumn.setText(bundle.getString("column.weight"));

        view.addButton.setText(bundle.getString("button.add"));
        view.updateButton.setText(bundle.getString("button.update"));
        view.deleteButton.setText(bundle.getString("button.delete"));
        view.clearButton.setText(bundle.getString("button.clear"));
        view.searchButton.setText(bundle.getString("button.search"));

        view.englishButton.setText(bundle.getString("language.english"));
        view.frenchButton.setText(bundle.getString("language.french"));
        view.romanianButton.setText(bundle.getString("language.romanian"));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}