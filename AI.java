import java.util.Random;

public class AI {
	
	private int difficulty = 0;
	private int row;
	private int col;
	
	public static void main(String[] args) {
		
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void nextMove() {
		if (difficulty == 0) {
			easyDifficulty();
		} else if (difficulty == 1) {
			hardDifficulty();
		}
	}
	
	public int randomRow() {
		Random row = new Random();
		row = nextInt(9)
		this.row = row;
	}
	
	public int randomCol() {
		Random col = new Random();
		col = nextInt(9);
		this.col = col;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void hardDifficulty() {
		
	}
	
	/** Method for easy AI
	*
	* @param checkHit 
	*/
	
	/** Default constructor for AI
	* Automatically sets difficulty to 0
	* I don't think this method is necessary.
	*/
	public AI() {
		playRandom();
	}
	
	/** Constructor for AI
	* Initiates the AI 
	* @param difficulty is a difficulty of 0 for easy or 1 for hard.
	*/
	public AI(int difficulty) {
		this.difficulty = difficulty;
		playRandom();
	}
	
	public void easyDifficulty(boolean checkHit) {
		boolean hit = checkHit;
		if (hit) {
			playStrategic();
		} else {
			playRandom();
		}
	}
	
	public void 
	
	public void playRandom() {
		randomRow();
		randomCol();
		while (canPlay(box[row][col]) == false) {
			randomRow();
			randomCol();
		} play(box[row][col]);
	}
	
	public boolean checkHit() {
		boolean hit = 
		return hit;
	}
	
	public void playStrategic() {
		boolean hit = checkHit;
		
		int left = col - 1;
		int top = row + 1;
		int right = col + 1;
		int bottom = row - 1;
		
		int positions[] = new int[]{left, top, right, bottom};
		
		if (can.Playbox{row][left]
		
		Random nextPlay = new Random();
		
		if (positions[nextPlay] == top) {
			canPlay
		}
		
		int count = 3;
		nextPlay = nextInt(count);
		
	}
	
	public int getCarrierStatus() {
		return carrier;
	}
	
	public int getBattleshipStatus() {
		return battleship;
	}
	
	public int getSubmarineStatus() {
		return submarine;
	}
	
	public int getCruiserStatus() {
		return cruiser;
	}
	
	public int getDestroyerStatus() {
		return destroyer;
	}
	
}