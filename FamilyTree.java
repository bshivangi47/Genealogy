import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Author name: Shivangi Bhatt
 * ClassName : FamilyTree
 * Purpose: To create family tree
 */

public class FamilyTree extends Config {
    //define variables
    private Map<PersonIdentity, List<PersonIdentity>> familyTree = new HashMap();

    /*
     * method name : getFamilyTree
     * method purpose : Returns the Map of familyTree
     * arguments : none
     * return value : familyTree
     */
    public Map<PersonIdentity, List<PersonIdentity>> getFamilyTree() {
        return this.familyTree;
    }

    /*
     * method name : createTree
     * method purpose : Creates the familyTree
     * arguments : List<PersonIdentity> listOfPeople
     * return value : none
     */
    void createTree(List<PersonIdentity> listOfPeople) {
        //get the connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //Iterate through listOFPeople
            for (PersonIdentity person :
                    listOfPeople) {
                //get the id
                int personId = person.getId();
                //Get the person object from the list and store it as parent

                PersonIdentity parent = findPerson(personId, listOfPeople);
                //Query to get the children from descendents table
                Statement statement = connection.createStatement();
                String queryString = "select childId from descendants where parentId = " + personId;
                ResultSet resultSet = statement.executeQuery(queryString);

                //initialize arraylist of descendents
                List<PersonIdentity> descendents = new ArrayList<>();
                //Until last row of the resultSet
                while (resultSet.next()) {
                    //Get the childId, find the person and add it to the arrayList
                    int childId = resultSet.getInt(1);
                    PersonIdentity child = findPerson(childId, listOfPeople);
                    descendents.add(child);
                }
                //Add the parent and its list of children to the hashmap of familyTree
                this.familyTree.put(parent, descendents);
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /*
     * method name : findPerson
     * method purpose : Finds the person object from the list of people with given id
     * arguments : int id, List<PersonIdentity> listPeople
     * return value : Person object
     */
    PersonIdentity findPerson(int id, List<PersonIdentity> listPeople) {
        return listPeople.stream().filter(people -> id == people.getId()).findFirst().orElse(null);
    }

    /*
     * method name : findParents
     * method purpose : Find the parents of given person from the given tree
     * arguments : Map<PersonIdentity, List<PersonIdentity>> tree, PersonIdentity person
     * return value : List of parents
     */
    List<PersonIdentity> findParents(Map<PersonIdentity, List<PersonIdentity>> tree, PersonIdentity person) {
        List<PersonIdentity> parents = new ArrayList<>();
        //Loop through the tree
        for (Map.Entry<PersonIdentity, List<PersonIdentity>> entry : tree.entrySet()) {
            //Check if the value of entrySet contains person object
            if (entry.getValue().contains(person)) {
                //if yes, add its key to the arrayList
                parents.add(entry.getKey());
            }
        }
        return parents;
    }

    /*
     * method name : getTreeHeight
     * method purpose : Find the height of the tree
     * arguments : none
     * return value : integer height
     */
      int getTreeHeight(){
          //set max length as 0
        int maxLength = 0;
        //loop through the tree
        for (Map.Entry<PersonIdentity, List<PersonIdentity>> entry : familyTree.entrySet()) {
            // if the size of entry.getValue is greater than maxLength
            int size = entry.getValue().size();
            if(size>maxLength){
                //set maxLength as size of entry.getValue
                maxLength =size;
            }
        }
        return maxLength;
    }
}
