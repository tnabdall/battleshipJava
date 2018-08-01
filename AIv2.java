import java.util.Random;
import java.util.ArrayList;

public class AIv2 {

	private int difficulty = 0; // The difficulty of the AI. 0 for normal, 1 for challenge, 2 for impossible, 3 for random

	private PlayerBoard playerBoard; // A copy of the player's board.

	private int row; // The row location of the AI
	private int col; // The column location of the AI
	private int status; // The status of the position of the AI
	private String direction;

	private AIShipData target; // AI target ship

	/**
	 * New variables for ship status
	 */
	private ArrayList<AIShipData> hitShips = new ArrayList<AIShipData>();

	/** Default constructor for AI
	*/
	public AIv2(){
		PlayerBoard playerBoard = new PlayerBoard();
	}
	/** Constructor for AI
	* @param playerBoard is the GameBoard object of the user's board
	*/

	public AIv2(PlayerBoard playerBoard) {
		this.playerBoard = playerBoard;
	}

	/** Setter method for difficulty
	 * @param difficulty is the difficulty the AI will be set to.
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/** Getter method for row
	*/
	public int getRow() {
		return row;
	}

	/** Getter method for column
	*/
	public int getCol() {
		return col;
	}

	/** Calculates next move depending on the difficulty setting.
	*/
	public void runDifficulty(){
		if (difficulty == 0){
			normalDifficulty();
		} else if (difficulty == 1) {
			challengeDifficulty();
		} else if (difficulty == 2) {
			impossibleDifficulty();
		} else if (difficulty == 3) {
			randomDifficulty();
		}
	}

	/** The AI for normal difficulty
	 * Initially checks to see if previous played location was a hit. If so it plays strategically,
	 * otherwise it plays randomly.
	 */
	private void normalDifficulty() {
		try {
			System.out.println(target.toString());
		} catch (NullPointerException e2) {
		}
		if (target != null) {
			System.out.println("normal target != null");
			checkTargetShip();
		}
		if (target == null) {
			playRandom();
		} else if (target.getTimesHit() > 0){
			System.out.println("normal times hit > 0");
			playStrategic();
		}
	}

	/** The AI for challenge difficulty.
	 *	Will be programmed so that when it hits it will end up sinking the ship
	 * without missing. It will know the coordinates of the ship after hitting once.
	 */
	private void challengeDifficulty() {
	}

	/** AI for impossible difficulty.
	 *  Will be programmed to always hit.
	 */
	private void impossibleDifficulty() {
		int[] coords = playerBoard.getShipLoc();
		row = coords[0];
		col = coords[1];
	}

	/** AI for random difficulty.
	 * Randomly plays locations that haven't been fired at
	 */
	private void randomDifficulty() {
		randomRow();
		randomCol();
		while (playerBoard.locStatus(row, col) == 1 || playerBoard.locStatus(row, col) == 2 ) {
			randomRow();
			randomCol();
		}
	}

	/**
	 * Method to check on the status of the AI's target ship.
	 * If the ship is sunk then the ship is removed from the AI's list of targets, and the
	 * target is set to null. If there are other ships on the list then it will choose a new target.
	 */
	private void checkTargetShip() {
		System.out.println("check targ ship");
		if (target.getIsSunk()) {
			target = null;
			for (int j = 0; j < hitShips.size(); j++) {
				if (!hitShips.get(j).getIsSunk()) {
					target = hitShips.get(j);
					if (target.getTimesHit() > 1) {
						direction = target.getDirection();
						System.out.println("Direction =" + direction);
					}
					break;
				}
			}
		}
	}

	/** Method for AI to fire on a random location.
	 * Randomizes row and column. Checks to see if location has been played on already.
	 * If the location has been played on it will randomize another location until an empty
	 * location is found.
	 */
	private void playRandom() {
		randomRow();
		randomCol();
		while (playerBoard.locStatus(row, col) == 1 || playerBoard.locStatus(row, col) == 2 ) {
			randomRow();
			randomCol();
		} if ((playerBoard.locStatus(row, col)) == 3) {
			System.out.println("play Random = 3");
			newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
			target.minusShipHealth();
			target.toString(); // <---- *********TRACING FROM HERE************
		}
	}

	/** Randomizes the row to hit
	 */
	private void randomRow() {
		Random num = new Random();
		row = num.nextInt(10);
		this.row = row;
	}

	/** Randomizes the column to hit
	 */
	private void randomCol() {
		Random num = new Random();
		col = num.nextInt(10);
		this.col = col;
	}

	/**
	 * Method to add new ship object to list of ships the AI has hit
	 * @param newShip
	 */
	private void newShip(AIShipData newShip) {
		System.out.println("new ship");
		System.out.println(newShip.toString());
		hitShips.add(newShip);
		target = newShip;
		System.out.println(target.toString());
	}

