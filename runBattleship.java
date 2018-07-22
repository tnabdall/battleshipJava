import java.util.Scanner;

/**
 * Class runs the battleship program and the game logic.
 */
class runBattleship{
    public static void main(String[] args){
        runBattleship battle = new runBattleship();
        battle.runGame(false);
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

	public void Introduction(){
		System.out.println("Welcome to Battleship.\nThe goal of the game is to sink all of the other oppenent's ships.\n" +
				"Each player will take turns firing at a coordinate on the other's board.\nOnce all of the opponent's ships are sunk, the game is over.\n" +
				"Have fun!");
	}

	/**
	Main function to call to run Game.
	@param randomPlayerFire For testing/demo purposes, does random fire as player's move.
	*/
	public void runGame(boolean randomPlayerFire){
		Introduction();
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
				enemyBoard.fire(); // User input fire
			}
			AI.runDifficulty(); //Get next move from AI.

			enemyBoard.printBoard(); //Show enemy board.
			playerBoard.fire(AI.getRow(),AI.getCol()); //Enemy fires at player.
			playerBoard.printBoard(); //Show player's board.

			if(randomPlayerFire == true){ //Function to cause code to sleep. Purely for demoing. https://stackoverflow.com/questions/24104313/how-to-delay-in-java (Ann Ragg)
				try{
						Thread.sleep(500);
				}
				catch(InterruptedException ex){
						Thread.currentThread().interrupt();
				}
			}

			
			
		}while(enemyBoard.getNumberOfShipElements()>0 && playerBoard.getNumberOfShipElements()>0); //When one player loses all ships, games over.
		if(playerBoard.getNumberOfShipElements()==0){
			System.out.println("You lose. Better luck next time.");
		}
		else{
			System.out.println("You win. Congratulations!");
		}
	}



}