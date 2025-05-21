package controller.DTO;

public class ExemplarDTO {
    private int exemplarId;
    private int animalId;
    private String nume;
    private String locatie;
    private String imagineUrl;
    private int varsta;
    private double greutate;

    private String specie;

    public ExemplarDTO(int exemplarId, int animalId, String nume, String locatie, String imagineUrl, int varsta, double greutate) {
        this.exemplarId = exemplarId;
        this.animalId = animalId;
        this.nume = nume;
        this.locatie = locatie;
        this.imagineUrl = imagineUrl;
        this.varsta = varsta;
        this.greutate = greutate;
    }

    // Getteri și setteri
    public int getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(int exemplarId) {
        this.exemplarId = exemplarId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getImagineUrl() {
        return imagineUrl;
    }

    public void setImagineUrl(String imagineUrl) {
        this.imagineUrl = imagineUrl;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public double getGreutate() {
        return greutate;
    }

    public void setGreutate(double greutate) {
        this.greutate = greutate;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }
}
