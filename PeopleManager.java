import java.sql.*;
import java.util.Map;

/**
 * Author name: Shivangi Bhatt
 * ClassName : People
 * Purpose : Manage People
 */
public class PeopleManager extends Config {

    /*
     * Constructor name : People
     * method purpose : Initialize peopleAttributes and listofPeople
     * arguments : none
     */
    PeopleManager() {
        //Get the connection object

        try (Connection connection = ConnectionClass.getConnection()) {
            peopleAttributes.clear();
            listOfPeople.clear();
            //execute query to get from media table
            Statement statement = connection.createStatement();
            String queryString = "SELECT * FROM PERSON";
            ResultSet resultSet = statement.executeQuery(queryString);
            //Get the metaData for media
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //get the total columns in the resultSet
            int totalColumns = resultSetMetaData.getColumnCount();
            //add the column names to mediaAttributes list
            for (int i = 1; i <= totalColumns; i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                peopleAttributes.add(columnName);
            }
            //loop through all the rows of resultSet

            while (resultSet.next()) {
                //Create the fileIdentifier object and add it to list OF media
                PersonIdentity newPerson = new PersonIdentity(resultSet.getString("name"), resultSet.getInt("personId"));
                listOfPeople.add(newPerson);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    /*
     * method name : addPerson
     * method purpose : add the Person to database
     * arguments : String name
     * return value : PersonIdentity object
     */
    PersonIdentity addPerson(String name) {
        //Check input
        if (name.equalsIgnoreCase("") || name == null || name.trim().length() == 0) {
            return null;
        }
        //Get the connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //query to Insert into table
            String queryString = "INSERT INTO person(personId, name) VALUES(Null,'" + name + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();
            //Get the generated key
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int generatedKey;
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
                //Create PersonIdentity object and add it to list of people
                PersonIdentity newPerson = new PersonIdentity(name, generatedKey);
                listOfPeople.add(newPerson);
                //return the object
                return newPerson;
            } else {
                //If not inserted into the table, return null
                return null;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    /*
     * method name : recordAttributes
     * method purpose : to record the attributes of the person
     * arguments : PersonIdentity person, Map<String, String> attributes
     * return value : Returns true if attributes are added to the table and false otherwise
     */
    Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        //input validation
        if (person == null || attributes == null) {
            return false;
        }
        //get person ID
        int personId = person.getId();
        //get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {
            //Create statement
            Statement statement = connection.createStatement();
            //Loop through all the map of attributes
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                //Convert the ambiguous column name that means the same to one name
                String columnName = convertColumnName(entry.getKey());
                //Check if the column name exist in list of people Attributes
                if (!peopleAttributeExist(columnName)) {
                    //execute query to add the column to the table
                    String query = "ALTER TABLE person ADD COLUMN " + columnName + " VARCHAR(50)";
                    statement.executeUpdate(query);
                    //add the column to the list of people attributes
                    peopleAttributes.add(columnName);
                }
                //Execute query to update the column values in the table
                String query = "UPDATE person SET " + columnName + "= '" + entry.getValue() + "' where personId=" + personId;
                statement.executeUpdate(query);

            }
            return true;
        } catch (SQLException sqlException) {
            return false;
        }
    }

    /*
     * method name : recordReference
     * method purpose : Adds a reference to the people
     * arguments : PersonIdentity person, String reference
     * return value : returns true is the reference is added and false otherwise
     */
    Boolean recordReference(PersonIdentity person, String reference) {
        //input validation
        if (person == null || reference.equalsIgnoreCase("") || reference == null || reference.trim().length() == 0) {
            return false;
        }
        //get the person id
        int personId = person.getId();
        //get the connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //execute query to insert reference into reference table
            Statement statement = connection.createStatement();
            String queryString = "INSERT INTO `references`(personId, reference) VALUES(" + personId + ", '" + reference + "')";
            int rowsAffected = statement.executeUpdate(queryString);
            //return true if the reference was added to the table and false otherwise
            return rowsAffected > 0;
        } catch (SQLException sqlException) {

            return false;
        }
    }


    /*
     * method name : recordNote
     * method purpose : Adds a note for the people
     * arguments : PersonIdentity person, String note
     * return value : returns true is the note is added and false otherwise
     */
    Boolean recordNote(PersonIdentity person, String note) {
        //input validation
        if (person == null || note.equalsIgnoreCase("") || note == null || note.trim().length() == 0) {
            return false;
        }
        //get the person id
        int personId = person.getId();
        //get the connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //execute query to insert note into notes table
            Statement statement = connection.createStatement();
            String queryString = "INSERT INTO notes(personId,note) VALUES(" + personId + ", '" + note + "')";
            int rowsAffected = statement.executeUpdate(queryString);
            //return true if the note was added to the table and false otherwise
            return rowsAffected > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }


