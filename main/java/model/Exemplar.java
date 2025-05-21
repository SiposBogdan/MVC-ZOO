package model;

public class Exemplar {
    private int exemplarId;
    private int animalId;
    private String locatie;
    private String imagineUrl;
    private int varsta;
    private double greutate;
    private String nume;

    public Exemplar() {}

    public Exemplar(String locatie, String imagineUrl, int varsta, double greutate, int animalId, String nume) {
        this.locatie = locatie;
        this.imagineUrl = imagineUrl;
        this.varsta = varsta;
        this.greutate = greutate;
        this.animalId = animalId;
        this.nume = nume;
    }

    public int getExemplarId() {
        return exemplarId;
    }

    public String getNume() {
        return nume;
    }



    public void setNume(String nume) {
        this.nume = nume;
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

    @Override
    public String toString() {
        return "Exemplar{" +
                "exemplarId=" + exemplarId +
                ", animalId=" + animalId +
                ", locatie='" + locatie + '\'' +
                ", imagineUrl='" + imagineUrl + '\'' +
                ", varsta=" + varsta +
                ", greutate=" + greutate +
                '}';
    }
}
