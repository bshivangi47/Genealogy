import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Author name: Shivangi Bhatt
 * ClassName : FileIdentifier
 *
 */
public class FileIdentifier extends Config{
    //define variables
    private String filename;
    private int id;

    /*
     * Constructor name : FileIdentifier
     * method purpose : Initialize filename and id
     * arguments : String filename, int id
     */
    FileIdentifier(String filename, int id){
        this.filename = filename;
        this.id=id;
    }

    /*
     * method name : getFilename
     * method purpose : Returns the filename of the file
     * arguments : none
     * return value : filename
     */
    public String getFilename() {
        return filename;
    }

    /*
     * method name : getId
     * method purpose : Returns the id of the file
     * arguments : none
     * return value : id
     */
    public int getId() {
        return id;
    }


}