    /*
     * method name : recordChild
     * method purpose : Add parent child relation
     * arguments : PersonIdentity parent, PersonIdentity child
     * return value : returns true if the child is recorded and false otherwise
     */
    Boolean recordChild(PersonIdentity parent, PersonIdentity child) {
        //input validation
        if (parent == null || child == null) {
            return false;
        }
        //Create genealogy object
        Genealogy genealogy = new Genealogy();
        //Check if parent and child are already biologically related
        if (genealogy.findRelation(parent, child) != null) {
            return false;
        }
        //Get parent and child id
        int parentId = parent.getId();
        int childId = child.getId();
        //Get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();
            //Check if there is partnering between given people
            String queryString1 = "select * from partnering where (partner_1_id =" + parentId + " or partner_1_id =" + childId + ") and (partner_2_id =" + parentId + " or partner_2_id =" + childId + ")";
            ResultSet resultSet = statement.executeQuery(queryString1);

            if (resultSet.next()) {
                //if true return false
                return false;
            }
            //Check if there is dissolutions between given people

            String queryString2 = "select * from dissolutions where (partner_1_id =" + parentId + " or partner_1_id =" + childId + ") and (partner_2_id =" + parentId + " or partner_2_id =" + childId + ")";
            ResultSet resultSet2 = statement.executeQuery(queryString2);

            if (resultSet2.next()) {
                //if true return false
                return false;
            }

            //inset the relation into descendants table
            String queryString = "INSERT INTO descendants VALUES(Null," + parentId + ", " + childId + ")";
            int rowsAffected = statement.executeUpdate(queryString);
            //return true if added to the table
            return rowsAffected > 0;
        } catch (SQLException sqlException) {
            return false;
        }
    }


    /*
     * method name : recordPartnering
     * method purpose : Add partnering relation
     * arguments : PersonIdentity partner1, PersonIdentity partner2
     * return value : returns true if the partnering is recorded and false otherwise
     */
    Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        //input validation
        if (partner1 == null || partner2 == null) {
            return false;
        }
        //Create genealogy object
        Genealogy genealogy = new Genealogy();
        //Check if partner1 and partner2 are already biologically related
        if (genealogy.findRelation(partner1, partner2) != null) {
            return false;
        }
        //Get partner1 and partner2 id
        int partner1Id = partner1.getId();
        int partner2Id = partner2.getId();
        //Get the connection object

        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();

            //Check if partner1 and partner2 have parent-child relation
            String queryStringForChildRelation = "select * from descendants where (parentId =" + partner1Id + " or parentId =" + partner2Id + ") and (childId =" + partner2Id + " or childId =" + partner1Id + ")";
            ResultSet resultSetForChildRelation = statement.executeQuery(queryStringForChildRelation);

            if (resultSetForChildRelation.next()) {
                //if true return false
                return false;
            }
            //Check if the partnering has already been recorded
            String queryString1 = "select * from partnering where (partner_1_id =" + partner1Id + " or partner_1_id =" + partner2Id + ") and (partner_2_id =" + partner2Id + " or partner_2_id =" + partner1Id + ")";
            ResultSet resultSet = statement.executeQuery(queryString1);

            if (resultSet.next()) {
                //if true return true
                return true;
            }

            //else, execute query to delete the dissolutions from dissolutions table
            String queryString2 = "delete from dissolutions where (partner_1_id =" + partner1Id + " or partner_1_id =" + partner2Id + ") and (partner_2_id =" + partner2Id + " or partner_2_id =" + partner1Id + ")";
            statement.executeUpdate(queryString2);
            //Execute query to the partnering to table

            String queryString = "INSERT INTO partnering VALUES(Null," + partner1Id + ", " + partner2Id + ")";
            int rowsAffected = statement.executeUpdate(queryString);
            //return true if successfully recorded
            return rowsAffected > 0;
        } catch (SQLException sqlException) {

            return false;
        }

    }

    /*
     * method name : recordDissolution
     * method purpose : Add partnering relation
     * arguments : PersonIdentity partner1, PersonIdentity partner2
     * return value : returns true if the dissolution is recorded and false otherwise
     */
    Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
        //input validation
        if (partner1 == null || partner2 == null) {
            return false;
        }
        //Create genealogy object
        Genealogy genealogy = new Genealogy();
        //Check if partner1 and partner2 are already biologically related
        if (genealogy.findRelation(partner1, partner2) != null) {
            return false;
        }
        //Get partner1 and partner2 id
        int partner1Id = partner1.getId();
        int partner2Id = partner2.getId();
        //Get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {
            //Check query to see if there is partnering recorded
            Statement statement = connection.createStatement();
            String queryString1 = "select * from partnering where (partner_1_id =" + partner1Id + " or partner_1_id =" + partner2Id + ") and (partner_2_id =" + partner2Id + " or partner_2_id =" + partner1Id + ")";
            ResultSet resultSet = statement.executeQuery(queryString1);
            //If not, return false
            if (!resultSet.next()) {
                return false;
            } else {
                //else, execute query to delete the partnering from partnering table
                String queryString2 = "delete from partnering where (partner_1_id =" + partner1Id + " or partner_1_id =" + partner2Id + ") and (partner_2_id =" + partner2Id + " or partner_2_id =" + partner1Id + ")";
                statement.executeUpdate(queryString2);
                //record the dissolution to dissolution table
                String queryString3 = "INSERT INTO dissolutions VALUES(Null," + partner1Id + ", " + partner2Id + ")";
                int rowsAffected = statement.executeUpdate(queryString3);
                //return true if added to the table
                return rowsAffected > 0;
            }
        } catch (SQLException sqlException) {
            return false;
        }
    }

}
