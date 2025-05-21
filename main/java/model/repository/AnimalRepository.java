package model.repository;


import model.Animal;
import model.Exemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class AnimalRepository extends AbstractRepository<Animal> {

    public AnimalRepository() {
        super();
    }

    public List<Exemplar> getExemplarsByAnimalId(int animalId) {
        List<Exemplar> exemplars = new ArrayList<>();
        String query = "SELECT * FROM Exemplar WHERE animalId = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Exemplar exemplar = new Exemplar();
                    exemplar.setExemplarId(resultSet.getInt("exemplarId"));
                    exemplar.setAnimalId(resultSet.getInt("animalId"));
                    exemplar.setLocatie(resultSet.getString("locatie"));
                    exemplar.setImagineUrl(resultSet.getString("imagineUrl"));
                    exemplar.setVarsta(resultSet.getInt("varsta"));
                    exemplar.setGreutate(resultSet.getDouble("greutate"));
                    exemplars.add(exemplar);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error fetching exemplars by animalId: " + e.getMessage());
        }

        return exemplars;
    }
}