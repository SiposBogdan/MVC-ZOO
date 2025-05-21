package model.viewmodel;

import controller.DTO.AnimalDTO;
import controller.DTO.AnimalMapper;
import controller.DTO.ExemplarDTO;
import controller.DTO.ExemplarMapper;
import controller.ZooExporter;
import model.Animal;
import model.Exemplar;
import model.Observable;
import model.repository.AnimalRepository;
import model.repository.ExemplarRepository;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ZooViewModel extends Observable {
    private final AnimalRepository animalRepository;
    private final ExemplarRepository exemplarRepository;

    public final ObservableList<AnimalDTO> animals = FXCollections.observableArrayList();
    public final ObservableList<ExemplarDTO> exemplars = FXCollections.observableArrayList();

    public ZooViewModel(AnimalRepository animalRepository, ExemplarRepository exemplarRepository) {
        this.animalRepository = animalRepository;
        this.exemplarRepository = exemplarRepository;
        loadAllData();
    }

    public void loadAllData() {
        animals.setAll(
                animalRepository.getTableContent().stream()
                        .map(AnimalMapper::toDTO)
                        .collect(Collectors.toList())
        );

        exemplars.setAll(
                exemplarRepository.getTableContent().stream()
                        .map(ExemplarMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    public List<AnimalDTO> filterAnimals(String specie, String alimentatie, String habitat) {
        List<Animal> allAnimals = animalRepository.getTableContent();

        List<AnimalDTO> filtered = allAnimals.stream()
                .filter(a -> specie == null || specie.equals("No Filter") || a.getSpecie().equalsIgnoreCase(specie))
                .filter(a -> alimentatie == null || alimentatie.equals("No Filter") || a.getAlimentatie().equalsIgnoreCase(alimentatie))
                .filter(a -> habitat == null || habitat.equals("No Filter") || a.getHabitat().equalsIgnoreCase(habitat))
                .map(AnimalMapper::toDTO)
                .collect(Collectors.toList());

        animals.setAll(filtered);
        return filtered;
    }

    public List<String> getAvailableSpecies() {
        return animalRepository.getTableContent().stream()
                .map(Animal::getSpecie)
                .distinct()
                .collect(Collectors.toList());
    }

    public void exportToCSV() {
        ZooExporter.exportToCSV(animalRepository.getTableContent(), "animals.csv");
        notifyObservers();
    }

    public void exportToDOC() {
        ZooExporter.exportToDOC(animalRepository.getTableContent(), "animals.docx");
        notifyObservers();
    }

    public List<ExemplarDTO> filterExemplars(String query) {
        List<Exemplar> allExemplars = exemplarRepository.getTableContent();

        List<ExemplarDTO> filtered = allExemplars.stream()
                .map(ExemplarMapper::toDTO)
                .filter(s -> query == null || query.isBlank() || (
                        String.valueOf(s.getExemplarId()).contains(query) ||
                                String.valueOf(s.getAnimalId()).contains(query) ||
                                s.getLocatie().toLowerCase().contains(query.toLowerCase())
                ))
                .collect(Collectors.toList());

        exemplars.setAll(filtered);
        return filtered;
    }

    public ObservableList<AnimalDTO> getAnimals() {
        return animals;
    }

    public ObservableList<ExemplarDTO> getExemplars() {
        return exemplars;
    }
}