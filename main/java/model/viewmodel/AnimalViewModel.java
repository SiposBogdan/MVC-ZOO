package model.viewmodel;


import controller.DTO.AnimalDTO;
import controller.DTO.AnimalMapper;
import model.Animal;
import model.Observable;
import model.repository.AnimalRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AnimalViewModel extends Observable {
    private final AnimalRepository repository;
    private List<AnimalDTO> currentAnimals;

    public AnimalViewModel(AnimalRepository repository) {
        this.repository = repository;
    }

    public void loadAnimals() {
        List<Animal> animals = repository.getTableContent();
        currentAnimals = animals.stream()
                .map(AnimalMapper::toDTO)
                .toList();
        notifyObservers();
    }

    public List<AnimalDTO> filterAnimals(String specie, String alimentatie, String habitat) {
        return currentAnimals.stream()
                .filter(a -> (specie == null || specie.isBlank() || a.getSpecie().equalsIgnoreCase(specie)) &&
                        (alimentatie == null || alimentatie.isBlank() || a.getAlimentatie().equalsIgnoreCase(alimentatie)) &&
                        (habitat == null || habitat.isBlank() || a.getHabitat().equalsIgnoreCase(habitat)))
                .toList();
    }

    public List<AnimalDTO> getCurrentAnimals() {
        return currentAnimals;
    }

    public void addAnimal(AnimalDTO dto) {
        repository.insert(AnimalMapper.toEntity(dto));
        loadAnimals();
        notifyObservers();

    }

    public void updateAnimal(AnimalDTO dto) {
        repository.update(AnimalMapper.toEntity(dto));
        loadAnimals();
        notifyObservers();

    }

    public void deleteAnimal(AnimalDTO dto) {
        repository.deleteById(AnimalMapper.toEntity(dto).getAnimalId());
        loadAnimals();
        notifyObservers();

    }
    public List<Animal> getAnimals() {
        return repository.getTableContent();
    }


}