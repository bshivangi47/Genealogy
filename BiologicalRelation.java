/**
 * Author name: Shivangi Bhatt
 * ClassName : BiologicalRelation
 *
 */
public class BiologicalRelation {
    //define variables
    private int CousinShip;
    private int Removal;

    /*
     * Constructor name : BiologicalRelation
     * method purpose : Calculate the degree of removal and degree of cousinShip based on the distance
     * arguments : int distance1, int distance2
     */
    BiologicalRelation(int distance1, int distance2){
        this.Removal = Math.abs(distance1-distance2);
        this.CousinShip = Math.min(distance1, distance2)-1;
    }

    /*
     * method name : getCousinShip
     * method purpose : Returns cousinShip
     * arguments : none
     * return value : integer CousinShip
     */
    public int getCousinShip() {
        return CousinShip;
    }

    /*
     * method name : getRemoval
     * method purpose : Returns Removal
     * arguments : none
     * return value : integer Removal
     */
    public int getRemoval() {
        return Removal;
    }


}
