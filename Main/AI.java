package Main;

import java.util.Random;
import java.util.ArrayList;

/** The Main.AI class for the game of Battleship.
 * The Main.AI has 4 difficulties: normal, challenge, impossible, and random.
 * It requires a playerBoard object and a difficulty setting to initialize.
 * To use: Use runDifficulty() with desired difficulty setting to set AI on a
 * playable position on the board every turn. getRow() and getCol() to return AI's position
 * and use as paramaters for the fire(row, col) method in PlayerBoard.
 */
public class AI {

	private int turn; //The number of turns the AI has played.

	private int difficulty = 0; // The difficulty of the Main.AI. 0 for normal, 1 for challenge, 2 for impossible, 3 for random

	private PlayerBoard playerBoard; // A copy of the player's board.
	private int bRows = GameBoard.getNUMROWS(); // The number of rows on the board.
	private int bCols = GameBoard.getNUMCOLS(); // The number of columns on the board.

	private int row; // The row location of the Main.AI
	private int col; // The column location of the Main.AI
	private int status; // The status of the position of the Main.AI
	private String direction; // The direction the Main.AI is facing on the board.
	private Probability prob; // The object of the probability calculator.

	private AIShipData target; // The ship the Main.AI is targeting.
	private ArrayList<AIShipData> hitShips = new ArrayList<AIShipData>(); // An array list of all the ships the Main.AI has hit.

	/** Default constructor for Main.AI
	*/
	public AI(){
		PlayerBoard playerBoard = new PlayerBoard();
		prob = new Probability(playerBoard);
		// bRows = playerBoard.getNUMROWS();
		// bCols = playerBoard.getNUMCOLS();
	}

	/**
	 * Copy constructor
	 * @param copy Other version of AI.
	 */
	public AI(AI copy) {
	    row = copy.row;
	    col = copy.col;
	    status = copy.status;
	    turn = copy.turn;
	    direction = copy.direction;
	    target = copy.target;
    }

	/** Constructor for the Main.AI
	 * @param playerBoard is the user's board
	 * @param difficulty is the difficulty setting of the Main.AI
	 */

	public AI(PlayerBoard playerBoard, int difficulty) {
		prob = new Probability(playerBoard);
		this.playerBoard = playerBoard;
		if (difficulty >= 0 && difficulty <= 3) {
			this.difficulty = difficulty;
		} else {
			this.difficulty = 0;
		}
		// bRows = playerBoard.getNumOfRows();
		// bCols = playerBoard.getNumOfCols();
	}

	/** Constructor for Main.AI
	 * @param playerBoard is the Main.GameBoard object of the user's board
	 */

	public AI(PlayerBoard playerBoard) {
		prob = new Probability(playerBoard);
		this.playerBoard = playerBoard;
	}

	/** Setter method for difficulty
	 * @param difficulty is the difficulty the Main.AI will be set to.
	 */
	public void setDifficulty(int difficulty) {
		if (difficulty >= 0 && difficulty <= 3) {
			this.difficulty = difficulty;
		} else {
			this.difficulty = 0;
		}
	}

	/** Getter method for row
	 * @return The row to fire on
	*/
	public int getRow() {
		AI copy = new AI(this);
	    return copy.row;
	}

	/** Getter method for column
	 * @return The column to fire on
	*/
	public int getCol() {
		AI copy = new AI(this);
	    return copy.col;
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
		turn++;
	}

	/** The Main.AI for normal difficulty
	 * First checks to see if the AI has a ship targetted. If it does then it checks to see if it is
	 * sunk. If no ship is targetted then the AI plays randomly. If the targetted ship is not sunk
	 * then it plays strategically.
	 */
	private void normalDifficulty() {
		if (target != null) {
			checkTargetShip();
		}
		if (target == null) {
			playRandom();
		} else if (target.getTimesHit() > 0){
			playStrategic();
		}
	}

	/** The Main.AI for challenge difficulty.
	 * The Main.AI calculates the probability of each location on the board and will play on the
	 * spot with the highest calculated probability of there being a ship. Otherwise it runs
	 * simliarly to normalDifficulty().
	 */
	private void challengeDifficulty() {
		if (target != null) {
			checkTargetShip();
		}
		if (target == null) {
			int[] coords = prob.setCoordToPlay();
			row = coords[0];
			col = coords[1];
			if (playerBoard.locStatus(row, col) == 3) {
				newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
				target.minusShipHealth();
			}
		} else if (target.getTimesHit() > 0) {
			playStrategic();
		}

	}

	/** Main.AI for impossible difficulty.
	 *  The Main.AI receives the location on the board with a ship on it.
	 *  The Main.AI will hit a ship every turn.
	 */
	private void impossibleDifficulty() {
		int[] coords = playerBoard.getShipLoc();
		row = coords[0];
		col = coords[1];
	}

