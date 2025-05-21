package model.repository;

import controller.DTO.ExemplarDTO;
import model.Exemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplarRepository extends AbstractRepository<Exemplar> {

        public ExemplarRepository() {
                super();
        }

        public List<ExemplarDTO> getExemplarsWithAnimalSpecie() {
                List<ExemplarDTO> exemplars = new ArrayList<>();

                String query = """
            SELECT e.exemplarId, e.animalId, e.nume, e.locatie, e.imagineUrl, e.varsta, e.greutate, a.specie
            FROM Exemplar e
            JOIN Animal a ON e.animalId = a.animalId
            
        """;

                try (Connection connection = ConnectionFactory.getConnection();
                     PreparedStatement stmt = connection.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                        while (rs.next()) {
                                ExemplarDTO dto = new ExemplarDTO(
                                        rs.getInt("exemplarId"),
                                        rs.getInt("animalId"),
                                        rs.getString("nume"),
                                        rs.getString("locatie"),
                                        rs.getString("imagineUrl"),
                                        rs.getInt("varsta"),
                                        rs.getDouble("greutate")
                                );
                                dto.setSpecie(rs.getString("specie"));
                                exemplars.add(dto);
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return exemplars;
        }
}
