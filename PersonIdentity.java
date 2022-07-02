import java.util.Map;

/**
 * Author name: Shivangi Bhatt
 * ClassName : PersonIdentity
 */
public class PersonIdentity extends Config {
    //define variables
    private String name;
    private int id;

    /*
     * Constructor name : PersonIdentity
     * method purpose : Initialize name and id
     * arguments : String name, int id
     */
    PersonIdentity(String name, int id) {
        this.name = name;
        this.id = id;
    }


    /*
     * method name : getName
     * method purpose : Returns the name of the person
     * arguments : none
     * return value : name
     */
    public String getName() {
        return name;
    }

    /*
     * method name : getId
     * method purpose : Returns the id of the person
     * arguments : none
     * return value : id
     */
    public int getId() {
        return id;
    }



}
