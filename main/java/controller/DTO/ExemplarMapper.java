package controller.DTO;

import model.Exemplar;

public class ExemplarMapper {

    public static ExemplarDTO toDTO(Exemplar exemplar) {
        return new ExemplarDTO(
                exemplar.getExemplarId(),
                exemplar.getAnimalId(),
                exemplar.getNume(),
                exemplar.getLocatie(),
                exemplar.getImagineUrl(),
                exemplar.getVarsta(),
                exemplar.getGreutate()
        );
    }

    public static Exemplar toEntity(ExemplarDTO dto) {
        Exemplar exemplar = new Exemplar();
        exemplar.setExemplarId(dto.getExemplarId());
        exemplar.setAnimalId(dto.getAnimalId());
        exemplar.setNume(dto.getNume());
        exemplar.setLocatie(dto.getLocatie());
        exemplar.setImagineUrl(dto.getImagineUrl());
        exemplar.setVarsta(dto.getVarsta());
        exemplar.setGreutate(dto.getGreutate());
        return exemplar;
    }
}