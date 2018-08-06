/** The AI class for the game of Battleship.
 * The AI has 4 difficulties: normal, challenge, impossible, and random.
 * It requires a playerBoard object and a difficulty setting to initialize.
 */

import java.util.Random;
import java.util.ArrayList;

public class AIv2  {

	private static int turn; //The number of turns the AI has played.

	private int difficulty = 0; // The difficulty of the AI. 0 for normal, 1 for challenge, 2 for impossible, 3 for random

	private PlayerBoard playerBoard; // A copy of the player's board.

	private int row; // The row location of the AI
	private int col; // The column location of the AI
	private int status; // The status of the position of the AI
	private String direction; // The direction the AI is facing on the board.
	private Probability prob; // The object of the probability calculator.

	private AIShipData target; // The ship the AI is targetting.
	private ArrayList<AIShipData> hitShips = new ArrayList<AIShipData>(); // An array list of all the ships the AI has hit.

	/** Default constructor for AI
	*/
	public AIv2(){
		PlayerBoard playerBoard = new PlayerBoard();
		prob = new Probability(playerBoard);

	}

	/** Constructor for the AI
	 * @param playerBoard is the user's board
	 * @param difficulty is the difficulty setting of the AI
	 */

	public AIv2(PlayerBoard playerBoard, int difficulty) {
		prob = new Probability(playerBoard);
		this.playerBoard = playerBoard;
		this.difficulty = difficulty;
	}

	/** Constructor for AI
	 * @param playerBoard is the GameBoard object of the user's board
	 */

	public AIv2(PlayerBoard playerBoard) {
		prob = new Probability(playerBoard);
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
		turn++;
	}

	/** The AI for normal difficulty
	 * Initially checks to see if previous played location was a hit. If so it plays strategically,
	 * otherwise it plays randomly.
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

	/** The AI for challenge difficulty.
	 * The AI calculates the probability of each location on the board and will play on the
	 * spot with the highest calculated probability of there being a ship.
	 */
	private void challengeDifficulty() {
		if (target != null) {
			checkTargetShip();
		}
		if (target == null) {
			if (turn > 0) {
				int[] coords = prob.setCoordToPlay();
				row = coords[0];
				col = coords[1];
				if (playerBoard.locStatus(row, col) == 3) {
					newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
					target.minusShipHealth();
				}
			} else {
				playRandom();
			}
		} else if (target.getTimesHit() > 0) {
			playStrategic();
		}

	}

	/** AI for impossible difficulty.
	 *  The AI receives the location on the board with a ship on it.
	 *  The AI will hit a ship every turn.
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
			newShip(new AIShipData(playerBoard.getShipObject(row, col), row, col));
			target.minusShipHealth();
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
		hitShips.add(newShip);
		target = newShip;
	}

	/** If the AI got a hit on the previous turn (ie. counter > 0), then AI plays strategically.
	 * On one hit (ie. counter == 1) the AI will try to determine the plane of the ship it hit.
	 * On two hits or more (ie. counter > 1) the AI will try to sink the ship.
	 */
	private void playStrategic() {
		if (target.getTimesHit() == 1) {
			determineShipPlane();
		} else if (target.getTimesHit() > 1){
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
			if (col < 0) {
				col += 1;
			}
		} else if (direction == "right") {
			col += 1;
			if (col > 9) {
				col -= 1;
			}
		} else if (direction == "up") {
			row -= 1;
			if (row < 0) {
				row += 1;
			}
		} else if (direction == "down") {
			row += 1;
			if (row > 9) {
				row -= 1;
			}
		}
	}

	/** After the AI determines the plane of the ship it hit (ie. after two hits) it will try to sink it
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
				checkStatus();;
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

	public static int getTurn() {
		return turn;
	}
}
