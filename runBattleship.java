import java.util.Random;
class runBattleship{
    public static void main(String[] args){
        runBattleship battle = new runBattleship();
        battle.runGame();
    }
	/**
	Function to call to setup player board with a non-random arrangement.
	*/
    public GameBoard setupGameBoard(){
        GameBoard board = new GameBoard();
        board.placeAllShips();
        board.printBoard();
        return board;

    }
	/** 
	Function to call to setup the enemy board with a random arrangement.
	*/
	public GameBoard setupEnemyBoard(){
		GameBoard board = new GameBoard();
		board.makeRandomBoard();
		board.printBoard();
		return board;
		
	}
	/**
	Main function to call to run Game.
	*/
	public void runGame(){
		GameBoard playerBoard = new GameBoard();
		GameBoard enemyBoard = new GameBoard();
		playerBoard.makeRandomBoard();// for testing, replace with playerBoard.placeAllShips(); 
		enemyBoard.makeRandomBoard();
		Random randr = new Random(); // replace once enemyAI implemented
		Random randc = new Random(); // replace once enemyAI implemented
		do{
			enemyBoard.enemyFire(randr.nextInt(10),randc.nextInt(10)); //Just for quick testing. Random firing from player.
			//enemyBoard.fire(); 
			
			playerBoard.enemyFire(randr.nextInt(10),randc.nextInt(10)); //replace with enemyAI fire functions
			enemyBoard.printEnemyBoard();
			playerBoard.printBoard();
		}while(enemyBoard.getNumberOfShipElements()>0 && playerBoard.getNumberOfShipElements()>0);
		if(playerBoard.getNumberOfShipElements()==0){
			System.out.println("You lose. Better luck next time.");
		}
		else{
			System.out.println("You win. Congratulations!");
		}
	}



}