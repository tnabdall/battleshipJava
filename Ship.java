/** Contains the ship and its properties.
 * To be placed on GameBoard using PlaceShip.
 */
class Ship{


    private int length; //elements
    private String name;
    private int[][] coords;
    private boolean isAlive = true;

    /** Constructor to create ship.
     * @param name Ship's name.
     * @param length Ship's length
     */
    public Ship(String name, int length){
        setName(name);
		setLength(length);
		coords = new int[length][2];
    }

    /** Constructor to create ship.
     * @param name Ship's name.
     * @param length Ship's length.
     * @param newCoords Lengthx2 Array containing ship's coordinates.
     */
    public Ship(String name, int length, int[][] newCoords){
        setName(name);
        setLength(length);
		coords = new int[length][2];
        for (int i = 0; i<newCoords.length; i++){
			for (int j = 0; j<newCoords[i].length; j++){
				coords[i][j] = newCoords[i][j];
			}
		}
    }
	
	/**
	Copy constructor for ship
	@param other Other ship to copy
	*/
    public Ship(Ship other){
        setName(other.getName());
        setLength(other.getLength());
        coords = new int[length][2];
        int[][] otherCoords = other.getAllCoords();
        for (int i = 0; i<length; i++){
            coords[i][0] = otherCoords[i][0];
            coords[i][1] = otherCoords[i][1];
        }
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
    public int[] getCoords(int index) {
        int[] copy = new int[2];
        for (int i = 0; i< 2; i++){
            copy[i] = coords[index][i];
        }
        return copy;
    }

    /**
     * Get all coordinates of the ship.
     * @return Lengthx2 Matrix containing all coordinates of the ship.
     */
	public int[][] getAllCoords() {
	    int[][] copy = new int[length][2];
	    for (int i = 0; i<length; i++){
	        for (int j = 0; j<2; j++){
	            copy[i][j] = this.coords[i][j];
            }
        }
	    return copy;}

    /**
     * Method to print coordinates of the ship. For testing purposes mainly.
     */
	public void printCoords(){
		for (int i = 0; i< coords.length; i++){
			System.out.println(name + ": " + coords[i][0] + ", " + coords[i][1]);
		}
	}

    /**
     * Checks to see if ship is alive on a game board.
     * @param g The gameBoard to check if the ship is alive on
     * @return True or False
     */
	public boolean isAlive(GameBoard g){
	    int health = length;
	    for (int i =0; i< coords.length; i++){
	        if (g.getBoardElement(coords[i][0],coords[i][1])==2){
	            health-=1;
            }
        }
        if (health==0){
            return false;
        }
        else{
            return true;
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
        if(length>0){
            this.length = length;
        }
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
















