import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Author name: Shivangi Bhatt
 * ClassName : ManageMediaArchive
 * Purpose : Manage media archive
 */

public class MediaArchive extends Config {

    /*
     * Constructor name : MediaArchive
     * method purpose : Initialize mediaAttributes and listofMedia
     * arguments : none
     */
    MediaArchive() {
        //Get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {
            mediaAttributes.clear();
            listOfMedia.clear();
            //execute query to get from media table
            Statement statement = connection.createStatement();
            String queryString = "SELECT * from MEDIA";
            ResultSet resultSet = statement.executeQuery(queryString);
            //Get the metaData for media
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //get the total columns in the resultSet
            int totalColumnsForMedia = resultSetMetaData.getColumnCount();
            //add the column names to mediaAttributes list
            for (int i = 1; i <= totalColumnsForMedia; i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                mediaAttributes.add(columnName);
            }

            //loop through all the rows of resultSet
            while (resultSet.next()) {
                //Create the fileIdentifier object and add it to list OF media

                FileIdentifier newFile = new FileIdentifier(resultSet.getString("filename"), resultSet.getInt("mediaId"));

                listOfMedia.add(newFile);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /*
     * method name : addMediaFile
     * method purpose : add the media file to database
     * arguments : String fileLocation
     * return value : FileIdentifier object
     */
    FileIdentifier addMediaFile(String fileLocation) {
        //Check input
        if (fileLocation.equalsIgnoreCase("") || fileLocation == null || fileLocation.trim().length() == 0) {
            return null;
        }
        //Get the connection
        try (Connection connection = ConnectionClass.getConnection()) {
            //query to Insert into table
            String queryString = "INSERT INTO media(mediaId, filename) VALUES(Null,'" + fileLocation + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();
            //Get the generated key
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedKey = resultSet.getInt(1);

                //Create fieleIdentifier object and add it to list of media
                FileIdentifier newFile = new FileIdentifier(fileLocation, generatedKey);
                listOfMedia.add(newFile);
                //return the object
                return newFile;
            } else {
                //If not inserted into the table, return null
                return null;
            }
        } catch (SQLException sqlException) {
            return null;
        }
    }

    /*
     * method name : recordMediaAttributes
     * method purpose : to record the attributes of the media
     * arguments : FileIdentifier fileIdentifier, Map<String, String> attributes
     * return value : Returns true if attributes are added to the table and false otherwise
     */
    Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        //input validation
        if (fileIdentifier == null || attributes == null) {
            return false;
        }
        //get media ID
        int mediaId = fileIdentifier.getId();
        //get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {
            //Create statement
            Statement statement = connection.createStatement();
            //Loop through all the map of attributes
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                //Convert the ambiguous column name that means the same to one name
                String columnName = convertColumnName(entry.getKey());
                //Check if the column name exist in list of media Attributes
                if (!mediaAttributeExist(columnName)) {
                    //execute query to add the column to the table
                    String query = "ALTER TABLE media ADD COLUMN " + columnName + " VARCHAR(50)";
                    statement.executeUpdate(query);
                    //add the column to the list of media attributes
                    mediaAttributes.add(columnName);
                }

                //Execute query to update the column values in the table
                String query = "UPDATE media SET " + columnName + "= '" + entry.getValue() + "' where mediaId=" + mediaId;
                statement.executeUpdate(query);
            }
            return true;
        } catch (SQLException sqlException) {

            return false;
        }

    }

    /*
     * method name : peopleInMedia
     * method purpose : Store the list of people in media
     * arguments : FileIdentifier fileIdentifier, List<PersonIdentity> people
     * return value : returns true if people are added in people in media and false otherwise
     */
    Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        //input validation
        if (fileIdentifier == null || people == null || people.size() == 0) {
            return false;
        }

        //get the id of file
        int fileId = fileIdentifier.getId();
        int rowsAffected = 0;
        //get the connection object
        try (Connection connection = ConnectionClass.getConnection()) {

            Statement statement = connection.createStatement();
            //Loop through the list of people
            for (PersonIdentity person : people
            ) {
                //get the person id
                int personId = person.getId();
                //execute query to insert people in media
                String queryString = "INSERT INTO people_in_media(mediaId,personId) VALUES(" + fileId + ", " + personId + ")";
                rowsAffected += statement.executeUpdate(queryString);
            }
            //return true if all the people are added and false otherwise
            return rowsAffected == people.size();
        } catch (SQLException sqlException) {

            return false;
        }

    }

    /*
     * method name : tagMedia
     * method purpose : Adds a tag to the media
     * arguments : FileIdentifier fileIdentifier, String tag
     * return value : returns true is the tag is added and false otherwise
     */
    Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        //Input validation
        if (fileIdentifier == null || tag.equalsIgnoreCase("") || tag == null || tag.trim().length() == 0) {
            return false;
        }
        //get the file Id
        int fileId = fileIdentifier.getId();
        try (Connection connection = ConnectionClass.getConnection()) {
            Statement statement = connection.createStatement();
            //Execute query to insert the tag into tags table
            String queryString = "INSERT INTO tags VALUES(Null," + fileId + ", '" + tag + "')";
            int rowsAffected = statement.executeUpdate(queryString);
            //return true if successfully added to the table and false otherwise
            return rowsAffected > 0;
        } catch (SQLException sqlException) {

            return false;
        }
    }

}
