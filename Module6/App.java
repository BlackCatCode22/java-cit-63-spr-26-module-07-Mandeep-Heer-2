package Module6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Module 6 Zookeeper's Challenge");
        System.out.println("Currently processing the incoming animal data from the files...\n");

        String filePath = "src/Module6/animalNames.txt";
        HashMap<String, LinkedList<String>> nameVault = Utilities.createAnimalNameLists(filePath);
        ArrayList<Animal> zooAnimals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Module6/arrivingAnimals.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] arrayOfStrPartsOnComma = line.split(", ");
                String aniArrivalDate = Utilities.arrivalDate();
                String aniColor = arrayOfStrPartsOnComma[2];
                String aniWeightStr = arrayOfStrPartsOnComma[3];
                String aniOrigin = arrayOfStrPartsOnComma[4];
                if (arrayOfStrPartsOnComma.length > 5) {
                    aniOrigin += ", " + arrayOfStrPartsOnComma[5];
                }

                int aniWeight = Integer.parseInt(aniWeightStr.trim().split(" ")[0]);
                String[] arrayOfStrPartsOnSpace = arrayOfStrPartsOnComma[0].split(" ");
                int intAniAge = Integer.parseInt(arrayOfStrPartsOnSpace[0]);
                String aniSex = arrayOfStrPartsOnSpace[3];
                String aniSpecies = arrayOfStrPartsOnSpace[4].toLowerCase();
                String[] arrayOfStrPartsOnSpace02 = arrayOfStrPartsOnComma[1].split(" ");
                String animalBirthSeason = arrayOfStrPartsOnSpace02[2];
                String aniBirthDate = Utilities.genBirthDay(intAniAge, animalBirthSeason);
                String aniID = Utilities.genUniqueID(aniSpecies);

                String assignedName = "Nameless";
                if (nameVault.containsKey(aniSpecies) && !nameVault.get(aniSpecies).isEmpty()) {
                    assignedName = nameVault.get(aniSpecies).poll();
                }

                zooAnimals.add(new Animal(aniSpecies, aniSex, intAniAge, aniWeight, assignedName, aniID, aniBirthDate, aniColor, aniOrigin, aniArrivalDate));
            }
        } catch (IOException e) {
            System.out.println("There was an error reading the arriving animals file: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n========= ZOO MANAGER UI =========");
            System.out.println("1. Generate Report and Exit");
            System.out.println("2. Add New Animal Entry");
            System.out.println("3. Exit without generating report");
            System.out.println("==================================\n");
            System.out.print("Choose an option (1-3): ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Module6/zooPopulation.txt"))) {
                    writer.write("******* Zoo Population and Habitat Assignment Report ********\n\n");

                    ArrayList<String> habitats = new ArrayList<>();
                    for (Animal a : zooAnimals) {
                        String speciesName = a.getSpecies();
                        if (!habitats.contains(speciesName)) {
                            habitats.add(speciesName);
                        }
                    }

                    for (String habitat : habitats) {
                        String displayHabitat = habitat.substring(0, 1).toUpperCase() + habitat.substring(1);
                        writer.write(displayHabitat + " Habitat:\n\n");

                        for (Animal a : zooAnimals) {
                            if (a.getSpecies().equals(habitat)) {
                                writer.write(a.getAnimalDetails() + "\n");
                            }
                        }
                        writer.write("\n");
                    }

                    System.out.println("\nSuccessfully generated zooPopulation.txt!");
                    System.out.println("\n==== ZOO SUMMARY ====");
                    System.out.println("Total Animals: " + zooAnimals.size());

                    HashMap<String, Integer> counts = Utilities.getSpeciesCounts();
                    for (String species : counts.keySet()) {
                        String displaySpecies = species.substring(0, 1).toUpperCase() + species.substring(1);
                        System.out.println("Total " + displaySpecies + "s: " + counts.get(species));
                    }
                    System.out.println("=====================\n");

                } catch (IOException e) {
                    System.out.println("There was an error when writing to file: " + e.getMessage());
                }

                keepRunning = false;

            } else if (choice.equals("2")) {
                System.out.println("\n===== Add New Animal =====");

                System.out.print("Species: ");
                String species = scanner.nextLine().trim().toLowerCase();

                System.out.print("Age: ");
                int age = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Sex (male/female): ");
                String sex = scanner.nextLine().trim();

                System.out.print("Birth Season: ");
                String season = scanner.nextLine().trim();

                System.out.print("Color: ");
                String color = scanner.nextLine().trim();
                if (!color.toLowerCase().contains("color")) color += " color";

                System.out.print("Weight: ");
                int weight = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Origin: ");
                String origin = scanner.nextLine().trim();
                if (!origin.toLowerCase().contains("from")) origin = "from " + origin;

                String arrivalDate = Utilities.arrivalDate();
                String birthDate = Utilities.genBirthDay(age, season);
                String id = Utilities.genUniqueID(species);

                String assignedName = "Nameless";
                if (nameVault.containsKey(species) && !nameVault.get(species).isEmpty()) {
                    assignedName = nameVault.get(species).poll();
                }
                zooAnimals.add(new Animal(species, sex, age, weight, assignedName, id, birthDate, color, origin, arrivalDate));
                try (BufferedWriter appendWriter = new BufferedWriter(new FileWriter("src/Module6/arrivingAnimals.txt", true))) {
                    String newArrivalLine = age + " year old " + sex + " " + species + ", born in " + season + ", " + color + ", " + weight + " pounds, " + origin;
                    appendWriter.write("\n" + newArrivalLine);
                } catch (IOException e) {
                    System.out.println("Error saving to arrivingAnimals.txt: " + e.getMessage());
                }

                System.out.println("\nSuccessfully Added " + species + " to the database. Name assigned: " + assignedName + ", ID: " + id);

            } else if (choice.equals("3")) {
                System.out.println("\nProgram exited without generating a report. Have a great day!");
                keepRunning = false;
            } else {
                System.out.println("\nInvalid choice. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}