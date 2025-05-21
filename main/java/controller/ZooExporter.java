package controller;

import model.Animal;
import model.Exemplar;
import model.repository.AnimalRepository;
import org.apache.poi.xwpf.usermodel.*;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZooExporter {


        public static void exportToCSV(List<Animal> animals, String filePath) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
                writer.write("ID,Specie,Exemplars\n");
                AnimalRepository repository = new AnimalRepository();

                for (Animal animal : animals) {
                    List<Exemplar> exemplars = repository.getExemplarsByAnimalId(animal.getAnimalId());

                    String exemplarStr = exemplars.stream()
                            .map(Exemplar::getLocatie)
                            .collect(Collectors.joining(" | "));

                    writer.write(animal.getAnimalId() + "," +
                            animal.getSpecie() + "," +
                            exemplarStr + "\n");
                }

                System.out.println("Animals exported successfully to CSV: " + filePath);
            } catch (IOException e) {
                System.err.println("Error exporting animals to CSV: " + e.getMessage());
            }
        }

        public static void exportToDOC(List<Animal> animals, String filePath) {
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                XWPFDocument document = new XWPFDocument();

                XWPFParagraph title = document.createParagraph();
                title.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = title.createRun();
                titleRun.setText("Animal List - Zoo");
                titleRun.setBold(true);
                titleRun.setFontSize(14);

                XWPFTable table = document.createTable();
                table.setWidth("100%");

                XWPFTableRow header = table.getRow(0);
                header.getCell(0).setText("ID");
                header.addNewTableCell().setText("Specie");
                header.addNewTableCell().setText("Exemplars");

                AnimalRepository repository = new AnimalRepository();

                for (Animal animal : animals) {
                    List<Exemplar> exemplars = repository.getExemplarsByAnimalId(animal.getAnimalId());
                    String exemplarStr = exemplars.stream()
                            .map(Exemplar::getLocatie)
                            .collect(Collectors.joining(" | "));

                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(String.valueOf(animal.getAnimalId()));
                    row.getCell(1).setText(animal.getSpecie());
                    row.getCell(2).setText(exemplarStr);
                }

                document.write(fos);
                document.close();
                System.out.println("Animals exported successfully to DOC: " + filePath);
            } catch (IOException e) {
                System.err.println("Error exporting animals to DOC: " + e.getMessage());
            }
        }
    public static void exportStatistics(List<Animal> animals) {
        AnimalRepository repository = new AnimalRepository();

        List<Exemplar> allExemplars = new ArrayList<>();
        for (Animal animal : animals) {
            List<Exemplar> exemplars = repository.getExemplarsByAnimalId(animal.getAnimalId());
            allExemplars.addAll(exemplars);
        }

        int total = allExemplars.size();

        double avgAge = allExemplars.stream()
                .mapToInt(Exemplar::getVarsta)
                .average()
                .orElse(0.0);

        Map<String, Long> countBySpecies = animals.stream()
                .collect(Collectors.toMap(
                        Animal::getSpecie,
                        a -> (long) repository.getExemplarsByAnimalId(a.getAnimalId()).size()
                ));

        System.out.println("=== Exemplar Statistics ===");
        System.out.println("Total exemplars: " + total);
        System.out.printf("Average age: %.2f%n", avgAge);
        System.out.println("Count by species:");
        countBySpecies.forEach((specie, count) ->
                System.out.printf("  %s : %d%n", specie, count)
        );
    }
}