	/** Main.AI for random difficulty.
	 * Randomly plays locations that hasn't been fired at
	 */
	private void randomDifficulty() {
		playRandom();
	}

	/**
	 * Method to check on the status of the Main.AI's target ship.
	 * If the ship is sunk then AI checks the hitShips list to see if there are any ships that
	 * it has hit but have not been sunk yet. If there ius then the AI will target one of those ships,
	 * otherwise it will target null.
	 */
	private void checkTargetShip() {
		if (target.getIsSunk()) {
			target = null;
			for (int j = 0; j < hitShips.size(); j++) {
				if (!hitShips.get(j).getIsSunk()) {
					target = hitShips.get(j);
					if (target.getTimesHit() > 1) {
						direction = target.getDirection();
					}
					break;
				}
			}
		}
	}

	/** Method for Main.AI to fire on a random location.
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
			newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
			target.minusShipHealth();
		}
	}

	/** Randomizes the row to hit
	 */
	private void randomRow() {
		Random num = new Random();
		row = num.nextInt(bRows);

	}

	/** Randomizes the column to hit
	 */
	private void randomCol() {
		Random num = new Random();
		col = num.nextInt(bCols);

	}

	/**
	 * Method to add new ship object to list of ships the Main.AI has hit
	 * @param newShip AIShipData object containing the ship that was hit.
	 */
	private void newShip(AIShipData newShip) {
		hitShips.add(newShip);
		target = newShip;
	}

	/** If the Main.AI got a hit on a ship the previous turn then Main.AI plays strategically.
	 * On one hit (ie. target.getTimesHit() == 1) the Main.AI will try to determine the plane of the ship it hit.
	 * On two hits or more (ie. target.getTimesHit() > 1) the Main.AI will try to sink the targetted ship.
	 */
	private void playStrategic() {
		if (target.getTimesHit() == 1) {
			determineShipPlane();
		} else if (target.getTimesHit() > 1){
			shootToSink();
		}
	}

	/** Method that initiates after the the Main.AI hit's a ship after the first time
	* The Main.AI then plays randomly on locations adjacent to the initial hit location until
	* it gets another hit. The direction the AI was facing when it got the second hit is set into AIShipData.
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

	/** Sets the position of the Main.AI to the location it first hit a unique ship.
	 */
	private void initPosition(){
		row = target.getInitRow();
		col = target.getInitCol();
	}


	/** Method to randomize the direction of the Main.AI's next move after hitting once.
	 * @return direction is the randomized direction.
	 */
	private String randomDirection() {
		Random rand = new Random();
		String directions[] = {"left", "right", "up", "down"};
		direction = directions[rand.nextInt(4)];
		return direction;
	}

	/** Method to move the Main.AI one square towards the direction its facing.
	 */
	private void moveDirection(){
		if (direction == "left") {
			col -= 1;
			if (col < 0) {
				col += 1;
			}
		} else if (direction == "right") {
			col += 1;
			if (col > bCols - 1) {
				col -= 1;
			}
		} else if (direction == "up") {
			row -= 1;
			if (row < 0) {
				row += 1;
			}
		} else if (direction == "down") {
			row += 1;
			if (row > bRows - 1) {
				row -= 1;
			}
		}
	}

	/** After the Main.AI determines the plane of the ship it hit (ie. after two hits) it will try to sink it
	 * by playing on locations in the direction of the targetted ship. If it misses and the targetted ship is
	 * still not sunk it will go back to the location it first hit the ship then switch to the opposite direction.
	 */
	private void shootToSink() {
		checkStatus();
		if (status == 1) {
			initPosition();
			switchDirection();
			moveDirection();
			checkStatus();
			if(status == 3) {
				target.minusShipHealth();
			}
			return;
		} else if (status == 2) {
			moveDirection();
			checkStatus();
			if (status == 0) {

			} else if (status == 1) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();
				if (status == 3) {
					target.minusShipHealth();
				}
				return;
			} else if (status == 2) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();
				if (status == 3) {
					target.minusShipHealth();
				}
				return;
			} else if (status == 3) {
				if (target.getHitShip() == playerBoard.getShipObject(row, col)) {
					target.minusShipHealth();
					return;
				} else {
					newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
					target.minusShipHealth();
					return;
				}
			} else if (status == 4) {
				initPosition();
				switchDirection();
				moveDirection();
				checkStatus();
				if (status == 3) {
					target.minusShipHealth();
				}
				return;
			}
		}
	}

	/**
	* Method to check the status of the board location of the Main.AI.
	*/
	private void checkStatus() {
		status = playerBoard.locStatus(row,col);
	}

	/** Method to switch the direction the Main.AI is facing
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

    /** Getter method for the number of turns the AI has played.
     * @return the number of turns the AI has played.
     */
	public int getTurn() {
		AI copy = new AI(this);
		return turn;
	}
}
