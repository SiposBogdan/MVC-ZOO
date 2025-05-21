package controller.DTO;


import model.Animal;

public class AnimalMapper {

    public static AnimalDTO toDTO(Animal animal) {
        return new AnimalDTO(
                animal.getAnimalId(),
                animal.getSpecie(),
                animal.getCategorie(),
                animal.getAlimentatie(),
                animal.getHabitat(),
                animal.getGreutateMedie(),
                animal.getVarstaMedie()
        );
    }

    public static Animal toEntity(AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setAnimalId(dto.getIdAnimal());
        animal.setSpecie(dto.getSpecie());
        animal.setCategorie(dto.getCategorie());
        animal.setAlimentatie(dto.getAlimentatie());
        animal.setHabitat(dto.getHabitat());
        animal.setGreutateMedie(dto.getGreutateMedie());
        animal.setVarstaMedie(dto.getVarstaMedie());
        return animal;
    }
}
