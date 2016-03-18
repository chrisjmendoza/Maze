package ad325.graphs;

/**
 * helper class to describe a wall
 */
public class Wall {

    // the wall characteristics
    // location
    int x, y;
    // orientation
    boolean horz;

    /**
     * create a Wall object
     * @param xcoord the X-coordinate for the wall
     * @param ycoord the Y-coordinate for the wall
     * @param horizontal true, if the wall extends to the right
     * of the point, false if it extends down
     */
    public Wall(int xcoord, int ycoord, boolean horizontal) {
        if(xcoord < 0 || ycoord < 0) {
            throw new IllegalArgumentException("Negative values not supported");
        }
        x = xcoord;
        y = ycoord;
        horz = horizontal;
    }
    /**
     * override equals for use in the map
     * @param o The object to be compared
     * @return true, if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Wall) {
            Wall other = (Wall)o;
            return this.x == other.x && this.y == other.y && this.horz == other.horz;
        } else {
            return false;
        }
    }
    /**
     * override hashCode for use in the map
     * @return A hashcode for use in the map
     */
    @Override
    public int hashCode() {
        if(horz) {
            return 1 + 3 * x + 97 * y;
        } else {
            return 3 * x + 97 * y;
        }
    }
}
