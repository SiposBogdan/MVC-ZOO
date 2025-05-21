package model;

public class Animal {
    private int animalId;
    private String categorie;   // pasare, reptila, peste etc.
    private String specie;      // ex: papagal, crocodil
    private String alimentatie; // ierbivor, carnivor, omnivor
    private String habitat;     // ex: tropical, polar, desertic
    private int varstaMedie; // in ani
    private double greutateMedie; // in kg

    public Animal() {}


    public Animal(int animalId, String categorie, String specie, String alimentatie, String habitat, int varstaMedie, double greutateMedie) {
        this.animalId = animalId;
        this.categorie = categorie;
        this.specie = specie;
        this.alimentatie = alimentatie;
        this.habitat = habitat;
        this.varstaMedie = varstaMedie;
        this.greutateMedie = greutateMedie;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getAlimentatie() {
        return alimentatie;
    }

    public void setAlimentatie(String alimentatie) {
        this.alimentatie = alimentatie;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public int getVarstaMedie() {
        return varstaMedie;
    }

    public void setVarstaMedie(int varstaMedie) {
        this.varstaMedie = varstaMedie;
    }

    public double getGreutateMedie() {
        return greutateMedie;
    }

    public void setGreutateMedie(double greutateMedie) {
        this.greutateMedie = greutateMedie;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", categorie='" + categorie + '\'' +
                ", specie='" + specie + '\'' +
                ", alimentatie='" + alimentatie + '\'' +
                ", habitat='" + habitat + '\'' +
                ", varstaMedie=" + varstaMedie +
                ", greutateMedie=" + greutateMedie +
                '}';
    }
}