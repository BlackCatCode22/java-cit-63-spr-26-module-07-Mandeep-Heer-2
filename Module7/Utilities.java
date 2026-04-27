package Module7;

import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;

public class Utilities {
    private static HashMap<String, Integer> speciesCounts = new HashMap<>();

    public static String genUniqueID(String species) {
        String cleanSpecies = species.toLowerCase().trim();
        int count = speciesCounts.getOrDefault(cleanSpecies, 0) + 1;
        speciesCounts.put(cleanSpecies, count);
        String prefix = cleanSpecies.substring(0, 1).toUpperCase() + cleanSpecies.substring(1, 2).toLowerCase();
        return prefix + String.format("%02d", count);
    }

    public static String arrivalDate() {
        return LocalDate.now().toString();
    }

    public static String genBirthDay(int age, String theSeason) {
        int todaysYear = LocalDate.now().getYear();
        int animalBirthYear = todaysYear - age;
        String season = theSeason.toLowerCase();
        switch (season) {
            case "spring": return animalBirthYear + "-03-21";
            case "fall": return animalBirthYear + "-09-21";
            case "winter": return animalBirthYear + "-12-21";
            case "summer": return animalBirthYear + "-06-21";
            default: return animalBirthYear + "-01-01";
        }
    }
    public static HashMap<String, LinkedList<String>> createAnimalNameLists(String filePath) {
        HashMap<String, LinkedList<String>> allNames = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            LinkedList<String> currentList = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.endsWith("Names:")) {
                    String speciesName = line.replace(" Names:", "").toLowerCase();
                    currentList = new LinkedList<>();
                    allNames.put(speciesName, currentList);
                } else if (!line.isEmpty() && currentList != null) {
                    String[] names = line.split(",\\s*");
                    for (String name : names) {
                        currentList.add(name);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return allNames;
    }
    public static HashMap<String, Integer> getSpeciesCounts() {
        return speciesCounts;
    }
}