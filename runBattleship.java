import java.util.Random;
class runBattleship{
    public static void main(String[] args){
        runBattleship battle = new runBattleship();
        battle.runGame();
    }
	/**
	Function to call to setup player board with a non-random arrangement.
	*/
    public PlayerBoard setupGameBoard(){
        PlayerBoard board = new PlayerBoard();
        board.placeAllShips();
        board.printBoard();
        return board;

    }
	/** 
	Function to call to setup the enemy board with a random arrangement.
	*/
	public EnemyBoard setupEnemyBoard(){
		EnemyBoard board = new EnemyBoard();
		board.makeRandomBoard();
		board.printBoard();
		return board;
		
	}
	/**
	Main function to call to run Game.
	*/
	public void runGame(){
		PlayerBoard playerBoard = setupGameBoard();
		EnemyBoard enemyBoard = setupEnemyBoard();
		//AI AI = new AI(playerBoard);
		Random randr = new Random(); // replace once enemyAI implemented
		Random randc = new Random(); // replace once enemyAI implemented
		do{
			enemyBoard.fire(); //Just for quick testing. Random firing from player.
			//enemyBoard.fire(); 
			//AI.runDifficulty();
			//System.out.println(AI.getRow() + " " + AI.getCol() + " " + AI.getCounter() + " " + AI.getDirection());
			playerBoard.fire(randr.nextInt(10),randc.nextInt(10)); //replace with enemyAI fire functions
			enemyBoard.printBoard();
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