package Module6;

public class Animal {
    private String species;
    private String sex;
    private int age;
    private int weight;
    private String animalName;
    private String animalID;
    private String animalBirthDate;
    private String animalColor;
    private String animalOrigin;
    private String animalArrivalDate;

    public Animal(String species, String sex, int age, int weight, String animalName,
                  String animalID, String animalBirthDate, String animalColor,
                  String animalOrigin, String animalArrivalDate) {
        this.species = species;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.animalName = animalName;
        this.animalID = animalID;
        this.animalBirthDate = animalBirthDate;
        this.animalColor = animalColor;
        this.animalOrigin = animalOrigin;
        this.animalArrivalDate = animalArrivalDate;
    }
    public String getAnimalDetails() {
        return animalID + "; " +
                animalName + "; birth date: " +
                animalBirthDate + "; " +
                animalColor + "; " +
                sex + "; " +
                weight + " pounds; " +
                animalOrigin + "; arrived " +
                animalArrivalDate;
    }
    public String getSpecies() { return species; }
    public String getAnimalOrigin() { return animalOrigin; }
    public String getAnimalColor() { return animalColor; }
    public String getAnimalBirthDate() { return animalBirthDate; }
    public String getAnimalID() { return animalID; }
    public String getAnimalName() { return animalName; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public int getWeight() { return weight; }
    public String getAnimalArrivalDate() { return animalArrivalDate; }
}