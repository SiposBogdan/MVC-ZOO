package controller.DTO;

import model.Animal;

public class AnimalDTO {
    private final int idAnimal;
    private final String categorie;
    private final String specie;
    private final String alimentatie;
    private final String habitat;
    private final double greutateMedie;
    private final int varstaMedie;

    public AnimalDTO(int idAnimal, String specie, String categorie, String alimentatie, String habitat, double greutateMedie, int varstaMedie) {
        this.idAnimal = idAnimal;

        this.specie = specie;
        this.categorie = categorie;
        this.alimentatie = alimentatie;
        this.habitat = habitat;
        this.greutateMedie = greutateMedie;
        this.varstaMedie = varstaMedie;

    }

    public int getIdAnimal() {
        return idAnimal;
    }


    public String getCategorie() {
        return categorie;
    }

    public String getSpecie() {
        return specie;
    }

    public String getAlimentatie() {
        return alimentatie;
    }

    public String getHabitat() {
        return habitat;
    }

    public double getGreutateMedie() {
        return greutateMedie;
    }

    public int getVarstaMedie() {
        return varstaMedie;
    }


    public Animal toEntity() {
        Animal animal = new Animal();
        animal.setAnimalId(idAnimal);
        animal.setSpecie(specie);
        animal.setCategorie(categorie);
        animal.setAlimentatie(alimentatie);
        animal.setHabitat(habitat);
        animal.setGreutateMedie(greutateMedie);
        animal.setVarstaMedie(varstaMedie);
        return animal;
    }

    @Override
    public String toString() {
        return "AnimalDTO{" +
                "idAnimal=" + idAnimal +
                ", specie='" + specie + '\'' +
                ", categorie='" + categorie + '\'' +
                ", alimentatie='" + alimentatie + '\'' +
                ", habitat='" + habitat + '\'' +
                ", greutateMedie=" + greutateMedie +
                ", varstaMedie=" + varstaMedie +
                '}';
    }
}
