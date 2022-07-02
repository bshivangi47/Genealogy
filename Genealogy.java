import java.sql.*;
import java.util.*;

/**
 * Author name: Shivangi Bhatt
 * ClassName : Genealogy
 */
public class Genealogy extends Config {

    /*
     * Constructor name : Genealogy
     * method purpose : Initialize columnNames
     * arguments : none
     */
    Genealogy() {
        // initialize the columnNames
        initializeColumnNames();
    }

    /*
     * method name : findPerson
     * method purpose : returns the person with given name
     * arguments : String name
     * return value : personIdentity object
     */
    PersonIdentity findPerson(String name) {
        //input validation
        if (name.equalsIgnoreCase("") || name == null || name.trim().length() == 0) {
            return null;
        }
        //get connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //Execute query to get person with given name with limit 1
            Statement statement = connection.createStatement();
            String queryString = "SELECT personId FROM PERSON where name = '" + name + "' limit 1";
            ResultSet resultSet = statement.executeQuery(queryString);
            int personId = 0;
            //get the personId and find the object in list of person
            while (resultSet.next()) {
                personId = resultSet.getInt(1);
            }
            //return the found object
            return findPersonInList(personId);
        } catch (SQLException sqlException) {
            return null;
        }
    }

    /*
     * method name : findPerson
     * method purpose : returns the person with given id
     * arguments : int id
     * return value : personIdentity object
     */
    PersonIdentity findPerson(int id) {
        //return the object find in the list with given id
        return findPersonInList(id);
    }

    /*
     * method name : findMediaFile
     * method purpose : returns the fileIdentifier with given name
     * arguments : String name
     * return value : FileIdentifier object
     */
    FileIdentifier findMediaFile(String name) {
        //input validation
        if (name.equalsIgnoreCase("") || name == null || name.trim().length() == 0) {
            return null;
        }
        //get connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //Execute query to get media with given name with limit 1
            Statement statement = connection.createStatement();
            String queryString = "SELECT mediaId FROM media where filename = '" + name + "' limit 1";
            ResultSet resultSet = statement.executeQuery(queryString);
            int mediaId = 0;
            //get the mediaId and find the object in list of media
            while (resultSet.next()) {
                mediaId = resultSet.getInt(1);
            }
            //return the found object
            return findMediaInList(mediaId);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    /*
     * method name : findMediaFile
     * method purpose : returns the media with given id
     * arguments : int id
     * return value : FileIdentifier object
     */
    FileIdentifier findMediaFile(int id) {
        return findMediaInList(id);
    }

    /*
     * method name : findName
     * method purpose : returns the namae of given PersonIdentity object
     * arguments : PersonIdentity id
     * return value : String name
     */
    String findName(PersonIdentity id) {
        //If id is null, return empty string
        if (id == null) {
            return "";
        }
        //else get the name of object
        return id.getName();
    }


    /*
     * method name : findMediaFile
     * method purpose : returns the name of given FileIdentifier object
     * arguments : FileIdentifier fileId
     * return value : String name
     */
    String findMediaFile(FileIdentifier fileId) {
        //If id is null, return empty string
        if (fileId == null) {
            return "";
        }
        //else get the name of object
        return fileId.getFilename();
    }


    /*
     * method name : findRelation
     * method purpose : returns the name of given FileIdentifier object
     * arguments : FileIdentifier fileId
     * return value : String name
     */
    BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
        //check if person1 and person2 are null
        if (person1 == null || person2 == null) {
            //if yes, return null
            return null;
        }
        //create family tree
        FamilyTree familyTree = new FamilyTree();
        List<PersonIdentity> people = listOfPeople;
        familyTree.createTree(people);
        //find distance from their common ancestor
        String distance = findCommonAncestor(familyTree, person1, person2);

        //get the distance from person 1 and person 2
        String[] distances = distance.split("\\s+");
        int distance1 = Integer.parseInt(distances[0]);
        int distance2 = Integer.parseInt(distances[1]);

        //if distance1 or distance2 are equal to minValue of integer
        if (distance1 == minValue || distance2 == minValue) {
            //return null
            return null;
        } else {
            //return biological relation
            return new BiologicalRelation(distance1, distance2);
        }
    }

    /*
     * method name : findCommonAncestor
     * method purpose : returns the string of distance of common ancestor from person1 and person2
     * arguments : FamilyTree familyTree, PersonIdentity person1, PersonIdentity person2
     * return value : String
     */
    String findCommonAncestor(FamilyTree familyTree, PersonIdentity person1, PersonIdentity person2) {
        //loop through the height of the tree
        for (int i = 0; i < familyTree.getTreeHeight(); i++) {
            //get the ancestors of person1 with generation i
            Set<PersonIdentity> ancestorsOf1 = ancestores(person1, i);
            //if i==0, ancestorsOf1 is person1
            if(i==0){
                Set<PersonIdentity> ancestors1 = new HashSet<>();
                ancestors1.add(person1);
                ancestorsOf1 = ancestors1;
            }
            //loop through the height of the tree

            for (int j = 0; j < familyTree.getTreeHeight(); j++) {
                //get the ancestors of person2 with generation j

                Set<PersonIdentity> ancestorsOf2 = ancestores(person2, j);
                if(j==0){
                    //if j==0, ancestorsOf1 is person1

                    Set<PersonIdentity> ancestors2 = new HashSet<>();
                    ancestors2.add(person2);
                    ancestorsOf2 = ancestors2;
                }
                //If there is common ancestor in ancestorsOf1 and ancestorsOf2
                for (PersonIdentity ancestorOf1 : ancestorsOf1) {
                    for (PersonIdentity ancestorOf2 : ancestorsOf2) {
                        if (ancestorOf1 == ancestorOf2) {
                            //return the string of their distances
                            return i + " " + j;
                        }
                    }
                }
            }
        }
        //if no common ancestor found, return string with minimum value of infinity
        return minValue+" "+minValue;
    }



    /*
     * method name : descendents
     * method purpose : Returns the set of descendents upto given generation
     * arguments : PersonIdentity person, Integer generations
     * return value : Set<PersonIdentity>
     */
    Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {
        //Initialize descendants
        Set<PersonIdentity> descendants = new HashSet<>();
        //Check if person is null or generations is 0
        if (person == null || generations == 0) {
            return descendants;
        }
        //Initialize familyTree instance
        FamilyTree familyTree = new FamilyTree();
        List<PersonIdentity> people = listOfPeople;
        //Create tree
        familyTree.createTree(people);
        //get family tree
        Map<PersonIdentity, List<PersonIdentity>> tree = familyTree.getFamilyTree();
        //set currentGeneration to 0
        int currentGeneration = 0;
        //call recursively to get descendants
        return getDescendantsRecursive(currentGeneration, generations, person, tree, descendants);
    }


    /*
     * method name : getDescendantsRecursive
     * method purpose : Returns the set of descendents upto given generation
     * arguments : int currentGeneration, int generations, PersonIdentity
            person, Map<PersonIdentity, List<PersonIdentity>> tree, Set<PersonIdentity> descendants
     * return value : Set<PersonIdentity>
     */
    Set<PersonIdentity> getDescendantsRecursive(int currentGeneration, int generations, PersonIdentity
            person, Map<PersonIdentity, List<PersonIdentity>> tree, Set<PersonIdentity> descendants) {
        //get the children of person
        List<PersonIdentity> children = tree.get(person);

        if (children != null) {
            //add all the children to list of descendants
            descendants.addAll(children);
            //increment the currentGeneration
            currentGeneration++;
            //if currentGeneration is equal to the generation, return the list of descendants
            if (generations == currentGeneration) {
                return descendants;
            } else {
                //else, get descendants recursively of all the children
                for (PersonIdentity people : children) {
                    descendants = getDescendantsRecursive(currentGeneration, generations, people, tree, descendants);
                }
            }
        }
        return descendants;


    }

    /*
     * method name : ancestores
     * method purpose : Returns the set of ancestores upto given generation
     * arguments : PersonIdentity person, Integer generations
     * return value : Set<PersonIdentity>
     */
    Set<PersonIdentity> ancestores(PersonIdentity person, Integer generations) {
        //Initialize ancestors
        Set<PersonIdentity> ancestors = new HashSet<>();
        //Check if person is null or generations is 0
        if (person == null || generations == 0) {
            return ancestors;
        }
        //Initialize familyTree instance
        FamilyTree familyTree = new FamilyTree();
        List<PersonIdentity> people = listOfPeople;
        //Create tree
        familyTree.createTree(people);
        //set currentGeneration to 0
        int currentGeneration = 0;
        //call recursively to get ancestors
        return getAncestorsRecursive(currentGeneration, generations, person, familyTree, ancestors);
    }


    /*
     * method name : getAncestorsRecursive
     * method purpose : Returns the set of ancestores upto given generation
     * arguments : int currentGeneration, int generations, PersonIdentity
            person, FamilyTree familyTree, Set<PersonIdentity> ancestors
     * return value : Set<PersonIdentity>
     */
    Set<PersonIdentity> getAncestorsRecursive(int currentGeneration, int generations, PersonIdentity
            person, FamilyTree familyTree, Set<PersonIdentity> ancestors) {
        //if generation is equal to currentGeneration
        if (generations == currentGeneration) {
            //return list of ancestors
            return ancestors;
        }
        //Get list of parents of the person
        List<PersonIdentity> parents = familyTree.findParents(familyTree.getFamilyTree(), person);

        //if parents is not null
        if (parents != null) {
            //add all parents to list of ancestors
            ancestors.addAll(parents);
            //increment currentGeneration
            currentGeneration++;
            //If generation is not equal to current generation
            if (generations != currentGeneration) {
                //Recursively find ancestors of all the parents
                for (PersonIdentity people : parents) {
                    ancestors = getAncestorsRecursive(currentGeneration, generations, people, familyTree, ancestors);
                }
            }
        }
        //return ancestors
        return ancestors;
    }


    /*
     * method name : notesAndReferences
     * method purpose : Returns the list of notes and references in order of the date added
     * arguments :  PersonIdentity person
     * return value :  List<String>
     */
    List<String> notesAndReferences(PersonIdentity person) {
        //Initialize list of output
        List<String> output = new ArrayList<>();
        //If person is null, return output
        if (person == null) {
            return output;
        }
        //get connection
        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();

            //Execute query to combine notes and references of a person and order them by date
            String queryString = "select combinedColumn from (SELECT note as combinedColumn,date FROM notes where personId = " + person.getId() + "\n" +
                    "UNION\n" +
                    "SELECT `reference` as combinedColumn,date FROM `references` where personId = " + person.getId() + ") as combinedTable order by date;";
            ResultSet resultSet = statement.executeQuery(queryString);
            //Add each row to the output list and return list
            while (resultSet.next()) {
                output.add(resultSet.getString("combinedColumn"));
            }
            //return list
            return output;

        } catch (SQLException e) {

            return output;
        }
    }

    /*
     * method name : findMediaByTag
     * method purpose : Returns the Set<FileIdentifier> with the given tag between the given date
     * arguments :  String tag, String startDate, String endDate
     * return value :  List<String>
     */
    Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
        //Inititalize set of fileIdentifier
        Set<FileIdentifier> output = new HashSet<>();

        //Input validation
        if (tag.equalsIgnoreCase("") || tag == null || tag.trim().length() == 0 || startDate.equalsIgnoreCase("") || startDate.trim().length() == 0 || endDate.equalsIgnoreCase("") || endDate.trim().length() == 0) {
            return output;
        }
        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();
            //Execute the query to find mediaId with given tags added between given date  if column dateOfPictureTaken exists
            String condition = "";

            if (mediaAttributeExist("dateOfPictureTaken")) {
                if (startDate.trim().length() == 0 || endDate.trim().length() == 0 || startDate.equalsIgnoreCase("null") || endDate.equalsIgnoreCase("null")) {
                    condition = "and media.dateOfPictureTaken " +
                            "between '" + startDate + "' and '" + endDate + "'";
                }
            }
            String queryString = "select tags.mediaId from tags join media on tags.mediaId= media.mediaId where tags.tag = '" + tag + "'" + condition;
            ResultSet resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                //get the media Id and find the fileIdentifier object for the corresponding id
                int mediaId = resultSet.getInt("mediaId");
                FileIdentifier fileIdentifier = findMediaInList(mediaId);
                //add the object to output list
                output.add(fileIdentifier);
            }


            return output;
        } catch (SQLException sqlException) {
            return output;
        }
    }


    /*
     * method name : findMediaByLocation
     * method purpose : Returns the Set<FileIdentifier> with given location between given date
     * arguments :  String location, String startDate, String endDate
     * return value :  Set<FileIdentifier>
     */
    Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {
        //Initialize set of output
        Set<FileIdentifier> output = new HashSet<>();
        //input validation
        if (location.equalsIgnoreCase("") || location == null || location.trim().length() == 0 || startDate.equalsIgnoreCase("") || startDate.trim().length() == 0 || endDate.equalsIgnoreCase("") || endDate.trim().length() == 0) {
            return output;
        }
        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();
            //Execute the query to find mediaId with given location added between given date if column locOfPictureTaken exists

            if (mediaAttributeExist("locOfPictureTaken")) {
                String condition = "";
                if (startDate.trim().length() == 0 || endDate.trim().length() == 0 || startDate.equalsIgnoreCase("null") || endDate.equalsIgnoreCase("null")) {
                    condition = "and media.dateOfPictureTaken " +
                            "between '" + startDate + "' and '" + endDate + "'";
                }
                String queryString = "select mediaId from media where locOfPictureTaken = '" + location + "'" + condition;
                ResultSet resultSet = statement.executeQuery(queryString);

                while (resultSet.next()) {
                    //get the media Id and find the fileIdentifier object for the corresponding id
                    int mediaId = resultSet.getInt("mediaId");
                    FileIdentifier fileIdentifier = findMediaInList(mediaId);
                    //add the object to output list
                    output.add(fileIdentifier);
                }
            }
            return output;
        } catch (SQLException sqlException) {
            return output;
        }
    }

    /*
     * method name : findIndividualsMedia
     * method purpose : Returns the List<FileIdentifier> that includes the people from given set between given date
     * arguments :  Set<PersonIdentity> people, String startDate, String endDate
     * return value :  List<FileIdentifier>
     */
    List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String
            endDate) {
        //Initialize output list
        List<FileIdentifier> output = new ArrayList<>();
        //input validation
        if (people == null || people.size() == 0 || startDate.equalsIgnoreCase("") || startDate.trim().length() == 0 || endDate.equalsIgnoreCase("") || endDate.trim().length() == 0) {
            return output;
        }
        //Convert the set of people to array of PersonIdentity
        PersonIdentity[] peopleArray = people.toArray(new PersonIdentity[people.size()]);

        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();
            String condition1 = "";
            //Find the media that consist of that people and exists between the given start and end date.
            for (int i = 0; i < peopleArray.length; i++) {
                if (i == 0) {
                    condition1 += "people_in_media.personId = " + peopleArray[i].getId();
                } else {
                    condition1 += " or people_in_media.personId = " + peopleArray[i].getId();
                }

                String condition2 = "";
                if (startDate.trim().length() == 0 || endDate.trim().length() == 0 || startDate.equalsIgnoreCase("null") || endDate.equalsIgnoreCase("null")) {
                    condition2 = "and media.dateOfPictureTaken " +
                            "between '" + startDate + "' and '" + endDate + "'";
                }
                String queryString = "select media.mediaId from people_in_media join media on media.mediaId = people_in_media.mediaId\n" +
                        "where (" + condition1 + ") " + condition2 + " order by media.dateOfPictureTaken, media.filename";
                ResultSet resultSet = statement.executeQuery(queryString);

                while (resultSet.next()) {
                    int mediaId = resultSet.getInt("mediaId");
                    FileIdentifier fileIdentifier = findMediaInList(mediaId);
                    //Check if the media already exist in the list, if not add it to the list
                    if (!output.contains(fileIdentifier)) {
                        output.add(fileIdentifier);
                    }
                }
            }
            //return output
            return output;
        } catch (SQLException sqlException) {
            return output;
        }
    }


    /*
     * method name : findBiologicalFamilyMedia
     * method purpose : Returns the List<FileIdentifier> that includes the picture of immediate children of people.
     * arguments :  PersonIdentity person
     * return value :  List<FileIdentifier>
     */
    List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
        //Initialize output list
        List<FileIdentifier> output = new ArrayList<>();
        if (person == null) {
            return output;
        }
        //Create instance of family tree and create tree
        FamilyTree familyTree = new FamilyTree();
        List<PersonIdentity> people = listOfPeople;
        familyTree.createTree(people);
        //get the list of children of person
        List<PersonIdentity> children = familyTree.getFamilyTree().get(person);
        try (Connection connection = ConnectionClass.getConnection()) {
            for (PersonIdentity personIdentity : children) {
                //Execute the query to find mediaId for personIdentity
                Statement statement = connection.createStatement();

                String queryString = "select mediaId from people_in_media where personId =" + personIdentity.getId() + " order by media.dateOfPictureTaken, media.filename";
                ResultSet resultSet = statement.executeQuery(queryString);

                while (resultSet.next()) {
                    int mediaId = resultSet.getInt("mediaId");
                    FileIdentifier fileIdentifier = findMediaInList(mediaId);
                    //Check if the media already exist in the list, if not add it to the list
                    if (!output.contains(fileIdentifier)) {
                        output.add(fileIdentifier);
                    }
                }
            }
            return output;
        } catch (SQLException sqlException) {
            return output;
        }
    }
}
