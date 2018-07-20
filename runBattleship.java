import java.util.Scanner;
class runBattleship{
    public static void main(String[] args){
        runBattleship battle = new runBattleship();
        battle.runGame(true);
    }
	/**
	Function to call to setup player board with a non-random arrangement.
	*/
    public PlayerBoard setupGameBoard(){
        PlayerBoard board = new PlayerBoard();
        board.placeAllShips();
        return board;

    }
	/** 
	Function to call to setup the enemy board with a random arrangement.
	*/
	public EnemyBoard setupEnemyBoard(){
		EnemyBoard board = new EnemyBoard();
		board.makeRandomBoard();
		return board;
		
	}
	/**
	Main function to call to run Game.
	@param randomPlayerFire For testing/demo purposes, does random fire as player's move.
	*/
	public void runGame(boolean randomPlayerFire){
		PlayerBoard playerBoard = setupGameBoard();
		EnemyBoard enemyBoard = setupEnemyBoard();
		AI AI = new AI(playerBoard);
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Which difficulty would you like to play on (0 for Normal, 3 for Random): ");
		AI.setDifficulty(keyboard.nextInt());
		do{
			if(randomPlayerFire == true){
				enemyBoard.randomFire(); //Just for quick testing. Random firing from player.
			}
			else{
				enemyBoard.fire();
			}
			//enemyBoard.fire(); 
			AI.runDifficulty();
			//System.out.println(AI.getRow() + " " + AI.getCol() + " " + AI.getCounter() + " " + AI.getDirection());
			enemyBoard.printBoard();
			playerBoard.fire(AI.getRow(),AI.getCol()); //replace with enemyAI fire functions
			playerBoard.printBoard();
			
			if(randomPlayerFire == true){
				try{
						Thread.sleep(1000);
				}
				catch(InterruptedException ex){
						Thread.currentThread().interrupt();
				}
			}
			
			
		}while(enemyBoard.getNumberOfShipElements()>0 && playerBoard.getNumberOfShipElements()>0);
		if(playerBoard.getNumberOfShipElements()==0){
			System.out.println("You lose. Better luck next time.");
		}
		else{
			System.out.println("You win. Congratulations!");
		}
	}



}