	/** If the AI got a hit on the previous turn (ie. counter > 0), then AI plays strategically.
	 * On one hit (ie. counter == 1) the AI will try to determine the plane of the ship it hit.
	 * On two hits or more (ie. counter > 1) the AI will try to sink the ship.
	 */
	private void playStrategic() {
		if (target.getTimesHit() == 1) {
			System.out.println("play strat == 1");
			determineShipPlane();
		} else if (target.getTimesHit() > 1){
			System.out.println("play start > 1");
			shootToSink();
		}
	}

	/** Method that initiates after the the AI hit's a ship after the first time
	* The AI then plays randomly on locations adjacent to the initial hit location until
	* it gets another hit
	*/
	private void determineShipPlane() {
		boolean failure = true;
		if (playerBoard.locStatus(row, col) == 1) {
			initPosition();
			while (failure) {
				direction = randomDirection();
				moveDirection();
				checkStatus();
				if (status == 0) {
					failure = false;
				} else if (status == 1) {
					initPosition();
				} else if (status == 2) {
					initPosition();
				} else if (status == 3) {
					if (target.getHitShip() == playerBoard.getShipObject(row,col)) {
						target.setDirection(direction);
						System.out.println(target.getDirection());
						target.minusShipHealth();
						failure = false;
					} else {
						newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
						target.minusShipHealth();
						failure = false;
					}
				} else if (status == 4) {
					initPosition();
				}
			}
		} else if (playerBoard.locStatus(row, col) == 2) {
			while (failure) {
				direction = randomDirection();
				moveDirection();
				checkStatus();
				if (status == 0) {
					failure = false;
				} else if (status == 1) {
					initPosition();
				} else if (status == 2) {
					initPosition();
				} else if (status == 3) {
					if (target.getHitShip() == playerBoard.getShipObject(row,col)) {
						target.setDirection(direction);
						target.minusShipHealth();
						failure = false;
					} else {
						newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
						target.minusShipHealth();
						failure = false;
					}
				} else if (status == 4) {
					initPosition();
				}
			}
		}
	}

	/** Sets the position of the AI to the location it first hit a unique ship.
	 */
	private void initPosition(){
		row = target.getInitRow();
		col = target.getInitCol();
	}


	/** Method to randomize the direction of the AI's next move after hitting once.
	 * @return direction is the randomized direction.
	 */
	private String randomDirection() {
		Random rand = new Random();
		String directions[] = {"left", "right", "up", "down"};
		direction = directions[rand.nextInt(4)];
		return direction;
	}

	/** Method to move the AI one square towards the direction its facing.
	 */
	private void moveDirection(){
		if (direction == "left") {
			col -= 1;
		} else if (direction == "right") {
			col += 1;
		} else if (direction == "up") {
			row -= 1;
		} else if (direction == "down") {
			row += 1;
		}
	}

	/** After the AI determines the plane of the ship it hit (ie. after two hits) it will try to sink it
	 */
	private void shootToSink() {
		checkStatus();
		if (status == 1) {
			System.out.println("STS 1");
			initPosition();
			switchDirection();
			moveDirection();
			checkStatus();
			if(status == 3) {
				target.minusShipHealth();
			}
			return;
		} else if (status == 2) {
			System.out.println("STS 2");
			moveDirection();
			checkStatus();
			System.out.println("Status = " + status);
			if (status == 0) {

			} else if (status == 1) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();;
				if (status == 3) {
					System.out.println("minus health");
					target.minusShipHealth();
				}
				return;
			} else if (status == 2) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();
				if (status == 3) {
					System.out.println("minus health");
					target.minusShipHealth();
				}
				return;
			} else if (status == 3) {
				if (target.getHitShip() == playerBoard.getShipObject(row, col)) {
					System.out.println("STS 2-3 ==");
					System.out.println("minus health");
					target.minusShipHealth();
					return;
				} else {
					System.out.println("STS 2-3 new");
					newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
					System.out.println("minus health");
					target.minusShipHealth();
					return;
				}
			} else if (status == 4) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();
				if (status == 3) {
					System.out.println("minus health");
					target.minusShipHealth();
				}
				return;
			}
		}
	}

	/**
	* Method to check the status of the location of the AI.
	*/
	public void checkStatus() {
		status = playerBoard.locStatus(row,col);
	}

	/** Method to switch the direction the AI is facing
	*/
	private void switchDirection() {
		switch (direction) {
			case "left":
				direction = "right";
				break;
			case "right":
				direction = "left";
				break;
			case "down":
				direction = "up";
				break;
			case "up":
				direction = "down";
				break;
			default:
				break;
		}
	}
}
