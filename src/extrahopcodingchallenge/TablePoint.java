/*
 * Class to encapsulate table (two-dimensional array) points so they can be
 * easily returned by a function
 * Created by David Johnson, October 3, 2017
 * for ExtraHop Networks interview
 */
package extrahopcodingchallenge;

public class TablePoint {
    
    // Fields
    private int x;
    private int y;
    
    // Constructor
    public TablePoint(int y, int x) {
        this.y = y;
        this.x = x;
    }
    
    // Accessors
    public int getY() {
        return this.y;
    }
    
    public int getX() {
        return this.x;
    }
    
    @Override
    public String toString() {
        return "[" + y + ", " + x + "]";
    }
    
    // Mutators
    public void setY(int y) {
        this.y = y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
}
