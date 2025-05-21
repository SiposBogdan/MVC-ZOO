package model.viewmodel;


import controller.DTO.ExemplarDTO;
import controller.DTO.ExemplarMapper;
import model.Exemplar;
import model.Observable;
import model.repository.ExemplarRepository;

import java.util.List;

public class ExemplarViewModel extends Observable {
    private final ExemplarRepository repository;
    private List<ExemplarDTO> currentExemplars = List.of();

    public ExemplarViewModel(ExemplarRepository repository) {
        this.repository = repository;
    }

    public void loadExemplars() {
//        List<Exemplar> exemplars = repository.getTableContent();
//        currentExemplars = exemplars.stream()
//                .map(ExemplarMapper::toDTO)
//                .toList();
        currentExemplars = repository.getExemplarsWithAnimalSpecie();
        notifyObservers();
    }

    public void addExemplar(ExemplarDTO dto) {
        repository.insert(ExemplarMapper.toEntity(dto));
        loadExemplars();
        notifyObservers();

    }

    public void updateExemplar(ExemplarDTO dto) {
        repository.update(ExemplarMapper.toEntity(dto));
        loadExemplars();
        notifyObservers();

    }

    public void deleteExemplar(ExemplarDTO dto) {
        repository.deleteById(ExemplarMapper.toEntity(dto).getExemplarId());
        loadExemplars();
        notifyObservers();

    }
    public List<ExemplarDTO> searchBySpecie(String specie) {
        return currentExemplars.stream()
                .filter(e -> specie == null || specie.isBlank() || e.getSpecie().equalsIgnoreCase(specie))
                .toList();
    }

    public List<ExemplarDTO> getCurrentExemplars() {
        return currentExemplars;
    }
}
