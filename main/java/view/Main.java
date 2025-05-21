package view;

import controller.AnimalController;
import controller.ExemplarController;
import controller.ZooController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.repository.AnimalRepository;
import model.repository.ExemplarRepository;
import model.viewmodel.AnimalViewModel;
import model.viewmodel.ExemplarViewModel;
import model.viewmodel.ZooViewModel;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Default language
            Locale locale = Locale.ENGLISH;

            // Init Animal view components
            AnimalRepository animalRepository = new AnimalRepository();
            AnimalViewModel animalViewModel = new AnimalViewModel(animalRepository);
            AnimalView animalView = new AnimalView();
            AnimalController animalController = new AnimalController(animalViewModel, animalView, locale);

            // Init Exemplar view components
            ExemplarRepository exemplarRepository = new ExemplarRepository();
            ExemplarViewModel exemplarViewModel = new ExemplarViewModel(exemplarRepository);
            ExemplarView exemplarView = new ExemplarView();
            ExemplarController exemplarController = new ExemplarController(exemplarViewModel, exemplarView, locale);

            // Navigation buttons
            Button buttonAnimals = new Button("Animals");
            Button buttonExemplars = new Button("Exemplars");

            HBox menuBar = new HBox(20, buttonAnimals, buttonExemplars);
            menuBar.setPadding(new Insets(10));

            // Root layout
            BorderPane root = new BorderPane();
            root.setTop(menuBar);
            root.setCenter(animalView.getView()); // Default view

            // Navigation actions
            buttonAnimals.setOnAction(e -> root.setCenter(animalView.getView()));
            buttonExemplars.setOnAction(e -> root.setCenter(exemplarView.getView()));
            Scene scene = new Scene(root, 1000, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Zoo Management");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
