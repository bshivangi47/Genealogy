import java.util.*;

/**
 * Author name: Shivangi Bhatt
 * ClassName : Config
 * class Purpose: Defines all the global constants and methods
 */
public class Config {
    //define variables
    static List<PersonIdentity> listOfPeople = new ArrayList<>();
    static List<FileIdentifier> listOfMedia = new ArrayList<>();
    static List<String> peopleAttributes = new ArrayList<>();
    static List<String> mediaAttributes = new ArrayList<>();
    static Map<String, List<String>> columnNames = new HashMap<>();

    int minValue = Integer.MIN_VALUE;


    boolean personExists(int id) {
        return listOfPeople.stream().anyMatch(person -> person.getId() == id);
    }

    /*
     * method name : peopleAttributeExist
     * method purpose : Checks if the attribute exist in the peopleAttributes list
     * arguments : String attribute
     * return value : returns true if attribute exist and false otherwise
     */
    boolean peopleAttributeExist(String attribute) {
        return peopleAttributes.stream().anyMatch(attribute::equalsIgnoreCase);
    }

    /*
     * method name : mediaAttributeExist
     * method purpose : Checks if the attribute exist in the mediaAttributes list
     * arguments : String attribute
     * return value : returns true if attribute exist and false otherwise
     */
    boolean mediaAttributeExist(String attribute) {
        return mediaAttributes.stream().anyMatch(attribute::equalsIgnoreCase);
    }

    /*
     * method name : findPersonInList
     * method purpose : Finds the person object from the list of people with given id
     * arguments : int id
     * return value : Person object
     */
    PersonIdentity findPersonInList(int id) {
        return listOfPeople.stream().filter(people -> id == people.getId()).findFirst().orElse(null);
    }

    /*
     * method name : findMediaInList
     * method purpose : Finds the fileIdentifier object from the list of media with given id
     * arguments : int id
     * return value : FileIdentifier object
     */
    FileIdentifier findMediaInList(int id) {
        return listOfMedia.stream().filter(media -> id == media.getId()).findFirst().orElse(null);
    }

    /*
     * method name : initializeColumnNames
     * method purpose : Sets the value of columnNames hashmap
     * arguments : none
     * return value : none
     */
    void initializeColumnNames() {
        List<String> dateOfBirthAnalogies = new ArrayList<>(Arrays.asList("DateOFBirth", "DOB", "date_of_birth", "birthdate", "birth_date"));
        columnNames.put("date_of_birth", dateOfBirthAnalogies);
        List<String> dateOfDeathAnalogies = new ArrayList<>(Arrays.asList("DateOFDeath", "DOD", "date_of_death", "deathdate", "death_date"));
        columnNames.put("date_of_death", dateOfDeathAnalogies);
        List<String> locationOfBirthAnalogies = new ArrayList<>(Arrays.asList("locationOFBirth", "LOB", "location_of_birth", "loc_of_death", "birthloc", "locbirth", "locofbirth", "birthlocation", "birth_location", "birth_loc"));
        columnNames.put("location_of_birth", locationOfBirthAnalogies);
        List<String> locationOfDeathAnalogies = new ArrayList<>(Arrays.asList("locationOFDeath", "LOD", "location_of_death", "loc_of_death", "deathloc", "locdeath", "locofdeath", "deathlocation", "death_location", "death_loc"));
        columnNames.put("location_of_death", locationOfDeathAnalogies);
        List<String> locationOfPictureTakenAnalogies = new ArrayList<>(Arrays.asList("locationOFPicture", "locationOFPictureTaken", "LOP", "location_of_picture", "location_of_picture_taken", "location", "loc_of_picture", "pictureloc", "locpicture", "locofpicture", "picturelocation", "picture_location", "picture_loc"));
        columnNames.put("locOfPictureTaken", locationOfPictureTakenAnalogies);
        List<String> DateOfPictureTakenAnalogies = new ArrayList<>(Arrays.asList("DateOFPicture", "DateOFPictureTaken", "DOP", "Date_of_picture", "Date_of_picture_taken", "Date", "date_of_picture", "datepicture", "dateofpicture", "picturedate", "picture_date", "picture_date"));
        columnNames.put("dateOfPictureTaken", DateOfPictureTakenAnalogies);
    }

    /*
     * method name : convertColumnName
     * method purpose : Finds the key in the hashmap that contains given name in its list of value
     * arguments : String name
     * return value : String name
     */
    String convertColumnName(String name) {
        //Loop through hashmap columnNames
        for (Map.Entry<String, List<String>> entry : columnNames.entrySet()) {
            //Loop through value of each key
            for (String columnName :
                    entry.getValue()) {
                //Check if the columnName value is same as name
                if (columnName.equalsIgnoreCase(name)) {
                    //if true, return key
                    return entry.getKey();
                }
            }

        }
        return name;
    }


}
