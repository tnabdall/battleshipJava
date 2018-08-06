package Main;

/**
 * This class symbolizes the memory of the Main.AI. It stores the information on the ships that the Main.AI has hit.
 */
public class AIShipData {

	private Ship hitShip; // The ship that the Main.AI has hit.
	private int initRow; // The row location of the first hit on a ship.
	private int initCol; // The column location of the first hit on a ship.
	private String direction; // The direction the Main.AI was facing when the Main.AI hit a ship twice.
	private int health; // The health of the hit ship.
	private int timesHit; // The number of times the Main.AI has hit a shit.
	private boolean isSunk = false; // If the hit ship has been sunk or not.

	/**
	 * Constructor class to initialize the ship that the Main.AI has hit
	 * @param hitShip is the ship that the Main.AI hit
	 * @param initRow is the row that the hit ship was on
	 * @param initCol is the column that the hit ship was on
	 */
	public AIShipData(Ship hitShip, int initRow, int initCol) {
		this.hitShip = hitShip;
		this.initRow = initRow;
		this.initCol = initCol;
		setHealth();
	}

	/**
	 * Constructor class used to copy row and column information
	 * @param initRow the row that the ship initially hit a ship on
	 * @param initCol the column that the ship initially hit a ship on
	 */
	public AIShipData(int initRow, int initCol) {
		this.initRow = initRow;
		this.initCol = initCol;
	}

	/**
	 * Constructor class used to copy the direction the Main.AI was facing when it determined
	 * which direction the ship was in
	 * @param direction is the direction the Main.AI was facing
	 */
	public AIShipData(String direction) {
		this.direction = direction;
	}

	/**
	 * Getter method to get the row that the Main.AI initially hit a ship on.
	 * @return is the row
	 */
	public int getInitRow() {
		return initRow;
	}

	/**
	 * Getter method to get the column that the Main.AI initially hit a ship on.
	 * @return is the column
	 */
	public int getInitCol() {
		return initCol;
	}

	/**
	 * Setter method to set the direction the Main.AI was facing when it hit a ship twice.
	 * @param direction is the direction the Main.AI was facing.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Getter method for the direction the Main.AI was facing when it hit a ship twice.
	 * @return is the direction.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Getter method for the ship object that the Main.AI hit.
	 * @return the ship object.
	 */
	public Ship getHitShip() {
		return hitShip;
	}

	/**
	 * Setter method to set the health of the ship that the Main.AI hit.
	 */
	public void setHealth() {
		health = hitShip.getLength();
	}

	/**
	 * Mutator method to adjust the ships health when the Main.AI gets another hit on it
	 * and adds to how many times the Main.AI has hit said ship. Also checks to see if the ship still
	 * has health after getting hit. If not, the ship is considered sunk.
	 */
	public void minusShipHealth() {
		health -= 1;
		timesHit += 1;
		if (health == 0) {
			isSunk = true;
		} else {
			isSunk = false;
		}
	}

	/**
	 * Getter method for the status of the ship.
	 * @return if the ship has been sunk or not.
	 */
	public boolean getIsSunk() {
		return isSunk;
	}

	/**
	 * Getter method to get the number of times the Main.AI has hit a ship.
	 * @return the number of times a ship has been hit.
	 */
	public int getTimesHit() {
		return timesHit;
	}

}
