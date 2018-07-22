/** Contains the ship and its properties.
 * To be placed on GameBoard using PlaceShip.
 */
class Ship{


    private int length; //elements
    private String name;
    private int[][] coords;

    /** Constructor to create ship.
     * @param name Ship's name.
     * @param length Ship's length
     */
    public Ship(String name, int length){
        this.name = name;
        this.length = length;
		coords = new int[length][2];
    }

    /** Constructor to create ship.
     * @param name Ship's name.
     * @param length Ship's length.
     * @param newCoords Lengthx2 Array containing ship's coordinates.
     */
    public Ship(String name, int length, int[][] newCoords){
        this.name = name;
        this.length = length;
        this.coords = newCoords;
		coords = new int[length][2];
    }

    /** Method to set coordinates for ship.
     *
     * @param newCoords Row and column to be set.
     * @param index Ship's index. Index 0 is first coordinate placed. Length - 1 is other end of ship.
     */
    public void setCoords(int[] newCoords, int index){
        int[] coordsCopy = new int[2];
		coordsCopy[0] = newCoords[0];
		coordsCopy[1] = newCoords[1];
		coords[index] = coordsCopy;
    }

    /** Getter method for a coordinate given the index.
     *
     * @param index Index of Ship coordinates
     * @return The row and column of that coordinate.
     */
    public int[] getCoords(int index) { return coords[index]; }

    /**
     * Get all coordinates of the ship.
     * @return Lengthx2 Matrix containing all coordinates of the ship.
     */
	public int[][] getAllCoords() {return coords;}

    /**
     * Method to print coordinates of the ship. For testing purposes mainly.
     */
	public void printCoords(){
		for (int i = 0; i< coords.length; i++){
			System.out.println(name + ": " + coords[i][0] + ", " + coords[i][1]);
		}
	}

    /**
     * Gives length of ship.
     * @return Ships length in elements.
     */
    public int getLength() {
        return length;
    }

    /** Sets length of the ship.
     *
     * @param length Length to be set for the ship.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Gets the ships name.
     * @return Name of the ship.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the ships name.
     * @param name New name of the ship.
     */
    public void setName(String name) {
        this.name = name;
    }
}