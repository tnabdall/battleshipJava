/**
 * This class symbolizes the memory of the AI. It stores the information on the ships that the AI has hit.
 */
public class AIShipData {

	private Ship hitShip;
	private int initRow;
	private int initCol;
	private String direction;
	private int health;
	private int timesHit;
	private boolean isSunk = false;

	public String toString() {
		return "name: " + hitShip.getName() + " health: " + health + " direction: " + direction;
	}

	/**
	 * Constructor class to initialize the ship that the AI has hit
	 * @param hitShip is the ship that the AI hit
	 * @param initRow is the row that the hit ship was on
	 * @param initCol is the column that the hit ship was on
	 */
	public AIShipData(Ship hitShip, int initRow, int initCol) {
		this.hitShip = hitShip;
		this.initRow = initRow;
		this.initCol = initCol;
		setHealth();
		System.out.println(health + "-is the health");
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
	 * Constructor class used to copy the direction the AI was facing when it determined
	 * which direction the ship was in
	 * @param direction is the direction the AI was facing
	 */
	public AIShipData(String direction) {
		this.direction = direction;
	}

	/**
	 * Getter method to get the row that the AI initially hit a ship on.
	 * @return is the row
	 */
	public int getInitRow() {
		return initRow;
	}

	/**
	 * Getter method to get the column that the AI initially hit a ship on.
	 * @return is the column
	 */
	public int getInitCol() {
		return initCol;
	}

	/**
	 * Setter method to set the direction the AI was facing when it hit a ship twice.
	 * @param direction is the direction the AI was facing.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Getter method for the direction the AI was facing when it hit a ship twice.
	 * @return is the direction.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Getter method for the ship object that the AI hit.
	 * @return the ship object.
	 */
	public Ship getHitShip() {
		return hitShip;
	}

	/**
	 * Setter method to set the health of the ship that the AI hit.
	 */
	public void setHealth() {
		health = hitShip.getLength();
	}

	/**
	 * Mutator method to adjust the ships health when the AI gets another hit on it
	 * and adds to how many times the AI has hit said ship. Also checks to see if the ship still
	 * has health after getting hit. If not, the ship is considered sunk.
	 */
	public void minusShipHealth() {
		health -= 1;
		timesHit += 1;
		System.out.println(health + "-is the health");
		if (health == 0) {
			isSunk = true;
		} else {
			isSunk = false;
		}
		System.out.println("isSunk?" + isSunk);
	}

	/**
	 * Getter method for the status of the ship.
	 * @return if the ship has been sunk or not.
	 */
	public boolean getIsSunk() {
		return isSunk;
	}

	/**
	 * Getter method to get the number of times the AI has hit a ship.
	 * @return the number of times a ship has been hit.
	 */
	public int getTimesHit() {
		return timesHit;
	}

}
